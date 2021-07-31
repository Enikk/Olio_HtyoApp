package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.SimpleTimeZone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Weight extends AppCompatActivity {
    double Height;
    double Weight;
    EditText heightinput;
    EditText weightinput;
    TextView ShowDate;
    Spinner historySpin;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //format date
    String date = dateFormat.format(calendar.getTime()); //get date
    String Filename = "LocalWeightLog.txt"; //log file name
    File Logfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight);
        ShowDate =(TextView)findViewById(R.id.DateView);
        ShowDate.setText(date);     //sets up date to be shown on the top of the screen

        Button calcB =findViewById(R.id.CalcButton);
        calcB.setOnClickListener(new View.OnClickListener() {   //button that attempts to calculate the body mass index of the values given
            @Override
            public void onClick(View view) {
                weightinput =(EditText)findViewById(R.id.weightbmi);
                String value= weightinput.getText().toString();
                Weight=Double.parseDouble(value);                   //weight value got, and converted to a double
                heightinput =(EditText)findViewById(R.id.heightbmi);
                String value2= heightinput.getText().toString();
                Height=Double.parseDouble(value2);                  //Height value got and converted to a double
                if (value.isEmpty()){
                    weightinput.setError("Cant be empty");
                }else if(value2.isEmpty()){
                    heightinput.setError("Cant be empty");
                }else

                {
                    calcBMI(Height, Weight);                    //if they are not empty, calculate the BMI
                    CharSequence text = "Calc finished!";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button saveB =findViewById(R.id.SaveButton);            //Button to Save the current values to a log
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                weightinput =(EditText)findViewById(R.id.weightbmi);
                String value= weightinput.getText().toString();
                Weight=Double.parseDouble(value);

                try {

                    saveHistory(Weight, date);  //the actual place it happen, can be found below
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button historyB =findViewById(R.id.HistoryButton);  // button for viewing log file about weight history
        historyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHistory();
            }
        });


        Button buttonBack = findViewById(R.id.BackButton2); //go back button, one more of these
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                /*Intent HomeIntent = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(HomeIntent);*/
            }
        });
    }

    public void viewHistory(){      //Views the log file

        List<String> spinnerArray = new ArrayList<String>(); //array in preparation to add any found logs to a spinner
        historySpin=(Spinner)findViewById(R.id.spinner);
        String Filepath =this.getFilesDir().getPath().toString() + "/" +Filename+".txt";    //gets the filepath
        Logfile = new File(Filepath);
        try {
            FileReader read= new FileReader(Logfile);
            BufferedReader br = new BufferedReader(read);               //tries reading the file
            String line=null;
            while((line=br.readLine())!= null){
                System.out.println("Lines found");                          //if it finds something it is added to an arrayy
                spinnerArray.add(line);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item
            , spinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //setting up the spinner

            historySpin.setAdapter(adapter);    //added the values
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace(); //some error handling
        }


    }
    public void saveHistory(double weight, String date) throws IOException {//Creates or adds values to a log file

        String Filepath =this.getFilesDir().getPath().toString() + "/" +Filename+".txt"; //gets filepath again
        File logfile = new File(Filepath);
        String WString = Double.toString(weight);
        FileWriter write = new FileWriter(logfile,true);    //writes to it
        write.append("Weight: "+WString+" kg "+"date: "+date+"\n");
        write.flush();
        write.close();      //closes file after done

    }
    public void calcBMI(double height, double weight){  //calculates the bmi, and sets the text for the result
        double BMI;
        BMI=weight/((height/100)*(height/100));
        DecimalFormat df = new DecimalFormat("#.##"); //sets up the format so it uses two decimals max
        String Bmi=df.format(BMI);
        TextView BMIS=(TextView)findViewById(R.id.BMI);
        if (BMI < 16.0) {
            BMIS.setText(Bmi+" Underweight (Severe thinness), for Adults");
        } else if (BMI >= 16 && BMI <= 17) {
            BMIS.setText(Bmi+" Underweight (Moderate thinness), for Adults");
        } else if (BMI >= 17 && BMI <= 18.5) {
            BMIS.setText(Bmi+" Underweight (Mild thinness), for Adults");
        } else if (BMI >= 18.5 && BMI <= 25) {
            BMIS.setText(Bmi+" Within normal range, for Adults");
        } else if (BMI >= 25 && BMI <= 30) {
            BMIS.setText(Bmi+" Slightly Overweight, for Adults");
        } else if (BMI >= 30 && BMI <= 35) {
            BMIS.setText(Bmi+" Obese (Class I), for Adults");
        } else if (BMI >= 35 && BMI <= 40) {
            BMIS.setText(Bmi+" Obese (Class II), for Adults");
        } else if (BMI >= 40) {
            BMIS.setText(Bmi+" Obese (Class III), for Adults");
        } else
            BMIS.setText("Error!");
    }
}

