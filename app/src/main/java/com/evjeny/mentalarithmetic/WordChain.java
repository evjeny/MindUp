package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

/**
 * Created by Evjeny on 29.10.2016.
 * at 9:52
 */
public class WordChain extends Activity {
    TextView shower;
    //25 x 8
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_chain_show);
        String currentsize = PreferenceManager
        .getDefaultSharedPreferences(this).getString("w_ch_size", "1");

    }
}
