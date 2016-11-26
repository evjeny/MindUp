package com.evjeny.mentalarithmetic;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Evjeny on 30.10.2016.
 * at 9:50
 */
public class CutoutsThreeInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cu_three_info);
        TextView tv = (TextView) findViewById(R.id.cu_three_info_tv);
        tv.setText(getString(R.string.cu_three_info_po)+"\n"+
        getString(R.string.cu_three_info_pt)+" "+ Environment.getExternalStorageDirectory()
                +"/cutouts/ "+getString(R.string.cu_three_info_pth));
    }
}
