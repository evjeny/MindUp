package com.evjeny.mentalarithmetic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private TextView last;
    private DialogShower ds;
    private String[][] results = new String[20][2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        last = (TextView) findViewById(R.id.main_last_result);
        ds = new DialogShower(this);
        clear_results();
        Button cu_three_button = (Button) findViewById(R.id.cu_three_button);
        cu_three_button.setEnabled(false);
        if (new File("/sdcard/cutouts/additional.zip").exists()) {
            cu_three_button.setEnabled(true);
        }
    }

    private void clear_results() {
        for (int i = 0; i < results.length; i++) {
            results[i][0] = "0";
            results[i][1] = "0";
        }
    }

    private void writeToMindUp() {
        String t = getString(R.string.tru) + ": ",
                f = getString(R.string.fals) + ": ",
                n = "\n";

        String res = getString(R.string.ma) + n + t + results[0][0] + n + f + results[0][1] + n + n +
                getString(R.string.ma_two) + n + t + results[10][0] + n + f + results[10][1] + n + n +
                getString(R.string.squares_one) + n + t + results[12][0] + n + f + results[12][1] + n + n +
                getString(R.string.squares_two) + n + t + results[13][0] + n + f + results[13][1] + n + n +
                getString(R.string.logic) + n + t + results[1][0] + n + f + results[1][1] + n + n +
                getString(R.string.logic_two) + n + t + results[2][0] + n + f + results[2][1] + n + n +
                getString(R.string.logic_three) + n + t + results[3][0] + n + f + results[3][1] + n + n +
                getString(R.string.num_chain) + n + t + results[4][0] + "/50" + n + n +
                getString(R.string.cutouts) + n + t + results[5][0] + n + f + results[5][1] + n + n +
                getString(R.string.cutouts_two) + n + t + results[6][0] + n + f + results[6][1] + n + n +
                getString(R.string.cutouts_three) + n + t + results[7][0] + n + f + results[7][1] + n + n +
                getString(R.string.sq_sh) + n + t + results[14][0] + "/" + results[14][1] + n + n +
                getString(R.string.colors) + n + t + results[8][0] + n + f + results[8][1] + n + n +
                getString(R.string.pi) + n + t + results[9][0] + "/200" + n + n +
                getString(R.string.text) + n + t + results[11][0] + n + f + results[11][1];
        String path = Saver.saveToMindUpWithCurrentDate("results", res.getBytes());
        ds.showDialogWithOneButton(getString(R.string.restart),
                getString(R.string.saved_as) + path, getString(R.string.ok), R.drawable.info);
    }

    public void ma(View viw) {
        //1
        Intent ma = new Intent(this, MA.class);
        startActivityForResult(ma, 1);
    }

    public void logic(View v) {
        //2
        Intent logic = new Intent(this, Logic.class);
        startActivityForResult(logic, 2);
    }

    public void logic_two(View v) {
        //3
        Intent logic = new Intent(this, LogicTwo.class);
        startActivityForResult(logic, 3);
    }

    public void logic_three(View v) {
        //4
        Intent logic = new Intent(this, LogicThree.class);
        startActivityForResult(logic, 4);
    }

    public void num_chain(View v) {
        //5
        Intent nc = new Intent(this, NumChain.class);
        startActivityForResult(nc, 5);
    }

    public void cu(View v) {
        //6
        Intent cu = new Intent(this, Cutouts.class);
        startActivityForResult(cu, 6);
    }

    public void cu_two(View v) {
        //7
        Intent cu_two = new Intent(this, CutoutsTwo.class);
        startActivityForResult(cu_two, 7);
    }

    public void cu_two_info(View v) {
        ds.showDialogWithOneButton(getString(R.string.cutouts_two),
                getString(R.string.cu_two_info), getString(R.string.ok), R.drawable.info);
    }

    public void cu_three(View v) {
        //8
        Intent cu_three = new Intent(this, CutoutsThree.class);
        startActivityForResult(cu_three, 8);
    }

    public void cu_three_info(View v) {
        Intent info = new Intent(this, CutoutsThreeInfo.class);
        startActivity(info);
    }

    public void word_chain(View v) {
        Intent words = new Intent(this, WordChain.class);
        startActivity(words);
    }

    public void colors(View v) {
        //9
        Intent colors = new Intent(this, Colors.class);
        startActivityForResult(colors, 9);
    }

    public void pi(View v) {
        //10
        Intent pi = new Intent(this, PiActivity.class);
        startActivityForResult(pi, 10);
    }

    public void ma_two(View v) {
        //11
        Intent ma_two = new Intent(this, MATwo.class);
        startActivityForResult(ma_two, 11);
    }

    public void graphs(View v) {
        Intent graphs = new Intent(this, Graphs.class);
        startActivity(graphs);
    }

    public void text(View v) {
        Intent text = new Intent(this, TextActivity.class);
        startActivityForResult(text, 12);
    }

    public void squares_one(View view) {
        Intent sq_one = new Intent(this, SquaresActivity.class);
        sq_one.putExtra("title", getString(R.string.squares_one));
        sq_one.putExtra("type", 0);
        startActivityForResult(sq_one, 13);
    }

    public void squares_two(View view) {
        Intent sq_two = new Intent(this, SquaresActivity.class);
        sq_two.putExtra("title", getString(R.string.squares_two));
        sq_two.putExtra("type", 1);
        startActivityForResult(sq_two, 14);
    }
    public void sq_sh(View v) {
        Intent sq = new Intent(this, ShulteActivity.class);
        startActivityForResult(sq, 15);
    }

    public void save(View v) {
        writeToMindUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent prefs = new Intent(this, Settings.class);
                startActivity(prefs);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.ma) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[0][0] = String.valueOf(todo[0]);
                    results[0][1] = String.valueOf(todo[1]);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.logic) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[1][0] = String.valueOf(todo[0]);
                    results[1][1] = String.valueOf(todo[1]);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.logic_two) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[2][0] = String.valueOf(todo[0]);
                    results[2][1] = String.valueOf(todo[1]);
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.logic_three) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[3][0] = String.valueOf(todo[0]);
                    results[3][1] = String.valueOf(todo[1]);
                }
                break;
            case 5:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.num_chain) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "/50");
                    results[4][0] = String.valueOf(todo[0]);
                    results[4][1] = String.valueOf(todo[1]);
                }
                break;
            case 6:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.cutouts) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[5][0] = String.valueOf(todo[0]);
                    results[5][1] = String.valueOf(todo[1]);
                }
                break;
            case 7:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.cutouts_two) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[6][0] = String.valueOf(todo[0]);
                    results[6][1] = String.valueOf(todo[1]);
                }
                break;
            case 8:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.cutouts_three) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[7][0] = String.valueOf(todo[0]);
                    results[7][1] = String.valueOf(todo[1]);
                }
                break;
            case 9:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.colors) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[8][0] = String.valueOf(todo[0]);
                    results[8][1] = String.valueOf(todo[1]);
                }
                break;
            case 10:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.pi) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "/200");
                    results[9][0] = String.valueOf(todo[0]);
                    results[9][1] = String.valueOf(todo[1]);
                }
                break;
            case 11:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.ma_two) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[10][0] = String.valueOf(todo[0]);
                    results[10][1] = String.valueOf(todo[1]);
                }
            case 12:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.text) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[11][0] = String.valueOf(todo[0]);
                    results[11][1] = String.valueOf(todo[1]);
                }
            case 13:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.squares_one) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[12][0] = String.valueOf(todo[0]);
                    results[12][1] = String.valueOf(todo[1]);
                }
            case 14:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.squares_two) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "\n" +
                            getString(R.string.fals) + ": " + todo[1]);
                    results[13][0] = String.valueOf(todo[0]);
                    results[13][1] = String.valueOf(todo[1]);
                }
            case 15:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int[] todo = bundle.getIntArray("result");
                    last.setText(getString(R.string.sq_sh) + "\n" +
                            getString(R.string.tru) + ": " + todo[0] + "/" + todo[1]);
                    results[14][0] = String.valueOf(todo[0]);
                    results[14][1] = String.valueOf(todo[1]);
                }
        }
    }

    @Override
    public void onBackPressed() {
        ds.showDialogWithTwoButtons(getString(R.string.exit), getString(R.string.exit_f_a),
                0, false,
                getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }, getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

}
