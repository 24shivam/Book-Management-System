package in.gfg.BookManagementSystem.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    //http://localhost:8080/swagger-ui/index.html#/

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Management API")
                        .version("1.0")
                        .description("API for managing books in the Book Management System"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("book-management")
                .pathsToMatch("/book/**","/student/**","/txn/**","/admin/**")
                .build();
    }
}
