package com.drago.jerseyexample.resource;

import com.drago.jerseyexample.assembler.UserAssembler;
import com.drago.jerseyexample.model.User;
import com.drago.jerseyexample.payload.UserPayload;
import com.drago.jerseyexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: juanjosequintanamelian
 * Date: 18/10/13
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
@Path("/users")
@Component
public class UserResource {

    private UserService userService;

    private UriInfo uriInfo;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserPayload userPayload){

        Long id = userService.create(UserAssembler.toEntity(userPayload));
        return Response.created(uriInfo.getAbsolutePathBuilder().path("{id}").build(id)).build();
    }


    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUser(@PathParam("id") Long id){

        User user = userService.get(id);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user.toPayload()).build();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAll(){
        List<User> users = userService.getAll();
        List<UserPayload> userPayloads = new ArrayList<UserPayload>();
        for(User user : users){
             userPayloads.add(user.toPayload());
        }
        GenericEntity<List<UserPayload>> entity = new GenericEntity<List<UserPayload>>(userPayloads){};
        return Response.ok(entity).build();
    }


    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, UserPayload userPayload){
        User user = userService.get(id);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user = UserAssembler.toEntity(userPayload);
        user.setId(id);
        userService.update(user);
        return Response.notModified().build();
    }


    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id){
        User user = userService.get(id);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userService.delete(id);
        return Response.ok().build();
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
