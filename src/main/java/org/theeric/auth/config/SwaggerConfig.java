package org.theeric.auth.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.collect.ImmutableList;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2) //
                .apiInfo(apiInfo()) //
                .securitySchemes(securitySchemes()) //
                .useDefaultResponseMessages(false) //
                .select() //
                .apis(RequestHandlerSelectors.any()) //
                .paths(PathSelectors.any()) //
                .build(); //
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder() //
                .title("User Authentication REST API") //
                .description("") //
                .version("1.0.0") //
                .build();
    }

    private List<ApiKey> securitySchemes() {
        return ImmutableList.of( //
                new ApiKey("Bearer", "Authorization", "header"));
    }

}
