package com.appsdeveloperblog.ws.client.photoappwebclients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import(com.appsdeveloperblog.ws.client.photoappwebclients.controllers.RestTemplateConfig.class)
public class PhotoAppWebClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppWebClientApplication.class, args);
	}
	

}
