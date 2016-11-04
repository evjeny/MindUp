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
 * Created by Evjeny on 13.10.2016.
 * at 16:26
 */
public class LogicTwo extends Activity {
    private TextView tvone, tvtwo, tvresult;
    private EditText result;
    private ProgressBar pb;
    private int tru = 0, fals = 0, part = Settings.LOGICS_TIME/100;
    private String resul = "";
    private Random r = new Random();
    private CountDownTimer cdt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logic_two);
        tvone = (TextView) findViewById(R.id.logic_two_tv1);
        tvtwo = (TextView) findViewById(R.id.logic_two_tv2);
        tvresult = (TextView) findViewById(R.id.logic_two_tv_result);
        result = (EditText) findViewById(R.id.logic_two_result);
        pb = (ProgressBar) findViewById(R.id.logic_two_pb);
        final boolean save = PreferenceManager.getDefaultSharedPreferences(this).
                getBoolean("save_results",false);
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
            ds.showDialogWithOneButton(getString(R.string.logic_two), getString(R.string.ma_info),
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
        result.setText("");
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
    private String[] generateSmth(String first) {
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
    private int sumOfCharArr(char[] chars) {
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
    private String vo(int todo) {
        return String.valueOf(todo);
    }
    private int vo(String todo) {
        return Integer.valueOf(todo);
    }
    private int menshe(int todo) {
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
        LogicTwo.this.finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(cdt!=null) cdt.cancel();
    }
}