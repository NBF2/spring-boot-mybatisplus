package com.cloud.spring.demo.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description:
 * @author: Administrator
 * @date: 2019/10/22
 * @modified by:
 * @modified date:
 * @problem no:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * swagger扫描包路径
     */
    private static final String SEAGGER_SCAN_BASE_PACKAGE = "com.cloud.spring.demo";

    /**
     * API版本
     */
    private static final String API_VERSION = "1.0";

    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SEAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建该API的基本信息（这些信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger API")
                .description("This is to show api description")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org.licenses/LICENSE-2.0.html")
                .version(API_VERSION)
                .build();
    }
}
