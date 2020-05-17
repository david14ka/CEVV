package com.davidkazad.cevv.songs;

import com.davidkazad.cevv.R;
import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Page;

import java.util.ArrayList;
import java.util.List;

public class ECODIM extends Book {

    public static List<Page> ECODIMM = new ArrayList<>();
    private List<Page> pageList = new ArrayList<>();

    public ECODIM() {
        super(3, "Chorale emmanuel", "CD", "Ecole de dimanche", R.drawable.ecodim);
    }

    @Override
    public List<Page> getPages() {
        return getPageList();
    }

    private static List<Page> getPageList() {
        return ECODIMM;
    }

}
