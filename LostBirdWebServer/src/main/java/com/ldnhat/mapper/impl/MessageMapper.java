package com.ldnhat.mapper.impl;

import com.ldnhat.mapper.RowMapper;
import com.ldnhat.model.MessageModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<MessageModel> {
    @Override
    public MessageModel mapRow(ResultSet rs) throws SQLException {

        MessageModel messageModel = new MessageModel();

        messageModel.setMessage(rs.getString("m.message"));
        messageModel.setMessageFrom(rs.getInt("m.message_from"));
        messageModel.setMessageTo(rs.getInt("m.message_to"));
        messageModel.setMessageStatus(rs.getInt("m.message_status"));

        return messageModel;
    }
}
