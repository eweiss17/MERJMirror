package com.merj.merjmirror;

import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/* Created By Eric Weiss snd James Gabriel
* The sun_image is a test. It is actually part of the Taiwanese flag and needs to be deleted eventually because the US does not like Taiwan.
* Upgraded to api 21 to make our lives somewhat easier
* the layout is a mess right now sorry*/

public class MainActivity extends AppCompatActivity {

    //Drawable SunImage = getResources().getDrawable(R.drawable.sun_image,null); //problem line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickTopMiddle(View view){
        //insert pop up menu here. Not sure whether this would be a fragment or menu

        // fuck me, this just needed to be public for the button to access it
        // change color to background image: sun, newspaper, etc.
        Button TopMiddleZoneButton = (Button) view;
        TopMiddleZoneButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
    }

}
