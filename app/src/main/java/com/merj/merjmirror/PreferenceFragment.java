package com.merj.merjmirror;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
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
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

import database.PutUtility;


/**
 * Created by Chad Roxx on 9/27/2017.
 *
 * Work to be done here still:
 * UserInput must be translated into api format. Eric is making an object to hold this data
 * UserInput must then be added into database string. Object will be translated before sending.
 * UserInput must change when spinners change. Eric has not worked on this yet.
 */

public class PreferenceFragment extends Fragment {

    View myView;
    //User input from each preference location, not yet included in loads or saves
    String[] UserInput = new String[7];
    ArrayList position = new ArrayList();
    ArrayList selection = new ArrayList();
    ArrayList details = new ArrayList();;
    //Set this to false every time you want to change all preferences at once. Then use the one 1 second delay and change it to true again.
    Boolean UserInputUnsaved = Boolean.TRUE;
    ArrayList setPreferenceList = new ArrayList();
    ArrayList prefSelections = new ArrayList();
    String newPrefListTitle = "";
    static String userID = null;
    JSONObject jobj = null;
    static JSONArray jarr = null;
    //Eric Home Ip address 192.168.0.6
    static String ipAddress = "192.168.0.14";

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Spinner spinner6;
    Spinner spinner7;
    Spinner spinner8;
    Spinner spinner9;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.preference_layout, container, false);

        Button newPrefButton = (Button) myView.findViewById(R.id.add_new_button);
        Button saveButton = (Button) myView.findViewById(R.id.save_button);

        userID = (getArguments() != null ? getArguments().getString("UserID") : "0");

        //Creating Default data
        CreatePreferenceSelectionList();

        //This will be constant
        CreateSpinners();

        //Adding a listener to listen to different selections from users preferences
        ChangesFromDatabase();

        //Giving new user button on click functionality
        PreferenceFragment.ButtonPopUpBox box = new PreferenceFragment.ButtonPopUpBox();
        newPrefButton.setOnClickListener(box);
        saveButton.setOnClickListener(box);

        setText(getArguments() != null ? getArguments().getString("UserName") : "No User Selected");

        return myView;
    }

    public void CreateSpinners() {

        spinner1 = (Spinner) myView.findViewById(R.id.spinner);
        spinner2 = (Spinner) myView.findViewById(R.id.spinner2);
        spinner3 = (Spinner) myView.findViewById(R.id.spinner3);
        spinner4 = (Spinner) myView.findViewById(R.id.spinner4);
        spinner6 = (Spinner) myView.findViewById(R.id.spinner6);
        spinner7 = (Spinner) myView.findViewById(R.id.spinner7);
        spinner8 = (Spinner) myView.findViewById(R.id.spinner8);
        spinner9 = (Spinner) myView.findViewById(R.id.spinner9);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> PreferenceNames = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.preferences_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        PreferenceNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner1.setAdapter(PreferenceNames);
        spinner2.setAdapter(PreferenceNames);
        spinner3.setAdapter(PreferenceNames);
        spinner4.setAdapter(PreferenceNames);
        spinner6.setAdapter(PreferenceNames);
        spinner7.setAdapter(PreferenceNames);
        spinner8.setAdapter(PreferenceNames);
        spinner9.setAdapter(PreferenceNames);

        //Declares a listener object and passes one of the spinners
        PreferenceFragment.YourItemSelectedListener spinnerListener = new PreferenceFragment.YourItemSelectedListener();

        spinner1.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner1, spinner1, 0, 0);
        spinner2.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner2, spinner2, 1, 0);
        spinner3.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner3, spinner3, 2, 0);
        spinner4.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner4, spinner4, 3, 0);
        spinner6.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner6, spinner6, 4, 0);
        spinner7.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner7, spinner7, 5, 0);
        spinner8.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner8, spinner8, 6, 0);
        spinner9.setOnItemSelectedListener(spinnerListener);
        spinnerListener.onItemSelected(spinner9, spinner9, 7, 0);
        // only passes the spinner. The numbers are irrelevant afaik.
    }

    public void CreatePreferenceSelectionList() {
        Spinner pref_spinner = (Spinner) myView.findViewById(R.id.preference_list);

        //Connecting to database
        //This default is required, if this list is empty, a null pointer exception will occur
        setPreferenceList.add("Default");
        new GetData().execute(userID);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, setPreferenceList);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pref_spinner.setAdapter(adapter);

    }

    public void ChangesFromDatabase() {
        //We should make these globally accessable if we are going to do it like this
        Spinner pref_spinner = (Spinner) myView.findViewById(R.id.preference_list);

        PreferenceFragment.YourItemSelectedListener prefListener = new PreferenceFragment.YourItemSelectedListener();

        pref_spinner.setOnItemSelectedListener(prefListener);
        prefListener.onItemSelected(pref_spinner, pref_spinner, 13, 0);

    }

    public void setText(String name) {
        TextView text = (TextView) myView.findViewById(R.id.username_text);
        text.setText(name);
    }

    //Declares a class used to listen to which spinner item is selected
    public class YourItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> spinner, View view, int pos, long id) {

            String ItemName = spinner.getSelectedItem().toString();
            Log.d("Cookies d", ItemName);
            Log.d("Cookies d", spinner.toString());
            //This is mainly here to see if i can get it to work, will need to make cleaner in future

            if (spinner.toString().contains("preference_list")) {

                //Pull data from database
                UserInputUnsaved = Boolean.FALSE;


                //Test until we get real data
                if (ItemName.equals("Testing")) {
                    Log.d("Cookies", selection.get(8).toString());
                    spinner1.setSelection(Integer.parseInt(selection.get(0).toString()));
                    spinner2.setSelection(Integer.parseInt(selection.get(1).toString()));
                    //spinner3.setSelection(Integer.parseInt(selection.get(2).toString()));
                    spinner4.setSelection(Integer.parseInt(selection.get(3).toString()));
                    spinner6.setSelection(Integer.parseInt(selection.get(5).toString()));
                    //spinner7.setSelection(Integer.parseInt(selection.get(6).toString()));
                    spinner8.setSelection(Integer.parseInt(selection.get(7).toString()));
                   // spinner9.setSelection(Integer.parseInt(selection.get(8).toString()));

                }
                else if (ItemName == "Night") {
                    spinner1.setSelection(6);
                    spinner2.setSelection(6);
                    spinner3.setSelection(6);
                    spinner4.setSelection(6);
                    spinner6.setSelection(6);
                    spinner7.setSelection(6);
                    spinner8.setSelection(6);
                    spinner9.setSelection(6);
                }
                else {
                    spinner1.setSelection(4);
                    spinner2.setSelection(5);
                    spinner3.setSelection(4);
                    spinner4.setSelection(3);
                    spinner6.setSelection(2);
                    spinner7.setSelection(0);
                    spinner8.setSelection(0);
                    spinner9.setSelection(0);
                }

                //The spinner listener only acts after the whole if statement is executed, thus we needed a delay.
                new CountDownTimer(1000, 1000) {
                    public void onFinish() {
                        // When timer is finished
                        UserInputUnsaved = Boolean.TRUE;
                    }
                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();

            }
            else if (UserInputUnsaved){

                switch (ItemName) {
                    //sports?
                    case "Weather":
                        //view.setBackground(weather);
                        String BuilderTitle = "Enter Weather Location";
                        String Example = "Toledo, OH";
                        CreatePrefPopUpBox(BuilderTitle, Example, pos);
                        break;
                    case "Calendar":
                        //view.setBackground(calendar);
                        break;
                    case "Horoscope":
                        //view.setBackground(getActivity().getDrawable(R.drawable.horoscope));
                        String BuilderTitle1 = "Enter Zodiac Sign";
                        String Example1 = "Sagittarius";
                        CreatePrefPopUpBox(BuilderTitle1, Example1, pos);
                        break;
                    case "Comic":
                        //view.setBackground(getActivity().getDrawable(R.drawable.comics));
                        break;
                    case "News":
                        //view.setBackground(getActivity().getDrawable(R.drawable.news));
                        String BuilderTitle2 = "Enter News Source";
                        String Example2 = "New York Times";
                        CreatePrefPopUpBox(BuilderTitle2, Example2, pos);

                        //this line converts the input into the API code
                        if (UserInput[pos] == "New York Times") UserInput[pos] = "the-new-york-times";

                        break;
                    case "Reminder":
                        //view.setBackground(getActivity().getDrawable(R.drawable.notes));
                        String BuilderTitle3 = "Enter a Reminder";
                        String Example3 = "Don't forget to buy eggs!";
                        CreatePrefPopUpBox(BuilderTitle3, Example3, pos);
                        break;
                    case "Stock":
                        //view.setBackground(getActivity().getDrawable(R.drawable.stocks));
                        String BuilderTitle4 = "Enter Stock Code";
                        String Example4 = "AAPL";
                        CreatePrefPopUpBox(BuilderTitle4, Example4, pos);
                        break;
                    case "Clock":
                        //view.setBackground(getActivity().getDrawable(R.drawable.clock));
                        break;
                    default:
                        break;
                }
            }
            else {}
        }

        public void onNothingSelected(AdapterView parent) {
            // This needs to be included. We won't use this though.
        }
    }

    public void CreatePrefPopUpBox(String BuilderTitle, final String Example, final int pos) {

        AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());

        builder.setTitle(BuilderTitle);

        // Set up the input
        final EditText input = new EditText(myView.getContext());
        input.setHint(Example);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        //This is all to get some margin on the dialog box
        FrameLayout container = new FrameLayout(myView.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        input.setLayoutParams(params);
        container.addView(input);

        builder.setView(container);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserInput[pos] = input.getText().toString();
                if (UserInput[pos].isEmpty()) UserInput[pos] = Example;
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

    //Pop up box for new preference button
    public class ButtonPopUpBox implements AdapterView.OnClickListener {
        public void onClick(View v) {
            if (v.toString().contains("save_button")) {

                prefSelections.clear();

                prefSelections.add(spinner1.getSelectedItem());
                prefSelections.add(spinner2.getSelectedItem());
                prefSelections.add(spinner3.getSelectedItem());
                prefSelections.add(spinner4.getSelectedItem());
                prefSelections.add(spinner6.getSelectedItem());
                prefSelections.add(spinner7.getSelectedItem());
                prefSelections.add(spinner8.getSelectedItem());
                prefSelections.add(spinner9.getSelectedItem());

                //Use UserInput[] here

                //Submit all changes to database here

                Toast toast = Toast.makeText(myView.getContext(), "Preference saved!", Toast.LENGTH_SHORT );
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("New Preference");

                // Set up the input
                final EditText input = new EditText(myView.getContext());

                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                //This is all to get some margin on the dialog box
                FrameLayout container = new FrameLayout(v.getContext());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
                input.setLayoutParams(params);
                container.addView(input);

                builder.setView(container);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        newPrefListTitle = input.getText().toString();

                        setPreferenceList.add(newPrefListTitle);

                        //Add to database
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

    //Object
    public void parse(String rawData) {
            String [] items = rawData.split(":");
            for (int i = 0; i < items.length; i++) {
                String [] rig = items[i].split(",");
                if (items[i].contains("(")) {
                    String [] bigRig = items[i].split("\\(");
                    details.add(bigRig[1]);
                }
                position.add(rig[0]);
                selection.add(rig[1]);
            }
    }

    //These classes are for database connections
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog mProgressDialog;
        private String res;

        @Override
        protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(myView.getContext(),
                    "", "Please wait...");
        }

        @Override
        protected String doInBackground(String... params) {
            res = null;
            PutUtility put = new PutUtility();

            put.setParam("UserID", params[0].toString());

            //EVEN THOUGH THIS IS A GET, I AM USING POST TO SPECIFY WHICH ID
            try {
                res = put.postData("http://"+ipAddress+"/android_connect/get_preference_data.php");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        protected void onPostExecute(String res) {
            //Here you get response from server in res
            Log.d("GET Response PREF", res);

            //This is sorting the data into a JSON array and Object to acquire wanted data
            try {
                jarr = new JSONArray(res);
                //setPreferenceList.clear();
                for(int n = 0; n < jarr.length(); n++)
                {
                    jobj = jarr.getJSONObject(n);
                    setPreferenceList.add(jobj.getString("PrefName"));
                    //Log.d("DataDisplayString", jobj.getString("DataDisplay"));
                    parse(jobj.getString("DataDisplay"));

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            //adaptArray(userList);
            mProgressDialog.cancel();
        }
    }
}
