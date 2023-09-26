package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//Activity to manage the settings of the app
public class ActivitySettings extends MainActivity {
    //declare all the Buttons and objects that are used in this activity
    public Button editValuesButton;
    public Button buttonEventA;
    public Button buttonEventB;
    public Button buttonEventC;
    public Button saveButton;
    public EditText editTextCounter1;
    public EditText editTextCounter2;
    public EditText editTextCounter3;
    public EditText editTextMaxCount;
    public Intent main_activity;
    public ActionBar myActionBar;
    public Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ActivityStatus", this + " started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //instatiate the Actionbar
        setSupportActionBar(findViewById(R.id.myToolbar));
        ActionBar settingsActionBar = getSupportActionBar();
        settingsActionBar.setTitle("Settings"); // set title of the actionbar

        //make back arrow clickable and visible (???)
        settingsActionBar.setDisplayHomeAsUpEnabled(true);
        settingsActionBar.setDisplayShowHomeEnabled(true);

        //instantiate all buttons/textView/EditText
        editValuesButton = findViewById(R.id.editValuesButton); //NOT NEEDED
        editValuesButton.setVisibility(View.INVISIBLE); // by default the button is invisible
        editTextCounter1 = findViewById(R.id.editTextCounter1);
        editTextCounter2 = findViewById(R.id.editTextCounter2);
        editTextCounter3 = findViewById(R.id.editTextCounter3);
        editTextMaxCount = findViewById(R.id.editTextMaxCount);
        saveButton = findViewById(R.id.saveButton);

        //fill out the EditText with thec currently saved values in sharedPreferences
        editTextCounter1.setText(preferencesController.getString("EventA").toString());
        editTextCounter2.setText(preferencesController.getString("EventB").toString());
        editTextCounter3.setText(preferencesController.getString("EventC").toString());
        editTextMaxCount.setText(Integer.toString(preferencesController.getInt("MaxCount")));


        //edit button listener (NOT NEEDED)
        editValuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Make the fields editable
                editTextCounter1.setEnabled(true);
                editTextCounter2.setEnabled(true);
                editTextCounter3.setEnabled(true);
                editTextMaxCount.setEnabled(true);
                //Make the save button visible
                saveButton.setVisibility(View.VISIBLE);
            }
        });

        //save button listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInput();
            } // call check onput method, which checks for valid inputs
        });
    }

    // used to inflate the menu in the "..." of the ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    //To handle the inflatable menu options for the "..." on the ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.editFields){     //when the "edit fields" option is pressed
            //Make the fields editable
            editTextCounter1.setEnabled(true);
            editTextCounter2.setEnabled(true);
            editTextCounter3.setEnabled(true);
            editTextMaxCount.setEnabled(true);
            //Make the save button visible
            saveButton.setVisibility(View.VISIBLE);
        }
        else if (item.getItemId() == android.R.id.home){// if the back button is pressed
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    boolean testString(String string_in){ // checks if string only contains letters and whitespace
        for (int i = 0; i < string_in.length(); i ++){
            char character = string_in.charAt(i);
            if (!(Character.isLetter(character) || Character.isWhitespace(character))){
                return false;
            }
        }
        return true;
    }

    boolean testStringNum(String string_in){ // checks if string only contains numbers
        for (int i = 0; i < string_in.length(); i ++){
            char character = string_in.charAt(i);
            if (!(Character.isDigit(character))){
                return false;
            }
        }
        return true;
    }

    void checkInput(){ // logic function to check validity of fields

        // puts all the button names entered into an array of strings
        String[] form_values= {editTextCounter1.getText().toString(),editTextCounter2.getText().toString(),editTextCounter3.getText().toString()};

        //Boolean to keep track of which tests of field validity have passed
        Boolean test_1 = true;
        Boolean test_2 = true;

        //Button name logic
        for (int i = 0; i < form_values.length; i++) {
            if (! (form_values[i].length() <= 20 && testString(form_values[i])) ) { // if lenght of name <20 and only contains alphabetic characters/spaces
                test_1 = false;
            }
        }

        if (!(testStringNum(editTextMaxCount.getText().toString()))){ // do not allow letters in max count field
            test_2 = false;
        }
        //make sure that the range of max counts is between 5 and 200, and also is not less than the current count saved
        else if (  !(Integer.parseInt(editTextMaxCount.getText().toString()) >=5 && Integer.parseInt(editTextMaxCount.getText().toString()) <=200)  || !(Integer.parseInt(editTextMaxCount.getText().toString()) >= preferencesController.getInt("currentCount")) ){
            test_2 = false;
        }

        //if all fields are valid inputs
        if (test_1 && test_2){

            //get the fields from the form
            String newCounter1Name = editTextCounter1.getText().toString();
            String newCounter2Name = editTextCounter2.getText().toString();
            String newCounter3Name = editTextCounter3.getText().toString();
            String newTextMaxCount = editTextMaxCount.getText().toString();

            //Make the fields not editable
            editTextCounter1.setEnabled(false);
            editTextCounter2.setEnabled(false);
            editTextCounter3.setEnabled(false);
            editTextMaxCount.setEnabled(false);
            //Make the save button invisible
            saveButton.setVisibility(View.INVISIBLE);

            //update sharedPreferences with the new values
            preferencesController.setPreference("EventA", newCounter1Name);
            preferencesController.setPreference("EventB", newCounter2Name);
            preferencesController.setPreference("EventC", newCounter3Name);
            preferencesController.setPreference("MaxCount", Integer.parseInt(newTextMaxCount));

            Log.d("newCounter1Name", newCounter1Name);
            Log.d("newCounter2Name", newCounter2Name);
            Log.d("newCounter3Name", newCounter3Name);
            Log.d("newTextMaxCount", newTextMaxCount);

        }
        else{

            //display toast messages in case of errors
            if (!test_1){
                Toast.makeText(getApplicationContext(), "Counter Names must only contain alphabetic characters/spaces and maximum of 20 characters.", Toast.LENGTH_LONG).show();
            }
            if (!test_2){
                Toast.makeText(getApplicationContext(), "Max Count must be between 5 and 200, and be greater than the current count (" + Integer.toString(preferencesController.getInt("currentCount")) + ")", Toast.LENGTH_LONG).show();
            }

        }
    }

}
