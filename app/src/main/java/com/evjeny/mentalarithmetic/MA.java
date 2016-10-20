package com.evjeny.mentalarithmetic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Евгений on 29.09.2016.
 */
public class MA extends Activity {
    EditText result;
    TextView primer, nums;
    Random r;
    public int resul = 0; //Правильный ответ на пример
    public int tru = 0; //Кол-во правильных ответов на примеры
    public int fals = 0; //Кол-во неправильных ответов
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ma);
        result = (EditText) findViewById(R.id.ma_result);
        primer = (TextView) findViewById(R.id.ma_primer);
        nums = (TextView) findViewById(R.id.ma_nums);
        r = new Random();
    }
    public String[] generateSum(int max) {
        /**TODO Создает рандомную сумму или разность из 2 чисел, меньше @max
         * В нулевой ячейке находится первое число, во второй - второе,
         * в третьей лежит ответ, а в четвёртой - знак (+ или -)
         */
        int one = r.nextInt(max);
        int two = r.nextInt(max);
        int pick = r.nextInt(2);
        String[] result = new String[4];
        result[0]=String.valueOf(one);
        result[1]=String.valueOf(two);
        switch (pick) {
            case 0:
                result[2]=String.valueOf(two+one);
                result[3]="+";
                return result;
            case 1:
                result[2]=String.valueOf(one-two);
                result[3]="-";
                return result;
            default:
                result[2]=String.valueOf(two+one);
                result[3]="+";
                return result;
        }
    }
    public String[] generateMult(int max) {
        /**TODO Создает рандомное произведение (a<max)*11
         * В нулевой ячейке находится первое число, во второй - результат
         */
        int one = r.nextInt(max);
        String[] result = new String[2];
        result[0]=String.valueOf(one);
        result[1]=String.valueOf(one*11);
        return result;
    }
    public void next(View v) {
        if(resul!=0&&!result.getText().toString().equals("")) {
            if(result.getText().toString().equals(String.valueOf(resul))) {
                tru += 1;
                nums.setText(getText(R.string.tru)+":"+tru
                +"\n"+getText(R.string.fals)+":"+fals);
            } else {
                fals += 1;
                nums.setText(getText(R.string.tru)+":"+tru
                +"\n"+getText(R.string.fals)+":"+fals);
            }
        }
        switch (r.nextInt(2)) {
            case 0:
                String[] sum = generateSum(1000);
                primer.setText(sum[0]+sum[3]+sum[1]+"=");
                resul = Integer.valueOf(sum[2]);
                break;
            case 1:
                String[] mp = generateMult(1000);
                primer.setText(mp[0]+"*11=");
                resul = Integer.valueOf(mp[1]);
                break;
        }
        result.setText("");
    }
}
