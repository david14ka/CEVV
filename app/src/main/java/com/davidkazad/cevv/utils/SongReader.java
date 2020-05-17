package com.davidkazad.cevv.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Page;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.davidkazad.cevv.common.Common.currentBook;
import static com.davidkazad.cevv.common.Common.currentUser;
import static com.davidkazad.cevv.fragment.FavFragment.TAG;

public class SongReader {



    public static class Reader {

        private Context context;
        private Book book;
        private String fileName;

        private List<Page> pageList;
        private List<String> titleList;
        private List<String> textList;
        private List<String> titleOrigin;

        public Reader(Context context) {
            this.context = context;
            this.fileName = "";

            titleList = new ArrayList<>();
            textList = new ArrayList<>();
            titleOrigin = new ArrayList<>();
        }

        public Reader fromAsset(String fileName){
            this.fileName = fileName;
            return this;
        }

        public Reader fromCard(String fileName){
            this.fileName = fileName;
            return this;
        }
        public Reader toBook(Book book){
            this.book = book;
            return this;
        }

        public Reader withContext(Context context){
            this.context = context;
            return this;
        }

        public Reader fillIn(List<Page> orchestre) {
            return this;
        }

        public Book read(){

            if (book==null){
                return null;
            }if (fileName.isEmpty()){
                return null;
            }

            /*if(Prefs.getBoolean(book.getAbbreviation(), false)){
                Log.d(TAG, "read: "+book.getName());
                return;
            }*/

            readFile();
            return storeData();

        }

        private void readFile(){

            AssetManager am = context.getAssets();
            try {

                InputStream is = am.open(fileName);
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

        private void buildTextItem(String sText) {
            //System.out.println(sText);
            textList = Arrays.asList(sText.split("#####"));
            //storeData();
        }

        private String addTag(String line) {

            String number = null;

            for (int i = 0; i <= 205; i++) {
                number = "00" + i;
                if (i >= 10) number = "0" + i;
                if (i >= 100) number = "" + i;
                number += ".";

                if (line.contains(number)) {
                    addTitle(line.replace(number, ""));
                    printOrigin(line);
                    return "#####";
                }

            }

            return line;
        }

        public void printOrigin(String line) {
            //System.out.println(line);
            titleOrigin.add(line);

        }



        private void addTitle(String line) {

            titleList.add(line);
        }

        public Book storeData() {

            currentBook = book;

            for (int i = 0; i < titleList.size(); i++) {
                //book.addPage(new Page(i+1,i+1+".",titleList.get(i),textList.get(i),book.getId()));

                Page page = new Page(i+1,i+1+".",titleList.get(i),textList.get(i+1),book.getId());
                //page.store();
                currentBook.addPage(page);
            }

            return book;
        }

        public Reader testReader(String FILENAME) {
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

            return this;
        }

        public void printTitle() {

            for (int i = 0; i < titleList.size(); i++) {
                System.out.println(i+1+"."+textList.get(i+1));
            }
        }

        public void compare(){
            for (int i=0;i<titleOrigin.size(); i++) {
                Page page = new Page(i+1,i+1+".",titleList.get(i),textList.get(i+1),book.getId());
                pageList.add(page);
            }
        }

        public void sizeList(){
            System.out.println("title: "+titleList.size()+" text: "+textList.size());
        }


        public Book readBook() {

            for (int i = 0; i < titleList.size(); i++) {
                //book.addPage(new Page(i+1,i+1+".",titleList.get(i),textList.get(i),book.getId()));

                Page page = new Page(i+1,i+1+".",titleList.get(i),textList.get(i+1),book.getId());
                //page.store();
                //pageList.add(page);

                book.addPage(page);
            }

            return book;

        }
    }


}
