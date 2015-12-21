package com.codepath.apps.simpletweets.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.activities.ProfileViewActivity;
import com.codepath.apps.simpletweets.adapters.TweetsAdapter;
import com.codepath.apps.simpletweets.models.TweetModel;
import com.codepath.apps.simpletweets.models.UserProfile;
import com.codepath.apps.simpletweets.network.TwitterClient;

import java.util.ArrayList;

/**
 * Created by k.sridhar on 12/18/2015.
 */
public abstract class TweetsListFragment extends Fragment {

    private ArrayList<TweetModel> tweetModelArrayList;
    private UserProfile userProfile;
    private TwitterClient twitterClient;
    private TweetsAdapter tweetsAdapter;
    private static final String TAG = TweetsListFragment.class.getSimpleName();

    public TweetsListFragment() {
        super();
    }

    abstract void onScrollImpl(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_time_line,container,false);
        ListView lvHomeTimeLine = (ListView) v.findViewById(R.id.lv_home_time_line);
        lvHomeTimeLine.setAdapter(tweetsAdapter);
        lvHomeTimeLine.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                onScrollImpl(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });

        lvHomeTimeLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"onItemClick position:"+position+" item:"+tweetsAdapter.getItem(position).getScreenName() );
                Intent newIntent = new Intent(getActivity(),ProfileViewActivity.class );
                newIntent.putExtra("screen_name",tweetsAdapter.getItem(position).getScreenName());
                startActivity(newIntent);
            }
        });
         return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetModelArrayList = new ArrayList<>();
        userProfile = new UserProfile();
        twitterClient = TwitterApplication.getRestClient();
        tweetsAdapter = new TweetsAdapter(getActivity(),tweetModelArrayList);
    }

    public TweetsAdapter getAdapter() {
        return this.tweetsAdapter;
    }



 }

