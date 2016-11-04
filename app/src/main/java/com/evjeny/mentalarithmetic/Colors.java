package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Evjeny on 03.11.2016.
 * at 18:28
 */
public class Colors extends Activity{
    private Button one, two, three, four, five, six;
    private TextView result, showed;
    private ProgressBar pb;

    private String current = "";
    private int tru, fals,part = Settings.COLORS_TIME/100;

    private CountDownTimer cdt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colors);
        one = (Button) findViewById(R.id.colors_one);
        two = (Button) findViewById(R.id.colors_two);
        three = (Button) findViewById(R.id.colors_three);
        four = (Button) findViewById(R.id.colors_four);
        five = (Button) findViewById(R.id.colors_five);
        six = (Button) findViewById(R.id.colors_six);
        result = (TextView) findViewById(R.id.colors_result);
        showed = (TextView) findViewById(R.id.colors_current);
        pb = (ProgressBar) findViewById(R.id.colors_pb);
        final boolean save = PreferenceManager.getDefaultSharedPreferences(this).
                getBoolean("save_results",false);
        generateColor();
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt = new CountDownTimer(Settings.COLORS_TIME, 1000) {
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
            ds.showDialogWithOneButton(getString(R.string.colors), getString(R.string.colors_info),
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
    public void onColor(View v) {
        if(v.getTag().equals("this")) {
            tru+=1;
        } else {
            fals+=1;
        }
        showResult();
        generateColor();
    }
    private void setButton(int buttonNum, String text) {
        switch (buttonNum) {
            case 0:
                st(one, text);
                break;
            case 1:
                st(two, text);
                break;
            case 2:
                st(three, text);
                break;
            case 3:
                st(four, text);
                break;
            case 4:
                st(five, text);
                break;
            case 5:
                st(six, text);
                break;
        }
    }
    private void clearTags() {
        for(int i = 0; i<6; i++) {
            setTag(i, "null");
        }
    }
    private void setTag(int buttonNum, String text) {
        switch (buttonNum) {
            case 0:
                bt(one, text);
                break;
            case 1:
                bt(two, text);
                break;
            case 2:
                bt(three, text);
                break;
            case 3:
                bt(four, text);
                break;
            case 4:
                bt(five, text);
                break;
            case 5:
                bt(six, text);
                break;
        }
    }
    private void st(Button todo, String text) {
        todo.setText(text);
    }
    private void bt(Button todo, String text) {
        todo.setTag(text);
    }
    private int colorOfString(String color) {
        int result = 0;
        if(color.equals(getString(R.string.black))) {
            result = Color.BLACK;
        } else if(color.equals(getString(R.string.blue))) {
            result = Color.BLUE;
        } else if(color.equals(getString(R.string.green))) {
            result = Color.GREEN;
        } else if(color.equals(getString(R.string.orange))) {
            result = Color.argb(255, 255, 108, 79);
        } else if(color.equals(getString(R.string.red))) {
            result = Color.RED;
        } else if(color.equals(getString(R.string.white))) {
            result = Color.WHITE;
        } else if(color.equals(getString(R.string.yellow))) {
            result = Color.YELLOW;
        }
        return result;
    }
    private void generateColor() {
        clearTags();
        Random r = new Random();
        int button = r.nextInt(6);
        String[] picker = getAllColorsFromString();
        current = picker[r.nextInt(picker.length)];
        showed.setTextColor(colorOfString(current));
        showed.setText(picker[r.nextInt(picker.length)]);
        setButton(button, current);
        setTag(button, "this");
        List<String> todo = without(picker, current);
        for(int i = 0; i<6; i++) {
            if(i!=button) setButton(i, todo.get(i));
        }
    }
    private void showResult() {
        result.setText(getString(R.string.tru)+": "+tru+
        "\n"+getString(R.string.fals)+": "+fals);
    }
    private String[] getAllColorsFromString() {
        String[] result = new String[7];
        result[0] = getString(R.string.black);
        result[1] = getString(R.string.blue);
        result[2] = getString(R.string.green);
        result[3] = getString(R.string.orange);
        result[4] = getString(R.string.red);
        result[5] = getString(R.string.white);
        result[6] = getString(R.string.yellow);
        return result;
    }
    private List<String> without(String[] base, String out) {
        List<String> result = new ArrayList<>();
        for(int i = 0; i <base.length; i++) {
            if(!base[i].equals(out)) {
                result.add(base[i]);
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
        Colors.this.finish();
    }

}
