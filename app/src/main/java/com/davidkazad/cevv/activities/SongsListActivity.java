package com.davidkazad.cevv.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidkazad.cevv.R;
import com.davidkazad.cevv.activities.helper.ReaderAsync;
import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Favoris;
import com.davidkazad.cevv.models.Page;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.davidkazad.cevv.common.Common.PREFS_TABLE_MATIERES_ALPHABETIQUE;

public class SongsListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = SongsListActivity.class.getName();
    public static int bookPosition;
    public static int position;
    public static Book currentBook;

    //public static Book currentBook;
    @BindView(R.id.grid_song) GridView grid_song;
    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;

    private List<Page> pageList;
    private GridAdapter adapter;

    public static String query;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationDrawer(savedInstanceState, null);

        grid_song.setVisibility(View.VISIBLE);
        grid_song.setOnItemClickListener(this);

        if (Prefs.getBoolean(PREFS_TABLE_MATIERES_ALPHABETIQUE, false)) {
            grid_song.setNumColumns(1);
        }

        toolbar.setTitle(currentBook.getName());


        readFile(bookPosition);
        //displayContents(currentBook);

    }

    private void displayContents(Book book) {

        currentBook = book;

        if (Prefs.getBoolean(PREFS_TABLE_MATIERES_ALPHABETIQUE, false)) {

            pageList = book.sort();

        } else

            pageList = book.getPages();

        adapter = new GridAdapter();

        toolbar.setTitle(book.getName());

        //if there is data in searchView
        if (query != null && !query.equals("")) {

            pageList = book.searchPage(query);

            if (Prefs.getBoolean(PREFS_TABLE_MATIERES_ALPHABETIQUE, false)) {

                pageList = book.sort(pageList);

            }

        }

        grid_song.setAdapter(adapter);
    }

    private void readFile(int position) {

        progressLayout.setVisibility(View.VISIBLE);

        ReaderAsync async = new ReaderAsync(this, currentBook,position);
        async.addOnCompleteListener(new ReaderAsync.OnCompleteTaskListener() {
            @Override
            public void onComplete(Book result) {

                progressLayout.setVisibility(View.GONE);
                displayContents(result);

            }
        }).execute();


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private class HolderItem {
        public TextView number;
        public TextView title;
        public ImageView fav;
        public ImageView note;
        public TextView letter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //Toast.makeText(getContext(), pageList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        final Page item = pageList.get(position);
        if (item.getPid() < 0) {
            return;
        }
        ItemActivity.currentPage = item;
        ItemActivity.currentBook = currentBook;
        startActivity(new Intent(getApplicationContext(), ItemActivity.class));
    }

    private class GridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public Object getItem(int position) {
            return Book.bookList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            HolderItem holder = new HolderItem();

            Page mPage = pageList.get(position);

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.item_numerique, parent, false);

                /*if (Prefs.getBoolean(PREFS_TABLE_MATIERES_ALPHABETIQUE, false)) {

                    convertView = inflater.inflate(R.layout.item_alphabetique, parent, false);
                } else {

                    convertView = inflater.inflate(R.layout.item_numerique, parent, false);
                }*/

                holder.number = convertView.findViewById(R.id.txt_number);
                holder.title = convertView.findViewById(R.id.txt_tittle);
                holder.fav = convertView.findViewById(R.id.fav);
                holder.note = convertView.findViewById(R.id.note);
                try {
                    holder.letter = convertView.findViewById(R.id.txt_letter);
                } catch (Exception r) {

                }

                convertView.setTag(holder);

            } else {

                holder = (HolderItem) convertView.getTag();
            }


            holder.title.setText(mPage.getTitle());

            if (mPage.getPid() < 0) {

                holder.letter.setText(mPage.getTitle());
                holder.letter.setVisibility(View.VISIBLE);

                holder.number.setVisibility(View.INVISIBLE);
                holder.note.setVisibility(View.GONE);
                holder.title.setVisibility(View.GONE);

            } else {

                holder.letter.setVisibility(View.GONE);
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setGravity(Gravity.LEFT);
                holder.number.setVisibility(View.VISIBLE);
                holder.note.setVisibility(View.VISIBLE);
            }

            if (Favoris.exists(mPage)) {
                holder.fav.setVisibility(View.VISIBLE);
            } else {
                holder.fav.setVisibility(View.GONE);
            }
            if (Prefs.getBoolean(PREFS_TABLE_MATIERES_ALPHABETIQUE, false)) {
                holder.number.setText(String.format(getString(R.string.txt_number), mPage.getNumber().replace(". ", "")));

            } else {
                holder.note.setVisibility(View.GONE);
                holder.number.setText(mPage.getNumber().replace(". ", ""));
            }

            //holder.number.setText(mPage.getNumber().replace(". ", ""));


            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        search(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.app_bar_search) {
            //findSongDialog();
            findItem();
            return true;
        }if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }if (id == R.id.action_helps) {
            //startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            openBrowser("help");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void search(Menu menu) {
        final MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                pageList = currentBook.searchPage(query);
                adapter.notifyDataSetChanged();
                invalidateOptionsMenu();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                pageList = currentBook.searchPage(query);
                adapter.notifyDataSetChanged();
                //Arrays.
                Log.d(TAG, "onQueryTextChange: ");
                return false;
            }
        });
    }
}
