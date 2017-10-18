package com.merj.merjmirror;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;


/**
 * Created by Chad Roxx on 9/27/2017.
 */

public class PreferenceFragment extends Fragment {

    View myView;
    String[] SelectedPreferences = new String[8];
    String[] Example = {"Morning", "Day", "Night"};
    String m_Text = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.preference_layout, container, false);
        Button newPrefButton = (Button) myView.findViewById(R.id.add_new_button);

        //Creating Default data
        CreatePreferenceSelectionList();

        //This will be constant
        CreateSpinners();

        //Adding a listener to listen to different selections from users preferences
        ChangesFromDatabase();

        //Giving new user button on click functionality
        PreferenceFragment.ButtonPopUpBox box = new PreferenceFragment.ButtonPopUpBox();
        newPrefButton.setOnClickListener(box);

        return myView;
    }

    public void CreateSpinners() {

        //Spinner 5 is invisible, BOO, spooky ghosts!
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

    public void CreatePreferenceSelectionList() {
        Spinner pref_spinner = (Spinner) myView.findViewById(R.id.preference_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, Example);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pref_spinner.setAdapter(adapter);

    }

    public void ChangesFromDatabase() {
        //We should make these globally accessable if we are going to do it like this
        Spinner pref_spinner = (Spinner) myView.findViewById(R.id.preference_list);

        PreferenceFragment.YourItemSelectedListener prefListener = new PreferenceFragment.YourItemSelectedListener();

        pref_spinner.setOnItemSelectedListener(prefListener);
        prefListener.onItemSelected(pref_spinner, pref_spinner, 0, 0);

    }

    //Declares a class used to listen to which spinner item is selected
    public class YourItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> spinner, View view, int pos, long id) {

            String ItemName = spinner.getSelectedItem().toString();

            //This is mainly here to see if i can get it to work, will need to make cleaner in future
            Spinner spinner1 = (Spinner) myView.findViewById(R.id.spinner);
            Spinner spinner2 = (Spinner) myView.findViewById(R.id.spinner2);
            Spinner spinner3 = (Spinner) myView.findViewById(R.id.spinner3);
            Spinner spinner4 = (Spinner) myView.findViewById(R.id.spinner4);
            Spinner spinner5 = (Spinner) myView.findViewById(R.id.spinner5);
            Spinner spinner6 = (Spinner) myView.findViewById(R.id.spinner6);
            Spinner spinner7 = (Spinner) myView.findViewById(R.id.spinner7);
            Spinner spinner8 = (Spinner) myView.findViewById(R.id.spinner8);
            Spinner spinner9 = (Spinner) myView.findViewById(R.id.spinner9);

            //This may be actually the worst thing i have ever done, i could not figure out how to just grab the spinner ID god damn
            if (spinner.getItemAtPosition(1) == "Day") {

                //Test until we get real data
                if (ItemName == "Day") {
                    spinner1.setSelection(0);
                    spinner2.setSelection(0);
                    spinner3.setSelection(0);
                    spinner4.setSelection(0);
                    spinner5.setSelection(0);
                    spinner6.setSelection(0);
                    spinner7.setSelection(0);
                    spinner8.setSelection(0);
                    spinner9.setSelection(0);
                }
                else if (ItemName == "Night") {
                    spinner1.setSelection(6);
                    spinner2.setSelection(6);
                    spinner3.setSelection(6);
                    spinner4.setSelection(6);
                    spinner5.setSelection(6);
                    spinner6.setSelection(6);
                    spinner7.setSelection(6);
                    spinner8.setSelection(6);
                    spinner9.setSelection(6);
                }
                else {
                    spinner1.setSelection(5);
                    spinner2.setSelection(5);
                    spinner3.setSelection(5);
                    spinner4.setSelection(5);
                    spinner5.setSelection(5);
                    spinner6.setSelection(5);
                    spinner7.setSelection(5);
                    spinner8.setSelection(5);
                    spinner9.setSelection(5);
                }
            }
            else {
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
        }

        public void onNothingSelected(AdapterView parent) {
            // This needs to be included. We won't use this though.
        }
    }

    //Pop up box for new user button
    public class ButtonPopUpBox implements AdapterView.OnClickListener {
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Title");

            // Set up the input
            final EditText input = new EditText(v.getContext());
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    Toast toast = Toast.makeText(myView.getContext(), m_Text, Toast.LENGTH_SHORT );
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
    }
    }
}
