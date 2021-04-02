package com.ldnhat.DAO.impl;

import com.ldnhat.DAO.ITweetDAO;
import com.ldnhat.mapper.impl.TweetMapper;
import com.ldnhat.model.TweetModel;
import com.ldnhat.model.UserModel;

import java.util.List;

public class TweetDAO extends AbstractDAO<TweetModel> implements ITweetDAO{
    @Override
    public List<TweetModel> tweet(int userId, int limit) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tweets t ");
        sql.append("WHERE t.user_id = ? AND t.status = 1 ");
        sql.append("OR t.user_id IN (SELECT f.user_receive FROM follow WHERE user_sender = ?) ");
        sql.append("ORDER BY retweet_count DESC limit ?");

        Object[] params = {userId, userId, limit};
        return query(sql.toString(), new TweetMapper(), params);
    }
}
