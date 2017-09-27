package com.merj.merjmirror;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * Created by Eric on 9/27/2017.
 */

public class PreferenceFragment extends Fragment {

    View myView;

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

        spinner2.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner2, spinner2, 0, 0);
        // only passes the spinner. The numbers are irrelevant afaik.
    }

    //Declares a class used to listen to which spinner item is selected
    public class YourItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> spinner, View view, int pos, long id) {

            String ItemName = spinner.getSelectedItem().toString();

            switch (ItemName) {
                //case "Empty": spinner.setBackgroundColor(555555);
                case "Weather":
                    view.setBackground(getActivity().getDrawable(R.drawable.weather));
                    break;
                case "Calendar":
                    view.setBackground(getActivity().getDrawable(R.drawable.calendar));
                    break;
                case "Horoscope":
                    view.setBackground(getActivity().getDrawable(R.drawable.horoscope));
                    break;
                case "Comics":
                    view.setBackground(getActivity().getDrawable(R.drawable.comics));
                    break;
                case "News":
                    view.setBackground(getActivity().getDrawable(R.drawable.news));
                    break;
                case "Reminders":
                    view.setBackground(getActivity().getDrawable(R.drawable.notes));
                    break;
                case "Stocks":
                    view.setBackground(getActivity().getDrawable(R.drawable.stocks));
                    break;
                case "Clock":
                    view.setBackground(getActivity().getDrawable(R.drawable.clock));
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
