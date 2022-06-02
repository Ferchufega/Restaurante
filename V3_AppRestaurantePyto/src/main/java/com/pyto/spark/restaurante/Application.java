package com.pyto.spark.restaurante;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.pyto.spark.restaurante.config.DatabaseConfig;
import com.pyto.spark.restaurante.service.RestauranteService;

@Configuration
@ComponentScan({"com.pyto.spark.restaurante"})
public class Application {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class, DatabaseConfig.class);
    	new MoviesApplication(ctx.getBean(RestauranteService.class));
        ctx.registerShutdownHook();
	}
	
}
