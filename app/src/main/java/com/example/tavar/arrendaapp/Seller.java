package com.example.tavar.arrendaapp;

import android.provider.BaseColumns;

public class Seller implements BaseColumns {
    private long id;
    private String userName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
