package com.davidkazad.cevv.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Comment;
import com.davidkazad.cevv.models.Page;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.davidkazad.cevv.common.Common.currentBook;

public class ReaderUtil {

    private static final String FILE_ROOT = "C:\\Users\\davidkazad\\Documents\\AndroidStudioProjects\\CEVV\\app\\src\\main\\assets\\";

    public static void main(String[] args) {
        readFile(FILE_ROOT + "chants ecodim.txt");
    }

    private static void readFile(String FILENAME) {
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
            //printTitle();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void buildTextItem(String sText) {
        //System.out.println(sText);
        List<String> textList = Arrays.asList(sText.split("#####"));
        //storeData();
    }

    private static String addTag(String line) {

        String number = null;

        for (int i = 0; i <= 205; i++) {
            number = "00" + i;
            if (i >= 10) number = "0" + i;
            if (i >= 100) number = "" + i;
            number += ".";

            if (line.contains(number)) {
                addTitle(line.replace(number, ""));
                return "#####";
            }

        }

        return "\"" + line + "\" +";
    }

    private static void addTitle(String line) {
        List<String> titleList = new ArrayList<>();
        titleList.add(line);

        System.out.println(""+"");
    }
}
