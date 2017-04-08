package com.evjeny.mentalarithmetic;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Evjeny on 03.11.2016.
 * at 21:27
 */
public class Saver {
    private static File save_dir = new File(Environment.getExternalStorageDirectory()+File.separator+"MindUp"),
    root_dir  = Environment.getExternalStorageDirectory();
    public static void saveFileToMindUp(String name, byte[] file) {
        /**TODO Save file to /sdcard/MindUp
         *      Сохраняет файл в /sdcard/MindUp **/
        save_dir.mkdir();
        File current = new File(save_dir+File.separator+name);
        try {
            FileOutputStream fos = new FileOutputStream(current);
            fos.write(file);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String saveToMindUpWithCurrentDate(String prefix, byte[] file, String extension) {
        /**TODO Save file to /sdcard/MindUp, where name is current date
         *      Сохраняет файл в /sdcard/MindUp, в качестве имени использует текущую дату **/
        save_dir.mkdir();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_hh:mm:ss");
        String filename = prefix+sdf.format(new Date(System.currentTimeMillis()))+extension;
        File current = new File(save_dir+File.separator+filename);
        try {
            FileOutputStream fos = new FileOutputStream(current);
            fos.write(file);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return current.getPath();
    }
    public static String saveToMindUpWithCurrentDate(String name, byte[] file) {
        /**TODO Save file to /sdcard/MindUp, where name of file is current date + name
         *      Сохраняет файл в /sdcard/MindUp, в качестве имени использует текущую
         *      дату и переменную name **/
        save_dir.mkdir();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-hh:mm:ss");
        String filename = name+"_"+sdf.format(new Date(System.currentTimeMillis()))+".txt";
        File current = new File(save_dir+File.separator+filename);
        try {
            FileOutputStream fos = new FileOutputStream(current);
            fos.write(file);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return current.getPath();
    }
    public static void saveFileToRoot(String name, byte[] file) {
        /**TODO Save file to /sdcard/
         *      Сохраняет файл в /sdcard/ **/
        File current = new File(root_dir+File.separator+name);
        try {
            FileOutputStream fos = new FileOutputStream(current);
            fos.write(file);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
