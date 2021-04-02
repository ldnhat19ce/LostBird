package com.ldnhat.model;

public class TweetModel extends AbstractModel{

    private String tweetStatus;
    private UserModel userModel;
    private String tweetImage;
    private LikeModel likeModel;
    private RetweetModel retweetModel;

    public String getTweetStatus() {
        return tweetStatus;
    }

    public void setTweetStatus(String tweetStatus) {
        this.tweetStatus = tweetStatus;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getTweetImage() {
        return tweetImage;
    }

    public void setTweetImage(String tweetImage) {
        this.tweetImage = tweetImage;
    }

    public LikeModel getLikeModel() {
        return likeModel;
    }

    public void setLikeModel(LikeModel likeModel) {
        this.likeModel = likeModel;
    }

    public RetweetModel getRetweetModel() {
        return retweetModel;
    }

    public void setRetweetModel(RetweetModel retweetModel) {
        this.retweetModel = retweetModel;
    }
}
