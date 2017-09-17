package com.merj.merjmirror;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/* Created By Eric Weiss snd James Gabriel
* The sun_image is a test. It is actually part of the Taiwanese flag and needs to be deleted eventually because the US does not like Taiwan.
* Upgraded to api 21 to make our lives somewhat easier
* the layout is a mess right now sorry*/

public class MainActivity extends AppCompatActivity {

    //Drawable SunImage = getResources().getDrawable(R.drawable.sun_image,null); //problem line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //NOT MAIN, I set it to the preference grid view layout
        setContentView(R.layout.preference_layout);

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.preferences_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        //Same here I know it looks bad
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);
        spinner6.setAdapter(adapter);
        spinner7.setAdapter(adapter);
        spinner8.setAdapter(adapter);
        spinner9.setAdapter(adapter);

    }

    public void onClickTopMiddle(View view){
        //insert pop up menu here. Not sure whether this would be a fragment or menu

        // fuck me, this just needed to be public for the button to access it
        // change color to background image: sun, newspaper, etc.
        Button TopMiddleZoneButton = (Button) view;
        TopMiddleZoneButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
    }

}
