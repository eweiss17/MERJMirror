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

import database.PutUtility;


/**
 * Created by Air-Rick on 9/27/2017.
 *
 * Work to be done here still:
 * Make a set active button.
 * Toggle? set active button on active preference (maybe do depending on how difficult this is)
 * UserInput must be translated into api format.
 *
 */

public class PreferenceFragment extends Fragment {

    View myView;

    String[] UserInput = new String[7];
    static ArrayList<String> position = new ArrayList<>();
    static ArrayList<String> selection = new ArrayList<>();
    static ArrayList<String> details = new ArrayList<>();;
    ArrayList setPreferenceList = new ArrayList();
    ArrayList<Object> prefSelections = new ArrayList<>();
    //Set this to false every time you want to change all preferences at once. Then use the one 1 second delay and change it to true again.
    Boolean UserInputUnsaved = Boolean.TRUE;
    String newPrefListTitle = "";
    static String userID = null;
    JSONObject jobj = null;
    static JSONArray jarr = null;
    //Eric Home Ip address 192.168.0.6
    //James 192.168.1.107
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
        //ChangesFromDatabase();

        //Giving new user button on click functionality
        PreferenceFragment.ButtonPopUpBox box = new PreferenceFragment.ButtonPopUpBox();
        newPrefButton.setOnClickListener(box);
        saveButton.setOnClickListener(box);

        setText(getArguments() != null ? getArguments().getString("UserName") : "No User Selected");

