package com.ldnhat.service;

import com.ldnhat.model.TweetModel;

import java.util.List;

public interface ITweetService {

    List<TweetModel> tweet(int userId);
}
