package com.ldnhat.model;

public class RetweetModel extends AbstractModel {

    private String retweetStatus;
    private UserModel userModel;
    private TweetModel tweetModel;
    private LikeModel likeModel;

    public String getRetweetStatus() {
        return retweetStatus;
    }

    public void setRetweetStatus(String retweetStatus) {
        this.retweetStatus = retweetStatus;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public TweetModel getTweetModel() {
        return tweetModel;
    }

    public void setTweetModel(TweetModel tweetModel) {
        this.tweetModel = tweetModel;
    }

    public LikeModel getLikeModel() {
        return likeModel;
    }

    public void setLikeModel(LikeModel likeModel) {
        this.likeModel = likeModel;
    }
}
