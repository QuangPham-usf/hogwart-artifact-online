package edu.usf.cs.hogwart_artifact_online.artifact.util;

import java.util.Random;

/**
 * Twitter Snowflake ID Generator.
 * This class generates unique 64-bit IDs across a distributed system.
 */
public class IdWorker {

    private final long workerId;
    private final long datacenterId;
    private final long idepoch;

    private static final long workerIdBits = 5L;
    private static final long datacenterIdBits = 5L;
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private static final long sequenceBits = 12L;
    private static final long workerIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;
    private long sequence;
    private static final Random r = new Random();

    // Default Constructor: Uses a fixed epoch (starting time)
    public IdWorker() {
        this(1344322705519L);
    }

    // Constructor with custom epoch: Generates random worker and datacenter IDs
    public IdWorker(long idepoch) {
        this(r.nextInt((int) maxWorkerId), r.nextInt((int) maxDatacenterId), 0, idepoch);
    }

    // Recommended Constructor for Spring Bean: Use (1, 1, 0) for your project
    public IdWorker(long workerId, long datacenterId, long sequence) {
        this(workerId, datacenterId, sequence, 1344322705519L);
    }

    public IdWorker(long workerId, long datacenterId, long sequence, long idepoch) {
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException("workerId is illegal: " + workerId);
        }
        if (datacenterId < 0 || datacenterId > maxDatacenterId) {
            throw new IllegalArgumentException("datacenterId is illegal: " + datacenterId);
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
        this.idepoch = idepoch;
    }

    // This is the method your ArtifactService calls!
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;

        // The bitwise "magic" that builds the ID
        return ((timestamp - idepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "IdWorker{" +
                "workerId=" + workerId +
                ", datacenterId=" + datacenterId +
                ", idepoch=" + idepoch +
                ", lastTimestamp=" + lastTimestamp +
                ", sequence=" + sequence +
                '}';
    }
}