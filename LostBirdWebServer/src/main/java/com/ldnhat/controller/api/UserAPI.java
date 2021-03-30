package com.ldnhat.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldnhat.Utils.HttpUtil;
import com.ldnhat.model.UserModel;
import com.ldnhat.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet(urlPatterns = {"/api/users"})
public class UserAPI  extends HttpServlet {

    private UserService userService;

    public UserAPI() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<UserModel> users = userService.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.println(userJson);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        UserModel userModel = HttpUtil.of(request.getReader()).toModel(UserModel.class);
        if(userModel.getMessage().equals("REGISTER")){
            if(userService.findByUsernameAndEmail(userModel.getUsername(),
                    userModel.getEmail()) == null){

                userModel = userService.save(userModel);
                userModel.setMessage("REGISTER_ACCOUNT_SUCCESS");
                objectMapper.writeValue(response.getOutputStream(), userModel);
            }else{
                userModel.setMessage("REGISTER_NAME_OR_EMAIL_EXIS");
                objectMapper.writeValue(response.getOutputStream(), userModel);
            }
        }

    }

}
