package com.codepath.apps.simpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.fragments.UserProfileTweetsListFragment;
import com.codepath.apps.simpletweets.models.UserProfile;
import com.codepath.apps.simpletweets.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class ProfileViewActivity extends AppCompatActivity {

    private String screenName;
    private TwitterClient twitterClient;
    private UserProfile userProfile;
    private static final String TAG = ProfileViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_view);
        twitterClient = TwitterApplication.getRestClient();

        Intent intent =getIntent();
        screenName = intent.getStringExtra("screen_name");

        userProfile = TwitterApplication.getLoggedInUserProfile();

        if (userProfile.getScreenName() == screenName)
            populateProfileView(userProfile);
        else {

            UserProfile newUserProfile = new UserProfile();
            userProfile = newUserProfile;

            twitterClient.getSelectedUserProfile(screenName, new JsonHttpResponseHandler() {

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
                        populateProfileView(userProfile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d(TAG, "Failed to load user profile");
                }
            });

        }
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UserProfileTweetsListFragment userProfileTweetsListFragment = UserProfileTweetsListFragment.newInstance(screenName);
        fragmentTransaction.replace(R.id.fl_profile_fragment, userProfileTweetsListFragment);
        fragmentTransaction.commit();

        ActionBar actionBar = getSupportActionBar();
        // Display the layout xml for this activity and the action bar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_white_twitter_bird);
        actionBar.setDisplayUseLogoEnabled(true);

    }

    private void populateProfileView(UserProfile userProfile) {
        TextView tv_screenName = (TextView)findViewById(R.id.tv_profile_user_name);
        tv_screenName.setText(screenName);

        TextView tv_tweetCnt = (TextView)findViewById(R.id.tv_profile_tweets_txt);
        String tweets= getString(R.string.TWEETS);
        tv_tweetCnt.setText(String.valueOf(userProfile.getTweetCnt())+ " "+tweets);

        TextView tv_followingCnt = (TextView)findViewById(R.id.tv_profile_following_txt);
        String following= getString(R.string.FOLLOWING);
        tv_followingCnt.setText(String.valueOf(userProfile.getFollowingCnt())+" "+following);

        TextView tv_followersCnt = (TextView)findViewById(R.id.tv_profile_followers_txt);
        String followers = getString(R.string.FOLLOWERS);
        tv_followersCnt.setText(String.valueOf(userProfile.getFollowersCnt())+" "+followers);

        ImageView ivImageView = (ImageView)findViewById(R.id.iv_profile_view);

        if(! userProfile.getUserProfileUrl().isEmpty()) {
            Picasso.with(getApplicationContext()).load(userProfile.getUserProfileUrl()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder_error).into(ivImageView);
        }
    }
}
