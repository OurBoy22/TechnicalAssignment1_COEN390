package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


//Activity to manage the main menu of the app
public class MainActivity extends AppCompatActivity {
    //declare all the Buttons and objects that are used in this activity
    public Button settingsButton;
    public Button buttonEventA;
    public Button buttonEventB;
    public Button buttonEventC;
    public Button buttonCounts;
    public TextView totalCountText;
    public TextView totalIndividual;
    public TextView eventsList;
    public Count count_1;
    public Count count_2;
    public Count count_3;
    public Count current_count;
    public Count max_count;
    private boolean prefsCreated;
    public SharedPreferencesHelper preferencesController;
    public Boolean reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create SharedPreferencesHelper Object, used throughout this activity
        preferencesController = new SharedPreferencesHelper(getApplicationContext());
        Log.d("prefsCreated", String.valueOf(preferencesController.getBoolean("prefsCreated")) + " "+  this);

        //if the sharedPreferences are empty, instantiate with some default values
        if (!(preferencesController.getBoolean("prefsCreated")) ){

            //Launch settingsActivity if there is no data
            Intent settings_intent = new Intent(this, ActivitySettings.class);
            startActivity(settings_intent);


            Log.d("prefsCreated", "Creating..." + " "+  this);
            preferencesController.setPreference("prefsCreated", true);   //Set prefsCreated to true, to indicate that there is already information saved
            ArrayList arrayEvents =  new ArrayList(); // used to save the string of history of pressed buttons
            preferencesController.setPreference("EventA", "Event A"); // set name of EventA button (or button 1)
            preferencesController.setPreference("EventB", "Event B");// set name of EventB button (or button 2)
            preferencesController.setPreference("EventC", "Event C");// set name of EventB button (or button 3)
            preferencesController.setPreference("MaxCount", 20); // set the max count of buttons to a default of 20
            preferencesController.setPreference("count1", 0); //set current count of EventA button to 0
            preferencesController.setPreference("count2", 0);  //set current count of EventB button to 0
            preferencesController.setPreference("count3", 0);  //set current count of EventC button to 0
            preferencesController.setPreference("currentCount", 0);  //set current count of total button presses to 0
            preferencesController.setPreference("arrayEvents", arrayEvents.toString().replace("[","").replace("]","")); // sets the history of pressed buttons to empty

        }

        Log.d("prefsCreated", "Finished..."+ " "+  this);

        //create Count objects based on the values currently stored in sharedPreferences
        this.count_1 = new Count(preferencesController.getInt("count1")); // create count Object to keep track of counts for button 1
        this.count_2 = new Count(preferencesController.getInt("count2"));  // create count Object to keep track of counts for button 2
        this.count_3 = new Count(preferencesController.getInt("count3"));  // create count Object to keep track of counts for button 3
        this.current_count = new Count(preferencesController.getInt("currentCount")); // create count Object to keep track of counts for total counts
        this.max_count = new Count(preferencesController.getInt("MaxCount"));// create count Object to keep track of max number of counts

        String eventHistory = preferencesController.getString("arrayEvents"); // convert history of buttons pressed to a string (NOT NEEDED)

        //Instantiate all the buttons
        buttonEventA = findViewById(R.id.buttonEventA);
        buttonEventB = findViewById(R.id.buttonEventB);
        buttonEventC = findViewById(R.id.buttonEventC);
        buttonCounts = findViewById(R.id.buttonCounts);
        settingsButton = findViewById(R.id.settingsButton);

        eventsList = findViewById(R.id.eventsList); // instantiate the textview to see history of pressed buttons

        //set the text of the Buttons according to the names saved in SharedPreferences
        buttonEventA.setText(preferencesController.getString("EventA"));
        buttonEventB.setText(preferencesController.getString("EventB"));
        buttonEventC.setText(preferencesController.getString("EventC"));

        //set the maxCount object to the correct value according to the value saved (NOT NEEDED)
        max_count.setValue(preferencesController.getInt("MaxCount"));

        //instantiate textViews and assign values to them
        totalCountText = findViewById(R.id.totalCountText);
        totalIndividual = findViewById(R.id.totalIndividual);
        totalCountText.setText("Total Count: " + Integer.toString(current_count.getValue()));
        eventsList.setText(eventHistory);
        totalIndividual.setText("A: " + count_1.getValue() +" B: " + count_2.getValue() +" C: " + count_3.getValue());


        //settingsButton On click
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings_intent = new Intent(view.getContext(), ActivitySettings.class); // go to settings activity
                startActivity(settings_intent);
            }
        });

        buttonCounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity_view_counts = new Intent(view.getContext(), ActivityViewCounts.class); // go to view Counts activity
                startActivity(activity_view_counts);
            }
        });

        //1st button onClick
        buttonEventA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count_1.tryIncrement(current_count, max_count); // attempt to increment the count for button A
                Boolean test = current_count.tryIncrement(current_count, max_count); // attempt to increase the total count
                if (test){ // if the total count was successfully incremented, update the textView and variables accordingly
                    preferencesController.addEventsArray(1); // add to history of button presses

                    //update TextView
                    totalCountText.setText(("Total Count: " + Integer.toString(current_count.getValue())));
                    totalIndividual.setText( ("A: " + count_1.getValue() +" B: " + count_2.getValue() +" C: " + count_3.getValue()));

                    //update sharedPreferences with new values
                    preferencesController.setPreference("currentCount", current_count.getValue());
                    preferencesController.setPreference("count1", count_1.getValue());
                    eventsList.setText(preferencesController.getString("arrayEvents"));
                }
            }
        });

        //same logic as Button A
        buttonEventB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count_2.tryIncrement(current_count, max_count);
                Boolean test = current_count.tryIncrement(current_count, max_count);
                if (test){
                    preferencesController.addEventsArray(2);
                    totalCountText.setText(("Total Count: " + Integer.toString(current_count.getValue())));
                    totalIndividual.setText( ("A: " + count_1.getValue() +" B: " + count_2.getValue() +" C: " + count_3.getValue()));
                    preferencesController.setPreference("currentCount", current_count.getValue());
                    preferencesController.setPreference("count2", count_2.getValue());
                    eventsList.setText(preferencesController.getString("arrayEvents"));
                }
            }
        });

        //same Logic as Button A
        buttonEventC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count_3.tryIncrement(current_count, max_count);
                Boolean test = current_count.tryIncrement(current_count, max_count);
                if (test){
                    preferencesController.addEventsArray(3);
                    totalCountText.setText(("Total Count: " + Integer.toString(current_count.getValue())));
                    totalIndividual.setText( ("A: " + count_1.getValue() +" B: " + count_2.getValue() +" C: " + count_3.getValue()));
                    preferencesController.setPreference("currentCount", current_count.getValue());
                    preferencesController.setPreference("count3", count_3.getValue());
                    eventsList.setText(preferencesController.getString("arrayEvents"));
                }

            }
        });
    }

    @Override
    protected void onResume(){ // measure to update displayed TextViews in some test cases
        super.onResume();
        max_count = new Count(preferencesController.getInt("MaxCount"));
        buttonEventA.setText(preferencesController.getString("EventA"));
        buttonEventB.setText(preferencesController.getString("EventB"));
        buttonEventC.setText(preferencesController.getString("EventC"));
    }

}

