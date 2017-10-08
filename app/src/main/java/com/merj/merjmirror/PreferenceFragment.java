package com.merj.merjmirror;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageSwitcher;
import android.widget.Spinner;

import java.lang.reflect.Array;


/**
 * Created by Eric on 9/27/2017.
 */

public class PreferenceFragment extends Fragment {

    View myView;
    String[] SelectedPreferences = new String[8];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.preference_layout, container, false);

        CreateSpinners();

        return myView;
    }

    public void CreateSpinners() {

        //I will fix this to look better later, right now this works.
        Spinner spinner = (Spinner) myView.findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) myView.findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) myView.findViewById(R.id.spinner3);
        Spinner spinner4 = (Spinner) myView.findViewById(R.id.spinner4);
        Spinner spinner5 = (Spinner) myView.findViewById(R.id.spinner5);
        Spinner spinner6 = (Spinner) myView.findViewById(R.id.spinner6);
        Spinner spinner7 = (Spinner) myView.findViewById(R.id.spinner7);
        Spinner spinner8 = (Spinner) myView.findViewById(R.id.spinner8);
        Spinner spinner9 = (Spinner) myView.findViewById(R.id.spinner9);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> PreferenceNames = ArrayAdapter.createFromResource(this.getActivity(),
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
        PreferenceFragment.YourItemSelectedListener spinnerListener = new PreferenceFragment.YourItemSelectedListener();

        spinner.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner, spinner, 0, 0);
        spinner2.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner2, spinner2, 1, 0);
        spinner3.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner3, spinner3, 2, 0);
        spinner4.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner4, spinner4, 3, 0);
        spinner5.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner5, spinner5, 4, 0);
        spinner6.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner6, spinner6, 5, 0);
        spinner7.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner7, spinner7, 6, 0);
        spinner8.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner8, spinner8, 7, 0);
        spinner9.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner9, spinner9, 8, 0);
        // only passes the spinner. The numbers are irrelevant afaik.





    }

    //Declares a class used to listen to which spinner item is selected
    public class YourItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> spinner, View view, int pos, long id) {

            String ItemName = spinner.getSelectedItem().toString();

            switch (ItemName) {

                //sports?
                case "Weather":
                    //view.setBackground(weather);
                    SelectedPreferences[pos] = ItemName;
                    // ^^inside because it crashes outside
                    break;
                case "Calendar":
                    //view.setBackground(calendar);
                    SelectedPreferences[pos] = ItemName;
                    break;
                case "Horoscope":
                    //view.setBackground(getActivity().getDrawable(R.drawable.horoscope));
                    SelectedPreferences[pos] = ItemName;
                    break;
                case "Comics":
                    //view.setBackground(getActivity().getDrawable(R.drawable.comics));
                    SelectedPreferences[pos] = ItemName;
                    break;
                case "News":
                    //view.setBackground(getActivity().getDrawable(R.drawable.news));
                    SelectedPreferences[pos] = ItemName;
                    break;
                case "Reminders":
                    //view.setBackground(getActivity().getDrawable(R.drawable.notes));
                    SelectedPreferences[pos] = ItemName;
                    break;
                case "Stocks":
                    //view.setBackground(getActivity().getDrawable(R.drawable.stocks));
                    SelectedPreferences[pos] = ItemName;
                    break;
                case "Clock":
                    //view.setBackground(getActivity().getDrawable(R.drawable.clock));
                    SelectedPreferences[pos] = ItemName;
                    break;
                default:
                    break;
            }
        }

        public void onNothingSelected(AdapterView parent) {
            // This needs to be included. We won't use this though.
        }
    }
}
