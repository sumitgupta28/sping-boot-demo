package com.spring.boot.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;

import com.spring.boot.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Path("/v1/user")
@Api(value = "UserServices", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
public class UserServices {

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Gets all the users. Version 1 - (version in URL)", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "All the users in Json"),
			@ApiResponse(code = 404, message = "Hello resource not found") })
	public Response getAllUser() {
		return null;
	}

	@GET
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "get User details for provided User Name. Version 1 - (version in URL)", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User in Json"),
			@ApiResponse(code = 404, message = "Hello resource not found") })
	public Response getUser(@PathParam("userName") String userName) {
		return null;
	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(final User user) {
		return null;
	}

	@PUT
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(final User user) {
		return null;
	}

	@DELETE
	@Path("/delete/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(final String userName) {
		return null;
	}

}
