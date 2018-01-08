package com.antya.xchange.stream.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		ApiInfo info = new ApiInfo("Antya Xchange Streaming API", "Provide description", "1.0",
							"www.antya.com",
							new Contact("Antya Project Developers", "www.antya.com", "developers@antya.com"),
							"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0",
							new ArrayList<VendorExtension>());

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(info).select().paths(PathSelectors.any()).build();
	}
}
