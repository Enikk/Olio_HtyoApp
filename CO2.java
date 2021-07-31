package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;

public class CO2 extends AppCompatActivity{


    private Spinner spinner;
    private CheckBox checkBox;
    private String diet;
    private Boolean CarbonPref;
    private int beef;
    private int pork;
    private int fish;
    private int dairy;
    private int cheese;
    private int rice;
    private int egg;
    private int wVeg;
    private int restaurant;
    private int weight;
    private String totalE;

    private static DecimalFormat d2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.co2);//set content view

        //Send diet options from res/values/strings.xml to a spinner.
        spinner=(Spinner)findViewById(R.id.spinnerD);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.diet, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //checkbox regarding lowcarbon selections
        checkBox = findViewById(R.id.CarbonCheck);

        //running all seekbars, these also set the values you get for the actual calculation
        beefSBar();
        porkSBar();
        fishSBar();
        dairySBar();
        cheeseSBar();
        riceSBar();
        eggsSBar();
        vegSBar();
        restaurantSBar();

        //Button to use for calculating co2 emissions
        Button calcButton = findViewById(R.id.CalcButton2);
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Calc button clicked");
                RequestQueue myQ = Volley.newRequestQueue(v.getContext());

                diet = spinner.getSelectedItem().toString();
                CarbonPref = checkBox.isChecked();
                TextView Result = (TextView)findViewById(R.id.Result);

                //URL that you send a request in, using values got from the seekbars
                String url ="https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet="
                        +diet+"&query.lowCarbonPreference="+CarbonPref+"&query.beefLevel="+beef+"&query.fishLevel="
                        +fish+"&query.porkPoultryLevel="+pork+"&query.dairyLevel="+dairy+"&query.cheeseLevel="
                        +cheese+"&query.riceLevel="+rice+"&query.eggLevel="+egg+"&query.winterSaladLevel="
                        +wVeg+"&query.restaurantSpending="+restaurant;

                //setting up the request
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {

                            totalE=response.getString("Total");
                            String total = totalE.split("\\.")[0];
                            Result.setText("Your total CO2 footprint, from food items/restaurants is "+total+" kg");//showing the result
                            CharSequence text = "Result calculated!";
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Result.setText("Error!");
                        }
                    });

            myQ.add(request);//running the request

            }
        });

        configBackButton();//sets up the back button
    }

    //seekbars that set up the values and also show the user them. This one is for beef.
    public void beefSBar(){
        SeekBar beefb = (SeekBar)findViewById(R.id.seekBarBeef);
        beefb.setMax(200);
        TextView beefW = (TextView)findViewById(R.id.tWbeef);

        beefb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                double consumption = (double) i *0.004;//these values are setup in the way the api requested.
                beefW.setText("Beef/lamb "+d2.format(consumption)+"kg/week");
                beef = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { //these got automatically added when I set up the seekbar listener, ill try to remember to check if they are needed or can I remove them as they will be emptyy

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //not much different from the beef one, I wont comment on these all. The values are just dependant on what the api requested.
    public void porkSBar(){
        SeekBar porkb = (SeekBar)findViewById(R.id.seekBarPork);
        porkb.setMax(200);

        TextView porkW = (TextView)findViewById(R.id.tWPork);
        porkb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double consumption = (double) i *0.01;
                porkW.setText("Pork/Poultry "+d2.format(consumption)+"kg/week");
                pork=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void fishSBar(){
        SeekBar fishb = (SeekBar)findViewById(R.id.seekBarFish);
        fishb.setMax(200);

        TextView fishW = (TextView)findViewById(R.id.tWFish);

        fishb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double consumption = (double) i *0.006;
                fishW.setText("Fish/Seafood "+d2.format(consumption)+"kg/week");
                fish = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void cheeseSBar(){
        SeekBar cheeseb = (SeekBar)findViewById(R.id.seekBarCheese);
        cheeseb.setMax(200);

        TextView cheeseW = (TextView)findViewById(R.id.tWcheese);


        cheeseb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double consumption = (double) i *0.003;
                cheeseW.setText("Cheese "+d2.format(consumption)+"kg/week");
                cheese = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void dairySBar(){
        SeekBar dairyb = (SeekBar)findViewById(R.id.seekBarDairy);
        dairyb.setMax(200);

        TextView dairyW = (TextView)findViewById(R.id.textViewDairy);

        dairyb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double consumption = (double) i *0.038;
                dairyW.setText("Dairy "+d2.format(consumption)+"kg/week");
                dairy=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void riceSBar(){
        SeekBar riceb = (SeekBar)findViewById(R.id.seekBarRice);
        riceb.setMax(200);

        TextView riceW = (TextView)findViewById(R.id.textViewRice);

        riceb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double consumption = (double) i *0.0009;
                riceW.setText("Rice "+d2.format(consumption)+"kg/week");
                rice=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void vegSBar(){
        SeekBar vegb = (SeekBar)findViewById(R.id.seekBarWVeg);
        vegb.setMax(200);

        TextView vegW = (TextView)findViewById(R.id.textViewWveg);

        vegb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double consumption = (double) i *0.014;
                vegW.setText("Winter vegetables "+d2.format(consumption)+"kg/week");
                wVeg = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void eggsSBar(){
        SeekBar eggb= (SeekBar)findViewById(R.id.seekBarEggs);
        eggb.setMax(1000);

        TextView eggW = (TextView)findViewById(R.id.textViewEggs);


        eggb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double consumption = (double) i *0.03;
                eggW.setText("Eggs "+d2.format(consumption)+"pcs/week");
                egg=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void restaurantSBar(){
        SeekBar restaurantb = (SeekBar)findViewById(R.id.seekBarRestaurant);
        restaurantb.setMax(800);

        TextView restW = (TextView)findViewById(R.id.textViewRestaurant);

        restaurantb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                restW.setText("Restaurants "+d2.format(i)+"€/week");
                restaurant = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }




    //back button ends activity with finish() returning to an earlier activity.
    public void configBackButton(){
        Button buttonBack = findViewById(R.id.BackButton3);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
                    /*Intent HomeIntent = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(HomeIntent);*/
            }
        });
    }
}