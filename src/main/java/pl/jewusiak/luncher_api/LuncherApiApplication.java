package pl.jewusiak.luncher_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(info = @Info(title = "Luncher API", description = "API for Luncher project - mobile/web app. \nIf JWT access token expires - returns http 460.", contact = @Contact(name = "Grzegorz Jewusiak", url = "https://jewusiak.pl", email = "grzegorz@jewusiak.pl")))
public class LuncherApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuncherApiApplication.class, args);
    }

}
