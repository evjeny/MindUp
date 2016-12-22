package com.evjeny.mentalarithmetic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Evjeny on 22.12.2016.
 * at 17:06
 */
public class SquaresActivity extends AppCompatActivity {
    private EditText result;
    private TextView primer, nums;
    private ProgressBar pb;
    private Random r;
    private int expr_num = 0, expr_answer = 0;
    private int type = 0, tru = 0, fals = 0, part = Settings.SQUARES_TIME/100;
    private CountDownTimer cdt = null;

    private int[] sq = {0,1,4,9,16,25,36,49,64,81,100,121,144,169,196,225,256,289,324,361,400,441,
    484,529,576,625,676,729,784,841,900,961,1024,1089,1156,1225,1296,1369,1444,1521,1600,1681,1764,
    1849,1936,2025,2116,2209,2304,2401,2500,2601,2704,2809,2916,3025,3136,3249,3364,3481,3600,3721,
    3844,3969,4096,4225,4356,4489,4624,4761,4900,5041,5184,5329,5476,5625,5776,5929,6084,6241,6400,
    6561,6724,6889,7056,7225,7396,7569,7744,7921,8100,8281,8464,8649,8836,9025,9216,9409,9604,9801};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ma);
        init();
    }
    public void next(View v) {
        if(expr_answer!=0&&!result.getText().toString().equals("")) {
            if(result.getText().toString().equals(String.valueOf(expr_answer))) {
                tru += 1;
                nums.setText(getText(R.string.tru)+":"+tru
                        +"\n"+getText(R.string.fals)+":"+fals);
            } else {
                fals += 1;
                nums.setText(getText(R.string.tru)+":"+tru
                        +"\n"+getText(R.string.fals)+":"+fals);
            }
        } else if(result.getText().toString().equals("")) {
            fals+=1;
            nums.setText(getText(R.string.tru)+":"+tru
                    +"\n"+getText(R.string.fals)+":"+fals);
        }
        int[] str = generateExpr(type);
        primer.setText(str[0]+"*"+str[0]+"=");
        expr_num = str[0];
        expr_answer = str[1];
        result.setText("");
    }
    private int[] generateExpr(int type) {
        int[] result = new int[2];
        if(type==1) {
            result[0]=r.nextInt(44)+56;
            result[1]=result[0]*result[0];
        } else {
            result[0]=r.nextInt(45)+11;
            result[1]=result[0]*result[0];
        }
        return result;
    }
    private void init() {
        result = (EditText) findViewById(R.id.ma_result);
        primer = (TextView) findViewById(R.id.ma_primer);
        nums = (TextView) findViewById(R.id.ma_nums);
        pb = (ProgressBar) findViewById(R.id.ma_pb);
        r = new Random();
        type = getIntent().getIntExtra("type", 0);
        setTitle(getIntent().getStringExtra("title"));
        int[] str = generateExpr(type);
        primer.setText(str[0]+"*"+str[0]+"=");
        expr_num = str[0];
        expr_answer = str[1];
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
            ds.showDialogWithOneButton(getString(R.string.squares_one), getString(R.string.ma_info),
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
        SquaresActivity.this.finish();
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