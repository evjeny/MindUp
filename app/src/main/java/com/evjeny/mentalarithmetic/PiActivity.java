package com.evjeny.mentalarithmetic;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Evjeny on 11.11.2016.
 * at 21:00
 */
public class PiActivity extends AppCompatActivity {
    private String pi = "14159265358979323846264338327950288419716939937510582097494459230781640628620899"+
    "86280348253421170679821480864132823066470938446095505822317253594081284811174502841027019385"+
    "2110555964462294895493038196";
    private String [] pies;
    private EditText et;
    private ProgressBar pb;
    private int match_count, part = Settings.PI_TIME /100;
    private CountDownTimer cdt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pies = fromCharArray(pi.toCharArray());
        setContentView(R.layout.pi);
        et = (EditText) findViewById(R.id.piet);
        pb = (ProgressBar) findViewById(R.id.pi_bp);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt = new CountDownTimer(Settings.PI_TIME, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    pb.setProgress((int) millisUntilFinished / part);
                    if (millisUntilFinished < 10000) {
                        setTitle("" + millisUntilFinished / 1000);
                    }
                }

                @Override
                public void onFinish() {
                    String text = et.getText().toString();
                    if(text.equals("")) {
                        match_count = 0;
                        finishWithResult();
                    } else {
                        match_count = getMatchCount(pies, text);
                        finishWithResult();
                    }
                }
            }.start();
        }
    }
    public void next(View v) {
        String text = et.getText().toString();
        if(!text.equals("")) {
            match_count = getMatchCount(pies, text);
            finishWithResult();
        } else {
            match_count = 0;
            finishWithResult();
        }
    }
    private void finishWithResult()
    {
        String tos = getString(R.string.tru) + ":" + match_count+"/200";
        Toast.makeText(getApplicationContext(), tos, Toast.LENGTH_LONG).show();
        Bundle conData = new Bundle();
        conData.putIntArray("result", new int[] {match_count, (200-match_count)});
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        PiActivity.this.finish();
    }
    private int getMatchCount(String[] src, String ept) {
        int result = 0;
        String[] second = fromCharArray(ept.toCharArray());
        for(int i = 0;i<second.length;i++) {
            if(src[i].equals(second[i])) result = result + 1;
        }
        return result;
    }
    private String[] fromCharArray(char[] chars) {
        String[] result = new String[chars.length];
        for(int i = 0; i< chars.length; i++) {
            result[i] = String.valueOf(chars[i]);
        }
        return result;
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(cdt!=null) cdt.cancel();
    }
}