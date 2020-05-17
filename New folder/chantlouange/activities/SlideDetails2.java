package com.davidkazad.cevv.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.davidkazad.cevv.R;
import com.davidkazad.cevv.fragment.SongListFragment;
import com.davidkazad.cevv.fragment.SongsItemFragment;
import com.davidkazad.cevv.models.Favoris;
import com.davidkazad.cevv.songs.SongsBook;
import com.davidkazad.cevv.songs.SongsItem;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.ButterKnife;

public class SlideDetails2 extends BaseActivity {
    private static final String TAG = SongListFragment.class.getName();

    public static int songId;
    public static int bookId;
    private Toolbar toolbar;
    private Activity main;
    private TabLayout tabLayout;
    private SongsItem mSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_details);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("List des chants");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        navigationDrawer(savedInstanceState, null);
        onCreateFabMenu();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        main = this;

        setDisplaySong(bookId, songId);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                try {
                    new MyAsyncTask().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class PagerAdapter1 extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter1(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            return SongsItemFragment.getInstance(bookId, position);

        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

    private FloatingActionMenu fabMenu;
    private Handler mUiHandler = new Handler();
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;
    private FloatingActionButton fab4;

    @Override
    protected void onCreateFabMenu() {

        fabMenu = findViewById(R.id.menu_red);

        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        fab4 = findViewById(R.id.fab4);

        fabMenu.showMenuButton(false);
        fabMenu.setClosedOnTouchOutside(true);

        fab1.setOnClickListener(fabClickListener);
        fab2.setOnClickListener(fabClickListener);
        fab3.setOnClickListener(fabClickListener);
        fab4.setOnClickListener(fabClickListener);

        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                fabMenu.showMenuButton(true);
            }
        }, 400);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            createCustomAnimation();
        }

        fabMenu.getMenuIconView().setImageResource(fabMenu.isOpened()
                ? R.drawable.ic_close : R.drawable.ic_menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                fabMenu.getMenuIconView().setImageResource(fabMenu.isOpened()
                        ? R.drawable.ic_menu : R.drawable.ic_close);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        fabMenu.setIconToggleAnimatorSet(set);
    }

    private View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab1:
                    fab1.setImageDrawable(getResources().getDrawable(R.drawable.fav_full));
                    addToLike(bookId, mSong.getId());
                    break;
                case R.id.fab2:

                    sendText(mSong.getNumber()+mSong.getTitle()+"\n\n"+mSong.getContent());

                    break;
                case R.id.fab3:
                    //fab1.setVisibility(View.GONE);
                    fab1.setImageDrawable(getResources().getDrawable(R.drawable.fav_full));
                    fab3.setImageDrawable(getResources().getDrawable(R.drawable.star_filed));

                    addToFavoris(bookId, songId);

                    break;

                case R.id.fab4:
                    SongDetailsActivity.mItem = SongsBook.getSong(bookId,songId);
                    SongDetailsActivity.bookId = bookId;
                    startActivity(new Intent(getApplicationContext(),SongDetailsActivity.class).putExtra("edit",true));

                    break;
            }
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_help) {

            openHelp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        super.onStop();
        SongListFragment.query = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SongListFragment.query = null;
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, TabLayout> {

        @Override
        protected TabLayout doInBackground(Void... params) {

            return fillTablayout();
        }

        @Override
        protected void onPostExecute(final TabLayout tabLayout) {

            super.onPostExecute(tabLayout);
            main.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (tabLayout != null) {
                        //Toast.makeText(main, "tabCount:"+tabLayout.getTabCount(), Toast.LENGTH_SHORT).show();
                        fillViewPager(tabLayout);
                        findViewById(R.id.pbar).setVisibility(View.GONE);

                        if (Prefs.getBoolean("slideTab",true)){
                            final ImageView slide = main.findViewById(R.id.slide);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    slide.setVisibility(View.VISIBLE);
                                    slide.setAnimation(AnimationUtils.loadAnimation(main.getApplicationContext(),R.anim.shake));

                                }
                            },3000);
                        }
                    }
                }
            });
        }
    }

    private TabLayout fillTablayout() {

        TabLayout tabLayout = new TabLayout(main);

        for (int i = 0; i < SongsBook.getSongBook(bookId - 1).getTitle().length; i++) {

            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        return tabLayout;
    }

    private void fillViewPager(TabLayout tabLayout) {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        PagerAdapter1 adapter = new PagerAdapter1(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        int tabIndex = songId;
        tabLayout.setScrollPosition(tabIndex, 0f, true);
        viewPager.setCurrentItem(tabIndex);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                setDisplaySong(bookId, tab.getPosition());
                songId = tab.getPosition();

                checkFavoris();

                Prefs.putBoolean("slideTab",false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }

    private void checkFavoris() {
        if (Favoris.isAdded(bookId,songId)) {
            fab1.setImageDrawable(getResources().getDrawable(R.drawable.fav_full));
            fab3.setImageDrawable(getResources().getDrawable(R.drawable.star_filed));
        }else {
            fab1.setImageDrawable(getResources().getDrawable(R.drawable.fav));
            fab3.setImageDrawable(getResources().getDrawable(R.drawable.star));
        }
    }

    private void setDisplaySong(int bookId, int songId) {
        try {
            mSong = SongsBook.getSong(bookId, songId);
            toolbar.setSubtitle(mSong.getNumber() + mSong.getTitle());

            SongsBook book = SongsBook.getSongBook(bookId-1);
            toolbar.setTitle(book.getSongsBookName());

            checkFavoris();

        } catch (Exception ax) {
            Toast.makeText(this, "error fatal!", Toast.LENGTH_SHORT).show();
        }
    }

}
