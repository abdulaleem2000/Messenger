package com.abdulaleem.i181570_i180514;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {
    public boolean isContactOk(Context context)
    {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;

    }
    public void requestContact(Activity activity)
    {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS},2000 );
    }
}
