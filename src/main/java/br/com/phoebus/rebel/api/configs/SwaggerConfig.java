package br.com.phoebus.rebel.api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.Tag;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * Copyright 2019 Phoebus Tecnologia Ltda.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * <p>
	 * Method responsible for configuring the Swagger. This service can be acessed
	 * by the fowlling links:
	 * </p>
	 * <ul>
	 * <li>http://localhost:8080/swagger-ui.html</li>
	 * <li>http://localhost:8080/v2/api-docs</li>
	 * </ul>
	 * 
	 * @return The bean responsible for containing the settings needed to run the
	 *         Swagger
	 */
	@Bean
	public Docket api() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("Rebel Alliance API").description("Rest API of Rebel Alliance")
				.version("1.0").build();

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.phoebus.rebel.api")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo).tags(new Tag("betrayals", "Operations pertaining to betrayals in Rebel API."),
						new Tag("galaxies", "Operations pertaining to galaxies in Rebel API."),
						new Tag("locations", "Operations pertaining to locations in Rebel API."),
						new Tag("rebels", "Operations pertaining to rebels in Rebel API."));
	}

}
