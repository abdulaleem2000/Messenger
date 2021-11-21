package com.abdulaleem.i181570_i180514;

public class ItemsClass {
    private String imageView;
    private String textView, msg, time,uid;
    public ItemsClass(){

    }
    public ItemsClass(String  imageView, String textView, String uid,String msg) {
        this.imageView = imageView;
        this.textView = textView;
        this.uid = uid;
        this.msg = msg;

    }

    public String getTextView() {
        return textView;
    }

    public  String getImageView() {
        return imageView;
    }

    public String getUid() {
        return uid;
    }

    public String getTime() {
        return time;
    }
    public String getMsg() {
        return msg;
    }

}
