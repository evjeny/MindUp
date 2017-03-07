package com.evjeny.mentalarithmetic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Evjeny on 18.12.2016.
 * at 15:48
 * P.s. I'm sorry but I wont translate this activity
 * P.s.s. Class of stupid comments
 */
public class TextActivity extends AppCompatActivity {
    private TextView out, question, nums;
    private ProgressBar pb, text_pb;
    private TextCreator tc;

    private RadioGroup answers;
    private RadioButton a1,a2,a3,a4;

    private ArrayList<String> special_track = new ArrayList<>();
    private String[][] qaa; //like a frog (questions and answers)
    private String trueAnswer;
    private int tru, fals, current_qaa_pos = 0;

    private CountDownTimer cdt_view, cdt_answers;
    private int part = Settings.TEXT_TIME/100;

    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);
        initText();
    }
    public void init_answers(View v) {
        initAnswers();
    }
    public void next(View v) {
        RadioButton current = (RadioButton) findViewById(answers.getCheckedRadioButtonId());
        if(current_qaa_pos!=9) {
            if(current.getText().toString().equals(trueAnswer)) {
                tru+=1;
                current_qaa_pos+=1;
                viewsInitializer(qaa, current_qaa_pos);
            } else {
                fals+=1;
                current_qaa_pos+=1;
                viewsInitializer(qaa, current_qaa_pos);
            }
            nums.setText(getString(R.string.tru)+": "+tru+
                    "\n"+getString(R.string.fals)+": "+fals);
        } else if(current_qaa_pos==9) {
            if(current.getText().toString().equals(trueAnswer)) {
                tru+=1;
            } else {
                fals+=1;
            }
            finishWithResult();
        }
    }
    private void initText() {
        out = (TextView) findViewById(R.id.text_tv);
        text_pb = (ProgressBar) findViewById(R.id.text_view_pb);
        tc = new TextCreator(this);
        tc.createText(TextCreator.text_types.Space);
        out.setText(tc.getMainStory());
        special_track.clear();
        //because cycles for strongless (Evjeny.createNewWord())
        special_track.add(vo(tc.getSpace_year()));
        special_track.add(tc.getSpace_color_planet());
        special_track.add(tc.getSpace_name_planet());
        special_track.add(tc.getSpace_color_star());
        special_track.add(tc.getSpace_name_star());
        special_track.add(vo(tc.getSpace_men()));
        special_track.add(vo(tc.getSpace_women()));
        special_track.add(vo(tc.getSpace_ships()));
        special_track.add(vo(tc.getEng_class()));
        special_track.add(tc.getSpace_eng());
        special_track.add(vo(tc.getDoc_class()));
        special_track.add(tc.getSpace_doc());
        special_track.add(vo(tc.getChem_class()));
        special_track.add(tc.getSpace_chem());
        special_track.add(vo(tc.getSpace_days()));
        special_track.add(tc.getSpace_name_cap());
        special_track.add(tc.getSpace_name_saver());
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt_view = new CountDownTimer(Settings.TEXT_TIME, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    text_pb.setProgress((int)millisUntilFinished/part);
                    if(millisUntilFinished<10000) {
                        setTitle(""+millisUntilFinished/1000);
                    }
                }
                @Override
                public void onFinish() {
                    initAnswers();
                }
            }.start();}
        DialogShower ds = new DialogShower(this);
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("hints", true)) {
            ds.showDialogWithOneButton(getString(R.string.text), getString(R.string.text_info),
                    getString(R.string.ok), R.drawable.info, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (cdt_view != null) cdt_view.start();
                        }
                    });
        }
        else {
            if(cdt_view!=null) cdt_view.start();
        }
    }
    private void initAnswers() {
        setContentView(R.layout.text_answers);
        question = (TextView) findViewById(R.id.text_question);
        answers = (RadioGroup) findViewById(R.id.text_rg);
        a1 = (RadioButton) findViewById(R.id.text_answer_one);
        a2 = (RadioButton) findViewById(R.id.text_answer_two);
        a3 = (RadioButton) findViewById(R.id.text_answer_thr);
        a4 = (RadioButton) findViewById(R.id.text_answer_four);
        nums = (TextView) findViewById(R.id.text_nums);
        pb = (ProgressBar) findViewById(R.id.text_pb);
        qaa = generateQuestions(10);
        viewsInitializer(qaa, current_qaa_pos);
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("countdown", false)) {
            cdt_answers = new CountDownTimer(Settings.TEXT_TIME, 1000) {
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
            }.start();}
    }
    private void viewsInitializer(String[][] qaa, int current) {
        question.setText(qaa[current][0]);
        ArrayList<RadioButton> buttons = new ArrayList<>();
        buttons.add(a1);
        buttons.add(a2);
        buttons.add(a3);
        buttons.add(a4);
        int rp = random.nextInt(buttons.size());
        buttons.get(rp).setText(qaa[current][1]);
        buttons.remove(rp);
        for(int i = 0; i<3; i++) {
            int rpa = random.nextInt(buttons.size());
            buttons.get(rpa).setText(qaa[current][i+2]);
            buttons.remove(rpa);
        }
        trueAnswer = qaa[current][1];
    }
    private void finishWithResult() {
        String tos = getString(R.string.tru) + ":" + tru
                + "\n" + getString(R.string.fals) + ":" + fals;
        Toast.makeText(getApplicationContext(), tos, Toast.LENGTH_LONG).show();
        Bundle conData = new Bundle();
        conData.putIntArray("result", new int[] {tru, fals});
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        TextActivity.this.finish();
    }
    private String[][] generateQuestions(int length) {
        String[][] result = new String[length][5]; //question and true answer with other answers
        int result_count = 0;
        ArrayList<Integer> picked_pos = new ArrayList<>(); //There's a fine line between bravery and stupidity.
        while(result_count<length) {
            int random_pos = random.nextInt(special_track.size());
            if(compare(random_pos, picked_pos)==false) {
                picked_pos.add(random_pos);
                String[] add_answers;
                switch (random_pos) {
                    case 0:
                        result[result_count][0] = "В каком году найдена планета?";
                        result[result_count][1] = special_track.get(random_pos);
                        result[result_count][2] = vo(random.nextInt(30)+2001);
                        result[result_count][3] = vo(random.nextInt(30)+2001);
                        result[result_count][4] = vo(random.nextInt(30)+2001);
                        break;
                    case 1:
                        result[result_count][0] = "Какую нашли планету?";
                        result[result_count][1] = special_track.get(random_pos);
                        add_answers = tc.getRandoms(getResources().getStringArray(R.array.planet_color_text),3);
                        result[result_count][2] = add_answers[0];
                        result[result_count][3] = add_answers[1];
                        result[result_count][4] = add_answers[2];
                        break;
                    case 2:
                        result[result_count][0] = "Какое название у планеты?";
                        result[result_count][1] = special_track.get(random_pos);
                        result[result_count][2] = tc.generateRandomName();
                        result[result_count][3] = tc.generateRandomName();
                        result[result_count][4] = tc.generateRandomName();
                        break;
                    case 3:
                        result[result_count][0] = "Возле какой звезды нашли планету?";
                        result[result_count][1] = special_track.get(random_pos);
                        add_answers = tc.getRandoms(getResources().
                                getStringArray(R.array.star_color_text),3);
                        result[result_count][2] = add_answers[0];
                        result[result_count][3] = add_answers[1];
                        result[result_count][4] = add_answers[2];
                        break;
                    case 4:
                        result[result_count][0] = "Какое название у звезды?";
                        result[result_count][1] = special_track.get(random_pos);
                        result[result_count][2] = tc.generateRandomName();
                        result[result_count][3] = tc.generateRandomName();
                        result[result_count][4] = tc.generateRandomName();
                        break;
                    case 5:
                        result[result_count][0] = "Сколько мужчин отправилось на планету?";
                        result[result_count][1] = special_track.get(random_pos);
                        result[result_count][2] = vo(random.nextInt(18)+1);
                        result[result_count][3] = vo(random.nextInt(18)+1);
                        result[result_count][4] = vo(random.nextInt(18)+1);
                        break;
                    case 6:
                        result[result_count][0] = "Сколько женщин отправилось на планету?";
                        result[result_count][1] = special_track.get(random_pos);
                        result[result_count][2] = vo(random.nextInt(18)+1);
                        result[result_count][3] = vo(random.nextInt(18)+1);
                        result[result_count][4] = vo(random.nextInt(18)+1);
                        break;
                    case 7:
                        result[result_count][0] = "Сколько кораблей отправилось к планете?";
                        result[result_count][1] = special_track.get(random_pos);
                        result[result_count][2] = vo(random.nextInt(6)+1);
                        result[result_count][3] = vo(random.nextInt(6)+1);
                        result[result_count][4] = vo(random.nextInt(6)+1);
                        break;
                    case 8:
                        result[result_count][0] = "Какой класс у инженера?";
                        result[result_count][1] = special_track.get(random_pos);
                        result[result_count][2] = vo(random.nextInt(5));
                        result[result_count][3] = vo(random.nextInt(5));
                        result[result_count][4] = vo(random.nextInt(5));
                        break;
                    case 9:
                        result[result_count][0] = "Какое имя у инженера?";
                        result[result_count][1] = special_track.get(random_pos);
                        add_answers = tc.getRandoms(getResources().getStringArray(R.array.names),3);
                        result[result_count][2] = add_answers[0];
                        result[result_count][3] = add_answers[1];
                        result[result_count][4] = add_answers[2];
                        break;
                    case 10:
                        result[result_count][0] = "Какой класс у доктора?";
                        result[result_count][1] = special_track.get(random_pos);
                        result[result_count][2] = vo(random.nextInt(5));
                        result[result_count][3] = vo(random.nextInt(5));
                        result[result_count][4] = vo(random.nextInt(5));
                        break;
                    case 11:
                        result[result_count][0] = "Какое имя у доктора?";
                        result[result_count][1] = special_track.get(random_pos);
                        add_answers = tc.getRandoms(getResources().getStringArray(R.array.names),3);
                        result[result_count][2] = add_answers[0];
                        result[result_count][3] = add_answers[1];
                        result[result_count][4] = add_answers[2];
                        break;
                    case 12:
                        result[result_count][0] = "Какой класс у химика?";
                        result[result_count][1] = special_track.get(random_pos);
                        result[result_count][2] = vo(random.nextInt(5));
                        result[result_count][3] = vo(random.nextInt(5));
                        result[result_count][4] = vo(random.nextInt(5));
                        break;
                    case 13:
                        result[result_count][0] = "Какое имя у химика?";
                        result[result_count][1] = special_track.get(random_pos);
                        add_answers = tc.getRandoms(getResources().getStringArray(R.array.names),3);
                        result[result_count][2] = add_answers[0];
                        result[result_count][3] = add_answers[1];
                        result[result_count][4] = add_answers[2];
                        break;
                    case 14:
                        result[result_count][0] = "Сколько дней проводились исследования?";
                        result[result_count][1] = special_track.get(random_pos);
                        result[result_count][2] = vo(random.nextInt(10)+2);
                        result[result_count][3] = vo(random.nextInt(10)+2);
                        result[result_count][4] = vo(random.nextInt(10)+2);
                        break;
                    case 15:
                        result[result_count][0] = "С кем группа не смогла выйти на связь?";
                        result[result_count][1] = special_track.get(random_pos);
                        add_answers = tc.getRandoms(getResources().getStringArray(R.array.caps_names),3);
                        result[result_count][2] = add_answers[0];
                        result[result_count][3] = add_answers[1];
                        result[result_count][4] = add_answers[2];
                        break;
                    case 16:
                        result[result_count][0] = "С кем связалась группа?";
                        result[result_count][1] = special_track.get(random_pos);
                        add_answers = tc.getRandoms(getResources().getStringArray(R.array.caps_names),3);
                        result[result_count][2] = add_answers[0];
                        result[result_count][3] = add_answers[1];
                        result[result_count][4] = add_answers[2];
                        break;
                }
                result_count+=1;
            }
        }
        return result;
    }
    private String vo(int todo) {
        return String.valueOf(todo);
    }
    private int vo(String todo) {
        return Integer.valueOf(todo);
    }
    private boolean compare(int todo, ArrayList<Integer> from) {
        boolean result = false;
        for(int i = 0; i<from.size(); i++) {
            if(todo==from.get(i)) {
                result = true;
            }
        }
        return result;
    }
    @Override
    public void onBackPressed() {
        Bundle conData = new Bundle();
        conData.putIntArray("result", new int[] {tru, fals});
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(cdt_view!=null) cdt_view.cancel();
        if(cdt_answers!=null) cdt_answers.cancel();
    }
}