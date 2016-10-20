package com.evjeny.mentalarithmetic;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Evjeny on 19.10.2016.
 * at 21:30
 */
public class Settings extends PreferenceActivity {
    public static int LOGICS_TIME = 10000,
    MA_TIME = 10000, NUM_CHAIN_TIME = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
