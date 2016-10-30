package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Evjeny on 12.10.2016.
 * at 21:20
 */
public class LogicThree extends Activity {
    TextView primer,stat;
    EditText result;
    private int tru = 0, fals = 0;
    public String resul = "";
    Random r = new Random();
    boolean use_timer, started;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logic_three);
        primer = (TextView) findViewById(R.id.logic_three_tv);
        stat = (TextView) findViewById(R.id.logic_three_tv_result);
        result = (EditText) findViewById(R.id.logic_three_result);
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
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(getApplicationContext(), getString(R.string.tru) + ":" + tru
                                + "\n" + getString(R.string.fals) + ":" + fals, Toast.LENGTH_LONG).show();
                        LogicThree.this.finish();
                    }
                }.start();
            }
        }
    }
    public void next(View v) {
        if(!result.getText().equals("")&&!resul.equals("")) {
            if(result.getText().toString().equals(resul)) {
                tru += 1;
                stat.setText(getString(R.string.tru)+":"+tru
                        +"\n"+getString(R.string.fals)+":"+fals);
            } else {
                fals += 1;
                stat.setText(getString(R.string.tru)+":"+tru
                        +"\n"+getString(R.string.fals)+":"+fals);
            }
        }
        result.setText("");
        String[] rock;
        switch (r.nextInt(3)) {
            case 0:
                rock = generateFirst();
                fillWithoutLast(rock);
                break;
            case 1:
                rock = generateSecond();
                fillWithoutLast(rock);
                break;
            case 2:
                rock = generateThird();
                fillWithoutLast(rock);
                break;
        }
    }
    public void fillWithoutLast(String[] todo) {
        primer.setText("");
        for(int i = 0; i<(todo.length-1); i++ ) {
            primer.setText(primer.getText().toString()+todo[i]+" ");
        }
        resul = todo[todo.length-1];
    }
    public String[] generateFirst() {
        String[] result = new String[4];
        int one = r.nextInt(50)+1;
        int interval = r.nextInt(15)+5;
        int two = one+interval;
        int three = two+interval;
        int four = three+interval;
        result[0]=vo(one);
        result[1]=vo(two);
        result[2]=vo(three);
        result[3]=vo(four);
        return result;
    }
    public String[] generateSecond() {
        String[] result = new String[5];
        int n;
        int interval = r.nextInt(15)+5;
        int aft_interval = r.nextInt(10)+5;
        int one = r.nextInt(50)+1;
        n = interval;
        int two = one+n;
        n += aft_interval;
        int three = two+n;
        n += aft_interval;
        int four = three+n;
        n += aft_interval;
        int five = four+n;
        result[0]=vo(one);
        result[1]=vo(two);
        result[2]=vo(three);
        result[3]=vo(four);
        result[4]=vo(five);
        return result;
    }
    public String[] generateThird() {
        String[] result = new String[4];
        int one = r.nextInt(20)+1;
        int two = one*2;
        int three = two*2;
        int four = three*2;
        result[0]=vo(one);
        result[1]=vo(two);
        result[2]=vo(three);
        result[3]=vo(four);
        return result;
    }
    public String vo(int i) {
        return String.valueOf(i);
    }
}
