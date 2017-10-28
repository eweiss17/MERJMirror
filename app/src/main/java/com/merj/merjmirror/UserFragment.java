package com.merj.merjmirror;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
import java.util.ArrayList;

import database.MerjMirror;

/**
 * Created by ??? on 9/27/2017.
 */

public class UserFragment extends Fragment {
    View myView;
    UserSelectedListener mCallback;
    String newUser = "";
    //Array List is way better than basic arrays for this.
    ArrayList al = new ArrayList();
    ArrayList <String> spaghetti = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.user_layout, container, false);


        //ConnectToDatabase connect = new ConnectToDatabase();
        //connect.execute();

        MerjMirror database = new MerjMirror();
        spaghetti = database.getUsers();
        Log.d("Spaghetti ", spaghetti.toString());
        //Hardcoded until access to database. Can use these for testing
        al.add("Megan");
        al.add("Eric");
        al.add("James");
        al.add("Ryan");


        //Button crap
        Button newUserButton = (Button) myView.findViewById(R.id.add_new_user_button);

        UserFragment.MessagePopUp clicks = new UserFragment.MessagePopUp();
        newUserButton.setOnClickListener(clicks);

        //List crap
        adaptArray(spaghetti);

        return myView;
    }

    void adaptArray(ArrayList listData) {
        //Call to database to make sure we have updated data


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, listData);

        ListView listView = (ListView) myView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        UserFragment.UserSelected userListener = new UserFragment.UserSelected();
        listView.setOnItemClickListener(userListener);
    }


    public class MessagePopUp implements AdapterView.OnClickListener {
        public void onClick(View v) {
            // implements your things
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("New User");

            // Set up the input
            final EditText input = new EditText(v.getContext());
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
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
                    newUser = input.getText().toString();
                    //Toast toast = Toast.makeText(myView.getContext(), "Your new user is " + newUser, Toast.LENGTH_SHORT );
                    //toast.setGravity(Gravity.CENTER, 0, 0);
                    //toast.show();

                    //Adding the new user to the list and then updating it
                    al.add(newUser);

                    //Database call send

                    adaptArray(al);
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

    public class UserSelected implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> spinner, View view, int pos, long id) {
            mCallback.onUserSelected(pos);
        }
    }

    public interface UserSelectedListener {
        public void onUserSelected(int position);
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

    /*private class ConnectToDatabase extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] params) {
            // do above Server call here

            spaghetti = database.getUsers();
            Log.d("Spaghetti ", spaghetti.toString());
            return "some message";
        }

        @Override
        protected void onPostExecute(String message) {
            //process message

        }
    }*/
}