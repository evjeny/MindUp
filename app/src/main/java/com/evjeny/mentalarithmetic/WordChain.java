package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Evjeny on 29.10.2016.
 * at 9:52
 */
public class WordChain extends AppCompatActivity {
    private String[] all_words, words;
    private int i = 0, counter = 0;
    private int tru = 0, fals = 0;
    private int time = 30000;
    private int chain_size = 0;
    private boolean is_enter = false;

    private ArrayAdapter<String> adapter;

    private ArrayList<String> wrds;

    private AutoCompleteTextView actv;

    private TextView tv, result;

    private Handler h;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_chain_show);
        tv = (TextView) findViewById(R.id.wc_show_tv);
        chain_size = Integer.valueOf(PreferenceManager
        .getDefaultSharedPreferences(this).getString("w_ch_size", "1"));
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
            case 4:
                time = 75000;
        }
        getNeededWords("dict/rus.txt", ";", time/3000);
        h = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                words = msg.getData().getStringArray("words");
                new CountDownTimer(time, 3000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tv.setText(words[i]);
                        i+=1;
                    }
                    @Override
                    public void onFinish() {
                        init_ans();
                        is_enter = true;
                    }
                }.start();
            }
        };
    }
    private String[] getWordsFromAssets(String path, String separator) throws IOException {
        AssetManager am = getAssets();
        InputStream is = am.open(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String buffer;
        String text = "";
        while((buffer=br.readLine())!=null) {
            text +=buffer;
        }
        return text.split(separator);
    }
    private String[] getSomeWords(String[] base, int count) {
        ArrayList<String> wrds = new ArrayList<>(Arrays.asList(base));
        String[] result = new String[count];
        for(int i = 0; i<count; i++) {
            int random_pos = random.nextInt(wrds.size());
            result[i] = wrds.get(random_pos);
            wrds.remove(random_pos);
        }
        return result;
    }

    private void getNeededWords(final String path, final String separator, final int size) {
        //function sends String[size] to Handler
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String[] result =  null;
                try {
                    all_words = getWordsFromAssets(path,separator);
                    result = getSomeWords(all_words, size);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putStringArray("words", result);
                msg.setData(data);
                h.sendMessage(msg);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    public void next(View v) {
        String text = actv.getText().toString();
        if(text.equals("")) {
            if (counter < words.length) {
                fals += 1;
                showResult();
                actv.setText("");
            } else {
                finishWithResult();
            }
            counter++;
        } else {
            if(counter<words.length) {
                if (text.equals(words[counter])) {
                    tru ++;
                } else {
                    fals ++;
                }
                showResult();
                actv.setText("");
            }
            else {
                finishWithResult();
            }
            counter++;
            adapter.remove(text);
            adapter.notifyDataSetChanged();
        }
    }
    private void init_ans() {
        setContentView(R.layout.word_chain_enter);
        actv = (AutoCompleteTextView) findViewById(R.id.wc_enter_acet);
        result = (TextView) findViewById(R.id.wc_enter_result);
        wrds = new ArrayList<>(Arrays.asList(all_words));
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, wrds);
        actv.setAdapter(adapter);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) parent.getItemAtPosition(position);
                if(counter<words.length) {
                    if (text.equals(words[counter])) {
                        tru ++;
                    } else {
                        fals ++;
                    }
                    showResult();
                    actv.setText("");
                }
                else {
                    finishWithResult();
                }
                counter++;
                wrds.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void showResult() {
        result.setText(getString(R.string.tru)+": "+tru+
                "\n"+getString(R.string.fals)+": "+fals);
    }
    @Override
    public void onBackPressed() {
        finishWithResult();
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
        WordChain.this.finish();
    }
}