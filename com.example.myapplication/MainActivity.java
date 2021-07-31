package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String Email;
    String Password;
    EditText pw;
    EditText em;
    UserList userL=new UserList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);//FIRST screen is login screen

        //creates a default user to login as, so you dont need to register every time. TODO make this a file or database based system
        userL.createUser("Email", "Pass");


        //login button
        Button buttonLogin = findViewById(R.id.LogInButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pw = (EditText)findViewById(R.id.TextPassword);
                em = (EditText)findViewById(R.id.TextEmailAddress);
                Password =pw.getText().toString();
                Email = em.getText().toString();
                userL.printUserList();
                if (userL.UserExists(Email, Password)){ //if login values exist in user list, you get in.
                    Intent HomeIntent = new Intent(v.getContext(), HomeScreen.class);
                    CharSequence text = "Login Success!";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();//tells user login worked
                    startActivity(HomeIntent);//go to home screen

                } else {
                    pw.setError("Password and Username didn't match");//wrong attempt
                }
            }
        });

        Button buttonReg = findViewById(R.id.regScreenButton);//go to register menu, on a click of a button
        buttonReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent RegIntent = new Intent(v.getContext(), Register.class);
                RegIntent.putExtra("key", userL);//send the userlist there as well so they use the same one.
                startActivity(RegIntent);
            }
        });
    }


}
