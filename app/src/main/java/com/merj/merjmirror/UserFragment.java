package com.merj.merjmirror;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Eric on 9/27/2017.
 */

public class UserFragment extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.user_layout, container, false);

        String[] myStringArray = {"Eric", "Megan", "James", "Ryan", "New User"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, myStringArray);

        ListView listView = (ListView) myView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        return myView;
    }
}
