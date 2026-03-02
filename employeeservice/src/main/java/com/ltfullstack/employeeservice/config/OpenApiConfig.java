package com.ltfullstack.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee API Specification - LT Fullstack",
                description = "",
                version = "1.0",
                contact = @Contact(
                        name = "Sean Nguyen",
                        email = "tnguyenngocqa@gmail.com",
                        url = "https://github.com/tnguyenngocqa/event-sourcing-java17-gradle-spring-3-3-1"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://github.com/tnguyenngocqa/event-sourcing-java17-gradle-spring-3-3-1"
                ),
                termsOfService = "https://github.com/tnguyenngocqa/event-sourcing-java17-gradle-spring-3-3-1"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:9002"
                ),
                @Server(
                        description = "Dev ENV",
                        url = "https://employee-service.dev.com"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "https://employee-service.prod.com"
                )
        }
)
public class OpenApiConfig {
}
