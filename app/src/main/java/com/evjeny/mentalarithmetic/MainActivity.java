package com.evjeny.mentalarithmetic;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void ma(View view) {
        Intent ma = new Intent(this, MA.class);
        startActivity(ma);
    }
    public void logic(View v) {
        Intent logic = new Intent(this, Logic.class);
        startActivity(logic);
    }
    public void logic_two(View v) {
        Intent logic = new Intent(this, LogicTwo.class);
        startActivity(logic);
    }
    public void logic_three(View v) {
        Intent logic = new Intent(this, LogicThree.class);
        startActivity(logic);
    }
    public void num_chain(View v) {
        Intent nc = new Intent(this, NumChain.class);
        startActivity(nc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent prefs = new Intent(this, Settings.class);
                startActivity(prefs);
            default:
                return super.onMenuItemSelected(featureId, item);
        }
    }
}
