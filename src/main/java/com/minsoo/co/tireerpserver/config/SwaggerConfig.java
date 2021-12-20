package com.minsoo.co.tireerpserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("tire-erp-server")
                .version("v1")
                .description("tire-erp-server");

        List<Server> servers = Arrays.asList(
                new Server().url("http://ec2-13-209-69-28.ap-northeast-2.compute.amazonaws.com:8080/").description("dev"),
                new Server().url("http://localhost:8080/").description("local"));

        SecurityScheme auth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        SecurityRequirement securityItem = new SecurityRequirement().addList("Jwt Auth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearer", auth))
                .addSecurityItem(securityItem)
                .info(info)
                .servers(servers);
    }
}
