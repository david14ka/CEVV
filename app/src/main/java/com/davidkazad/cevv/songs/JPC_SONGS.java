package com.davidkazad.cevv.songs;

import com.davidkazad.cevv.R;
import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Page;

import java.util.ArrayList;
import java.util.List;

public class JPC_SONGS extends Book {

    public static List<Page> JPC_SONGS = new ArrayList<>();

    public JPC_SONGS() {
        super(2, "Ambassadeurs du seigneur", "JPC", "Jeunesse pour Christ", R.drawable.book_3757523_640);
    }

    @Override
    public List<Page> getPages() {
        return getPageList();
    }

    private static List<Page> getPageList() {
        return JPC_SONGS;

    }

}
