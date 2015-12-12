package com.codepath.apps.simpletweets.activities;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterClient;
import com.codepath.apps.simpletweets.adapters.HomeTimeLineAdapter;
import com.codepath.apps.simpletweets.models.HomeTimeLineModel;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeTimeLineActivity extends AppCompatActivity {

    private ArrayList<HomeTimeLineModel> homeTimeLineModelArrayList;
    private ListView lvHomeTimeLine;
    private TwitterClient twitterClient;
    private HomeTimeLineAdapter homeTimeLineAdapter;
    private String TAG = HomeTimeLineActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_time_line);
        homeTimeLineModelArrayList = new ArrayList<>();
        lvHomeTimeLine = (ListView) findViewById(R.id.lv_home_time_line);
        homeTimeLineAdapter = new HomeTimeLineAdapter(this,homeTimeLineModelArrayList);
        lvHomeTimeLine.setAdapter(homeTimeLineAdapter);
        twitterClient = new TwitterClient(this);
        populateHomeTimeLine();

    }

    private void populateHomeTimeLine() {
        twitterClient.getHomeTimeLine(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        HomeTimeLineModel homeTimeLineModel = new HomeTimeLineModel();
                        JSONObject jsonObject = response.optJSONObject(i);
                        if(jsonObject.has("user") && !jsonObject.isNull("user")){
                            JSONObject userJSONObj = jsonObject.getJSONObject("user");
                            homeTimeLineModel.setScreenName(userJSONObj.getString("screen_name"));
                            homeTimeLineModel.setUserName(userJSONObj.getString("name"));
                            if(userJSONObj.has("profile_image_url") && !userJSONObj.isNull("profile_image_url")) {
                                homeTimeLineModel.setUserProfileUrl(userJSONObj.getString("profile_image_url"));
                            }
                        }
                        if(jsonObject.has("text") && !jsonObject.isNull("text")){
                            homeTimeLineModel.setTwitterText(jsonObject.getString("text"));
                        }
                        if(jsonObject.has("favourites_count") && !jsonObject.isNull("favourites_count")) {
                            homeTimeLineModel.setFavCount(jsonObject.getInt("favourites_count"));
                        }
                        if(jsonObject.has("retweet_count") && !jsonObject.isNull("retweet_count")) {
                            homeTimeLineModel.setReTweetCount(jsonObject.getInt("retweet_count"));
                        }
                        homeTimeLineModelArrayList.add(i,homeTimeLineModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                homeTimeLineAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG,"onFailure received");
            }
        });
    }

}