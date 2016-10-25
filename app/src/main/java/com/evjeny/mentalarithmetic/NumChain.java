package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Evjeny on 15.10.2016.
 * at 19:11
 */
public class NumChain extends Activity {
    TextView one, two;
    EditText nums;
    CountDownTimer countDownTimer;
    public int match_count;
    boolean use_timer, started = false;
    String opaopa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num_chain_one);
        initViewer();
    }
    @Override
    protected void onResume() {
        super.onResume();
        use_timer = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false);
        if(use_timer) {
            if (started == false) {
                started = true;
                countDownTimer = new CountDownTimer(Settings.LOGICS_TIME, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        Log.d("timer", "tr: " + millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(getApplicationContext(), getString(R.string.tru) + ": "
                                + match_count+"/50", Toast.LENGTH_LONG).show();
                        NumChain.this.finish();
                    }
                }.start();
            }
        }
    }
    public void next(View v) {
        //cdt.cancel();
        initAnswer();
    }
    public void nums_ch(View v) {
        String n = nums.getText().toString();
        if(!n.equals("")) {
            match_count = getMatchCount(opaopa, n);
            two.setText(getText(R.string.tru)+": "+match_count+"/50");
        }
    }
    public void restart(View v) {
        initViewer();
    }
    private String createSuperNumber(int length) {
        Random r = new Random();
        String result = "";
        for(int i = 0;i<length;i++) {
            result+=String.valueOf(r.nextInt(10));
        }
        return result;
    }
    private int getMatchCount(String src, String ept) {
        int result = 0;
        String[] first = fromCharArray(src.toCharArray());
        String[] second = fromCharArray(ept.toCharArray());
        for(int i = 0;i<second.length;i++) {
            first[i] = first[i];
            if(first[i].equals(second[i])) result = result + 1;
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
    private void initViewer() {
        setContentView(R.layout.num_chain_one);
        one = (TextView) findViewById(R.id.num_chain_one_tv);
        opaopa = createSuperNumber(50);
        one.setText(opaopa);
    }
    private void initAnswer() {
        setContentView(R.layout.num_chain_two);
        nums = (EditText) findViewById(R.id.num_chain_et);
        two = (TextView) findViewById(R.id.num_chain_two_tv);
    }
}
