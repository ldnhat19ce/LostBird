package com.ldnhat.service.impl;

import com.ldnhat.DAO.ITweetDAO;
import com.ldnhat.DAO.impl.TweetDAO;
import com.ldnhat.model.TweetModel;
import com.ldnhat.service.ITweetService;

import java.util.List;

public class TweetService implements ITweetService {

    private ITweetDAO tweetDAO;
    public TweetService() {
        tweetDAO = new TweetDAO();
    }

    @Override
    public List<TweetModel> tweet(int userId){
        return tweetDAO.tweet(userId);
    }
}
