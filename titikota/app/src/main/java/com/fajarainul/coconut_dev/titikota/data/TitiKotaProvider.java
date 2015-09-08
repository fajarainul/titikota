/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fajarainul.coconut_dev.titikota.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class TitiKotaProvider extends ContentProvider {
    private final String LOG_TAG = TitiKotaProvider.class.getSimpleName();
    // The URI Matcher used by this content provider.


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private TitiKotaDbHelper mOpenHelper;

    static final int TWEET = 100;

    private static final SQLiteQueryBuilder sFloodSettingQueryBuilder;

    static{
        sFloodSettingQueryBuilder = new SQLiteQueryBuilder();

        sFloodSettingQueryBuilder.setTables(
                TitiKotaContract.TweetEntry.TABLE_NAME);

    }


    private Cursor getTweets(Uri uri, String[] projection, String sortOrder){
        return mOpenHelper.getReadableDatabase().query(
                TitiKotaContract.TweetEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TitiKotaContract.CONTENT_AUTHORITY;

        // path for each content provider
        matcher.addURI(authority, TitiKotaContract.PATH_TWEET, TWEET);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new TitiKotaDbHelper(getContext());
        return true;
    }


    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case TWEET:
                return TitiKotaContract.PWZEntry.CONTENT_TYPE;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case TWEET:{
                retCursor = getTweets(uri, projection, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case TWEET: {
                long _id = db.insert(TitiKotaContract.TweetEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = TitiKotaContract.TweetEntry.buildUri(String.valueOf(_id));
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Student: Start by getting a writable database
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case TWEET:
                rowsDeleted = db.delete(
                        TitiKotaContract.TweetEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;

    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case TWEET:
                rowsUpdated = db.update(TitiKotaContract.TweetEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        //Log.v(LOG_TAG, String.valueOf(existing.moveToFirst()));
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TWEET: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    getContext().getContentResolver().delete(TitiKotaContract.TweetEntry.CONTENT_URI, null, null);
                    for (ContentValues value : values) {
                        long _id = db.insert(TitiKotaContract.TweetEntry.TABLE_NAME, null, value);

                        if (_id != -1) {
                            returnCount++;
                        }

                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }

            

            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}