package com.lenovo.sap.api.config;

import com.lenovo.xframe.util.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerDocConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SAP API")
                .version("v1")
                .build();
    }

    @Bean
    public Docket defaultDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .paths(path -> path.startsWith("/sao/api"))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalRequestParameters(Lists.of(
                        new RequestParameterBuilder()
                                .in(ParameterType.HEADER)
                                .name("Accept-Language")
                                .required(false)
                                .build()
                ));
    }
}
