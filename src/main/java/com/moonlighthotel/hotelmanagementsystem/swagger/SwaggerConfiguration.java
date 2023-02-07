package com.moonlighthotel.hotelmanagementsystem.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(name = "bearerAuth", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class SwaggerConfiguration {

    public static final String ROOM_TAG = "Rooms";
    public static final String USER_TAG = "Users";
    public static final String TRANSFER_TAG = "Transfers";

    @Bean
    public OpenAPI getOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API")
                        .description("API Documentation")
                        .version("1.0.0"))
                .tags(List.of(
                        new Tag().name(ROOM_TAG).description("Actions with Rooms"),
                        new Tag().name(USER_TAG).description("Actions with Users")));
    }
}
