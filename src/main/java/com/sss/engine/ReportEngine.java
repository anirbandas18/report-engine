package com.sss.engine;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class ReportEngine {
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ReportEngine.class);
	    app.setBannerMode(Banner.Mode.OFF);
	    app.setWebEnvironment(false);
	    app.setLogStartupInfo(false);
	    ApplicationContext context = app.run(args);
	    int exitCode = SpringApplication.exit(context);
	    System.exit(exitCode);
	}

}
