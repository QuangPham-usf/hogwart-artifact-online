package edu.usf.cs.hogwart_artifact_online.system;

public class StatusCode {
    public static final int SUCCESS = 200; // Success
    public static final int FAILURE = 400; // General Failure
    public static final int UNAUTHORIZED = 401; // Authentication needed
    public static final int FORBIDDEN = 403; // Permission denied
    public static final int NOT_FOUND = 404; // Resource not found
    public static final int INTERNAL_SERVER_ERROR = 500; // Server error
}