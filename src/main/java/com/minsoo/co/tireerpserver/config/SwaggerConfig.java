package com.minsoo.co.tireerpserver.config;

import com.minsoo.co.tireerpserver.constant.ConstantValue;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
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

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name(ConstantValue.AUTHORIZATION_HEADER);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(securityRequirement))
                .info(info)
                .servers(servers);
    }
}
