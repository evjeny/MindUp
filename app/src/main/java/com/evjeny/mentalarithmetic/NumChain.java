package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Evjeny on 15.10.2016.
 * at 19:11
 */
public class NumChain extends Activity {
    TextView one, two;
    EditText nums;
    //CountDownTimer cdt;
    String opaopa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num_chain_one);
        initViewer();
        //60000*2 / 1000
         /**cdt = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                setContentView(R.layout.num_chain_two);
            }
        };
          //for the future :D
          **/
    }
    public void next(View v) {
        //cdt.cancel();
        initAnswer();
    }
    public void nums_ch(View v) {
        String n = nums.getText().toString();
        if(!n.equals("")) {
            two.setText(getText(R.string.tru)+": "+getMatchCount(opaopa,n));
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
