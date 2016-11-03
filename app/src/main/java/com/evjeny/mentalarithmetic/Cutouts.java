package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageView main;
    private ImageButton one, two, three, four, five, six;
    private TextView result;
    private LinearLayout root;
    private ProgressBar pb;

    private int tru = 0, fals = 0, part = Settings.CUTOUTS_TIME/100;
    private String exs = "cutouts/exs",
    ans = "cutouts/ans";
    private Random r = new Random();
    private AssetManager am;
    private CountDownTimer cdt = null;

    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cu);
        main = (ImageView) findViewById(R.id.cu_main);
        result = (TextView) findViewById(R.id.cu_result);
        root = (LinearLayout) findViewById(R.id.cu_root);
        am = this.getResources().getAssets();
        one = (ImageButton) findViewById(R.id.cub_one);
        two = (ImageButton) findViewById(R.id.cub_two);
        three = (ImageButton) findViewById(R.id.cub_three);
        four = (ImageButton) findViewById(R.id.cub_four);
        five = (ImageButton) findViewById(R.id.cub_five);
        six = (ImageButton) findViewById(R.id.cub_six);
        pb = (ProgressBar) findViewById(R.id.cu_pb);
        h = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                clearIBTags();
                Bundle b = msg.getData();
                String[] files = b.getStringArray("files");
                int btodo = b.getInt("this");
                setThisTag(btodo);
                for(int i = 0; i<files.length; i++) {
                    Bitmap bmp = fs(amOpen(files[i]));
                    setSrc(i, bmp);
                }
            }
        };
        initImgs();
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt = new CountDownTimer(Settings.CUTOUTS_TIME, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    pb.setProgress((int)millisUntilFinished/part);
                    if(millisUntilFinished<10000) {
                        setTitle(""+millisUntilFinished/1000);
                    }
                }

                @Override
                public void onFinish() {
                    Toast.makeText(getApplicationContext(), getString(R.string.tru) + ":" + tru
                            + "\n" + getString(R.string.fals) + ":" + fals, Toast.LENGTH_LONG).show();
                    Cutouts.this.finish();
                }
            };
        }
        DialogShower ds = new DialogShower(this);
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("hints", true)) {
            ds.showDialogWithOneButton(getString(R.string.cutouts), getString(R.string.cu_info),
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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String[] result = new String[7];
                String[] files = amList("cutouts/ans");
                String f = files[r.nextInt(files.length)];
                result[6] = exs+"/"+f;
                int btodo = r.nextInt(6);
                result[btodo] = ans+"/"+f;
                List<String> newp = amListWithout(ans, f);
                for(int i = 0; i<6; i++) {
                    if(i!=btodo) {
                        result[i] = ans+"/"+newp.get(r.nextInt(newp.size()));
                    }
                }
                Message message = new Message();
                Bundle data = new Bundle();
                data.putStringArray("files", result);
                data.putInt("this", btodo);
                message.setData(data);
                h.sendMessage(message);
            }
        };
        Thread t = new Thread(runnable);
        t.start();
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
            case 6:
                main.setImageBitmap(todo);
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

    @Override
    protected void onStop() {
        super.onStop();
        if(cdt!=null) cdt.cancel();
    }
}
