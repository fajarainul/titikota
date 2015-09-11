package com.fajarainul.coconut_dev.titikota;

/**
 * Created by Fajar Ainul on 04/09/2015.
 */

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import com.fajarainul.coconut_dev.titikota.data.TitiKotaContract;

import java.util.Vector;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Fetch extends AsyncTask<String, Void, Void> {

    private SetTimeActivity activity;
    ProgressDialog pd;

    public Fetch(SetTimeActivity activity) {
        this.activity = activity;
        pd = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.setMessage("Please wait...");
        pd.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        activity.drawCircleLancar(activity);
        pd.dismiss();
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

        Vector<ContentValues> cVector = new Vector<ContentValues>();

        try{
            Query query = new Query();
            query.setCount(180); // Limit of resultset
            query.setGeoCode(new GeoLocation(-6.21462, 106.84513), 271, Query.KILOMETERS);
            QueryResult result = twitter.search(query);
            for (twitter4j.Status status : result.getTweets()) {
                Log.e("@" + status.getUser().getScreenName(), status.getText() + " " + status.getCreatedAt());

                processClassification process = new processClassification(activity);
                int resultClass = process.main(status.getText());
                //Log.d("FINAL RESULT", Integer.toString(resultClass));

                ContentValues nilaiValues = new ContentValues();

                nilaiValues.put(TitiKotaContract.TweetEntry.COLUMN_CREATED_AT, status.getCreatedAt().toString());
                nilaiValues.put(TitiKotaContract.TweetEntry.COLUMN_USER, status.getUser().getScreenName());
                nilaiValues.put(TitiKotaContract.TweetEntry.COLUMN_TWEET, status.getText());
                nilaiValues.put(TitiKotaContract.TweetEntry.COLUMN_CLASS, resultClass);

                cVector.add(nilaiValues);
            }

            if (cVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVector.size()];
                cVector.toArray(cvArray);

                activity.getContentResolver().bulkInsert(TitiKotaContract.TweetEntry.CONTENT_URI, cvArray);
            }
        }
        catch (TwitterException tw){
            tw.printStackTrace();
        }
        return null;
    }

}


