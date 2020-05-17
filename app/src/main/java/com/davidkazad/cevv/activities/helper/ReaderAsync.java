package com.davidkazad.cevv.activities.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.songs.CEVV;
import com.davidkazad.cevv.songs.ECODIM;
import com.davidkazad.cevv.songs.JPC_SONGS;
import com.davidkazad.cevv.songs.MOISSONNEUR;
import com.davidkazad.cevv.songs.ORCHESTRE;
import com.davidkazad.cevv.utils.SongReader;

public class ReaderAsync extends AsyncTask<Void, Void, Book> {

    private OnCompleteTaskListener listener;
    private Context mContext;
    private Book book;
    private int position;

    public ReaderAsync addOnCompleteListener(OnCompleteTaskListener listener) {
        this.listener = listener;
        return this;
    }

    public ReaderAsync(Context mContext, Book currentBook, int position) {
        this.mContext = mContext;
        this.position = position;
        this.book = currentBook;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("Email sending", "sending start");
    }

    @Override
    protected Book doInBackground(Void... params) {

        Book mBook = null;
        switch (position) {
            case 0:
                mBook = new SongReader
                        .Reader(mContext).fromAsset("chants_cevv_cr_utf8.txt")
                        .toBook(new CEVV()).read();
                break;
            case 1:
                mBook=  new SongReader
                        .Reader(mContext).fromAsset("JPC_SONGS.txt")
                        .toBook(new JPC_SONGS()).read();
                break;

            case 2:
                mBook=  new SongReader
                        .Reader(mContext).fromAsset("chants ecodim.txt")
                        .toBook(new ECODIM()).read();
                break;
            case 3:
                mBook= new SongReader
                        .Reader(mContext).fromAsset("orchestre.txt")
                        .toBook(new ORCHESTRE()).read();
                break;
                case 4:
                mBook= new SongReader
                        .Reader(mContext).fromAsset("moissonneurs.txt")
                        .toBook(new MOISSONNEUR()).read();
                break;

        }
        return mBook;
    }

    @Override
    protected void onPostExecute(Book result) {
        super.onPostExecute(result);
        listener.onComplete(result);
    }

    public interface OnCompleteTaskListener {
        void onComplete(Book result);
    }
}

