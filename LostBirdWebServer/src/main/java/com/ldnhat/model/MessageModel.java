package com.ldnhat.model;

public class MessageModel extends AbstractModel {

    private String message;
    private int messageFrom;
    private int messageTo;
    private int messageStatus;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(int messageFrom) {
        this.messageFrom = messageFrom;
    }

    public int getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(int messageTo) {
        this.messageTo = messageTo;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }
}
