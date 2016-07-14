package com.example.asus.rusrun;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText;
    private ImageView imageView;
    private static final String ulrLogo = "http://swiftcodingthai.com/rus/image/logo_rus.png";
    private String userString, passwordString;
    private static final String urlJSON = "http://swiftcodingthai.com/rus/get_user_nattikan.php";


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

    // Create Inner Class
    private class SynUser extends AsyncTask<Void, Void, String> {

        // Explicit
        private String myJSONString,myUserString, myPasswordString;
        private Context context;
        public SynUser(String myJSONString,
                       String myUserString,
                       String myPasswordString,
                       Context context)
        {
            this.myJSONString = myJSONString;
            this.myUserString = myUserString;
            this.myPasswordString = myPasswordString;
            this.context = context;

        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(myJSONString).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            }catch (Exception e) {
                Log.d("RusV1", "e doIn ==>" + e.toString());
                return null;
            }

        }// doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("RusV1", "JSON ==>" + s);

        } //onPost

    } // SyUser Class


        public void  clickSingIn(View view){

            userString        = userEditText.getText().toString().trim();
            passwordString    = passwordEditText.getText().toString().trim();

            //Check Space
            if (userString.equals("") || passwordString.equals("")) {
                //Have Space
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this,"Have Space","Please Fill All Every Blank");

            }else{
                //No Space
                SynUser synUser = new SynUser(urlJSON, userString, passwordString, this);
                synUser.execute();


            }   //if

        }//clickSign


    public void clikeSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }

}   // Main Class นี่ คือคลาสหลัก

