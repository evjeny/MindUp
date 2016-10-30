package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button cu_three_button = (Button) findViewById(R.id.cu_three_button);
        cu_three_button.setEnabled(false);
        if(new File("/sdcard/cutouts/additional.zip").exists()) {
            cu_three_button.setEnabled(true);
        }
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
    public void cu(View v) {
        Intent cu = new Intent(this, Cutouts.class);
        startActivity(cu);
    }
    public void cu_two(View v) {
        Intent cu_two = new Intent(this, CutoutsTwo.class);
        startActivity(cu_two);
    }
    public void cu_two_info(View v) {
        DialogShower ds = new DialogShower(this);
        ds.showDialogWithOneButton(getString(R.string.cutouts_two),
        getString(R.string.cu_two_info), getString(R.string.ok), R.drawable.info);
    }
    public void cu_three(View v) {
        Intent cu_three = new Intent(this, CutoutsThree.class);
        startActivity(cu_three);
    }
    public void cu_three_info(View v) {
        Intent info = new Intent(this, CutoutsThreeInfo.class);
        startActivity(info);
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
