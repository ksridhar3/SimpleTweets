package com.codepath.apps.simpletweets.models;

import org.json.JSONObject;

/**
 * Created by k.sridhar on 12/11/2015.
 */
public class HomeTimeLineModel {
    private String screenName;
    private String userName;
    private String userProfileUrl;
    private String location;
    private String twitterText;
    private Integer reTweetCount;
    private Integer favCount;
    private String createdAt;

    public HomeTimeLineModel() {
        screenName = "";
        userName = "";
        userProfileUrl = "";
        location = "";
        twitterText = "";
        reTweetCount = 0;
        favCount=0;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getUserName() {
        return userName;
    }
    public String getUserProfileUrl() {
        return userProfileUrl;
    }
    public String getLocation() {
        return location;
    }

    public String getTwitterText() {
        return twitterText;
    }

    public Integer getReTweetCount() {
        return reTweetCount;
    }

    public Integer getFavCount() {
        return favCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTwitterText(String twitterText) {
        this.twitterText = twitterText;
    }

    public void setReTweetCount(Integer reTweetCount) {
        this.reTweetCount = reTweetCount;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setFavCount(Integer favCount) {
        this.favCount = favCount;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
