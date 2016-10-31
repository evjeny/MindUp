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
 * at 6:52
 */
public class Logic extends Activity {
    private TextView tv1,tv2,tv;
    private EditText result;
    private Random r = new Random();
    private int tru = 0, fals = 0;
    private  String resul = "";
    private boolean use_timer, started=false;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logic);
        tv1 = (TextView) findViewById(R.id.logic_tv1);
        tv2 = (TextView) findViewById(R.id.logic_tv2);
        result = (EditText) findViewById(R.id.logic_result);
        tv = (TextView)findViewById(R.id.logic_tv_result);
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
                        Logic.this.finish();
                    }
                }.start();
            }
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
}
