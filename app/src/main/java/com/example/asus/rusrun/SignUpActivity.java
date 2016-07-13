package com.example.asus.rusrun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, userEditText, passwordEditText;
    private RadioGroup  radioGroup;
    private RadioButton avata0RadioButton, avata1RadioButton,
                        avata2RadioButton, avata3RadioButton,
                        avata4RadioButton;
    private  String nameString, UserString, passwordString, avataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    //Bind Widget
        nameEditText =      (EditText) findViewById(R.id.editText);
        userEditText =      (EditText) findViewById(R.id.editText2);
        passwordEditText =  (EditText) findViewById(R.id.editText3);
        radioGroup =        (RadioGroup) findViewById(R.id.redAvata);
        avata0RadioButton = (RadioButton) findViewById(R.id.radioButton);
        avata1RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton5);


    } //Main Method

    public void  clickSingUpSign(View view){

       //Get Value from Edit Text
        nameString      = nameEditText.getText().toString().trim();
        UserString      = userEditText.getText().toString().trim();
        passwordString  = passwordEditText.getText().toString().trim();

    } // clickSign

} //Main class
