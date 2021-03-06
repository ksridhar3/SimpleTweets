package com.codepath.apps.simpletweets.activities;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.fragments.HomeLineFragment;
import com.codepath.apps.simpletweets.fragments.MentionsFragment;
import com.codepath.apps.simpletweets.models.UserProfile;
import com.codepath.apps.simpletweets.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class HomeTimeLineActivity extends AppCompatActivity {


    private String TAG = HomeTimeLineActivity.class.getSimpleName();
    private ViewPager vPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        // Display the layout xml for this activity and the action bar
       setContentView(R.layout.activity_home_time_line);
       actionBar.setDisplayShowHomeEnabled(true);
       actionBar.setLogo(R.mipmap.ic_white_twitter_bird);
       actionBar.setDisplayUseLogoEnabled(true);

       vPager = (ViewPager)findViewById(R.id.viewpager);
       vPager.setAdapter(new TweetsPageAdapter(getSupportFragmentManager()));
       PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
       tabStrip.setViewPager(vPager);

   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_time_line_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        UserProfile curUserProfile = TwitterApplication.getLoggedInUserProfile();

        switch (item.getItemId()) {
            case R.id.mi_compose:
                Intent i = new Intent(this,ComposeTweetActivity.class);
                i.putExtra("name",curUserProfile.getUserName());
                i.putExtra("screen_name",curUserProfile.getScreenName());
                i.putExtra("url",curUserProfile.getUserProfileUrl());
                startActivity(i);
                return true;
            case R.id.mi_profile:
                Intent p = new Intent(this,ProfileViewActivity.class);
                p.putExtra("screen_name",curUserProfile.getScreenName());
                startActivity(p);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class TweetsPageAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = {"Home", "Mentions"};
        public TweetsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new HomeLineFragment();
            }
            else if(position == 1) {
                return new MentionsFragment();
            }
            else
                return null;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }


    }

}