package com.ldnhat.service;

import com.ldnhat.DAO.impl.TweetDAO;
import com.ldnhat.model.TweetModel;

import java.util.List;

public class TweetService {

    private TweetDAO tweetDAO;
    public TweetService() {
        tweetDAO = new TweetDAO();
    }

    public List<TweetModel> tweet(int userId, int limit){
        return tweetDAO.tweet(userId, limit);
    }
}
