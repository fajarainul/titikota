package com.fajarainul.coconut_dev.titikota;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Asus on 26/09/2015.
 */
public class TweetListAdapter extends CursorAdapter{
    public static final String LOG_TAG = TweetListAdapter.class.getSimpleName();

    /**
     * Cache of the children views for a news list item.
     */
    public static class ViewHolder {
        public final TextView noView;
        public final TextView nameView;
        public final TextView tweetView;
        public final TextView createdView;

        public ViewHolder(View view) {
            noView = (TextView) view.findViewById(R.id.tweet_list_number);
            nameView = (TextView) view.findViewById(R.id.list_item_name_textview);
            tweetView = (TextView) view.findViewById(R.id.list_item_tweet_textview);
            createdView = (TextView) view.findViewById(R.id.list_item_created_textview);
        }
    }

    public TweetListAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Choose the layout type
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        layoutId = R.layout.activity_use_item_tweet;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int viewType = getItemViewType(cursor.getPosition());

        String noString = String.valueOf(cursor.getPosition()+1);
        viewHolder.noView.setText(noString);

        String wilString = cursor.getString(TweetListFragment.COL_USER);
        viewHolder.nameView.setText(wilString);

        String kecString = cursor.getString(TweetListFragment.COL_TWEET);
        viewHolder.tweetView.setText(kecString);

        String kelString = cursor.getString(TweetListFragment.COL_CREATED_AT);
        viewHolder.createdView.setText(kelString);

    }
}
