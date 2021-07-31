package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {


    String email;
    String p1;
    String p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);//register.xml on screen
        Intent i=getIntent();
        UserList UL=(UserList)i.getSerializableExtra("key");//get the Userlist from mainactivity so you can keep saving new users there
        Button buttonR = (Button)findViewById(R.id.registerButton);
        EditText em =(EditText)findViewById(R.id.editTextTextEmailAddress2);
        EditText pass1=(EditText)findViewById(R.id.editTextTextPassword2);
        EditText pass2=(EditText)findViewById(R.id.editTextTextPassword3);

        configBackButton(); //back to login screen button

        buttonR.setOnClickListener(new View.OnClickListener() { //button that attemps to register a new user IF
            public void onClick(View v) {
                p1 =pass1.getText().toString();
                p2 =pass2.getText().toString();
                email = em.getText().toString();

                if(p1.equals(p2)) { //passwords are accepted

                    if(email.isEmpty()){ //and email is not empty
                        em.setError("Cant be empty");
                    }else {
                        UL.createUser(email, p1); //added to the userlist
                        Intent HomeIntent = new Intent(v.getContext(), HomeScreen.class);
                        CharSequence text = "Registration saved!";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        finish(); //closes register activity
                        startActivity(HomeIntent); //opens homescreen, the above is needed so if you click back from homescreen it doesnt being you to registration menu
                    }

                }else{
                    pass2.setError("The information doesn't seem correct."); //tells user the passwords put in were incorrect
                }

            }
        });

    }



    private void configBackButton(){ //Another back button, ends activity with finish() goes back.
        Button buttonB = findViewById(R.id.BackButton);
        buttonB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
            }
        });
    }
}
