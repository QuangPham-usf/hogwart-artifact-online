package edu.usf.cs.hogwart_artifact_online;

import edu.usf.cs.hogwart_artifact_online.artifact.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
    public class HogwartArtifactOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(HogwartArtifactOnlineApplication.class, args);
	}
    @Bean
    public IdWorker idWorker() {
        // Matches his "0, 0" logic
        return new IdWorker(0, 0, 0);
    }

}
