package appParser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AppParserAplication {

	public static void main(String[] args) {
		SpringApplication.run(AppParserAplication.class, args);
	}

}
