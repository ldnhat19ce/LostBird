package com.ldnhat.DAO.impl;

import com.ldnhat.DAO.IMessageDAO;
import com.ldnhat.mapper.impl.MessageMapper;
import com.ldnhat.model.MessageModel;

import java.util.List;

public class MessageDAO extends AbstractDAO<MessageModel> implements IMessageDAO {

    @Override
    public List<MessageModel> findByUserSenderAndUserReceiver(int messageFrom, int messageTo) {
        StringBuilder sql = new StringBuilder("SELECT * FROM message m ");
        sql.append("WHERE m.message_from = ? AND m.message_to = ? ");
        sql.append("AND m.message_status = 1 GROUP BY m.create_date DESC");

        Object[] params = {messageFrom, messageTo};
        return query(sql.toString(), new MessageMapper(), params);
    }
}
