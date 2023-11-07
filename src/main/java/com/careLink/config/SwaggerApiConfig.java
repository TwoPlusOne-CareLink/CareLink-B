package com.careLink.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerApiConfig {

    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v1.0.0")
                .title("CareLink_API")
                .description("Restful Api");

        return new OpenAPI()
                .info(info);
    }
}