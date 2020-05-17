package com.davidkazad.cevv.common;

import com.davidkazad.cevv.models.Book;
import com.davidkazad.cevv.models.Post;
import com.davidkazad.cevv.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

public class Common {


    public static DatabaseReference root = FirebaseDatabase.getInstance().getReference("CEVV");
    public static DatabaseReference LogUtil = root.child("LogUtil");
    public static DatabaseReference LogClick = root.child("LogClick");
    public static DatabaseReference SongsError = root.child("SongsError");
    public static DatabaseReference LogPrefs = root.child("UserPref");
    public static DatabaseReference LogUsers = root.child("User");
    public static DatabaseReference postRef = root.child("Posts");
    public static DatabaseReference commentRef = root.child("Comments");
    public static DatabaseReference loginRef = root.child("login");

    public static final String PREFS_TABLE_MATIERES_ALPHABETIQUE = "numerotation";

    public static User currentUser = User.getUser();
    public static List<Post> posts = new ArrayList<>();
    public static Book currentBook;
}
