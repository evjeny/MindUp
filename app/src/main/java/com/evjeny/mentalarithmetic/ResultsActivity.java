package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Evjeny on 07.04.2017.
 */

public class ResultsActivity extends AppCompatActivity {
    private ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        lv = (ListView) findViewById(R.id.results_lv);
        final String current_json = getIntent().getStringExtra("current");
        ArrayList<String> files = new ArrayList<>();
        files.add(0, getString(R.string.current_results));
        files.addAll(1, getFiles("res_", ".json"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, files);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Item curr = getItem(current_json);
                    showDialog(curr.getDate(), curr.getItems());
                } else {
                    TextView tv = (TextView) view;
                    String json = readFile(tv.getText().toString());
                    Item curr = getItem(json);
                    showDialog(curr.getDate(), curr.getItems());
                }
            }
        });
    }

    private void showDialog(String title, ArrayList<ArrayList<Integer>> items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        final ObjResAdapter adapter = new ObjResAdapter(this, items);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private File root() {
        return new File(Environment.getExternalStorageDirectory().getPath(), "MindUp");
    }
    private ArrayList<String> getFiles(String prefix, String suffix) {
        ArrayList<String> result = new ArrayList<>();
        File root = root();
        File[] files = root.listFiles();
        for(int i = 0; i<files.length; i++) {
            File current = files[i];
            String name = current.getName();
            if(name.startsWith(prefix)&&name.endsWith(suffix)) {
                result.add(name);
            }
        }
        return result;
    }
    private String readFile(String filename) {
        String result ="";
        File sdFile = new File(root(), filename);
        String str = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            while ((str = br.readLine()) != null) {
                result = result +"\n"+str;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Item getItem(String json) {
        JSONParser parser = new JSONParser();
        String date = "";
        ArrayList<ArrayList<Integer>> items = new ArrayList<>();
        try {
            JSONObject root = (JSONObject) parser.parse(json);
            date = (String) root.get("date");
            JSONArray array = (JSONArray) root.get("results");
            for(int i = 0; i<array.size(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                ArrayList<Integer> vals = new ArrayList<>();
                vals.add(0, ((Long) obj.get("name")).intValue());
                vals.add(1, ((Long) obj.get("true")).intValue());
                vals.add(2, ((Long) obj.get("false")).intValue());
                items.add(vals);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Item(date, items);
    }

    private class Item {
        private String date;
        private ArrayList<ArrayList<Integer>> items;

        Item(String date, ArrayList<ArrayList<Integer>> items) {
            this.date = date;
            this.items = items;
        }

        public String getDate() {
            return date;
        }

        public ArrayList<ArrayList<Integer>> getItems() {
            return items;
        }
    }
}
