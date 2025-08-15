package com.hexaware.HotPot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotPotBackendV2Application {

	public static void main(String[] args) {
		Logger  log =  LoggerFactory.getLogger(HotPotBackendV2Application.class);	
		
		log.info("main() started your application");
		
		SpringApplication.run(HotPotBackendV2Application.class, args);

	}

}
