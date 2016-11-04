package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Evjeny on 25.10.2016.
 * at 15:21
 */
public class CutoutsTwo extends Activity {
    private ImageButton one, two, three, four, five, six;
    private LinearLayout container;
    private ProgressBar pb;
    private int tru = 0, fals = 0, part = Settings.CUTOUTS_TIME /100;
    private TextView res;
    private Random r = new Random();
    private CountDownTimer cdt = null;
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
        pb = (ProgressBar) findViewById(R.id.cu_two_pb);
        final boolean save = PreferenceManager.getDefaultSharedPreferences(this).
                getBoolean("save_results",false);
        initViews();
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
        cdt = new CountDownTimer(Settings.CUTOUTS_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                pb.setProgress((int)millisUntilFinished/part);
                if(millisUntilFinished<10000) {
                    setTitle(""+millisUntilFinished/1000);
                }
            }

            @Override
            public void onFinish() {
                finishWithResult();
            }
        };}
        DialogShower ds = new DialogShower(this);
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("hints", true)) {
            ds.showDialogWithOneButton(getString(R.string.cutouts_two), getString(R.string.cu_info),
                    getString(R.string.ok), R.drawable.info, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (cdt != null) cdt.start();
                        }
                    });
        }
        else {
            if(cdt!=null) cdt.start();
        }
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
    private void finishWithResult()
    {
        String tos = getString(R.string.tru) + ":" + tru
                + "\n" + getString(R.string.fals) + ":" + fals;
        Toast.makeText(getApplicationContext(), tos, Toast.LENGTH_LONG).show();
        Bundle conData = new Bundle();
        conData.putIntArray("result", new int[] {tru, fals});
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        CutoutsTwo.this.finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(cdt!=null) cdt.cancel();
    }
}
