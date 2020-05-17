package com.davidkazad.cevv.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Page;
import com.davidkazad.cevv.songs.CEVV;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataUtil {

    private static final String FILENAME = "C:\\DATA\\AndroidStudioProjects\\042018\\CEVV\\app\\src\\main\\assets\\";
    private static List<String> titleList = new ArrayList<>();
    private static List<String> textList = new ArrayList<>();
    public static Book book;

    public static void main(String[] args) {
        //readFile(FILENAME);

        new SongReader.Reader(null).testReader(FILENAME+"chants_cevv_cr_utf8.txt").sizeList();
        new SongReader.Reader(null).testReader(FILENAME+"chants ecodim.txt").sizeList();
        new SongReader.Reader(null).testReader(FILENAME+"JPC_SONGS.txt").sizeList();
        new SongReader.Reader(null).testReader(FILENAME+"orchestre.txt").sizeList();
    }

    public static void readFile(Context context){

        if(Prefs.getBoolean("readFile", false)){
            return;
        }

        AssetManager am = context.getAssets();
        try {

            InputStream is = am.open("chants_cevv_cr_utf8.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String sCurrentLine;
            StringBuilder sText = new StringBuilder();

            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);
                sText.append(addTag(sCurrentLine)+"\n");
            }

            buildTextItem(String.valueOf(sText));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static String readFile(String FILENAME) {
        /*AssetManager am = context.getAssets();
        InputStream is = am.open("test.txt");*/
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));

            String sCurrentLine;
            StringBuilder sText = new StringBuilder();

            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);
                sText.append(addTag(sCurrentLine) + "\n");
            }

            buildTextItem(String.valueOf(sText));

            showFinal();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static void buildTextItem(String sText) {
        //System.out.println(sText);
        textList = Arrays.asList(sText.split("#####"));

    }


    private static String addTag(String line) {

        String number = null;

        for (int i = 0; i <= 200; i++) {
            number = "00" + i;
            if (i > 10) number = "0" + i;
            if (i > 100) number = "" + i;
            number += ".";

            if (line.contains(number)) {
                addTitle(line.replace(number, ""));

                return "#####";
            }

        }

        return line;
    }

    private static void addTitle(String line) {

        titleList.add(line);
    }

    private static void finalBuilder() {
        book = new CEVV();
        for (int i = 0; i < titleList.size(); i++) {
            //book.addPage(new Page(i+1,i+1+".",titleList.get(i),textList.get(i),book.getId()));
            Page page = new Page(i+1,i+1+".",titleList.get(i),textList.get(i+1),book.getId());
            //page.store();
        }
    }

    private static void showFinal() {
        for (int i = 0; i < titleList.size(); i++) {
            //book.addPage(new Page(i+1,i+1+".",titleList.get(i),textList.get(i),book.getId()));
            //Page page = new Page(i+1,i+1+".",titleList.get(i),textList.get(i+1),book.getId());
            //page.store();
            System.out.println(i+1+"."+titleList.get(i));
        }
    }
}
