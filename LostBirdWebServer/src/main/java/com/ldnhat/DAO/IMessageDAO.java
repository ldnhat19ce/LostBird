package com.ldnhat.DAO;

import com.ldnhat.model.MessageModel;

import java.util.List;

public interface IMessageDAO extends GenericDAO<MessageModel> {

    List<MessageModel> findByUserSenderAndUserReceiver(int messageFrom, int messageTo);
}
