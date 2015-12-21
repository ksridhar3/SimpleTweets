package com.codepath.apps.simpletweets;

import android.content.Context;

import com.codepath.apps.simpletweets.models.UserProfile;
import com.codepath.apps.simpletweets.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TwitterClient client = TwitterApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class TwitterApplication extends com.activeandroid.app.Application {
	private static Context context;
	private static UserProfile userProfile;

	@Override
	public void onCreate() {
		super.onCreate();
		TwitterApplication.context = this;
	}

	public static TwitterClient getRestClient() {
		return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, TwitterApplication.context);
	}

	public static void populateUserCredentials() {

		TwitterClient twitterClient = getRestClient();
		userProfile = new UserProfile();
		twitterClient.getLoggedInUserProfile(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				try {
					if (response.has("name") && !response.isNull("name")) {
						userProfile.setUserName(response.getString("name"));
					}
					if (response.has("screen_name") && !response.isNull("screen_name")) {
						userProfile.setScreenName(response.getString("screen_name"));
					}
					if (response.has("profile_image_url") && !response.isNull("profile_image_url")) {
						userProfile.setUserProfileUrl(response.getString("profile_image_url"));
					}
					if (response.has("followers_count")) {
						userProfile.setFollowersCnt(response.getInt("followers_count"));
					}
					if (response.has("friends_count")) {
						userProfile.setFollowingCnt(response.getInt("friends_count"));
					}
					if (response.has("statuses_count")) {
						userProfile.setTweetCnt(response.getInt("statuses_count"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});
	}

	public static UserProfile getLoggedInUserProfile() {
		return userProfile;
	}


}