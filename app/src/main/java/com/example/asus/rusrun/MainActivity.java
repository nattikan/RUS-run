package com.example.asus.rusrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText;
    private ImageView imageView;
    private static final String ulrLogo = "http://swiftcodingthai.com/rus/image/logo_rus.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind widget
        userEditText        = (EditText) findViewById(R.id.editText4);
        passwordEditText    = (EditText) findViewById(R.id.editText5);
        imageView           = (ImageView) findViewById(R.id.imageView6);

        //Load Imae from Server
        Picasso.with(this).load(ulrLogo).into(imageView);
    }   // Main Method

    public void clikeSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }

}   // Main Class นี่ คือคลาสหลัก

