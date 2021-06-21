/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * Provides the Swagger configuration for this application.
 * </p>
 *
 * <p>
 * When this application is running, you can serve up the swagger page by typing
 * the swagger url into a web browser (e.g. Google Chrome). The url will take the
 * form of http://<hostname>:<port>/swagger-ui.html#/. For example, if your are
 * running this application locally on your machine the url would look like
 * http://localhost:8091/swagger-ui.html#/.
 * </p>
 */
@Slf4j
@Configuration
@EnableSwagger2
class SwaggerConfiguration
{
    /**
     * @return The {@link Docket} object for swagger 2 support. This cannot be null. 
     */
    @Bean
    public Docket api()
    {
        log.info("Retrieving the springfox docket object for swagger 2.");

        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.any())
          .paths(PathSelectors.any())
          .build()
          .apiInfo(getApiInfo());
    }

    /**
     * @return The {@link ApiInfo} containing the general api details for this
     *         service. This cannot be null.
     */
    private ApiInfo getApiInfo()
    {
        final Contact contact = new Contact(
            "Justin Rackley",
            null,
            "justirack@gmail.com");

        return new ApiInfoBuilder()
            .title("Hospital Manager")
            .description("A REST API for managing hospital data.")
            .version(getApplicationVersion())
            .contact(contact)
            .build();
    }

    /**
     * @return The version of this application. This cannot be null.
     *         If there is an issue ascertaining the version from the package
     *         information, then "UNKNOWN" is returned.
     */
    private String getApplicationVersion()
    {
        try
        {
            // The following returns the ImplementationVersion tag value that is
            // essentially written to the /META-INF/MANIFEST.MF file contained within
            // the jar file encompassing this application. That value is mapped to the
            // {project.version} value configured within this project's pom.xml file.
            // This value will only ever resolve if this application is launched
            // using the jar file. If launched from an IDE or Maven operating directly
            // with the .class files, then this will not resolve.
            final String implementationVersion
                = getClass().getPackage().getImplementationVersion();

            final String version = implementationVersion == null
                ? UNKNOWN_VERSION : implementationVersion;

            log.info("Using version=\"{}\" for this application.", version);

            return version ; 
        }
        catch(final Exception e)
        {
            log.error("Unable to retieve this application's version from the package "
                + "information (Attempted to use the <ImplementationVersion> value. "
                + "Using version=\"{}\" instead.",
                UNKNOWN_VERSION,
                e);
        }

        return "UNKNOWN_VERSION";
    }

    // THe default version string to use for this application. This cannot be null.
    // This string is only used if, and only if, we cannot determine the actual
    // value to use.
    private static final String UNKNOWN_VERSION = "UNKNOWN";
}