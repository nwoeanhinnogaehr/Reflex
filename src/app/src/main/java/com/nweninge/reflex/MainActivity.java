package com.nweninge.reflex;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The main activity that contains the fragments for the 3 different parts of the application.
 * Implemented using a navigation drawer.
 */
public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /**
     * A database storing and keeping track of statistics from the app.
     */
    private RecordDatabase recordDb;

    /**
     * True if the the user has selected something on the menu yet, false if they have not.
     * Used to determine if we should show the blank fragment or one of the others.
     */
    private boolean itemSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        recordDb = new RecordDatabase();
        recordDb.loadRecords(this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment;
        if (itemSelected) {
            switch (position) {
                default:
                case 0:
                    fragment = SingleUserFragment.newInstance(recordDb);
                    mTitle = getString(R.string.title_section1);
                    break;
                case 1:
                    fragment = MultiUserFragment.newInstance(recordDb);
                    mTitle = getString(R.string.title_section2);
                    break;
                case 2:
                    fragment = StatsFragment.newInstance(recordDb);
                    mTitle = getString(R.string.title_section3);
                    break;

            }
        } else {
            mTitle = getTitle();
            fragment = new BlankFragment();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        // change the title of the activity depending on which option was selected
        getActionBar().setTitle(mTitle);
        itemSelected = true;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
