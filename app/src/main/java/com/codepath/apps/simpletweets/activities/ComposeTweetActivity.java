package com.codepath.apps.simpletweets.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.apps.simpletweets.R;

public class ComposeTweetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        ActionBar actionBar = getSupportActionBar();
        // Display the layout xml for this activity and the action bar
        setContentView(R.layout.activity_home_time_line);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_white_twitter_bird);
        actionBar.setDisplayUseLogoEnabled(true);

    }
}
