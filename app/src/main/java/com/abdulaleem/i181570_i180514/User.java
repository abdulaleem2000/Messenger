package com.abdulaleem.i181570_i180514;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Serializable {
    private String uid, fname,lname, gender, bio, email, profileImage;

    public User()
    {

    }
    public User(String uid, String fname,String lname, String gender, String bio, String email,String profileImage) {
        this.uid = uid;
        this.profileImage = profileImage;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.bio = bio;
        this.email = email;
    }

    public String getfName() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getProfileImage() {
        return profileImage;
    }
    public String getBio() {
        return bio;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getUid() {
        return uid;
    }


}
