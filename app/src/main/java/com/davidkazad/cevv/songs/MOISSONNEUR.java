package com.davidkazad.cevv.songs;

import com.davidkazad.cevv.R;
import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Page;

import java.util.ArrayList;
import java.util.List;

public class MOISSONNEUR extends Book {

    public static List<Page> MOISSONNEUR = new ArrayList<>();
    private List<Page> pageList = new ArrayList<>();

    public MOISSONNEUR() {
        super(3, "Moissonneur", "M", "Moissonneur du Seigneur", R.drawable.moissonneur);
    }

    @Override
    public List<Page> getPages() {
        return getPageList();
    }

    private static List<Page> getPageList() {
        return MOISSONNEUR;
    }

}
