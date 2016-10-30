package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Evjeny on 25.10.2016.
 * at 15:21
 */
public class CutoutsTwo extends Activity {
    ImageButton one, two, three, four, five, six;
    LinearLayout container;
    private int tru = 0, fals = 0;
    TextView res;
    Random r = new Random();
    CountDownTimer cdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cu_two);
        container = (LinearLayout) findViewById(R.id.cu_two_view_container);
        one = (ImageButton) findViewById(R.id.cu_two_one);
        two = (ImageButton) findViewById(R.id.cu_two_two);
        three = (ImageButton) findViewById(R.id.cu_two_three);
        four = (ImageButton) findViewById(R.id.cu_two_four);
        five = (ImageButton) findViewById(R.id.cu_two_five);
        six = (ImageButton) findViewById(R.id.cu_two_six);
        res = (TextView) findViewById(R.id.cu_two_result);
        initViews();
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
        cdt = new CountDownTimer(Settings.CUTOUTS_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), getString(R.string.tru) + ":" + tru
                        + "\n" + getString(R.string.fals) + ":" + fals, Toast.LENGTH_LONG).show();
                CutoutsTwo.this.finish();
            }
        }.start();}
    }
    public void clicked(View v) {
        if(v.getTag().equals("this")) {
            tru+=1;
        } else {
            fals+=1;
        }
        initViews();
        showResult();
    }
    private void showResult() {
        res.setText(getString(R.string.tru)+": "+tru+
                "\n"+getString(R.string.fals)+": "+fals);
    }
    private void clearIBTags() {
        one.setTag("null");
        two.setTag("null");
        three.setTag("null");
        four.setTag("null");
        five.setTag("null");
        six.setTag("null");
    }
    private void setThisTag(int count) {
        switch (count) {
            case 0:
                one.setTag("this");
            case 1:
                two.setTag("this");
            case 2:
                three.setTag("this");
            case 3:
                four.setTag("this");
            case 4:
                five.setTag("this");
            case 5:
                six.setTag("this");
        }
    }
    private void initViews() {
        container.removeAllViews();
        CutoutView cv = new CutoutView(this);
        container.addView(cv);
        clearIBTags();
        int btodo = r.nextInt(6);
        setSrc(btodo, cv.getCropped());
        setThisTag(btodo);
        Bitmap[] set = cv.getFiveCropped();
        int k = 0;
        for(int i = 0; i<6; i++) {
            if(i!=btodo) {
                setSrc(i, set[k]);
                k++;
            }
        }
    }
    private void setSrc(int count, Bitmap todo) {
        switch (count) {
            case 0:
                one.setImageBitmap(todo);
            case 1:
                two.setImageBitmap(todo);
            case 2:
                three.setImageBitmap(todo);
            case 3:
                four.setImageBitmap(todo);
            case 4:
                five.setImageBitmap(todo);
            case 5:
                six.setImageBitmap(todo);
        }
    }
}
