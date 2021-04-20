package com.ldnhat.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldnhat.model.TweetModel;
import com.ldnhat.model.UserModel;
import com.ldnhat.service.ITweetService;
import com.ldnhat.service.IUserService;
import com.ldnhat.service.impl.TweetService;
import com.ldnhat.service.impl.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/api/tweets"})
public class TweetAPI extends HttpServlet {

    private ITweetService tweetService;
    private IUserService userService;

    public TweetAPI() {
        tweetService = new TweetService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String message = request.getParameter("message");
        String userId = request.getParameter("userId");

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println(userId);

        if(message.equals("RESPONSE_TWEET_BY_USER_ID")){
            List<TweetModel> tweetModels = tweetService.tweet(Integer.parseInt(userId));
            UserModel userModel = userService.findOne((long) Integer.parseInt(userId));
            for (TweetModel tweetModel : tweetModels) {
                tweetModel.setUserModel(userModel);
            }
            objectMapper.writeValue(response.getOutputStream(), tweetModels);
            System.out.println("success");
        }
    }
}
