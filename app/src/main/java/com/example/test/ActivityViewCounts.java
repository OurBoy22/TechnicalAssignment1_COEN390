package com.example.test;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

//Dedicated activity to see current counts and count history
public class ActivityViewCounts extends MainActivity {
    //instantiate all the objects/buttons used in this activity
    public ListView eventList;
    public TextView countA;
    public TextView countB;
    public TextView countC;
    public Button toggleDisplay;
    public boolean viewName = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_counts);

        //get and setup the action bar
        setSupportActionBar(findViewById(R.id.myToolbar));
        ActionBar settingsActionBar = getSupportActionBar();

        settingsActionBar.setTitle("Data Activity"); // set the title of the ActionBar

        //make back arrow clickable and visible (???)
        settingsActionBar.setDisplayHomeAsUpEnabled(true);
        settingsActionBar.setDisplayShowHomeEnabled(true);

        //Instantiate all TextViews/lists/Buttons
        eventList = findViewById(R.id.eventList);
        countA = findViewById(R.id.countA);
        countB = findViewById(R.id.countB);
        countC = findViewById(R.id.countC);
        toggleDisplay = findViewById(R.id.toggleDisplay);
        toggleDisplay.setVisibility(View.INVISIBLE);

        //Set the text to display the current counts
        countA.setText(preferencesController.getString("EventA").toString() + ": " + Integer.toString(preferencesController.getInt("count1")));
        countB.setText(preferencesController.getString("EventB").toString() + ": " + Integer.toString(preferencesController.getInt("count2")));
        countC.setText(preferencesController.getString("EventC").toString() + ": " + Integer.toString(preferencesController.getInt("count3")));

        //get the button click history and put it into an array of Strings
//        String numString = flipArray(preferencesController.getString("arrayEvents").toString());
        String[] eventNameString = translateArray(preferencesController.getString("arrayEvents")).toString().replace("[","").replace("]","").split(",");

        //display the list of button click history
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout. simple_list_item_1, eventNameString);
        eventList.setAdapter(arrayAdapter);

    }
    // used to inflate the menu in the "..." of the ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.data, menu);
        return true;
    }
    //To handle the inflatable menu options for the "..." on the ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.toggleEvents){ // if toggle events buttons is clicked
            toggleView();
        }
        else if (item.getItemId() == android.R.id.home){ // if back button is pressed
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void toggleView(){ // function to toggle the view of the list of button history
        if (!viewName){ // if the boolean of viewNames is false
            //update text
            countA.setText(preferencesController.getString("EventA").toString() + ": " + Integer.toString(preferencesController.getInt("count1")));
            countB.setText(preferencesController.getString("EventB").toString() + ": " + Integer.toString(preferencesController.getInt("count2")));
            countC.setText(preferencesController.getString("EventC").toString() + ": " + Integer.toString(preferencesController.getInt("count3")));

            String[] eventNameString = translateArray(preferencesController.getString("arrayEvents")).toString().split(",");// translate string from numbers to names of Events


            //update the list
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout. simple_list_item_1, eventNameString);
            eventList.setAdapter(arrayAdapter);
            viewName = true; //update toggle boolean
        }
        else{
            //update text
            countA.setText("Counter 1: " + Integer.toString(preferencesController.getInt("count1")));
            countB.setText("Counter 2: " + Integer.toString(preferencesController.getInt("count2")));
            countC.setText("Counter 3: " + Integer.toString(preferencesController.getInt("count3")));

            String[] eventNameString = (preferencesController.getString("arrayEvents")).toString().split(",");// translate string from numbers to names of Events
            //update the list
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout. simple_list_item_1, eventNameString);
            eventList.setAdapter(arrayAdapter);
            viewName = false; //update toggle boolean
        }
    }

    String flipArray(String arrString){ // function to reverse the array of Strings (not needed in the end)
        String[] array = arrString.replace(" ", "").replace("[","").replace("]","").split(",");
        ArrayList numArray = new ArrayList();
        for (int i = 0; i < array.length; i++){
            Log.d("element", array[i]);
            try {
                numArray.add(Integer.parseInt(array[i]));
            }
            catch (Exception exception){
                System.out.println(exception);
            }
        }
        Collections.reverse(numArray); // reverse the array
        Log.d("prefsCreated", numArray.toString());
        return numArray.toString().replace("[","").replace("]","");
    }

    String translateArray(String arrString){ // function to change the display mode, from numbers to names of the events
        String[] array = arrString.replace(" ", "").replace("[","").replace("]","").split(",");
        ArrayList strArray = new ArrayList();
        for (int i = 0; i < array.length; i++){
            Log.d("element", array[i]);
            try {
                int currentNum = Integer.parseInt(array[i]);
                if (currentNum == 1){ // if the button id is 1, associate EventA name to it
                    strArray.add(preferencesController.getString("EventA").toString().trim());
                }
                else if (currentNum == 2){
                    strArray.add(preferencesController.getString("EventB").toString().trim());
                }
                else if (currentNum == 3){
                    strArray.add(preferencesController.getString("EventC").toString().trim());
                }
                else{
                    strArray.add("Invalid");
                }
            }
            catch (Exception exception){
                System.out.println(exception);
            }
        }

        Log.d("Array Out", strArray.toString());
        return String.join(",", strArray); //return translated string separated by commas
    }

}
