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

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class TitiKotaContract {

    public static final String CONTENT_AUTHORITY = "com.fajarainul.coconut_dev.titikota";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TWEET = "tweet";
    public static final String PATH_PWZ = "pwz";
    public static final String PATH_PZC = "pzc";
    public static final String PATH_PZ = "pz";

    /* Inner class that defines the table contents of the news table */
    public static final class TweetEntry implements BaseColumns {


        private static final String LOG_TAG = TweetEntry.class.getSimpleName();

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TWEET).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TWEET;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TWEET;

        public static final String TABLE_NAME = "tweet";
        //public static final String COLUMN_TWEET_ID = "id_tweet";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_TWEET = "tweet";
        public static final String COLUMN_KLD = "KLD";

        public static Uri buildUri(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }

    public static final class PWZEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PWZ).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PWZ;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PWZ;

        public static final String TABLE_NAME = "pwz";

        /*public static final String COLUMN_FLOOD_ID ="id_flood";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_CAPTION = "caption";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_PHOTO = "photo";*/

    }

    public static final class PZCEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PZC).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PZC;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PZC;

        public static final String TABLE_NAME = "pzc";

        /*public static final String COLUMN_FLOOD_ID ="id_flood";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_CAPTION = "caption";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_PHOTO = "photo";*/

    }

    public static final class PZEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PZ).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PZ;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PZ;

        public static final String TABLE_NAME = "pwz";

        /*public static final String COLUMN_FLOOD_ID ="id_flood";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_CAPTION = "caption";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_PHOTO = "photo";*/

    }

}
