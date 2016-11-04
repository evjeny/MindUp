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
 * at 6:52
 */
public class Logic extends Activity {
    private TextView tv1,tv2,tv;
    private EditText result;
    private ProgressBar pb;
    private Random r = new Random();
    private int tru = 0, fals = 0, part = Settings.LOGICS_TIME / 100;
    private  String resul = "";
    private CountDownTimer cdt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logic);
        tv1 = (TextView) findViewById(R.id.logic_tv1);
        tv2 = (TextView) findViewById(R.id.logic_tv2);
        result = (EditText) findViewById(R.id.logic_result);
        tv = (TextView)findViewById(R.id.logic_tv_result);
        pb = (ProgressBar) findViewById(R.id.logic_pb);
        final boolean save = PreferenceManager.getDefaultSharedPreferences(this).
                getBoolean("save_results",false);
        String[] one, two;
        switch (r.nextInt(4)) {
            case 0:
                one = generateSmth("+","/");
                two = generateSmth("+","/");
                tv1.setText(one[0]+"("+one[2]+")"+one[1]);
                tv2.setText(two[0]+"( ? )"+two[1]);
                resul = two[2];
                break;
            case 1:
                one = generateSmth("+","*");
                two = generateSmth("+","*");
                tv1.setText(one[0]+"("+one[2]+")"+one[1]);
                tv2.setText(two[0]+"( ? )"+two[1]);
                resul = two[2];
                break;
            case 2:
                one = generateSmth("-","/");
                two = generateSmth("-","/");
                tv1.setText(one[0]+"("+one[2]+")"+one[1]);
                tv2.setText(two[0]+"( ? )"+two[1]);
                resul = two[2];
                break;
            case 3:
                one = generateSmth("-","*");
                two = generateSmth("-","*");
                tv1.setText(one[0]+"("+one[2]+")"+one[1]);
                tv2.setText(two[0]+"( ? )"+two[1]);
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
            ds.showDialogWithOneButton(getString(R.string.logic), getString(R.string.ma_info),
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
                tv.setText(getString(R.string.tru)+":"+tru
                +"\n"+getString(R.string.fals)+":"+fals);
            } else {
                fals += 1;
                tv.setText(getString(R.string.tru)+":"+tru
                        +"\n"+getString(R.string.fals)+":"+fals);
            }
            result.setText("");
        }
        result.setText("");
        String[] one, two;
        switch (r.nextInt(4)) {
            case 0:
                one = generateSmth("+","/");
                two = generateSmth("+","/");
                tv1.setText(one[0]+"("+one[2]+")"+one[1]);
                tv2.setText(two[0]+"( ? )"+two[1]);
                resul = two[2];
                break;
            case 1:
                one = generateSmth("+","*");
                two = generateSmth("+","*");
                tv1.setText(one[0]+"("+one[2]+")"+one[1]);
                tv2.setText(two[0]+"( ? )"+two[1]);
                resul = two[2];
                break;
            case 2:
                one = generateSmth("-","/");
                two = generateSmth("-","/");
                tv1.setText(one[0]+"("+one[2]+")"+one[1]);
                tv2.setText(two[0]+"( ? )"+two[1]);
                resul = two[2];
                break;
            case 3:
                one = generateSmth("-","*");
                two = generateSmth("-","*");
                tv1.setText(one[0]+"("+one[2]+")"+one[1]);
                tv2.setText(two[0]+"( ? )"+two[1]);
                resul = two[2];
                break;
        }
    }
    private  String[] generateSmth(String first, String second) {
        /** first - знак между числами в скобках
         * second - знак между числами за скобками
         **/
        String[] result = new String[3];
        int one,  two;
        int middle;
        if(first.equals("+")) {
            //useful comment 4 stupit developer(me)
            //(int x + int y) / 2 = int z
            // x = z * 2 - y
            if(second.equals("/")) {
                middle = r.nextInt(500)+1;
                two = menshe(middle*2);
                one = middle*2-two;
                result[0]=vo(one);
                result[1]=vo(two);
                result[2]=vo(middle);

            } else if(second.equals("*")) {
                one = r.nextInt(800)+1;
                two = r.nextInt(800)+1;
                middle = (one+two)*2;
                result[0]=vo(one);
                result[1]=vo(two);
                result[2]=vo(middle);
            }
        } else if(first.equals("-")) {
            if(second.equals("/")) {
                //1 more comment
                //(x-y)/2=z
                //x=z*2+y
                two = r.nextInt(800)+1;
                middle = r.nextInt(500)+1;
                one = middle*2+two;
                result[0]=vo(one);
                result[1]=vo(two);
                result[2]=vo(middle);
            } else if(second.equals("*")) {
                //(x-y)*2=z
                one = r.nextInt(800)+1;
                two = menshe(one);
                middle = (one-two)*2;
                result[0]=vo(one);
                result[1]=vo(two);
                result[2]=vo(middle);
            }
        }
        return result;
    }
    private  String vo(int todo) {
        return String.valueOf(todo);
    }
    private  int vo(String todo) {
        return Integer.valueOf(todo);
    }
    private  int menshe(int todo) {
        //when you znaesh Angliisky
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
    @Override
    protected void onStop() {
        super.onStop();
        if(cdt!=null) cdt.cancel();
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
        Logic.this.finish();
    }
}
