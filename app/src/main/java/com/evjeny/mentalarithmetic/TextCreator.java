package com.evjeny.mentalarithmetic;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*
 * Created by Evjeny on 18.12.2016.
 * at 12:45
 */
public class TextCreator {
    private Context c;
    private Random random;
    private Resources res;

    private int space_year, space_men, space_women, space_people_total, space_ships, eng_class, doc_class,
    chem_class, space_days;
    private String space_MAIN_STORY, space_color_planet, space_color_star, space_name_planet, space_name_star,
    space_eng, space_doc, space_chem, space_name_cap, space_name_saver;

    public enum text_types {
        Space
    }
    public TextCreator(Context context) {
        c = context;
        random = new Random();
        res = c.getResources();
    }
    public void createText(text_types type) {
        switch (type) {
            case Space:
                space_MAIN_STORY = spaceStory();
                break;
        }
    }
    //space starts here
    private String spaceStory() {
        space_year = random.nextInt(30)+2001;
        space_ships = random.nextInt(6)+1;
        space_people_total = space_ships*random.nextInt(6)+6;
        space_men = random.nextInt(space_people_total)/2+1;
        space_women = space_people_total - space_men;
        eng_class = random.nextInt(5);
        doc_class = random.nextInt(5);
        chem_class = random.nextInt(5);
        space_days = random.nextInt(10)+2;
        space_color_planet = pickRandom(res.getStringArray(R.array.planet_color_text));
        space_color_star = pickRandom(res.getStringArray(R.array.star_color_text));
        space_name_planet = generateRandomName();
        space_name_star = generateRandomName();
        String[] names = getRandoms(res.getStringArray(R.array.names), 3);
        space_eng = names[0];
        space_doc = names[1];
        space_chem = names[2];
        String[] caps = getRandoms(res.getStringArray(R.array.caps_names), 2);
        space_name_cap = caps[0];
        space_name_saver = caps[1];
        String main_story = res.getString(R.string.space);
        main_story = main_story.replace("@year",String.valueOf(space_year)).
        replace("@color_planet", space_color_planet).
        replace("@color_star", space_color_star).
        replace("@planet_name", space_name_planet).
        replace("@star_name", space_name_star).
        replace("@men_count", String.valueOf(space_men)).
        replace("@women_count", String.valueOf(space_women)).
        replace("@spaceship_count", String.valueOf(space_ships)).
        replace("@engineer_class", String.valueOf(eng_class)).
        replace("@engineer_name", space_eng).
        replace("@doctor_class", String.valueOf(doc_class)).
        replace("@doctor_name", space_doc).
        replace("@chemist_class", String.valueOf(chem_class)).
        replace("@chemist_name", space_chem).
        replace("@days_count", String.valueOf(space_days)).
        replace("@cap_name", space_name_cap).
        replace("@saver_name", space_name_saver);
        return main_story;
    }
    public String getMainStory() {
        return space_MAIN_STORY;
    }
    public String getSpace_name_cap() {
        return space_name_cap;
    }
    public int getChem_class() {
        return chem_class;
    }
    public int getDoc_class() {
        return doc_class;
    }
    public int getEng_class() {
        return eng_class;
    }
    public int getSpace_days() {
        return space_days;
    }
    public int getSpace_men() {
        return space_men;
    }
    public int getSpace_ships() {
        return space_ships;
    }
    public int getSpace_women() {
        return space_women;
    }
    public int getSpace_year() {
        return space_year;
    }
    public String getSpace_chem() {
        return space_chem;
    }
    public String getSpace_color_planet() {
        return space_color_planet;
    }
    public String getSpace_color_star() {
        return space_color_star;
    }
    public String getSpace_doc() {
        return space_doc;
    }
    public String getSpace_eng() {
        return space_eng;
    }
    public String getSpace_name_planet() {
        return space_name_planet;
    }
    public String getSpace_name_saver() {
        return space_name_saver;
    }
    public String getSpace_name_star() {
        return space_name_star;
    }
    //space ends here :D

    public String pickRandom(String[] array) {
        return array[random.nextInt(array.length)];
    }
    public String generateRandomName() {
        String result = "";
        char[] chars = {'A','B','C','D','E','F','1','2','3','4','5','6'};
        int name_length = random.nextInt(4)+2;
        for(int i = 0; i<name_length; i++) {
            result+=chars[random.nextInt(chars.length)];
        }
        return result;
    }
    public String[] getRandoms(String[] array, int random_length) {
        String[] result = new String[random_length];
        ArrayList<String> list = new ArrayList<>(Arrays.asList(array));
        for(int i = 0; i<random_length; i++) {
            int random_item = random.nextInt(list.size());
            result[i] = list.get(random_item);
            list.remove(random_item);
        }
        return result;
    }
}
