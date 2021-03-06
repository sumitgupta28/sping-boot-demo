package com.spring.boot.controller;

import java.util.List;

import javax.annotation.PostConstruct;
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
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import com.spring.boot.common.ErrorCodes;
import com.spring.boot.domain.ErrorMessage;
import com.spring.boot.domain.User;
import com.spring.boot.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Path("/v1/user")
@Api(value = "UserServices", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
public class UserServicesController {

	@Autowired
	private UserService userService;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns  All the available users.", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "All the users in Json"),
			@ApiResponse(code = 503, message = "Error While Procssing") })
	public Response getAllUser() {
		final List<User> entity = userService.getAllUsers();
		return Response.status(Status.OK).entity(entity).type(MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns User Details for provided User Name.", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found User by porvided UserName"),
			@ApiResponse(code = 400, message = "Not Found User by porvided UserName"),
			@ApiResponse(code = 503, message = "Error While Procssing") })
	public Response getUser(@PathParam("userName") String userName) {
		final List<User> entity = userService.getUserByUserName(userName);
		if (!CollectionUtils.isEmpty(entity)) {
			return Response.status(Status.OK).entity(entity).type(MediaType.APPLICATION_JSON).build();
		} else {
			return getUserNotFoundResponse();
		}
	}

	private Response getUserNotFoundResponse() {
		return Response.status(Status.BAD_REQUEST)
				.entity(new ErrorMessage(ErrorCodes.USER_NOT_FOUND_CODE, ErrorCodes.USER_NOT_FOUND_CODE))
				.type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Create user
	 * 
	 * @param user
	 *            {@link User}
	 * @return {@link Response}
	 */
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create user.", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Created Successfully!!"),
			@ApiResponse(code = 503, message = "Error While Procssing") })
	public Response createUser(final User user) {
		boolean userCreated = userService.createUser(user);
		return userCreated ? Response.status(Status.OK).build()
				: Response.status(Status.BAD_REQUEST).entity(
						new ErrorMessage(ErrorCodes.USER_ALREADY_PRESENT_CODE, ErrorCodes.USER_ALREADY_PRESENT_MSG))
						.build();
	}

	/**
	 * Gets all the users
	 * 
	 * @param user
	 * @return {@link Response}
	 */
	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Gets all the users.", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Updated Successfully!!"),
			@ApiResponse(code = 400, message = "Not Found User by porvided UserName"),
			@ApiResponse(code = 503, message = "Error While Procssing") })
	public Response updatebyUserName(final User user) {
		boolean userUpdated = userService.updateUser(user);
		return userUpdated ? Response.status(Status.OK).build()
				: Response.status(Status.BAD_REQUEST)
						.entity(new ErrorMessage(ErrorCodes.USER_NOT_FOUND_CODE, ErrorCodes.USER_NOT_FOUND_MSG))
						.build();
	}

	/**
	 * Delete the user for given userName
	 * 
	 * @param userName
	 * @return {@link Response}
	 */
	@DELETE
	@Path("/delete/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete the user for given userName.", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Deleted Successfully!!"),
			@ApiResponse(code = 400, message = "Not Found User by porvided UserName"),
			@ApiResponse(code = 503, message = "Error While Procssing") })
	public Response deleteByUserName(final String userName) {
		userService.deleteUserByUserName(userName);
		return Response.status(Status.OK).build();
	}

	/**
	 * Delete All the Users
	 * 
	 * @param userName
	 * @return {@link Response}
	 */
	@DELETE
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete All the Users.", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "All Users Deleted successfully!!"),
			@ApiResponse(code = 503, message = "Error While Procssing") })
	public Response deleteAll(final String userName) {
		userService.deleteAllUser();
		return Response.status(Status.OK).build();
	}

	/**
	 * Post Construct add users in db.
	 */
	@PostConstruct
	public void loadData() {
		User user = null;
		for (int i = 0; i < 2; i++) {
			user = new User();
			user.setUserName("userName" + i);
			user.setEmailAddress("userName" + i + "@mail.com");
			user.setFirstName("User First Name" + i);
			user.setLastName("User Last Name" + i);
			user.setPhoneNumber("111-222-222" + i);
			user.setAddress("Illions");
			userService.createUser(user);
		}

	}
}
