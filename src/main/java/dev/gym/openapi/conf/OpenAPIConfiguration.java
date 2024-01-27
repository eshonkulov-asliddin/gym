package dev.gym.openapi.conf;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.gym.controller.util.RestApiConst.AUTHENTICATION_NAME;

@Configuration
@SecurityScheme(
        name = AUTHENTICATION_NAME,
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
@EnableConfigurationProperties(OpenAPIProperties.class)
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openAPI(OpenAPIProperties openAPIProperties) {
        return openAPIProperties;
    }
}
