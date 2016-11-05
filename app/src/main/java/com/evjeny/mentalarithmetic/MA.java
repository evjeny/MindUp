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
 * Created by Евгений on 29.09.2016.
 */
public class MA extends Activity {
    private EditText result;
    private TextView primer, nums;
    private ProgressBar pb;
    private Random r;
    private int resul = 0; //Правильный ответ на пример
    private int tru = 0, fals = 0, part = Settings.MA_TIME/100;
    private CountDownTimer cdt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ma);
        result = (EditText) findViewById(R.id.ma_result);
        primer = (TextView) findViewById(R.id.ma_primer);
        nums = (TextView) findViewById(R.id.ma_nums);
        pb = (ProgressBar) findViewById(R.id.ma_pb);
        r = new Random();
        switch (r.nextInt(2)) {
            case 0:
                String[] sum = generateSum(1000);
                primer.setText(sum[0]+sum[3]+sum[1]+"=");
                resul = Integer.valueOf(sum[2]);
                break;
            case 1:
                String[] mp = generateMult(1000);
                primer.setText(mp[0]+"*11=");
                resul = Integer.valueOf(mp[1]);
                break;
        }
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt = new CountDownTimer(Settings.MA_TIME, 1000) {
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
    private String[] generateSum(int max) {
        /**TODO Создает рандомную сумму или разность из 2 чисел, меньше @max
         * В нулевой ячейке находится первое число, во второй - второе,
         * в третьей лежит ответ, а в четвёртой - знак (+ или -)
         */
        int one = r.nextInt(max);
        int two = r.nextInt(max);
        int pick = r.nextInt(2);
        String[] result = new String[4];
        result[0]=String.valueOf(one);
        result[1]=String.valueOf(two);
        switch (pick) {
            case 0:
                result[2]=String.valueOf(two+one);
                result[3]="+";
                return result;
            case 1:
                result[2]=String.valueOf(one-two);
                result[3]="-";
                return result;
            default:
                result[2]=String.valueOf(two+one);
                result[3]="+";
                return result;
        }
    }
    private String[] generateMult(int max) {
        /**TODO Создает рандомное произведение (a<max)*11
         * В нулевой ячейке находится первое число, во второй - результат
         */
        int one = r.nextInt(max);
        String[] result = new String[2];
        result[0]=String.valueOf(one);
        result[1]=String.valueOf(one*11);
        return result;
    }
    public void next(View v) {
        if(resul!=0&&!result.getText().toString().equals("")) {
            if(result.getText().toString().equals(String.valueOf(resul))) {
                tru += 1;
                nums.setText(getText(R.string.tru)+":"+tru
                +"\n"+getText(R.string.fals)+":"+fals);
            } else {
                fals += 1;
                nums.setText(getText(R.string.tru)+":"+tru
                +"\n"+getText(R.string.fals)+":"+fals);
            }
        }
        switch (r.nextInt(2)) {
            case 0:
                String[] sum = generateSum(1000);
                primer.setText(sum[0]+sum[3]+sum[1]+"=");
                resul = Integer.valueOf(sum[2]);
                break;
            case 1:
                String[] mp = generateMult(1000);
                primer.setText(mp[0]+"*11=");
                resul = Integer.valueOf(mp[1]);
                break;
        }
        result.setText("");
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
        MA.this.finish();
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
