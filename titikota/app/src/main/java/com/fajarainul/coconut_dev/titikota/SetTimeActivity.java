package com.fajarainul.coconut_dev.titikota;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fajarainul.coconut_dev.titikota.data.TitiKotaContract;

import java.util.Random;


public class SetTimeActivity extends ActionBarActivity {
    Context context;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        Button button = (Button) findViewById(R.id.search);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fetch fetch = new Fetch(SetTimeActivity.this);

                try{
                    fetch.execute();
                   // progressBar.dismiss();

                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        });
    }

    public static int test(){
        return 3*5;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void drawCircleLancar(Context context){
        //Log.e("DRAW","LANCAR");


        RelativeLayout RL_lancar = (RelativeLayout) findViewById(R.id.lancar);
        RelativeLayout RL_ramai_lancar = (RelativeLayout) findViewById(R.id.ramai_lancar);
        RelativeLayout RL_padat_merayap = (RelativeLayout) findViewById(R.id.padat_merayap);
        RelativeLayout RL_macet_total = (RelativeLayout) findViewById(R.id.macet_total);

        Paint paint_lancar = new Paint();
        Paint paint_ramai_lancar = new Paint();
        Paint paint_padat_merayap = new Paint();
        Paint paint_macet_total = new Paint();

        paint_lancar.setColor(Color.parseColor("#78a7ff"));
        paint_ramai_lancar.setColor(Color.parseColor("#ffeb3b"));
        paint_padat_merayap.setColor(Color.parseColor("#ff9800"));
        paint_macet_total.setColor(Color.parseColor("#d74633"));

        Bitmap bg_lancar = Bitmap.createBitmap(RL_lancar.getWidth(), RL_lancar.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bg_ramai_lancar = Bitmap.createBitmap(RL_ramai_lancar.getWidth(), RL_ramai_lancar.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bg_padat_merayap = Bitmap.createBitmap(RL_padat_merayap.getWidth(), RL_padat_merayap.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bg_macet_total = Bitmap.createBitmap(RL_macet_total.getWidth(), RL_macet_total.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas_lancar = new Canvas(bg_lancar);
        Canvas canvas_ramai_lancar = new Canvas(bg_ramai_lancar);
        Canvas canvas_padat_merayap = new Canvas(bg_padat_merayap);
        Canvas canvas_macet_total = new Canvas(bg_macet_total);

        RL_lancar.setBackgroundDrawable(new BitmapDrawable(bg_lancar));
        RL_ramai_lancar.setBackgroundDrawable(new BitmapDrawable(bg_ramai_lancar));
        RL_padat_merayap.setBackgroundDrawable(new BitmapDrawable(bg_padat_merayap));
        RL_macet_total.setBackgroundDrawable(new BitmapDrawable(bg_macet_total));


        Cursor cursor = context.getContentResolver().query(TitiKotaContract.TweetEntry.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //Log.e("Test", cursor.getString(cursor.getColumnIndex("class")) );
            String kelas = cursor.getString(cursor.getColumnIndex("class"));

            switch (kelas){
                case "0":  {
                    Random rand = new Random();

                    // nextInt is normally exclusive of the top value,
                    // so add 1 to make it inclusive
                    int randX = rand.nextInt(((RL_padat_merayap.getWidth()-10) - 10) + 1) + 10;
                    int randY = rand.nextInt(((RL_padat_merayap.getHeight()-10) - 10) + 1) + 10;

                    canvas_padat_merayap.drawCircle( randX,  randY, 10, paint_padat_merayap);

                    break;
                }
                case "1":  {
                    Random rand = new Random();

                    // nextInt is normally exclusive of the top value,
                    // so add 1 to make it inclusive
                    int randX = rand.nextInt(((RL_macet_total.getWidth()-10) - 10) + 1) + 10;
                    int randY = rand.nextInt(((RL_macet_total.getHeight()-10) - 10) + 1) + 10;

                    canvas_macet_total.drawCircle( randX,  randY, 10, paint_macet_total);

                    break;
                }
                case "2":  {
                    Random rand = new Random();

                    // nextInt is normally exclusive of the top value,
                    // so add 1 to make it inclusive
                    int randX = rand.nextInt(((RL_ramai_lancar.getWidth()-10) - 10) + 1) + 10;
                    int randY = rand.nextInt(((RL_ramai_lancar.getHeight()-10) - 10) + 1) + 10;

                    canvas_ramai_lancar.drawCircle( randX,  randY, 10, paint_ramai_lancar);

                    break;
                }
                case "3":  {
                    Random rand = new Random();

                    // nextInt is normally exclusive of the top value,
                    // so add 1 to make it inclusive
                    int randX = rand.nextInt(((RL_lancar.getWidth()-10) - 10) + 1) + 10;
                    int randY = rand.nextInt(((RL_lancar.getHeight()-10) - 10) + 1) + 10;

                    canvas_lancar.drawCircle( randX,  randY, 10, paint_lancar);

                    break;
                }

            }
            cursor.moveToNext();
        }
        cursor.close();
    }

}
