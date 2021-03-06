package com.davidkazad.cevv.models;

import com.davidkazad.cevv.common.Common;

public class UserPref {

    private String email;
    private String favGson;

    public UserPref() {

        this.email = Common.currentUser.getEmail();
        this.favGson = Favoris.getListGon();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFavGson() {
        return favGson;
    }

    public void setFavGson(String favGson) {
        this.favGson = favGson;
    }
}
