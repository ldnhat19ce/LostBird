package com.ldnhat.controller.api;

import com.ldnhat.model.TweetModel;
import com.ldnhat.service.TweetService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/api/tweets"})
public class TweetAPI extends HttpServlet {

    private TweetService tweetService;

    public TweetAPI() {
        tweetService = new TweetService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<TweetModel> tweetModels = tweetService.tweet(1, 2);
    }
}
