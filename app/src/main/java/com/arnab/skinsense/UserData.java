package com.arnab.skinsense;

import android.graphics.Bitmap;

/**
 * Created by AlphaBAT69 on 20-03-2018.
 */

public class UserData {
    private String text;
    private String bitmap;

    public UserData(String text, String bitmap) {
        this.text = text;
        this.bitmap = bitmap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

}
