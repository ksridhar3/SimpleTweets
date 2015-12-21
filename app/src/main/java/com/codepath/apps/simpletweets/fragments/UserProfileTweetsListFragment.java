package com.codepath.apps.simpletweets.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.adapters.TweetsAdapter;
import com.codepath.apps.simpletweets.models.TweetModel;
import com.codepath.apps.simpletweets.models.UserProfile;
import com.codepath.apps.simpletweets.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by k.sridhar on 12/19/2015.
 */
public class UserProfileTweetsListFragment extends TweetsListFragment {
    private ListView lvHomeTimeLine;
    private String TAG = UserProfileTweetsListFragment.class.getSimpleName();
    private Boolean loading = false;
    private long maxId = 0L;
    private TwitterClient twitterClient;
    private TweetsAdapter homeTimeLineAdapter;
    private String screenName;

    public static UserProfileTweetsListFragment newInstance(String screenName) {
        UserProfileTweetsListFragment userProfileTweetsListFragment = new UserProfileTweetsListFragment();
        Bundle args = new Bundle();

        args.putString("screenName",screenName);
        userProfileTweetsListFragment.setArguments(args);
        return userProfileTweetsListFragment;
    }

    @Override
    public void onScrollImpl(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d(TAG, "firstVisibleItem=" + firstVisibleItem + " visibleItemCount=" + visibleItemCount + " totalItemCount=" + totalItemCount);
        if (!loading) {
            if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                Log.d(TAG, " fetching again");
                loading = true;
                populateUserTweetTimeline();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        String userName;

        super.onCreate(savedInstanceState);
        twitterClient = TwitterApplication.getRestClient();
        homeTimeLineAdapter = super.getAdapter();
        screenName= getArguments().getString("screenName");
        UserProfile curLoggedInUser = TwitterApplication.getLoggedInUserProfile();
        if (!loading) {
            loading = true;
            if (curLoggedInUser.getScreenName() != screenName)
                populateUserTweetTimeline();
            else
                populateLoggedInUserProfile();

        }
    }

    private void populateLoggedInUserProfile() {

    }

    private void populateUserTweetTimeline() {

        twitterClient.getUserTweetTimeLine(maxId,screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG, "populateUserTweetTimeline onSucess response.length:"+response.length());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        TweetModel homeTimeLineModel = new TweetModel();
                        JSONObject jsonObject = response.optJSONObject(i);
                        if (jsonObject.has("user") && !jsonObject.isNull("user")) {
                            JSONObject userJSONObj = jsonObject.getJSONObject("user");

                            if (userJSONObj.has("screen_name") && !userJSONObj.isNull("screen_name")) {
                                homeTimeLineModel.setScreenName(userJSONObj.getString("screen_name"));
                                Log.d(TAG, "onSuccess screen name:" + homeTimeLineModel.getScreenName());
                            }
                            if (userJSONObj.has("name") && !userJSONObj.isNull("name")) {
                                homeTimeLineModel.setUserName(userJSONObj.getString("name"));
                            }
                            if (userJSONObj.has("profile_image_url") && !userJSONObj.isNull("profile_image_url")) {
                                homeTimeLineModel.setUserProfileUrl(userJSONObj.getString("profile_image_url"));
                            }

                            if (userJSONObj.has("favourites_count")) {
                                homeTimeLineModel.setFavCount(userJSONObj.getInt("favourites_count"));
                            }
                        }
                        if (jsonObject.has("text") && !jsonObject.isNull("text")) {
                            homeTimeLineModel.setTwitterText(jsonObject.getString("text"));
                        }
                        if (jsonObject.has("created_at") && !jsonObject.isNull("created_at")) {
                            homeTimeLineModel.setCreatedAt(jsonObject.getString("created_at"));
                        }

                        if (jsonObject.has("retweet_count")) {
                            homeTimeLineModel.setReTweetCount(jsonObject.getInt("retweet_count"));
                        }
                        if (jsonObject.has("id")) {
                            maxId = jsonObject.getLong("id");
                            homeTimeLineModel.setTweetId(maxId);
                        }

                        homeTimeLineAdapter.add(homeTimeLineModel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                homeTimeLineAdapter.notifyDataSetChanged();
                loading = false;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "UserProfileTweetsListFragment onFailure received");
                loading = false;
            }
        });
    }
}
