package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
//this class serves as a helper for all the sharedPreferences actions, to make it easier to use
public class SharedPreferencesHelper extends Activity {
    static String namePreferences;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor sharedPreferencesEdit;
    static Context context;

    SharedPreferencesHelper(Context context){ // constructor
        namePreferences = "sharedPreferences";
        this.context = context;
        sharedPreferences = context.getSharedPreferences(this.namePreferences, context.MODE_PRIVATE);
        sharedPreferencesEdit = sharedPreferences.edit();
    }

    static void refreshPreferences(){ // refreshes the attributes of the class when needed
        sharedPreferences = context.getSharedPreferences(namePreferences, MODE_PRIVATE);
        sharedPreferencesEdit = sharedPreferences.edit();
    }

    //setters for String, Bool and Int
    void setPreference(String key, String value){
        refreshPreferences();
        sharedPreferencesEdit.putString(key, value);
        sharedPreferencesEdit.commit();
    }
    void setPreference(String key, int value){
        refreshPreferences();
        sharedPreferencesEdit.putInt(key, value);
        sharedPreferencesEdit.commit();
    }
    void setPreference(String key, boolean value){
        refreshPreferences();
        sharedPreferencesEdit.putBoolean(key, value);
        sharedPreferencesEdit.commit();
    }

    //setter for the button click history
    void addEventsArray(int num){
        //create an array of strings from the comma-separated string stored in sharedPreferences
        String[] array = this.getString("arrayEvents").toString().replace(" ", "").replace("[","").replace("]","").split(",");

        ArrayList numArray = new ArrayList();// num array for adding numbers easily

        for (int i = 0; i < array.length; i++){ // loop throught string array and add every number to numArray
            Log.d("element", array[i]);
            try { // exception handling to only allow numbers into the array
                numArray.add(Integer.parseInt(array[i]));
            }
            catch (Exception exception){
                System.out.println(exception);
            }
        }
        numArray.add(num); // add the new number to the history of events
        Log.d("prefsCreated", numArray.toString().replace("[","").replace("]",""));

        //convert numArray to a string again and store it in sharedPreferences
        setPreference("arrayEvents", numArray.toString().replace("[","").replace("]",""));
    }

    //getters for string, Int, and Booleans
    String getString(String key){
        refreshPreferences();
        return sharedPreferences.getString(key, "");
    }
    int getInt(String key){
        refreshPreferences();
        return sharedPreferences.getInt(key, 0);
    }
    boolean getBoolean(String key){
        refreshPreferences();
        return sharedPreferences.getBoolean(key, false);
    }

    //function to reset sharedPreferences if Needed
    void resetSharedPreferences(){
        sharedPreferencesEdit.clear().commit();
    }


}
