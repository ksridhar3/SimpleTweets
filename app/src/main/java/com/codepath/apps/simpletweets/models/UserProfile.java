package com.codepath.apps.simpletweets.models;

/**
 * Created by k.sridhar on 12/13/2015.
 */
public class UserProfile {
    private String userName;
    private String screenName;
    private String userProfileUrl;
    private int followersCnt;
    private int followingCnt;
    private int tweetCnt;

    public UserProfile() {
        this.userName = "";
        this.screenName = "";
        this.userProfileUrl = "";
    }

    public String getScreenName() {
        return screenName;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public String getUserName() {
        return userName;
    }

    public int getFollowersCnt() { return followersCnt;}

    public int getFollowingCnt() { return followingCnt;}

    public int getTweetCnt() { return tweetCnt;}

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public void setFollowersCnt(int followersCnt) { this.followersCnt = followersCnt;}

    public void setFollowingCnt(int followingCnt) { this.followingCnt = followingCnt;}

    public void setTweetCnt(int tweetCnt) { this.tweetCnt = tweetCnt;}
}
