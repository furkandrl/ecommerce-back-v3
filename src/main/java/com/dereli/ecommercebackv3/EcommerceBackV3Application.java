package com.dereli.ecommercebackv3;

import com.dereli.ecommercebackv3.helpers.CollaborativeFiltering;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class EcommerceBackV3Application implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBackV3Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CollaborativeFiltering collaborativeFiltering(){
        return new CollaborativeFiltering();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.dereli.ecommercebackv3"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void run(String... args) throws Exception {
        collaborativeFiltering().executeCollaborativeFiltering();
    }
}
