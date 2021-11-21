package com.abdulaleem.i181570_i180514;

import android.net.Uri;

public class Contacts {
    private Uri imageView;
    private String textView, phone;

    public Contacts(Uri imageView, String textView, String phone) {
        this.imageView = imageView;
        this.textView = textView;
        this.phone = phone;

    }

    public String getTextView() {
        return textView;
    }

    public Uri getImageView() {
        return imageView;
    }

    public String getPhone() {
        return phone;
    }


}
