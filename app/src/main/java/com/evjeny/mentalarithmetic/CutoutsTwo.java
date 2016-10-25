package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Evjeny on 25.10.2016.
 * at 15:21
 */
public class CutoutsTwo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cu_two);
        CutoutView cv = (CutoutView) findViewById(R.id.cu_two_view);
        ImageView iv = (ImageView) findViewById(R.id.cu_two_iv);
        iv.setImageBitmap(cv.getRandomCroppedBmp(cv.getBase(), 70, 70));
    }
}
