package com.wangchuncheng.service;

import com.wangchuncheng.dao.UserDao;
import com.wangchuncheng.entity.User;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * RESTful CRUD using JAX-SR-API(jersey implements)
 */
@Path("/UserService")
public class UserService {
    UserDao userDao = new UserDao();
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String FAILURE_RESULT = "<result>failure</result>";

    @GET
    @Path("/users")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> getUsers() {
        return userDao.getAllUsers();
    }

    @GET
    @Path("/users/{userid}")
    @Produces({MediaType.APPLICATION_XML})
    public User getUser(@PathParam("userid") int userid) {
        return userDao.getUser(userid);
    }

    @PUT
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createUser(@FormParam("id") int id,
                             @FormParam("name") String name,
                             @FormParam("profession") String profession,
                             @Context HttpServletResponse servletResponse) {
        User user = new User(id, name, profession);
        int result = userDao.addUser(user);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateUser(@FormParam("id") int id,
                             @FormParam("name") String name,
                             @FormParam("profession") String profession,
                             @Context HttpServletResponse servletResponse) {
        User user = new User(id, name, profession);
        int result = userDao.updateUser(user);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @DELETE
    @Path("/users/{userid}")
    @Produces(MediaType.APPLICATION_XML)
    public String deleteUser(@PathParam("userid") int userid) {
        int result = userDao.deleteUser(userid);
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    @OPTIONS
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    public String getSupportedOperations() {
        return "<operations>GET, PUT, POST, DELETE</operations>";
    }
}
