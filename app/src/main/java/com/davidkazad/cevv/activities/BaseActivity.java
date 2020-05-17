package com.davidkazad.cevv.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.davidkazad.cevv.R;
import com.davidkazad.cevv.chat.app.ChatActivity;
import com.davidkazad.cevv.chat.app.CommentActivity;
import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Favoris;
import com.davidkazad.cevv.models.Page;
import com.davidkazad.cevv.models.User;
import com.davidkazad.cevv.models.UserPref;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.List;

import static com.davidkazad.cevv.common.Common.LogPrefs;
import static com.davidkazad.cevv.common.Common.LogUsers;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    public static final String EXTRA_COMMUNITY = "community";
    private FragmentManager fm;
    private boolean isListVisible;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (Prefs.getBoolean("Theme",false)){
            setTheme(R.style.MyAppTheme2);
        }else {

            setTheme(R.style.MyAppTheme);
        }*/

        fm = getSupportFragmentManager();
    }

    protected void navigationDrawer(Bundle savedInstanceState, Toolbar toolbar) {
        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile userProfil = new ProfileDrawerItem()
                .withName("Developer")
                .withEmail("14ka135@gmail.com")
                .withIcon(R.drawable.logo).withIdentifier(1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        dialogContact();
                        return false;
                    }
                });

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withTextColor(Color.WHITE)
                .withActivity(this)
                .withHeaderBackground(R.drawable.guitar_3283649_640)
                .withTranslucentStatusBar(true)
                .addProfiles(
                        userProfil,
                        new ProfileSettingDrawerItem()
                                .withName(R.string.problems)
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        sendEmail();
                                        return false;
                                    }
                                })
                )
                .withSavedInstance(savedInstanceState)
                .build();

        Drawer result = new DrawerBuilder()
                .withSelectedItemByPosition(1)
                .withSavedInstance(savedInstanceState)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(false)
                .withTranslucentStatusBar(false)
                .withShowDrawerOnFirstLaunch(true)
                .withActivity(this)
                .withHasStableIds(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        //new PrimaryDrawerItem().withName("Programme de culte").withIcon(R.drawable.details_48px).withIdentifier(5),

                        new PrimaryDrawerItem().withName(R.string.home).withIcon(R.drawable.logo).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.community).withIcon(R.drawable.coderwall_48px).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.favorities).withIcon(R.drawable.star0).withIdentifier(3),

                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.settings).withIcon(R.drawable.settings_48px).withIdentifier(5),
                        new SecondaryDrawerItem().withName(R.string.send_comment).withIcon(R.drawable.comments_24px).withIdentifier(6),
                        new SecondaryDrawerItem().withName(R.string.about).withIcon(R.drawable.info_48px).withIdentifier(7)
                )

                .withSavedInstance(savedInstanceState)
                .build();

        result.addStickyFooterItem(new PrimaryDrawerItem().withName(R.string.support_dev).withIcon(R.drawable.pay)

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        return false;

                    }
                }));

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);*/
        }

        if (Build.VERSION.SDK_INT >= 19) {
            //result.getDrawerLayout().setFitsSystemWindows(false);
        }

        if (savedInstanceState == null) {
            /*Fragment f = new AccueilFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();*/
        }

        result.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                //Toast.makeText(BaseActivity.this, "item: "+position, Toast.LENGTH_SHORT).show();
                switch (position) {

                    case 1:
                        //startActivity(new Intent(getApplicationContext(), RapideAccess.class));
                        return false;
                    case 2:
                        openBrowser(getString(R.string.fb));
                        return false;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), FavorisActivity.class));
                        break;
                    case 4:


                    case 5:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        break;

                    case 6:
                        startActivity(new Intent(getApplicationContext(), CommentActivity.class));
                        break;
                    case 7:

                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                        break;
                    case 8:
                        sendEmail();
                        break;
                    default:
                        return false;
                }

                return false;
            }
        });
    }

    protected void dialogContact() {

        new MaterialDialog.Builder(BaseActivity.this)
                .title(R.string.contacts)
                .items(R.array.contact)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                sendSMS();
                                break;
                            case 2:
                                sendEmail();
                                break;
                            case 3:
                                break;
                            case 4:
                                openGoogle();
                                break;
                            case 5:
                                openYoutub();
                                break;
                            case 6:
                                openFacebook();
                                break;
                            case 7:
                                openGithub();
                                break;
                        }
                    }
                })
                .show();

    }

    protected void addToFavoris(Page mPage) {
        Favoris favoris = new Favoris(mPage);
        favoris.add();
    }

    protected void addToLike(int bookId, int songId) {
        Prefs.putBoolean(String.format(getString(R.string.like_preferences), bookId, songId), true);
        Toast.makeText(this, "successfully added!", Toast.LENGTH_SHORT).show();
    }


    protected void openHelp() {
        openBrowser("help");
    }


    protected void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(getString(R.string.alive_corp)));
        startActivity(i);
    }

    protected void openBrowser(String bookid) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(bookid));
        startActivity(i);

    }

    protected void openPlayStore() {
        String url = "https://play.google.com/store/apps/details?id=com.davidkazad.chantlouange";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    protected void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.davidkazad.chantlouange");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    protected void sendText(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_with)));
    }

    protected void openFacebook() {
        String url = "https://github.com/david14ka/tclcantiques";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    protected void openGithub() {
        String url = "https://github.com/david14ka/tclcantiques";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    protected void openYoutub() {
        String url = "https://www.youtube.com/watch?v=4zPa0t4pz7o&list=PLVIN7hVeWee3v5gLMSb_FcWScjG_uYqQs";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    protected void openGoogle() {
        String url = "https://plus.google.com/u/0/communities/103970766906777906788";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    String message;
    String phoneNo;

    protected void sendEmail() {
        sendEmail("cevvdevapps@gmail.com");
    }
    protected void sendEmail(String to) {
        Log.i("Send email", "");

        Intent emailIntent = new Intent(Intent.ACTION_VIEW);
        String mailto = "mailto:" + to +
                "?cc=" + "eliakabunda@gmail.com" +
                "&subject=" + Uri.encode("#CEVV MESSAGE") +
                "&body=" + Uri.encode("Email message goes here");

        emailIntent.setData(Uri.parse(mailto));

        try {
            startActivity(emailIntent);

            Log.i(TAG, "Finished sending email...");

        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(BaseActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", new String("+243970015092"));
        smsIntent.putExtra("sms_body", "Chant&Louange:  ");

        try {
            startActivity(smsIntent);
            //finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BaseActivity.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }


    private FloatingActionMenu fabMenu;

    protected void onCreateFabMenu() {

    }
    protected void findItem() {

        new MaterialDialog.Builder(this)
                .title("Rechercher dans")
                .items(R.array.book_list2)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, final int bookId, final CharSequence text) {

                        new MaterialDialog.Builder(BaseActivity.this)
                                .title(text)
                                .input(R.string.number, R.string.action_search, false, new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence page) {

                                        final Book currentBook = Book.bookList.get(bookId);

                                        Page currentPage = currentBook.getPage(Integer.valueOf(String.valueOf(page)) - 1);

                                        if (currentPage != null) {

                                            initPageContent(currentBook, currentPage);

                                        } else {
                                            Toast.makeText(getApplicationContext(), R.string.number_ot_exists, Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                })
                                .inputType(InputType.TYPE_CLASS_NUMBER)
                                .negativeText(R.string.cancel)
                                .positiveText(R.string.research)
                                .show();
                        return true; // allow selection
                    }
                })
                .show();

    }

    private void initPageContent(Book currentBook, Page currentPage) {
        ItemActivity.currentBook = currentBook;
        ItemActivity.currentPage = currentPage;
        startActivity(new Intent(getApplicationContext(), ItemActivity.class));
    }
}
