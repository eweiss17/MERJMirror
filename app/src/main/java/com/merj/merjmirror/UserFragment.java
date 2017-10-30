package com.merj.merjmirror;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.Selection;
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
import java.util.List;

/**
 * Created by ??? on 9/27/2017.
 */

public class UserFragment extends Fragment {
    View myView;
    UserSelectedListener mCallback;
    String user = "";
    //Array List is way better than basic arrays for this.
    ArrayList al = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.user_layout, container, false);

        //Hardcoded until access to database. Can use these for testing
        al.add("Megan");
        al.add("Eric");
        al.add("Ryan");
        al.add("James");

        //Button crap
        Button newUserButton = (Button) myView.findViewById(R.id.add_new_user_button);
        Button deleteUserButton = (Button) myView.findViewById(R.id.delete_user_button);

        UserFragment.MessagePopUp clicks = new UserFragment.MessagePopUp();
        UserFragment.ListPopUp lists = new UserFragment.ListPopUp();
        newUserButton.setOnClickListener(clicks);
        deleteUserButton.setOnClickListener(lists);

        //List crap
        adaptArray(al);

        return myView;
    }

    void adaptArray(ArrayList listData) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, listData);

        ListView listView = (ListView) myView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        UserFragment.UserSelected userListener = new UserFragment.UserSelected();
        listView.setOnItemClickListener(userListener);
    }


    public class MessagePopUp implements AdapterView.OnClickListener {
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
                    user = input.getText().toString();

                    //Adding the new user to the list and then updating it
                    al.add(user);

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

    public class ListPopUp implements AdapterView.OnClickListener {
        public void onClick(View v) {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(v.getContext());

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.select_dialog_singlechoice);

            arrayAdapter.addAll(al);

            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String user = arrayAdapter.getItem(which);

                    Toast toast = Toast.makeText(myView.getContext(), user + " deleted.", Toast.LENGTH_SHORT );
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    al.remove(user);

                    adaptArray(al);
                }
            });
            builderSingle.show();

        }
    }

    public class UserSelected implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
            String userName = list.getItemAtPosition(pos).toString();
            mCallback.onUserSelected(userName);
        }
    }

    public interface UserSelectedListener {
        public void onUserSelected(String userName);
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