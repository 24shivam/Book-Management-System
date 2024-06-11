package in.gfg.BookManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class BookManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManagementSystemApplication.class, args);
	}

/*
@Bean
public Docket productApi() {
	return new Docket(DocumentationType.SWAGGER_2).select()
			.apis(RequestHandlerSelectors.basePackage("com.doc.swagger")).build();
}*/
}
