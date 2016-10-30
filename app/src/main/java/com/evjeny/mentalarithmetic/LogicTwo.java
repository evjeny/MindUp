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
 * Created by Evjeny on 13.10.2016.
 * at 16:26
 */
public class LogicTwo extends Activity {
    TextView tvone, tvtwo, tvresult;
    EditText result;
    private int tru = 0, fals = 0;
    String resul = "";
    Random r = new Random();
    boolean use_timer, started;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logic_two);
        tvone = (TextView) findViewById(R.id.logic_two_tv1);
        tvtwo = (TextView) findViewById(R.id.logic_two_tv2);
        tvresult = (TextView) findViewById(R.id.logic_two_tv_result);
        result = (EditText) findViewById(R.id.logic_two_result);
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
                        LogicTwo.this.finish();
                    }
                }.start();
            }
        }
    }
    public void next(View v) {
        if(!result.getText().toString().equals("")&&!resul.equals("")) {
            if(result.getText().toString().equals(resul)) {
                tru += 1;
                tvresult.setText(getString(R.string.tru)+":"+tru
                        +"\n"+getString(R.string.fals)+":"+fals);
            } else {
                fals += 1;
                tvresult.setText(getString(R.string.tru)+":"+tru
                        +"\n"+getString(R.string.fals)+":"+fals);
            }
            result.setText("");
        }
        String[] one, two;
        switch (r.nextInt(2)) {
            case 0:
                one = generateSmth("+");
                two = generateSmth("+");
                tvone.setText(one[0]+"("+one[2]+")"+one[1]);
                tvtwo.setText(two[0]+"( ? )"+two[1]);
                resul = two[2];
                break;
            case 1:
                one = generateSmth("-");
                two = generateSmth("-");
                tvone.setText(one[0]+"("+one[2]+")"+one[1]);
                tvtwo.setText(two[0]+"( ? )"+two[1]);
                resul = two[2];
                break;
        }
    }
    public String[] generateSmth(String first) {
        String[] result = new String[3];
        if(first.equals("+")) {
            int one = r.nextInt(500)+1;
            int two = r.nextInt(500)+1;
            int middle = (sumOfCharArr(String.valueOf(one).toCharArray())+sumOfCharArr(String.valueOf(two).toCharArray()));
            result[0]=vo(one);
            result[1]=vo(two);
            result[2]=vo(middle);
        } else if(first.equals("-")) {
            int one = r.nextInt(500)+1;
            int two = menshe(one);
            int middle = (sumOfCharArr(String.valueOf(one).toCharArray())-sumOfCharArr(String.valueOf(two).toCharArray()));
            result[0]=vo(one);
            result[1]=vo(two);
            result[2]=vo(middle);
        }
        return result;
    }
    public int sumOfCharArr(char[] chars) {
        String[] result = new String[chars.length];
        for(int i = 0; i< chars.length; i++) {
            result[i] = String.valueOf(chars[i]);
        }
        int resultt = 0;
        for(int i = 0; i<result.length;i++) {
            String current = result[i];
            resultt += Integer.valueOf(current);
        }
        return resultt;
    }
    public int sumOfChars(char[] arr) {
        int result = 0;
        for(int i = 0; i<arr.length; i++) {
            int now = Integer.valueOf(arr[i]);
            result += now;
        }
        return result;
    }
    public String vo(int todo) {
        return String.valueOf(todo);
    }
    public int vo(String todo) {
        return Integer.valueOf(todo);
    }
    public int menshe(int todo) {
        boolean cool = false;
        int result = 1;
        while (cool==false) {
            int lol = r.nextInt(todo);
            if(lol<todo) {
                result = lol;
                cool=true;
            }
        }
        return result;
    }
}