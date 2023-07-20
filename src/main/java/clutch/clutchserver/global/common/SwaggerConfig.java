package clutch.clutchserver.global.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenApi() {
        Server server = new Server().url("/api");

        return new OpenAPI()
                .info(getSwaggerInfo())
                .components(authSetting())
                .addServersItem(server);
    }

    private Info getSwaggerInfo() {
        License license = new License();
        license.setName("Clutch");

        return new Info()
                .title("\"Clutch API문서\"")
                .description("Clutch의 API 문서 입니다.")
                .version("v0.0.1")
                .license(license);
    }

    private Components authSetting() {
        return new Components()
                .addSecuritySchemes(
                        "access-token",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization"));
    }
}

