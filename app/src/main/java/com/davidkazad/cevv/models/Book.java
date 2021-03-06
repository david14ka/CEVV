package com.davidkazad.cevv.models;

import com.davidkazad.cevv.songs.CEVV;
import com.davidkazad.cevv.songs.ECODIM;
import com.davidkazad.cevv.songs.JPC_SONGS;
import com.davidkazad.cevv.songs.MOISSONNEUR;
import com.davidkazad.cevv.songs.ORCHESTRE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Book {

    private final String name;
    private final String abbreviation;
    private final String description;
    private final int image;
    private final int id;
    public static List<Book> bookList;


    static {
        bookList = new ArrayList<>();
        //bookList.add(new CC());
        bookList.add(new CEVV());
        bookList.add(new JPC_SONGS());
        bookList.add(new ECODIM());
        bookList.add(new ORCHESTRE());
        bookList.add(new MOISSONNEUR());
        //bookList.add(new CV());

    }

    public Book(int id, String name, String abbreviation, String description, int image) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.image = image;
        this.id = id;
    }

    public static List<Book> getAll() {

        return bookList;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    abstract public List<Page> getPages();
    public Boolean addPage(Page page){
        return this.getPages().add(page);
    }

    public static Book getInstance(int bookId) {

        switch (bookId) {
            case 1:
                return new CEVV();
            case 2:
                return new JPC_SONGS();
            case 3:
                return new ECODIM();
            case 4:
                return new ORCHESTRE();
            case 5:
                return new MOISSONNEUR();
        }

        return new CEVV();
    }

    public List<Page> searchPage(String query) {

        if (!query.equals("")) {

            List<Page> pageList = new ArrayList<>();

            for (Page page :
                    this.getPages()) {
                String title = page.getNumber() + page.getTitle();

                String number = page.getNumber();
                String tmp0 = page.getTitle().toLowerCase();
                String tmp1 = number + " " + tmp0;
                String tmp2 = query.toLowerCase();

                if (tmp1.contains(tmp2)) {
                    pageList.add(page);
                }
            }

            return pageList;
        }

        return getPages();
    }

    public List<Page> find(String query) {

        if (!query.equals("")) {

            List<Page> pageList = new ArrayList<>();

            for (Page page :
                    this.getPages()) {

                if (page.getNumber().equalsIgnoreCase(query + ". ")) {
                    pageList.add(page);
                } else if (page.getNumber().equalsIgnoreCase(query + "b. ")) {
                    pageList.add(page);
                } else if (page.getNumber().equalsIgnoreCase(query + "a. ")) {
                    pageList.add(page);
                }
            }

            return pageList;
        }

        return getPages();
    }

    public List<Page> sort() {

        List<Page> pageList = this.getPages();

        String[] letters = new String[]{
                "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
        };

        for (int j = 0; j < letters.length; j++) {

            pageList.add(new Page(-1*(j+1), "",letters[j],"",getId()));

        }

        Collections.sort(pageList, new Comparator<Page>() {
            @Override
            public int compare(Page tc1, Page tc2) {
                return tc1.getTitle().compareTo(tc2.getTitle());
            }
        });

        //Collections.reverse(pageList);

        return pageList;
    }

    public List<Page> sort(List<Page> pageList) {

        Collections.sort(pageList, new Comparator<Page>() {
            @Override
            public int compare(Page tc1, Page tc2) {
                return tc1.getTitle().compareTo(tc2.getTitle());
            }
        });

        //Collections.reverse(pageList);

       /* String[] letters = new String[]{
                "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
        };
        boolean[] isletters = new boolean[]{
                false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false
        };


        for (Page page : pageList) {

            for (int j = 0; j < letters.length; j++) {

                if (page.getTitle().startsWith(letters[j])){

                }

            }

        }*/

        return pageList;
    }

    public Page getPage(int pageId) {
        if (count() > pageId)
            return getPages().get(pageId);
        return null;
    }


    public int count() {
        return getPages().size();
    }

    public String[] numbers() {
        String[] data = new String[count()];
        int i = 0;
        for (Page pa :
                getPages()) {
            data[i++] = pa.getNumber().replace(". ","");
        }

        return data;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDescription() {
        return description;
    }
}
