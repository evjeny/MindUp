package com.evjeny.mentalarithmetic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Evjeny on 23.12.2016.
 * at 9:10
 */
public class ShulteActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private TextView nn;
    private ProgressBar pb;
    private Button[] buttons;
    private int last = 1, part = Settings.SHULTE_TIME/100;
    private CountDownTimer cdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shulte);
        tableLayout = (TableLayout) findViewById(R.id.shulte_table_layout);
        nn = (TextView) findViewById(R.id.shulte_next_num);
        pb = (ProgressBar) findViewById(R.id.shulte_pb);
        nn.setText(getString(R.string.next_num)+": "+last);
        addButtons(5,5);
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt = new CountDownTimer(Settings.SHULTE_TIME, 1000) {
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
            ds.showDialogWithOneButton(getString(R.string.sq_sh), getString(R.string.sq_sh_info),
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
    private void addButtons(int rows, int columns) {
        buttons = new Button[rows*columns];
        for(int i = 0; i<rows; i++) { //adding rows
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            for(int j = 0; j<columns; j++) {
                int current = j+columns*i;
                buttons[current] = new Button(this);
                buttons[current].setOnClickListener(listener);
                TableRow.LayoutParams lp = new TableRow.LayoutParams();
                lp.weight = 1;
                buttons[i].setLayoutParams(lp);
                tableRow.addView(buttons[current],j);

            }
            tableLayout.addView(tableRow,i);
        }
        tableLayout.setStretchAllColumns(true);
        fillButtons(buttons, createArray(1,rows*columns));
    }
    private void fillButtons(Button[] array, String[] values) {
        ArrayList<Button> bts = new ArrayList<>(Arrays.asList(array));
        Random r = new Random();
        for(int i = 0; i<values.length; i++) {
            int ll = bts.size();
            int current = r.nextInt(ll);
            bts.get(current).setText(values[i]);
            bts.remove(current);
        }
    }
    private String[] createArray(int from, int to) {
        String[] result = new String[to-from+1];
        for(int i=0;i<result.length;i++) {
            result[i] = String.valueOf(from+i);
        }
        return result;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            if(b.getText().toString().equals(String.valueOf(last))) {
                if(last == 25) finishWithResult();
                else {
                    last+=1;
                    nn.setText(getString(R.string.next_num)+": "+last);
                }
            } else {
                nn.setText(getString(R.string.next_num)+": "+last);
            }
        }
    };
    private void finishWithResult()
    {
        String tos;
        int tru;
        if(last<25) {
            tru = last - 1;
            tos = getString(R.string.tru) + ":" + tru + "/25";
        } else {
            tru = last;
            tos = getString(R.string.tru) + ":" + tru + "/25";
        }
        Toast.makeText(getApplicationContext(), tos, Toast.LENGTH_LONG).show();
        Bundle conData = new Bundle();
        conData.putIntArray("result", new int[] {tru, 25});
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        ShulteActivity.this.finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(cdt!=null) cdt.cancel();
    }
    @Override
    public void onBackPressed() {
        int tru;
        if(last<25) {
            tru = last - 1;
        } else {
            tru = last;
        }
        Bundle conData = new Bundle();
        conData.putIntArray("result", new int[] {tru, (25-tru)});
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
