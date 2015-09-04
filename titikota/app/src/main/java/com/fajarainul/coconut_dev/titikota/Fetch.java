package com.fajarainul.coconut_dev.titikota;

/**
 * Created by Fajar Ainul on 04/09/2015.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Fetch extends AsyncTask<String, Void, Void> {

    private final Context mContext;

    public Fetch(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("Qrw2e63fY9v4pqXTtVXTFw4bo")
                .setOAuthConsumerSecret("4WcCy8UoIOaYYXbjIZxGKcEyIuWSZvEOFHKTvsjIr5PR1l7MGo")
                .setOAuthAccessToken("795262196-Uc6dQxPlrOFYPmbksgY9sNwJCXGorbnC6AsAkt9L")
                .setOAuthAccessTokenSecret("KKr2sYsUrN6rqIqFUCbbsb2Tk4ORNzRpeT3dzxpLwQsKM");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try{
            Query query = new Query();
            // query.setCount(10); // Limit of resultset
            query.setSince("2011-01-01"); // Start date of search
            query.setGeoCode(new GeoLocation(-6.2215, 106.8452), 271, Query.KILOMETERS);
            QueryResult result = twitter.search(query);
            for (twitter4j.Status status : result.getTweets()) {
                Log.v("@" + status.getUser().getScreenName(), status.getText()+ " "+status.getCreatedAt());
            }
        }
        catch (TwitterException tw){
            tw.printStackTrace();
        }
        return null;
    }

}


