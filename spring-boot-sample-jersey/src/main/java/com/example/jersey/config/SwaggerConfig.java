package com.example.jersey.config;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@SwaggerDefinition(info = @Info(title = "Account API", version = "V1.0.0", 
					description = "Account ralated Resource management service：\n"
									+ "\t* User: CRUD\n"
									+ "\t* Placeholder: CRUD\n", 
					termsOfService = "http://www.windwu.me/terms", 
					contact = @Contact(name = "Wind Wu", email = "wind.wu@outlook.com", url = "http://www.windwu.me"), 
					license = @License(name = "Apache 2.0", url = "http://www.windwu.me/licenses")), 
					host = "localhost", 
					schemes = {SwaggerDefinition.Scheme.HTTPS, SwaggerDefinition.Scheme.HTTP }, 
					consumes = {"application/json" }, 
					produces = { "application/json" }, 
					tags = {@Tag(name = "user", description = "User"),
 })
public class SwaggerConfig {

}
