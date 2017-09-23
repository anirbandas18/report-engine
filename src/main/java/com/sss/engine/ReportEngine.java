package com.sss.engine;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReportEngine {
	
	public static void main(String[] args)  {
		SpringApplication app = new SpringApplication(ReportEngine.class);
		app.setWebEnvironment(false);
	    app.setBannerMode(Banner.Mode.OFF);
	    app.setLogStartupInfo(false);
	    ApplicationContext context = app.run(args);
	    int exitCode = SpringApplication.exit(context);
	    System.exit(exitCode);
	}

}
