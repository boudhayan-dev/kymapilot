package com.sap.clm.sl.cias.kyma.KymaPilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class KymaPilotApplication {

	public static void main(String[] args) {
		SpringApplication.run(KymaPilotApplication.class, args);
	}

}
