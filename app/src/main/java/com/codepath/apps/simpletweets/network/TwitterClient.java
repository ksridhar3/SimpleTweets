package com.codepath.apps.simpletweets.network;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	private static final String TAG = TwitterClient.class.getSimpleName();
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "MlAuViKMjQEiULEYg9onj4MlD";       // Change this
	public static final String REST_CONSUMER_SECRET = "kJvHF66k3opRqBNdkWG5V6g0PLfAsr2h6xQwXSe5A9yoOeCG9Z"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cprest"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeLine(long maxId,AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		Log.d(TAG,"getHomeTimeLine entered");
		if(maxId != 0) {
			params.put("max_id", maxId-1);
		}
		params.put("count",25);
		params.put("since_id",1);
		getClient().get(apiUrl, params, handler);
	}

	public void getMentionsTimeLine(long maxId,AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		Log.d(TAG,"getMentionsTimeLine entered maxID:"+maxId);
		if(maxId != 0) {
			params.put("max_id", maxId-1);
		}
		params.put("count",25);
		params.put("since_id",1);
		getClient().get(apiUrl, params, handler);
	}
	public void getUserTweetTimeLine(long maxId,String screenName,AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		Log.d(TAG, "getUserTweetTimeLine entered maxId:"+maxId);
		if(maxId != 0) {
			params.put("max_id", maxId-1);
		}
		params.put("count",25);
		params.put("since_id",1);
		params.put("screen_name",screenName);
		getClient().get(apiUrl, params, handler);
	}
	public void postTweet(String body, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", body);
		getClient().post(apiUrl, params, handler);
	}
	public void getLoggedInUserProfile(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		getClient().get(apiUrl, null, handler);
	}

	public void getSelectedUserProfile(String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("users/show.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", screenName);
		getClient().get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}