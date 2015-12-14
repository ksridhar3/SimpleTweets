package com.codepath.apps.simpletweets.activities;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.models.UserProfile;
import com.codepath.apps.simpletweets.network.TwitterClient;
import com.codepath.apps.simpletweets.adapters.HomeTimeLineAdapter;
import com.codepath.apps.simpletweets.models.HomeTimeLineModel;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeTimeLineActivity extends AppCompatActivity {

    private ArrayList<HomeTimeLineModel> homeTimeLineModelArrayList;
    private UserProfile userProfile;
    private ListView lvHomeTimeLine;
    private TwitterClient twitterClient;
    private HomeTimeLineAdapter homeTimeLineAdapter;
    private String TAG = HomeTimeLineActivity.class.getSimpleName();
    private Boolean loading = false;
    private Long maxId = 0L;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        // Display the layout xml for this activity and the action bar
        setContentView(R.layout.activity_home_time_line);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_white_twitter_bird);
        actionBar.setDisplayUseLogoEnabled(true);



        homeTimeLineModelArrayList = new ArrayList<>();
        userProfile = new UserProfile();
        twitterClient = new TwitterClient(this);
        homeTimeLineAdapter = new HomeTimeLineAdapter(this,homeTimeLineModelArrayList);
        lvHomeTimeLine = (ListView) findViewById(R.id.lv_home_time_line);
        lvHomeTimeLine.setAdapter(homeTimeLineAdapter);
        populateUserCredentials();
        lvHomeTimeLine.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d(TAG, "scrollState=" + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d(TAG, "firstVisibleItem=" + firstVisibleItem + " visibleItemCount=" + visibleItemCount + " totalItemCount=" + totalItemCount);
                if (!loading) {
                    if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                        Log.d(TAG, " fetching again");
                        loading = true;
                        populateHomeTimeLine();
                    }
                }
            }
        });

   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_time_line_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mi_compose:
                Intent i = new Intent(this,ComposeTweetActivity.class);

                i.putExtra("name",userProfile.getUserName());
                i.putExtra("screen_name",userProfile.getScreenName());
                i.putExtra("url",userProfile.getUserProfileUrl());
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateUserCredentials() {
        twitterClient.getUserCredentials(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if(response.has("name") && !response.isNull("name")) {
                        userProfile.setUserName(response.getString("name"));
                    }
                    if(response.has("screen_name") && !response.isNull("screen_name")) {
                        userProfile.setUserName(response.getString("name"));
                    }
                    if(response.has("profile_image_url") && !response.isNull("profile_image_url")) {
                        userProfile.setUserProfileUrl(response.getString("profile_image_url"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void populateHomeTimeLine() {

        twitterClient.getHomeTimeLine(maxId,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG,"populateHomeTimeLine onSucess");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        HomeTimeLineModel homeTimeLineModel = new HomeTimeLineModel();
                        JSONObject jsonObject = response.optJSONObject(i);
                        if(jsonObject.has("user") && !jsonObject.isNull("user")){
                            JSONObject userJSONObj = jsonObject.getJSONObject("user");

                            if(userJSONObj.has("screen_name") && !userJSONObj.isNull("screen_name")) {
                                homeTimeLineModel.setScreenName(userJSONObj.getString("screen_name"));
                            }
                            if(userJSONObj.has("name") && !userJSONObj.isNull("name")) {
                                homeTimeLineModel.setUserName(userJSONObj.getString("name"));
                            }
                            if(userJSONObj.has("profile_image_url") && !userJSONObj.isNull("profile_image_url")) {
                                homeTimeLineModel.setUserProfileUrl(userJSONObj.getString("profile_image_url"));
                            }

                            if(userJSONObj.has("favourites_count")) {
                                homeTimeLineModel.setFavCount(userJSONObj.getInt("favourites_count"));
                            }
                        }
                        if(jsonObject.has("text") && !jsonObject.isNull("text")){
                            homeTimeLineModel.setTwitterText(jsonObject.getString("text"));
                        }
                        if(jsonObject.has("created_at") && !jsonObject.isNull("created_at")) {
                            homeTimeLineModel.setCreatedAt(jsonObject.getString("created_at"));
                        }

                        if(jsonObject.has("retweet_count")) {
                            homeTimeLineModel.setReTweetCount(jsonObject.getInt("retweet_count"));
                        }
                        if(jsonObject.has("id")){
                            maxId = jsonObject.getLong("id");
                            homeTimeLineModel.setTweetId(maxId);
                        }
                        homeTimeLineModelArrayList.add(homeTimeLineModel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                homeTimeLineAdapter.notifyDataSetChanged();
                loading = false;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "onFailure received");
                loading = false;
            }
        });
    }


}