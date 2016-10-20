package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Evjeny on 18.10.2016.
 * at 19:35
 */
public class Parts extends Activity {
    ImageView main;
    AssetManager am;
    ImageButton one, two, three, four, five, six;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cu);
        am = getAssets();
        main = (ImageView) findViewById(R.id.cu_main);
        one = (ImageButton) findViewById(R.id.cub_one);
        two = (ImageButton) findViewById(R.id.cub_two);
        three = (ImageButton) findViewById(R.id.cub_three);
        four = (ImageButton) findViewById(R.id.cub_four);
        five = (ImageButton) findViewById(R.id.cub_five);
        six = (ImageButton) findViewById(R.id.cub_six);
    }
    public void donothing() {
    }
}