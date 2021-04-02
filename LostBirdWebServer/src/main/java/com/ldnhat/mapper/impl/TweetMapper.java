package com.ldnhat.mapper.impl;

import com.ldnhat.mapper.RowMapper;
import com.ldnhat.model.TweetModel;
import com.ldnhat.model.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TweetMapper implements RowMapper<TweetModel> {
    @Override
    public TweetModel mapRow(ResultSet rs) throws SQLException {
        TweetModel tweetModel = new TweetModel();
        tweetModel.setId(rs.getInt("t.tweet_id"));
        tweetModel.setTweetStatus("t.tweet_status");
        tweetModel.setTweetImage("t.tweet_image");

        UserModel userModel = new UserModel();
        userModel.setId(rs.getInt("t.user_id"));
        tweetModel.setUserModel(userModel);

        return tweetModel;
    }
}
