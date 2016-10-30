package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Evjeny on 30.10.2016.
 * at 10:09
 */
public class CutoutsThree extends Activity {
    ImageView main;
    ImageButton one, two, three, four, five, six;
    TextView result;
    private int tru = 0, fals = 0;
    private String ans = root()+"/cutouts/ans",
    exs = root()+"/cutouts/exs";
    Random r = new Random();
    private boolean use_timer, started;
    private CountDownTimer cdt;
    DialogShower ds;

    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cu);
        main = (ImageView) findViewById(R.id.cu_main);
        result = (TextView) findViewById(R.id.cu_result);
        one = (ImageButton) findViewById(R.id.cub_one);
        two = (ImageButton) findViewById(R.id.cub_two);
        three = (ImageButton) findViewById(R.id.cub_three);
        four = (ImageButton) findViewById(R.id.cub_four);
        five = (ImageButton) findViewById(R.id.cub_five);
        six = (ImageButton) findViewById(R.id.cub_six);
        ds = new DialogShower(getApplicationContext());
        try {
            unzip(new File(root()+"/cutouts/additional.zip"), new File(root()+"/cutouts/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        h = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                clearIBTags();
                Bundle b = msg.getData();
                String[] files = b.getStringArray("files");
                int btodo = b.getInt("this");
                setThisTag(btodo);
                for(int i = 0; i<files.length; i++) {
                    Bitmap bmp = fromPath(files[i]);
                    setSrc(i, bmp);
                }
            }
        };
        initImgs();
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt = new CountDownTimer(Settings.CUTOUTS_TIME, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Toast.makeText(getApplicationContext(), getString(R.string.tru) + ":" + tru
                            + "\n" + getString(R.string.fals) + ":" + fals, Toast.LENGTH_LONG).show();
                    CutoutsThree.this.finish();
                }
            }.start();}
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
                String[] files = filesFromSd(root(), "/cutouts/ans");
                String f = files[r.nextInt(files.length)];
                result[6] = exs+"/"+f;
                int btodo = r.nextInt(6);
                result[btodo] = ans+"/"+f;
                List<String> newp = amListWithout("ans", f);
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

    /**working code:
     * clearIBTags();
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
     */
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
    private Bitmap fromPath(String path) {
        return BitmapFactory.decodeFile(path);
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
    private String[] filesFromSd(File root, String dir_name) {
        File s = new File(root+File.separator+dir_name);
        return  s.list();
    }
    private File root() {
        String rootka;
        if(exists("/sdcard/")) {
            rootka = "/sdcard/";
        } if(exists("/storage/sdcard0/")) {
            rootka = "/storage/sdcard0/";
        } if(exists("/storage/sdcard1/")) {
            rootka = "/sdcard1/";
        } if(exists("/storage/extSdCard/")) {
            rootka = "/storage/extSdCard/";
        }
        return Environment.getExternalStorageDirectory();
    }
    private boolean exists(String path) {
        if(new File(path).exists()) return true;
        else return false;
    }
    private List<String> amListWithout(String path, String out) {
        List<String> result = new ArrayList<>();
        String[] base = filesFromSd(root(), "/cutouts/"+path);
        for(int i = 0; i <base.length; i++) {
            if(!base[i].equals(out)) {
                result.add(base[i]);
            }
        }
        return result;
    }
    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        //Thx http://stackoverflow.com/users/995891/zapl for his code
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            }
        } finally {
            zis.close();
        }
    }
}
