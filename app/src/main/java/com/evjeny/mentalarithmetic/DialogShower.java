package com.evjeny.mentalarithmetic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Evjeny on 28.10.2016.
 * at 20:52
 */
public class DialogShower {
    //Нет, shower - это не "душ"
    private Context c;
    public DialogShower(Context context) {
        c = context;
    }
    public void showDialogWithOneButton(String title, String message, String buttonText, int iconResId) {
        //Что же делает эта функция?
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        if(iconResId!=0) {
            builder.setIcon(iconResId);
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showDialogWithOneButton(String title, String message, String buttonText,
        int iconResId, DialogInterface.OnClickListener onClickListener) {
        //Что же делает эта функция?
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(buttonText, onClickListener);
        builder.setCancelable(false);
        if(iconResId!=0) {
            builder.setIcon(iconResId);
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
