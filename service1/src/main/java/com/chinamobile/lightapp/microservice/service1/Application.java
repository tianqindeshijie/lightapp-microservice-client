package com.chinamobile.lightapp.microservice.service1;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@SpringCloudApplication
@RestController
public class Application {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
	@Autowired Tracer tracer;
	@RequestMapping("/start")
	public String start() {
		RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<>();
		LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
		for (Logger logger : context.getLoggerList()) {

		}
		log.info("Hello from service1.");
		return "Hello world service1";
	}

	public static void main(String... args) {
		new SpringApplication(Application.class).run(args);
	}


}
