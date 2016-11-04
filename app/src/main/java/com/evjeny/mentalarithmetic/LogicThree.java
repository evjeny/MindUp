package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Evjeny on 12.10.2016.
 * at 21:20
 */
public class LogicThree extends Activity {
    private TextView primer,stat;
    private EditText result;
    private ProgressBar pb;
    private int tru = 0, fals = 0, part = Settings.LOGICS_TIME/100;
    public String resul = "";
    private Random r = new Random();
    private CountDownTimer cdt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logic_three);
        primer = (TextView) findViewById(R.id.logic_three_tv);
        stat = (TextView) findViewById(R.id.logic_three_tv_result);
        result = (EditText) findViewById(R.id.logic_three_result);
        pb = (ProgressBar) findViewById(R.id.logic_three_pb);
        final boolean save = PreferenceManager.getDefaultSharedPreferences(this).
                getBoolean("save_results",false);
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
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt = new CountDownTimer(Settings.LOGICS_TIME, 1000) {
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
            ds.showDialogWithOneButton(getString(R.string.logic_three), getString(R.string.ma_info),
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
    private  void fillWithoutLast(String[] todo) {
        primer.setText("");
        for(int i = 0; i<(todo.length-1); i++ ) {
            primer.setText(primer.getText().toString()+todo[i]+" ");
        }
        resul = todo[todo.length-1];
    }
    private  String[] generateFirst() {
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
    private  String[] generateSecond() {
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
    private  String[] generateThird() {
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
        LogicThree.this.finish();
    }
    private  String vo(int i) {
        return String.valueOf(i);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(cdt!=null) cdt.cancel();
    }
}
