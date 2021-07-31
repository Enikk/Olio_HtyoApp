package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);//content view to homescreen.xml

        Button buttonW = findViewById(R.id.WeightButton);   //goto weight/BMI calculation part of the program
        buttonW.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent WeightIntent = new Intent(v.getContext(), Weight.class);
                startActivity(WeightIntent);
            }
        });

        Button buttonCO2 = findViewById(R.id.CO2Button); //goto CO2 calc part of the program
        buttonCO2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent CO2Intent = new Intent(v.getContext(), CO2.class);
                startActivity(CO2Intent);
            }
        });

        Button buttonB = findViewById(R.id.LogOutButton); //go back to earlier
        buttonB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}