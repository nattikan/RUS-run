package com.example.asus.rusrun;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

/**
 * Created by ASUS on 13/7/2559.
 */
public class MyAlert {

    public void myDialog(Context context,
                        String strTitle,
                        String strMessage){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.doremon48);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }



} // Main Class
