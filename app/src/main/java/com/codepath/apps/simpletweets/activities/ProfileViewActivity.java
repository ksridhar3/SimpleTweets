package com.codepath.apps.simpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.fragments.UserProfileViewFragment;


public class ProfileViewActivity extends AppCompatActivity {

    private String screenName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_view);
        Intent intent =getIntent();
        screenName = intent.getStringExtra("screen_name");

        TextView textView = (TextView)findViewById(R.id.tv_profile_user_name);
        textView.setText(screenName);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UserProfileViewFragment userProfileViewFragment = UserProfileViewFragment.newInstance(screenName);
        fragmentTransaction.replace(R.id.fl_profile_fragment,userProfileViewFragment);
        fragmentTransaction.commit();
        ActionBar actionBar = getSupportActionBar();
        // Display the layout xml for this activity and the action bar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_white_twitter_bird);
        actionBar.setDisplayUseLogoEnabled(true);

    }
}
