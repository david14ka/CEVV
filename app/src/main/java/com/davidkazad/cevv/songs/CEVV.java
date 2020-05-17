package com.davidkazad.cevv.songs;

import com.davidkazad.cevv.R;
import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Page;

import java.util.ArrayList;
import java.util.List;

public class CEVV extends Book {

    public static List<Page> CEVV = new ArrayList<>();

    public CEVV() {
        super(1, "Groupe de louange", "CEVV", "Cantiques du Groupe de louange", R.drawable.book_3755514_640);
    }

    @Override
    public List<Page> getPages() {
        return getPageList();
    }

    private static List<Page> getPageList() {
        return CEVV;

    }

}
