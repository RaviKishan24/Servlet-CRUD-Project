package com.reviewsummarizer.Servlet;

import com.reviewsummarizer.Service.UserService;
import com.reviewsummarizer.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.reviewsummarizer.Service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    UserService userService = new UserService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        if (id == null || name == null || email == null || mobile == null) {
            // you do not need to return because every method return type is void and also in servlet the tomcat
            // send the request and response object , request has the paraeneter and respnse is empty
            // so you have to eneter the alld data or error in response object and the returing prosess is handle by tomcat;
            response.setStatus(400);
            response.setContentType("application/json");
            response.getWriter().write("{\n" +
                    "    \"message\":\"Missing input field.\"\n" +
                    "}");

        }
        User newUser = new User(id, name, email, mobile);
        User createdUser = userService.createUser(newUser);
        // Return value
        response.setContentType("application/json");
        response.setStatus(201);
        response.getWriter().write("{\n" +
                "    \"message\":\"User added Successfuly\"\n" +
                "}");


    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            List<User> users = userService.getAllUseer();
            response.setContentType("application/json");
            response.setStatus(200);
          response.getWriter().write(usersToJsons(users));
          return;

        }
        Integer id = Integer.parseInt(idParam);
        User userResponse = userService.getUserById(Integer.valueOf(id));
        if (userResponse == null) {
            response.setStatus(404);
            response.setContentType("application/json");
            response.getWriter().write("{\n" +
                    "    \"message\":\"User not Found.\"\n" +
                    "}");
        }

        response.setContentType("application/json");
        response.setStatus(200);
        response.getWriter().write(userTojson(userResponse));


    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {

    }

    private String userTojson(User userResponse) {
        return "{\n" +
                "    \"id\":" + userResponse.getId() + ",\n" +
                "       \"name\":" + userResponse.getName() + ",\n" +
                "          \"email\":" + userResponse.getEmail() + ",\n" +
                "           \"mobile\":" + userResponse.getMobile() + "\n" +
                "          \n" +
                "    \n" +
                "}";
    }

    private String usersToJsons(List<User> users) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < users.size(); i++) {
            stringBuilder.append(userTojson(users.get(i)));
            if (i < users.size() - 1) {
                stringBuilder.append(",");
            }
        }

        stringBuilder.append("]");

        return stringBuilder.toString();

    }


}
