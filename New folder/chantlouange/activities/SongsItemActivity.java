package com.davidkazad.cevv.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import com.davidkazad.cevv.MainActivity;
import com.davidkazad.cevv.R;
import com.davidkazad.cevv.fragment.SongsItemFragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.pixplicity.easyprefs.library.Prefs;

public class SongsItemActivity extends CustomIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SongsItemFragment.getInstance(1,0));
        addSlide(SongsItemFragment.getInstance(2,1));
        addSlide(SongsItemFragment.getInstance(1,2));
        addSlide(SongsItemFragment.getInstance(2,3));
        addSlide(SongsItemFragment.getInstance(3,4));
        addSlide(SongsItemFragment.getInstance(1,5));

        //onPageSelected(3);
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Prefs.putBoolean("firstRun",true);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Prefs.putBoolean("firstRun",true);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

        if (newFragment != null) {
            if (newFragment.getArguments() != null) {
                String title = newFragment.getArguments().getString("songTitle");
                //Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

                toolbar.setTitle(title);
            }
        }

    }

    @Override
    protected void onPageSelected(int position) {
        super.onPageSelected(position);
    }
}
