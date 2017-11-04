package com.merj.merjmirror;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/* Created By Eric the Great and King James VI
* might need to account for when the app closes and opens, certain changes must be saved*/

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UserFragment.UserSelectedListener {

    //SettingsFragment settingsFragment = new SettingsFragment();
    //UserFragment userFragment = new UserFragment();
    //PreferenceFragment preferenceFragment = new PreferenceFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();

        //Eric here, doing User fragment so they can select their active user
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame
                        , new UserFragment())
                .commit();
        StartupRoutine();

        NavigationViewStart();
    }

    private void StartupRoutine() {
        /*if needed
        connect to mirror*/
    }

    private void NavigationViewStart() {

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        //getFragmentManager().beginTransaction().add(R.id.fragment_container, fragmentToBeAdded, tag).commit();

        if (id == R.id.nav_user_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new UserFragment(), "user")
                    .commit();
        } else if (id == R.id.nav_settings_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new SettingsFragment(), "settings")
                    .commit();
        } else if (id == R.id.nav_preference_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new PreferenceFragment(), "pref")
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onUserSelected(String userName) {

        PreferenceFragment prefFrag = (PreferenceFragment)
                getFragmentManager().findFragmentById(R.id.nav_preference_layout);

        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        //Menu menu = navView.getMenu();
        //Log.d("ChECking", userName);


        if (prefFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            prefFrag.setText(userName);
            //prefFrag.updateArticleView(position);

        } else {
                // Otherwise, we're in the one-pane layout and must swap frags...

                FragmentManager fragmentManager = getFragmentManager();

                // Create fragment and give it an argument for the selected article
                PreferenceFragment newFragment = new PreferenceFragment();
                Bundle args = new Bundle();
                args.putString("UserName", userName);
                newFragment.setArguments(args);

                fragmentManager.beginTransaction().replace(R.id.content_frame, newFragment).addToBackStack(null).commit();
                //onNavigationItemSelected(menu.getItem(2));
                //drawer.clearFocus();
                //newFragment.setText(userName);

        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }
}