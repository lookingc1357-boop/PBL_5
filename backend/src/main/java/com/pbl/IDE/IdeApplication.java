package com.pbl.IDE;

//import com.pbl.IDE.Services.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IdeApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(IdeApplication.class, args);

		// Test container lifecycle
//		ContainerService service = context.getBean(ContainerService.class);
//		service.createContainer("alpine:latest");
//		service.start();
//		System.out.println("Container started with ID: " + service.getContainerId());
	}
}
