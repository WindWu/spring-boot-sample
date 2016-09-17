package com.example.jersey.config;

import javax.annotation.PostConstruct;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.example.jersey.api.UserResource;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

/**
 * From:
 * http://stackoverflow.com/questions/37640863/springfox-swagger-no-api-docs-with-spring-boot-jersey-and-gardle
 * 
 * As of version 2.5.0 springfox only supports spring-mvc controllers. Jax-rs
 * implementations like jersey aren't supported.
 *
 * Fortunately io.swagger::swagger-jersey2-jaxrs::1.5.3 have the hooks needed to
 * implement support for jersey in 2.6+.
 *
 * some pointers I used to get this swagger config done and swagger-core,
 * springboot and jersey integrated:
 * http://stackoverflow.com/questions/37640863/springfox-swagger-no-api-docs-with-spring-boot-jersey-and-gardle
 * https://www.insaneprogramming.be/blog/2015/09/04/spring-jaxrs/
 * https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5#adding-the-dependencies-to-your-application
 *
 */
@Configuration
public class JerseyConfig extends ResourceConfig {
	
	@Value("${server.servlet-path:/}")
	private String apiPath;

	public JerseyConfig() {
		// Register endpoints, providers, ...
		this.registerEndpoints();
	}

	private void registerEndpoints() {
		this.register(UserResource.class);
		// Access through /<Jersey's servlet path>/application.wadl
		this.register(WadlResource.class);
	}

	@PostConstruct
	public void config() {
		// Register components where DI is needed
		this.configureSwaggerV1();
	}

	private void configureSwaggerV1() {
		// Available at localhost:port/swagger.json
		this.register(ApiListingResource.class);
		this.register(SwaggerSerializers.class);

		BeanConfig beanConfig = new BeanConfig();
		// Use Annotation in SwaggerConfig class
//		beanConfig.setTitle("Account Api");
//		beanConfig.setDescription("Account ralated Resource management service：\n"
//								+ "\t* User: CRUD\n"
//								+ "\t* Placeholder: CRUD\n");
//		beanConfig.setTermsOfServiceUrl("http://www.windwu.me/terms");
//		beanConfig.setContact("Wind Wu");
//		beanConfig.setVersion("1.0.0");
//		beanConfig.setLicense("Apache 2.0");
//		beanConfig.setHost("localhost");
//		beanConfig.setSchemes(new String[] { "http", "https" });
		beanConfig.setBasePath(this.apiPath);
		beanConfig.setPrettyPrint(true);
		beanConfig.setResourcePackage("com.example.jersey.api,com.example.jersey.model,com.example.jersey.config");
		beanConfig.setScan(true);
		}
}
