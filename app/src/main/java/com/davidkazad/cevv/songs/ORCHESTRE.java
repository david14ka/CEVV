package com.davidkazad.cevv.songs;

import com.davidkazad.cevv.R;
import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Page;

import java.util.ArrayList;
import java.util.List;

public class ORCHESTRE extends Book {

    public static List<Page> ORCHESTRE = new ArrayList<>();

    public ORCHESTRE() {
        super(4, "Orchestre", "Or", "Orchestre Viens & Vois", R.drawable.orchestre);
    }

    @Override
    public List<Page> getPages() {
        return getPageList();
    }

    private static List<Page> getPageList() {
        return ORCHESTRE;

    }

}
