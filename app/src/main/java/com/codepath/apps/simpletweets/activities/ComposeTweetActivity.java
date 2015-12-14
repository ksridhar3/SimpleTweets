package com.codepath.apps.simpletweets.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeTweetActivity extends AppCompatActivity {

    private TwitterClient twitterClient;
    private String userName;
    private String screenUserName;
    private String userProfileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        Intent intent =getIntent();
        userName = intent.getStringExtra("name");
        screenUserName = intent.getStringExtra("screen_name");
        userProfileUrl=intent.getStringExtra("url");

        TextView textView = (TextView)findViewById(R.id.tv_compose_user);
        textView.setText(userName);

        ImageView ivImageView = (ImageView)findViewById(R.id.iv_compose_img);

        if(! userProfileUrl.isEmpty()) {
            Picasso.with(getApplicationContext()).load(userProfileUrl).placeholder(R.drawable.placeholder).error(R.drawable.placeholder_error).into(ivImageView);
        }

        ActionBar actionBar = getSupportActionBar();
        // Display the layout xml for this activity and the action bar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_white_twitter_bird);
        actionBar.setDisplayUseLogoEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_compose_tweet_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mi_tweet:
                EditText editText = (EditText)findViewById(R.id.et_tweet_text);
                String string;
                twitterClient = new TwitterClient(getApplicationContext());
                if(editText.getText().toString().isEmpty()) {
                    string=editText.getHint().toString();
                }
                else {
                    string=editText.getText().toString();
                }
                twitterClient.postTweet(string,new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                });


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
