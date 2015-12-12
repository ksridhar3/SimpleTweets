package com.codepath.apps.simpletweets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.models.HomeTimeLineModel;
import com.codepath.apps.simpletweets.utils.RelativeTimeStamp;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by k.sridhar on 12/11/2015.
 */
public class HomeTimeLineAdapter extends ArrayAdapter<HomeTimeLineModel>{
    public HomeTimeLineAdapter(Context context,ArrayList<HomeTimeLineModel> tweets) {
        super(context,0,tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeTimeLineModel timeLineModel=getItem(position);
        RelativeTimeStamp relativeTimeStamp =new RelativeTimeStamp(timeLineModel.getCreatedAt());
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_individual_item_home_time_line,parent,false);
        }
        ImageView ivImg = (ImageView)convertView.findViewById(R.id.iv_user_image);
        TextView tvUser =(TextView)convertView.findViewById(R.id.tv_user_name);
        TextView tvTweet = (TextView)convertView.findViewById(R.id.tv_twitter_text);
        TextView tvDate =(TextView)convertView.findViewById(R.id.tv_date);
        TextView tvFav =(TextView)convertView.findViewById(R.id.tv_favourites);
        TextView tvRetweet =(TextView)convertView.findViewById(R.id.tv_retweet_cnt);
        if(! timeLineModel.getUserProfileUrl().isEmpty()) {
            Picasso.with(getContext()).load(timeLineModel.getUserProfileUrl()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder_error).into(ivImg);
        }
        tvUser.setText(timeLineModel.getUserName());
        tvTweet.setText(timeLineModel.getTwitterText());
        tvDate.setText(relativeTimeStamp.getRelativeTimeAgo());
        tvFav.setText(timeLineModel.getFavCount());
        tvRetweet.setText(timeLineModel.getReTweetCount());

        return convertView;

    }
}
