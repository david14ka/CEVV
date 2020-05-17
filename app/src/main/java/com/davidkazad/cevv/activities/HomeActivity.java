package com.davidkazad.cevv.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.davidkazad.cevv.R;
import com.davidkazad.cevv.chat.app.CommentActivity;
import com.davidkazad.cevv.common.Common;
import com.davidkazad.cevv.common.Login;
import com.davidkazad.cevv.fragment.BookFragment;
import com.davidkazad.cevv.fragment.DashFragment;
import com.davidkazad.cevv.utils.LogUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    public static final String EXTRA_WRITE_POST = "write";
    private FirebaseAuth mAuth;

    private Toolbar toolbar;
    private FragmentManager fm;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(new DashFragment());
                    toolbar.setTitle(R.string.favorities);

                    return true;
                case R.id.navigation_dashboard:
                    showFragment(new BookFragment());
                    toolbar.setTitle(R.string.home_title);
                    return true;
                case R.id.navigation_notifications:
                    //showFragment(new BookFragment());
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.home_title);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        fm = getSupportFragmentManager();

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new BookFragment()).commit();

        navigationDrawer(savedInstanceState, toolbar);

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.fade_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.fade_out)
                .replace(R.id.container, fragment, fragment.getTag())
                .show(fragment)
                .commit();
    }

    protected void hideFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentByTag(fragment.getTag());
        if (frag != null) {
            fm.beginTransaction()
                    .setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_right,
                            R.anim.slide_left,
                            R.anim.slide_right)
                    .remove(frag)
                    .commitAllowingStateLoss();
            toolbar.setTitle(R.string.home_title);
        }
    }
    public void writePost1(View view) {
        startActivity(new Intent(getApplicationContext(), CommentActivity.class).putExtra(EXTRA_WRITE_POST,true));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }if (id == R.id.action_helps) {
            //startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            openBrowser("help");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


