package com.fajarainul.coconut_dev.titikota;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.fajarainul.coconut_dev.titikota.data.TitiKotaContract;

/**
 * Created by Fajar Ainul on 09/09/2015.
 */
public class DrawCircle {
    Context context;
    public DrawCircle(Context context){
        this.context = context;
    }

    public void circle(){
        Cursor cursor = context.getContentResolver().query(TitiKotaContract.TweetEntry.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.e("Test",cursor.getString(cursor.getColumnIndex("class")) );

            cursor.moveToNext();
        }



    }
}
