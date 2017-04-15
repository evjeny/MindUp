package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Evjeny on 16.12.2016.
 * at 20:58
 */
public class MATwo extends AppCompatActivity {
    private EditText result;
    private ProgressBar pb;
    private TextView nums, expr;
    private Random random = new Random();
    private CountDownTimer cdt;

    private int tru = 0, fals = 0, answer = 0, part = Settings.MA_TWO_TIME/100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ma_two);
        result = (EditText) findViewById(R.id.ma_two_result);
        pb = (ProgressBar) findViewById(R.id.ma_two_pb);
        nums = (TextView) findViewById(R.id.ma_two_nums);
        expr = (TextView) findViewById(R.id.ma_two_expr);
        createExpr(7,1000);
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt = new CountDownTimer(Settings.MA_TWO_TIME, 1000) {
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
            };
        }
        DialogShower ds = new DialogShower(this);
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("hints", true)) {
            ds.showDialogWithOneButton(getString(R.string.ma), getString(R.string.ma_info),
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

    public void go(View v) {
        String text = result.getText().toString();
        if(!text.equals("")) {
            int value = Integer.valueOf(text);
            if(value==answer) {
                tru += 1;
            } else {
                fals += 1;
            }
            nums.setText(getText(R.string.tru)+":"+tru
                    +"\n"+getText(R.string.fals)+":"+fals);
            createExpr(7, 1000);
        }
    }
    private void createExpr(int actions, int max_size) {
        int result_value = 0;
        String result = "";
        for(int i = 0; i<actions; i++) {
            int num = random.nextInt(max_size);
            String operator;
            int o = random.nextInt(2);
            if(o==0) {
                result_value = result_value - num;
                operator = "-";
            } else {
                result_value = result_value + num;
                operator = "+";
            }
            result=result+operator+num;
        }
        answer = result_value;
        expr.setText(result);
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
        MATwo.this.finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(cdt!=null) cdt.cancel();
    }
    @Override
    public void onBackPressed() {
        Bundle conData = new Bundle();
        conData.putIntArray("result", new int[] {tru, fals});
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
