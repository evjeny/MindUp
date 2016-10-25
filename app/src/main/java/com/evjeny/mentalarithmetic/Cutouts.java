package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Evjeny on 22.10.2016.
 * at 17:16
 */
public class Cutouts extends Activity {
    ImageView main;
    ImageButton one, two, three, four, five, six;
    TextView result;

    private int tru, fals;
    private String exs = "cutouts/exs",
    ans = "cutouts/ans";
    Random r = new Random();
    AssetManager am;
    private boolean use_timer, started;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cu);
        main = (ImageView) findViewById(R.id.cu_main);
        result = (TextView) findViewById(R.id.cu_result);
        am = this.getResources().getAssets();
        one = (ImageButton) findViewById(R.id.cub_one);
        two = (ImageButton) findViewById(R.id.cub_two);
        three = (ImageButton) findViewById(R.id.cub_three);
        four = (ImageButton) findViewById(R.id.cub_four);
        five = (ImageButton) findViewById(R.id.cub_five);
        six = (ImageButton) findViewById(R.id.cub_six);
        initImgs();
    }
    public void clicked(View v) {
            if(v.getTag().equals("this")) {
                tru+=1;
            } else {
                fals+=1;
            }
            initImgs();
        showResult();
    }

    private void initImgs() {
        clearIBTags();
        String[] files = amList("cutouts/ans");
        String f = files[r.nextInt(files.length)];
        main.setImageBitmap(fs(amOpen(exs+ File.separator+f)));
        Bitmap current = fs(amOpen(ans+File.separator+f));
        int btodo = r.nextInt(6);
        setSrc(btodo, current);
        setThisTag(btodo);
        List<String> newp = amListWithout(ans, f);
        for(int i = 0; i<6; i++) {
            if(i!=btodo) {
                setSrc(i, fs(amOpen(ans+File.separator+newp.get(r.nextInt(newp.size())))));
            } else {
                setSrc(i, current);
            }
        }
    }
    private void clearIBTags() {
        one.setTag("null");
        two.setTag("null");
        three.setTag("null");
        four.setTag("null");
        five.setTag("null");
        six.setTag("null");
    }
    private void showResult() {
        result.setText(getString(R.string.tru)+": "+tru+
        "\n"+getString(R.string.fals)+": "+fals);
    }
    private void setThisTag(int count) {
        switch (count) {
            case 0:
                one.setTag("this");
            case 1:
                two.setTag("this");
            case 2:
                three.setTag("this");
            case 3:
                four.setTag("this");
            case 4:
                five.setTag("this");
            case 5:
                six.setTag("this");
        }
    }
    private void setSrc(int count, Bitmap todo) {
        switch (count) {
            case 0:
                one.setImageBitmap(todo);
            case 1:
                two.setImageBitmap(todo);
            case 2:
                three.setImageBitmap(todo);
            case 3:
                four.setImageBitmap(todo);
            case 4:
                five.setImageBitmap(todo);
            case 5:
                six.setImageBitmap(todo);
        }
    }
    private String[] amList(String path) {
        try {
            return am.list(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private Bitmap fs(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }
    private InputStream amOpen(String path) {
        try {
            return am.open(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private List<String> amListWithout(String path, String out) {
        List<String> result = new ArrayList<>();
        String[] base = amList(path);
        for(int i = 0; i <base.length; i++) {
            if(!base[i].equals(out)) {
                result.add(base[i]);
            }
        }
        return result;
    }
}
