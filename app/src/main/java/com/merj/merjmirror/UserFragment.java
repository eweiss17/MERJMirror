package com.merj.merjmirror;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

import database.NonSavedValues;
import database.PutUtility;
import database.SavedValues;

/**
 * Created by Eric Weiss on 9/27/2017.
 */

public class UserFragment extends Fragment  {
    View myView;
    UserSelectedListener mCallback;
    ArrayList<String> userList = new ArrayList<>();
    ArrayList<String> userIDList = new ArrayList<>();
    JSONObject jobj = null;
    JSONArray jarr = null;
    String ipAddress;
    Boolean IPset = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.user_layout, container, false);

        ipAddress = SavedValues.getIP(myView.getContext());

        if (NonSavedValues.IPset == Boolean.FALSE) {
            GetIPAddressWithAPopUpBox();
            NonSavedValues.IPset = true;}
        else {new GetUserData().execute();}

        Button newUserButton = (Button) myView.findViewById(R.id.add_new_user_button);
        Button deleteUserButton = (Button) myView.findViewById(R.id.delete_user_button);

        UserFragment.MessagePopUp clicks = new UserFragment.MessagePopUp();
        UserFragment.ListPopUp lists = new UserFragment.ListPopUp();
        newUserButton.setOnClickListener(clicks);
        deleteUserButton.setOnClickListener(lists);

        adaptArray(userList);
        return myView;
    }


    public void GetIPAddressWithAPopUpBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());

        builder.setTitle("Enter IP Address");

        // Set up the input
        final EditText input = new EditText(myView.getContext());
        input.setText(ipAddress);
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

                ipAddress = input.getText().toString();

                SavedValues.setIP(myView.getContext(), ipAddress);

                new GetUserData().execute();
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

    void adaptArray(ArrayList listData) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, listData);

        ListView listView = (ListView) myView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        UserFragment.UserSelected userListener = new UserFragment.UserSelected();
        listView.setOnItemClickListener(userListener);
    }


    private class MessagePopUp implements AdapterView.OnClickListener {
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            builder.setTitle("New User");

            // Set up the input
            final EditText input = new EditText(v.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            //This is all to get some margin on the dialog box
            FrameLayout container = new FrameLayout(v.getContext());
            FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
            input.setLayoutParams(params);
            container.addView(input);

            builder.setView(container);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String user = input.getText().toString();

                    //Adding the new user to the list and then updating it
                    userList.add(user);
                    adaptArray(userList);
                    new SendUserData().execute(user);
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

    private class ListPopUp implements AdapterView.OnClickListener {
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.select_dialog_singlechoice);

            arrayAdapter.addAll(userList);

            builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String user = arrayAdapter.getItem(which);

                    Toast toast = Toast.makeText(myView.getContext(), user + " deleted.", Toast.LENGTH_SHORT );
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    //Deleting user from list and database
                    userList.remove(user);
                    adaptArray(userList);
                    new DeleteUserData().execute(user);

                }
            });

            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }
    }

    //These classes connect this fragment to main activity
    private class UserSelected implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
            String userName = list.getItemAtPosition(pos).toString();
            int f = userList.indexOf(userName);
            String userID = userIDList.get(f).toString();
            mCallback.onUserSelected(userName, userID);
        }
    }

    public interface UserSelectedListener {
        void onUserSelected(String userName, String userID);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (UserSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement UserSelectedListener");
        }
    }

    //These classes are for database connections
    private class GetUserData extends AsyncTask<String, Void, String> {

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

            try {
                res = put.getData("http://"+ipAddress+"/android_connect/get_user_data.php");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        protected void onPostExecute(String res) {
            Log.d("GET Response", res);

            //This is sorting the data into a JSON array and Object to acquire wanted data
            try {
                jarr = new JSONArray(res);

                for(int n = 0; n < jarr.length(); n++)
                {
                    jobj = jarr.getJSONObject(n);
                    userList.add(jobj.getString("userName"));
                    userIDList.add(jobj.getString("userID"));
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            adaptArray(userList);
            mProgressDialog.cancel();
        }
    }

    private class DeleteUserData extends AsyncTask<String, Void, String> {

        private String res;

        @Override
        protected String doInBackground(String... params) {
            res = null;
            PutUtility put = new PutUtility();

            put.setParam("userName", params[0]);

            try {
                res = put.postData("http://"+ipAddress+"/android_connect/delete_user.php");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;

        }

        protected void onPostExecute(String res) {
            Log.v("DELETE response", res);
        }
    }

    private class SendUserData extends AsyncTask<String, Void, String> {

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

            put.setParam("userName", params[0]);

            try {
                res = put.postData("http://"+ipAddress+"/android_connect/add_user.php");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        protected void onPostExecute(String res) {
            //response is the decided userID
            Log.v("POST response", res);

            userIDList.add(res);

            mProgressDialog.cancel();
        }
    }

}