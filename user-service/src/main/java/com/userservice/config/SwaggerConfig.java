package com.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String AUTH_HEADER = "Authorization";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metaInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.userservice.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo metaInfo() {
        return new ApiInfoBuilder()
                .title("User Service API")
                .description("JWT + Redis-based User Management APIs")
                .version("1.0.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTH_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api/.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        return Collections.singletonList(new SecurityReference("JWT", new AuthorizationScope[]{scope}));
    }
}



//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .securityContexts(List.of(securityContext()))
//                .securitySchemes(List.of(apiKey()))
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.userservice"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
//        return List.of(new SecurityReference("JWT", new AuthorizationScope[]{scope}));
//    }
//}





//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .securityContexts(Collections.singletonList(securityContext())) // 👈 Add SecurityContext
//                .securitySchemes(Collections.singletonList(apiKey())) // 👈 Add API Key (Bearer Token)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.userservice.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("User Management Service API")
//                .description("User Registration, Login, Profile Management with JWT + Redis Session")
//                .version("1.0.0")
//                .build();
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
//    }
//
////    private SecurityContext securityContext() {
////        return SecurityContext.builder()
////                .securityReferences(defaultAuth())
////                .operationSelector(o -> o.requestMappingPattern().matches(DEFAULT_INCLUDE_PATTERN))
////                .build();
////    }
//    private SecurityContext securityContext() {
//      return SecurityContext.builder()
//              .securityReferences(defaultAuth())
//              .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)) // ✅ Correct for Springfox 2.9.2
//              .build();
//    }
//
////    private List<SecurityReference> defaultAuth() {
////        AuthorizationScope authorizationScope =
////                new AuthorizationScopeBuilder().scope("global").description("accessEverything").build();
////        return Collections.singletonList(
////                new SecurityReference("JWT", new AuthorizationScope[]{authorizationScope}));
////    }
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope =
//                new AuthorizationScope("global", "accessEverything");
//        return Collections.singletonList(
//                new SecurityReference("JWT", new AuthorizationScope[]{authorizationScope}));
//    }
//}


























//// --- ✅ Final SwaggerConfig.java ---
//package com.userservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.builders.*;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.Collections;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("User Management Service API")
//                .description("User management operations for sign-up, login, profile update, etc.")
//                .version("1.0")
//                .contact(new Contact("Dev Team", "https://github.com", "team@email.com"))
//                .build();
//    }
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .securitySchemes(Collections.singletonList(apiKey()))
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.userservice.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
//    }
//
////    @Bean
////    public Docket api() {
////        return new Docket(DocumentationType.SWAGGER_2)
////                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.userservice.controller"))
////                .paths(PathSelectors.any())
////                .build();
////    }
//}















//package com.userservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
////    @Bean
////    public Docket apiDocket() {
////        return new Docket(DocumentationType.SWAGGER_2)
////                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.userservice.controller"))
////                .paths(PathSelectors.any())
////                .build();
////    }
//
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.userservice.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//
////    @Bean
////    public Docket api() {
////        return new Docket(DocumentationType.SWAGGER_2)
////                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.userservice.controller"))
////                .paths(PathSelectors.any())
////                .build()
////                .apiInfo(new ApiInfoBuilder()
////                        .title("User Management Service API")
////                        .description("User management operations for sign-up, login, profile update, etc.")
////                        .version("1.0")
////                        .build());
////    }
//}
