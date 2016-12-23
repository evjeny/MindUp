package com.evjeny.mentalarithmetic;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Evjeny on 19.10.2016.
 * at 21:30
 */
public class Settings extends PreferenceActivity {
    public static int LOGICS_TIME = 120000, //120000
    MA_TIME = 120000, MA_TWO_TIME = 150000, NUM_CHAIN_TIME = 100000, CUTOUTS_TIME = 30000,
    COLORS_TIME = 60000, PI_TIME = 120000, SQUARES_TIME = 120000, TEXT_TIME = 150000,
    SHULTE_TIME = 40000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
