package com.dbp.patientservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Patient CRUD",
                version = "1.0.0",
                description = "CRUD del servicio Patient"
        )
)
public class OpenApiConfig {

}