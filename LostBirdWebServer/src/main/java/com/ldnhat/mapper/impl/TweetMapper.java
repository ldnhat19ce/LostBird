package com.ldnhat.mapper.impl;

import com.ldnhat.mapper.RowMapper;
import com.ldnhat.model.LikeModel;
import com.ldnhat.model.TweetModel;
import com.ldnhat.model.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TweetMapper implements RowMapper<TweetModel> {
    @Override
    public TweetModel mapRow(ResultSet rs) throws SQLException {
        TweetModel tweetModel = new TweetModel();
        tweetModel.setId(rs.getInt("t.tweet_id"));
        tweetModel.setTweetStatus(rs.getString("t.tweet_status"));
        tweetModel.setTweetImage(rs.getString("t.tweet_image"));
        tweetModel.setCreateDate(rs.getTimestamp("t.create_date"));

        LikeModel likeModel = new LikeModel();
        likeModel.setId(rs.getInt("l.like_id"));
        likeModel.setUserId(rs.getInt("l.user_id"));
        likeModel.setTweetId(rs.getInt("l.tweet_id"));
        tweetModel.setLikeModel(likeModel);

        return tweetModel;
    }
}
