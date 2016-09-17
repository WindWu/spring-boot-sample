package com.example.jersey.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.validation.Validation;
//import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.jersey.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/* TODO: 
 * 1) Adopt JSON API
 * 2) Validate
 */

@Component
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "User resource", produces = "application/json")
public class UserResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
	// private static Validator validator =
	// Validation.buildDefaultValidatorFactory().getValidator();

	public UserResource() {

	}

	@GET
	@Path("v1/users/{id}")
	@ApiOperation(value = "Gets an User resource by user id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User resource found", response = User.class),
			@ApiResponse(code = 404, message = "User resource not found") })
	public Response getUser(@ApiParam @PathParam("id") String id, @Context UriInfo uriInfo) {
		if (id.compareTo("1") == 0) {
			User user = new User();
			user.setFirstName("Wind");
			user.setLastName("Wu");
			user.setId("1");
			return Response.status(Response.Status.OK).entity(user).build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}


	@GET
	@Path("v1/users")
	@ApiOperation(value = "Get an User resource. Version 1 - (version in URL)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User resource found", response = User.class),
			@ApiResponse(code = 404, message = "User resource not found") })
	public Response getUsers(@Context UriInfo info) {
		Map<Object, Object> apiResponse = new HashMap<Object, Object>();
		Integer limit = 100;
		Integer offset = 0;

		LOGGER.info("getUsers()");
		try {
			LOGGER.info("Check params");
			if (info.getQueryParameters().getFirst("limit") != null) {
				limit = Integer.parseInt(info.getQueryParameters().getFirst("limit"));
			}
			if (info.getQueryParameters().getFirst("offset") != null) {
				offset = Integer.parseInt(info.getQueryParameters().getFirst("offset"));
			}
			User user = new User();
			user.setId("1");
			user.setFirstName("Wind");
			user.setLastName("Wu");
			List<User> users = new ArrayList<User>();
			users.add(user);
			users.add(user);
			apiResponse.put("offset", offset);
			apiResponse.put("limit", limit);
			apiResponse.put("total", users.size());
			apiResponse.put("users", users);
			return Response.ok(apiResponse).build();
		} catch (Exception e) {
			LOGGER.error("Error retrieving user", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
