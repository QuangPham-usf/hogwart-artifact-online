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
/*
What we write in postman is URL + method:
    URL = protocol + host +   port + path
    Url = http://   localhost:8080  /api/v1/artifacts

Api endpoint = Path + HTTP method
Ex: GET /api/v1/artifacts

HTTP Request = all the above and others like headers, body, query params, etc.

How works:
Postman act as client, we write url + choosing Method then it create and send HTTP request to the server, the server will process the request and return a HTTP response,

 *Two POST endpoints MUST have different paths!
 */