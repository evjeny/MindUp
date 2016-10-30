package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Evjeny on 30.10.2016.
 * at 9:50
 */
public class CutoutsThreeInfo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cu_three_info);
        TextView tv = (TextView) findViewById(R.id.cu_three_info_tv);
        tv.setText(getString(R.string.cu_three_info_po)+"\n"+
        getString(R.string.cu_three_info_pt));
    }
}
