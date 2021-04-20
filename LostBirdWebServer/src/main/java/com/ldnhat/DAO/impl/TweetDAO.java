package com.ldnhat.DAO.impl;

import com.ldnhat.DAO.ITweetDAO;
import com.ldnhat.mapper.impl.TweetMapper;
import com.ldnhat.model.TweetModel;

import java.util.List;

public class TweetDAO extends AbstractDAO<TweetModel> implements ITweetDAO{
    @Override
    public List<TweetModel> tweet(int userId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tweets t ");
        sql.append("LEFT JOIN likes l ON t.tweet_id = l.tweet_id ");
        sql.append("WHERE t.user_id = ? ");
        sql.append("OR t.user_id IN (SELECT f.user_receive FROM follow f WHERE f.user_sender = ?) ");
        sql.append("ORDER BY t.retweet_count DESC");

        Object[] params = {userId, userId};
        return query(sql.toString(), new TweetMapper(), params);
    }
}
