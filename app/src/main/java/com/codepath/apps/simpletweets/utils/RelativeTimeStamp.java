package com.codepath.apps.simpletweets.utils;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by k.sridhar on 12/12/2015.
 */
public class RelativeTimeStamp {

    private String date;
    private static final String TAG = RelativeTimeStamp.class.getSimpleName();

    public RelativeTimeStamp(String date) {
        this.date = date;
        Log.d(TAG, "RelativeTimeStamp date:"+date);
    }

    public String getRelativeTimeAgo() {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(this.date).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            Log.d(TAG,"getRelativeTimeAgo relativeDate:"+relativeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
