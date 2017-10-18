package com.merj.merjmirror;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by ??? on 9/27/2017.
 */

public class UserFragment extends Fragment {
    View myView;
    UserSelectedListener mCallback;
    String newUser = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.user_layout, container, false);

        //Hard-coded for right now, will pull from data base.
        String[] myStringArray = {"Eric", "Megan", "James", "Ryan"};
        Button newUserButton = (Button) myView.findViewById(R.id.add_new_user_button);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, myStringArray);

        ListView listView = (ListView) myView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        UserFragment.UserSelected userListener = new UserFragment.UserSelected();
        listView.setOnItemClickListener(userListener);

        UserFragment.MessagePopUp clicks = new UserFragment.MessagePopUp();
        newUserButton.setOnClickListener(clicks);

        return myView;
    }


    public class MessagePopUp implements AdapterView.OnClickListener {
        public void onClick(View v) {
            // implements your things
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
                    newUser = input.getText().toString();
                    Toast toast = Toast.makeText(myView.getContext(), "Your new user is " + newUser, Toast.LENGTH_SHORT );
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
}