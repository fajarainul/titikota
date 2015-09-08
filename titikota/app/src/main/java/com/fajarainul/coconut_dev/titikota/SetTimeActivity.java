package com.fajarainul.coconut_dev.titikota;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SetTimeActivity extends ActionBarActivity {
    String msg = "InI Hanya UnTUK mENcoba TOkENIsasi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        Button button = (Button) findViewById(R.id.search);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fetch fetch = new Fetch(getApplicationContext());
                fetch.execute();

                Log.d("STATUS", fetch.getStatus().toString());
                //String[] testSaja = {"satu","dua","tiga","empat"};
                //processClassification process = new processClassification(getApplicationContext());
                //process.main();
                /*double[] result = process.checkWord(testSaja);

                for(int i=0;i<result.length;i++){
                    Log.d("RESULT", Double.toString(result[i]));
                }*/


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

    public void processAll(View view){

    }

    public void config(){

    }



}
