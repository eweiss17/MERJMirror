package com.merj.merjmirror;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;

/* Created By Eric Weiss snd James Gabriel
* The sun_image is a test. It is actually part of the Taiwanese flag and needs to be deleted eventually because the US does not like Taiwan.
* must account for when the app closes and opens, changes must be saved*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //NOT MAIN, I set it to the preference grid view layout
        setContentView(R.layout.preference_layout);

        CreateSpinners();

        StartupRoutine();
    }

    private void CreateSpinners(){

        //I will fix this to look better later, right now this works.
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
        Spinner spinner6 = (Spinner) findViewById(R.id.spinner6);
        Spinner spinner7 = (Spinner) findViewById(R.id.spinner7);
        Spinner spinner8 = (Spinner) findViewById(R.id.spinner8);
        Spinner spinner9 = (Spinner) findViewById(R.id.spinner9);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> PreferenceNames = ArrayAdapter.createFromResource(this,
                R.array.preferences_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        PreferenceNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        //Same here I know it looks bad
        spinner.setAdapter(PreferenceNames);
        spinner2.setAdapter(PreferenceNames);
        spinner3.setAdapter(PreferenceNames);
        spinner4.setAdapter(PreferenceNames);
        spinner5.setAdapter(PreferenceNames);
        spinner6.setAdapter(PreferenceNames);
        spinner7.setAdapter(PreferenceNames);
        spinner8.setAdapter(PreferenceNames);
        spinner9.setAdapter(PreferenceNames);

        //Declares a listener object and passes one of the spinners
        YourItemSelectedListener spinnerListener = new YourItemSelectedListener();

        spinner2.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner2, spinner2, 0, 0);
        // only passes the spinner. The numbers are irrelevant afaik.
    }

    private void StartupRoutine() {
        //check to see if first time user
        //if true, run setup
        //if false, continue
    }
    
    //Declares a class used to listen to which spinner item is selected
    private class YourItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> spinner, View view, int pos, long id) {

            String ItemName = spinner.getSelectedItem().toString();

            switch (ItemName) {
                //case "Empty": spinner.setBackgroundColor(555555);
                case "Weather": view.setBackground(getDrawable(R.drawable.weather));
                    break;
                case "Calendar": view.setBackground(getDrawable(R.drawable.calendar));
                    break;
                case "Horoscope": view.setBackground(getDrawable(R.drawable.horoscope));
                    break;
                case "Comics": view.setBackground(getDrawable(R.drawable.comics));
                    break;
                case "News": view.setBackground(getDrawable(R.drawable.news));
                    break;
                case "Reminders": view.setBackground(getDrawable(R.drawable.notes));
                    break;
                case "Stocks": view.setBackground(getDrawable(R.drawable.stocks));
                    break;
                case "Clock": view.setBackground(getDrawable(R.drawable.clock));
                    break;
                default: break;
            }
        }

        public void onNothingSelected(AdapterView parent) {
            // This needs to be included. We won't use this though.
        }
    }

}
