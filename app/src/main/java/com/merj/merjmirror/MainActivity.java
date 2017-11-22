package com.merj.merjmirror;

import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/*
 * Created By Eric Weiss and James Gabriel
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UserFragment.UserSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame
                        , new UserFragment())
                .commit();

        NavigationViewStart();
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
        int id = item.getItemId();

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

    //This is connecting from the UserFragment and sending information to the Preference Fragment
    public void onUserSelected(String userName, String userID) {
        FragmentManager fragmentManager = getFragmentManager();

        // Create fragment and give it an argument for the selected article
        PreferenceFragment newFragment = new PreferenceFragment();
        Bundle args = new Bundle();
        args.putString("UserName", userName);
        args.putString("UserID", userID);
        newFragment.setArguments(args);

        fragmentManager.beginTransaction().replace(R.id.content_frame, newFragment).addToBackStack(null).commit();
    }
}