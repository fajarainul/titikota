package com.fajarainul.coconut_dev.titikota;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fajarainul.coconut_dev.titikota.data.TitiKotaContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class TweetListFragment extends Fragment implements LoaderManager.LoaderCallbacks {

    private static final String LOG_TAG = TweetListFragment.class.getSimpleName();
    private TweetListAdapter mTweetListAdapter;
    private static final int TWEET_LIST_LOADER = 0;
    private static final String SELECTED_KEY = "selected_position";
    int mPosition;
    private String Kategori;


    private static final String[] TWEET_LIST_COLUMNS = {

            TitiKotaContract.TweetEntry.TABLE_NAME + "." + TitiKotaContract.TweetEntry._ID,
            TitiKotaContract.TweetEntry.COLUMN_CREATED_AT,
            TitiKotaContract.TweetEntry.COLUMN_USER,
            TitiKotaContract.TweetEntry.COLUMN_TWEET,
            TitiKotaContract.TweetEntry.COLUMN_CLASS
    };

    public static final int COL_CREATED_AT = 1;
    public static final int COL_USER = 2;
    public static final int COL_TWEET = 3;
    public static final int COL_CLASS = 4;

    public TweetListFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(TWEET_LIST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);

        Bundle args = getArguments();
        Kategori = args.getString("Kategori");
        
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tweet_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mTweetListAdapter = new TweetListAdapter(getActivity(), null, 0);

        final View rootView = inflater.inflate(R.layout.fragment_tweet_list, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_tweet_list);
        listView.setAdapter(mTweetListAdapter);

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(TWEET_LIST_LOADER, null, this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Sort order:  Ascending, by id
        String sortOrder = TitiKotaContract.TweetEntry._ID + " DESC";
        Uri TweetListUri = TitiKotaContract.TweetEntry.buildClassUri(Kategori);
        Log.v(LOG_TAG, "URI: " + TweetListUri);

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                TweetListUri,
                TWEET_LIST_COLUMNS,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mTweetListAdapter.swapCursor((Cursor) data);

    }

    @Override
    public void onLoaderReset(Loader loader) {
        mTweetListAdapter.swapCursor(null);

    }
}