        return myView;
    }

    public void CreateSpinners() {

        spinner1 = (Spinner) myView.findViewById(R.id.spinner1);
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

        //Connecting to database
        //This default is required, if this list is empty, a null pointer exception will occur
        setPreferenceList.clear();

        setPreferenceList.add("Default");
        new GetPrefData().execute(userID);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
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

            int whichSpinner;
            if (spinner.toString().contains("spinner1")) {
                whichSpinner = 0;
            }
            else if (spinner.toString().contains("spinner2")) {
                whichSpinner = 1;
            }
            else if (spinner.toString().contains("spinner3")) {
                whichSpinner = 2;
            }
            else if (spinner.toString().contains("spinner4")) {
                whichSpinner = 3;
            }
            else if (spinner.toString().contains("spinner6")) {
                whichSpinner = 5;
            }
            else if (spinner.toString().contains("spinner7")) {
                whichSpinner = 6;
            }
            else if (spinner.toString().contains("spinner8")) {
                whichSpinner = 7;
            }
            else if (spinner.toString().contains("spinner9")) {
                whichSpinner = 8;
            }
            else {
                whichSpinner = 0;
            }

            String ItemName = spinner.getSelectedItem().toString();

            //This is mainly here to see if i can get it to work, will need to make cleaner in future

            if (spinner.toString().contains("preference_list")) {

                //Pull data from database
                UserInputUnsaved = Boolean.FALSE;

                //Default set everything to Empty
                spinner1.setSelection(0);
                spinner2.setSelection(0);
                spinner3.setSelection(0);
                spinner4.setSelection(0);
                spinner6.setSelection(0);
                spinner7.setSelection(0);
                spinner8.setSelection(0);
                spinner9.setSelection(0);

                try {
                    for (int i = 0; i < jarr.length(); i++) {

                        ArrayList<String> selectList;
                        jobj = jarr.getJSONObject(i);
                        if (ItemName.equals(jobj.getString("PrefName"))) {
                            selectList = parse(jobj.getString("DataDisplay"));

                            spinner1.setSelection(Integer.parseInt(selectList.get(0)));
                            spinner2.setSelection(Integer.parseInt(selectList.get(1)));
                            spinner3.setSelection(Integer.parseInt(selectList.get(2)));
                            spinner4.setSelection(Integer.parseInt(selectList.get(3)));
                            spinner6.setSelection(Integer.parseInt(selectList.get(5)));
                            spinner7.setSelection(Integer.parseInt(selectList.get(6)));
                            spinner8.setSelection(Integer.parseInt(selectList.get(7)));
                            spinner9.setSelection(Integer.parseInt(selectList.get(8)));
                        }
                    }
                }
                catch (JSONException e) {
                    Log.e("JSON Exception", e.toString());
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
                        String[] detailArray = {"Toledo, OH"};
                        String boxType = "Text";
                        CreateDetailsPopUpBox(BuilderTitle, detailArray, whichSpinner,boxType);
                        break;
                    case "Calendar":
                        //view.setBackground(calendar);
                        break;
                    case "Horoscope":
                        //view.setBackground(getActivity().getDrawable(R.drawable.horoscope));
                        String BuilderTitle1 = "Select Zodiac Sign";
                        String[] detailArray1 = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
                        String boxType1 = "RadioButton";
                        CreateDetailsPopUpBox(BuilderTitle1, detailArray1, whichSpinner, boxType1);
                        break;
                    case "Comic":
                        //view.setBackground(getActivity().getDrawable(R.drawable.comics));
                        break;
                    case "News":
                        //view.setBackground(getActivity().getDrawable(R.drawable.news));
                        String BuilderTitle2 = "Select News Source";
                        String[] detailArray2 = {"New York Times", "Business Insider", "CNN", "ESPN", "The Independent", "IGN", "Huffington Post", "Time", "USA Today"};
                        String boxType2 = "RadioButton";
                        CreateDetailsPopUpBox(BuilderTitle2, detailArray2, whichSpinner, boxType2);

                        //this line converts the input into the API code
                        if (UserInput[pos] == "New York Times") UserInput[pos] = "the-new-york-times";

                        break;
                    case "Reminder":
                        //view.setBackground(getActivity().getDrawable(R.drawable.notes));
                        String BuilderTitle3 = "Enter a Reminder";
                        String detailArray3[] = {"Don't forget to buy eggs!"};
                        String boxType3 = "Text";
                        CreateDetailsPopUpBox(BuilderTitle3, detailArray3, whichSpinner, boxType3);
                        break;
                    case "Stocks":
                        //view.setBackground(getActivity().getDrawable(R.drawable.stocks));
                        String BuilderTitle4 = "Enter Stock Code";
                        String Example4 = "AAPL";
                        //CreateDetailsPopUpBox(BuilderTitle4, Example4, whichSpinner);
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

    public void CreateDetailsPopUpBox(String BuilderTitle, final String[] detailArray, final int whichSpinner, String boxType) {

        if (boxType.equals("RadioButton")){
            AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());

            builder.setTitle(BuilderTitle);

            int checkedItem = 0;

            builder.setSingleChoiceItems(detailArray, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String newDetail = detailArray[which];
                    details.set(whichSpinner, newDetail);

                    Log.d("pastalavista", details.toString());
                    dialog.cancel();
                }
            });

            builder.setNegativeButton("Cancel", null);

            builder.show();

        }else if(boxType.equals("Text")){

            AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());

            builder.setTitle(BuilderTitle);

            // Set up the input
            final EditText input = new EditText(myView.getContext());
            input.setHint(detailArray[0]);
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

                    String newDetail = input.getText().toString();
                    if (newDetail.isEmpty()){
                        newDetail = detailArray[0];
                    }
                    details.set(whichSpinner, newDetail);

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

    //Pop up box for new preference button
    public class ButtonPopUpBox implements AdapterView.OnClickListener {
        public void onClick(View v) {
            if (v.toString().contains("save_button")) {

                prefSelections.clear();

                //#5 empty is because of needing for slot #5
                prefSelections.add(spinner1.getSelectedItem());
                prefSelections.add(spinner2.getSelectedItem());
                prefSelections.add(spinner3.getSelectedItem());
                prefSelections.add(spinner4.getSelectedItem());
                prefSelections.add("Empty");
                prefSelections.add(spinner6.getSelectedItem());
                prefSelections.add(spinner7.getSelectedItem());
                prefSelections.add(spinner8.getSelectedItem());
                prefSelections.add(spinner9.getSelectedItem());

                //Submit all changes to database here
                parseForDatabase(prefSelections, details);

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

                        //Creating default empty on new creation
                        details.clear();
                        for (int i = 0; i < 9; i++) {
                            details.add("Empty");
                        }


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

    public void parseForDatabase(ArrayList<Object> data, ArrayList<String> details) {
        String reparsedString = "";
        String holder = "";
        ArrayList<Object> intForm = data;

        for (int i = 0; i < intForm.size(); i++) {
            switch (intForm.get(i).toString()) {
                case "Clock":
                    intForm.set(i, "1");
                    break;
                case "Weather":
                    intForm.set(i, "2");
                    break;
                case "News":
                    intForm.set(i, "3");
                    break;
                case "Horoscope":
                    intForm.set(i, "4");
                    break;
                case "Stocks":
                    intForm.set(i, "5");
                    break;
                case "Reminder":
                    intForm.set(i, "6");
                    break;
                case "Comic":
                    intForm.set(i, "7");
                    break;
                default:
                    intForm.set(i, "0");
                    break;
            }
        }

        for (int i = 0; i < intForm.size(); i++){
            if (details.get(i).toString().equals("Empty")) {
                reparsedString += holder.concat(Integer.toString(i+1) + "," + intForm.get(i) + ":");
            }
            else {
                reparsedString += holder.concat(Integer.toString(i+1) + "," + intForm.get(i) + "(" + details.get(i) + ":");
            }
        }
        reparsedString = reparsedString.substring(0, reparsedString.length() - 1);

        Spinner pref_spinner = (Spinner) myView.findViewById(R.id.preference_list);
        String prefName = pref_spinner.getSelectedItem().toString();

        //Sending data
        new SendPrefData().execute(prefName,reparsedString,"0",userID);

    }

    public ArrayList<String> parse(String rawData) {
        ArrayList<String> sl = new ArrayList<>();

        String [] items = rawData.split(":");
        details.clear();
            for (int i = 0; i < items.length; i++) {
                String[] rig = items[i].split(",");
                if (items[i].contains("(")) {
                    String[] bigRig = items[i].split("\\(");
                    String[] rig2 = bigRig[0].split(",");
                    position.add(rig2[0]);
                    sl.add(rig2[1]);
                    details.add(bigRig[1]);
                } else {
                    position.add(rig[0]);
                    sl.add(rig[1]);
                    details.add("Empty");
                }
            }
            return sl;
    }





    //These classes are for database connections
    private class GetPrefData extends AsyncTask<String, Void, String> {

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

            put.setParam("UserID", params[0]);

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
                //setPreferenceList.remove("Default");
                int initialSize = setPreferenceList.size();

                for(int n = 0; n < jarr.length(); n++)
                {
                    jobj = jarr.getJSONObject(n);

                    if (initialSize == 1) {
                        setPreferenceList.add(jobj.getString("PrefName"));
                    }
                    else {
                        setPreferenceList.set(n + 1,jobj.getString("PrefName"));
                        //setPreferenceList.set(n,jobj.getString("PrefName"));
                    }
                    ChangesFromDatabase();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            //adaptArray(userList);
            mProgressDialog.cancel();
        }
    }

    private class SendPrefData extends AsyncTask<String, Void, String> {

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

            put.setParam("PrefName", params[0]);
            put.setParam("DataDisplay", params[1]);
            put.setParam("Active", params[2]);
            put.setParam("UserId", params[3]);

            try {
                res = put.postData("http://"+ipAddress+"/android_connect/set_preference_data.php");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        protected void onPostExecute(String res) {
            //Here you get response from server in res
            Log.d("POST Response PREF", res);

            mProgressDialog.cancel();

            //Getting the new updated data
            new GetPrefData().execute(userID);
        }
    }
}
