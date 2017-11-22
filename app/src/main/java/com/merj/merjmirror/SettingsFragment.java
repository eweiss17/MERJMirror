package com.merj.merjmirror;

import android.app.Application;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static android.R.attr.onClick;

/**
 * Created by Eric Weiss and James Gabriel on 9/27/2017.
 */

public class SettingsFragment extends Fragment {
    View myView;
    String IpAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings_layout, container, false);
        Button ApplyChanges = (Button) myView.findViewById(R.id.button);
        ApplyChanges.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText IpEditText = (EditText) myView.findViewById(R.id.editText5);
                IpAddress = IpEditText.getText().toString();
            }
        });

        return myView;
    }
}
