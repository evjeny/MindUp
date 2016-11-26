package com.evjeny.mentalarithmetic;

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
 * Created by Evjeny on 15.10.2016.
 * at 19:11
 */
public class NumChain extends AppCompatActivity {
    private TextView one, two;
    private EditText nums;
    private ProgressBar pb1, pb2;
    private CountDownTimer countDownTimer;
    private int match_count, part = Settings.NUM_CHAIN_TIME/100;
    private String opaopa;
    private boolean inited = false;
    private CountDownTimer cdt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num_chain_one);
        initViewer();
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt = new CountDownTimer(Settings.NUM_CHAIN_TIME, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    pb1.setProgress((int)millisUntilFinished/part);
                    if(millisUntilFinished<10000) {
                        setTitle(""+millisUntilFinished/1000);
                    }
                }

                @Override
                public void onFinish() {
                    initAnswer();
                    new CountDownTimer(Settings.NUM_CHAIN_TIME, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            pb2.setProgress((int)millisUntilFinished/part);
                            if(millisUntilFinished<10000) {
                                setTitle(""+millisUntilFinished/1000);
                            }
                        }

                        @Override
                        public void onFinish() {
                            String tos = getString(R.string.tru) + ": "
                                    + match_count + "/50";
                            Toast.makeText(getApplicationContext(), tos, Toast.LENGTH_LONG).show();
                            Saver.saveToMindUpWithCurrentDate("num_chain_",tos.getBytes());
                            NumChain.this.finish();
                        }
                    }.start();
                }
            };
        }
        DialogShower ds = new DialogShower(this);
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("hints", true)) {
            ds.showDialogWithOneButton(getString(R.string.num_chain), getString(R.string.nc_info),
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
    public void next(View v) {
        //cdt.cancel();
        initAnswer();
        new CountDownTimer(Settings.NUM_CHAIN_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                pb2.setProgress((int)millisUntilFinished/part);
                if(millisUntilFinished<10000) {
                    setTitle(""+millisUntilFinished/1000);
                }
            }

            @Override
            public void onFinish() {
                String tos = getString(R.string.tru) + ": "
                        + match_count + "/50";
                Toast.makeText(getApplicationContext(), tos, Toast.LENGTH_LONG).show();
                Saver.saveToMindUpWithCurrentDate("num_chain_",tos.getBytes());
                NumChain.this.finish();
            }
        }.start();
    }
    public void nums_ch(View v) {
        String n = nums.getText().toString();
        if(!n.equals("")) {
            match_count = getMatchCount(opaopa, n);
            two.setText(getText(R.string.tru)+": "+match_count+"/50");
            finishWithResult();
        }
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
        pb1 = (ProgressBar) findViewById(R.id.nc_one_pb);
        opaopa = createSuperNumber(50);
        one.setText(opaopa);
        inited = false;
    }
    private void initAnswer() {
        inited = true;
        setContentView(R.layout.num_chain_two);
        nums = (EditText) findViewById(R.id.num_chain_et);
        two = (TextView) findViewById(R.id.num_chain_two_tv);
        pb2 = (ProgressBar) findViewById(R.id.nc_two_pb);
    }
    private void finishWithResult()
    {
        int falser = 50-match_count;
        String tos = getString(R.string.tru) + ":" + match_count+"/50";
        Toast.makeText(getApplicationContext(), tos, Toast.LENGTH_LONG).show();
        Bundle conData = new Bundle();
        conData.putIntArray("result", new int[] {match_count, falser});
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        NumChain.this.finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(cdt!=null) cdt.cancel();
    }
    @Override
    public void onBackPressed() {
        Bundle conData = new Bundle();
        conData.putIntArray("result", new int[] {match_count, 50-match_count});
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
