package com.formedix.rateexchanger.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/**
	 * Configure Swagger UI Client with Paths
	 * 		- /api/v1/.*
	 */
	@Bean
	public Docket productApi () {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(
				new ApiInfoBuilder().title("Formedix Rate Exchanger API")
					.description("Formedix Rate Exchanger API Reference for Developers")
					.version("1.0")
					.build()
			)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(regex("/api/v1/.*"))
			.build();
	}
	
}
