package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by Evjeny on 29.10.2016.
 * at 9:52
 */
public class WordChain extends Activity {
    private TextView shower;
    private int chain_size = 0;
    private Random random = new Random();
    private String[] words;
    private TextView tv;
    private  int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_chain_show);
        tv = (TextView) findViewById(R.id.wc_show_tv);
        chain_size = Integer.valueOf(PreferenceManager
        .getDefaultSharedPreferences(this).getString("w_ch_size", "1"));
        words = generateWords();
        int time = 30000;
        switch (chain_size) {
            case 1:
                time = 30000;
                break;
            case 2:
                time = 45000;
                break;
            case 3:
                time = 60000;
                break;
        }
        new CountDownTimer(time, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv.setText(words[i]);
                i+=1;
            }

            @Override
            public void onFinish() {
                setContentView(R.layout.word_chain_enter);
            }
        }.start();
    }
    private String[] generateWords() {
        final String[] resultt = new String[20];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String result = "";
                try {
                    InputStream is = getAssets().open("dict/words_ru.txt");
                    result = fromIs(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] words = result.split(" ");
                int current_value = 0;
                for(int i = 0; i<20; i++) {
                    int margin = random.nextInt(1000);
                    current_value += margin;
                    resultt[i] = words[current_value];
                    current_value += 2000;
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        return resultt;
    }
    private String fromIs(InputStream inputStream) {
        byte[] rb = new byte[] {};
        try {
            inputStream.read(rb);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(rb);
    }
}
