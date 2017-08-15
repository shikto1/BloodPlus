package com.example.shishir.blood.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.Fragment.AboutBloodPlus;
import com.example.shishir.blood.Fragment.Admin;
import com.example.shishir.blood.Fragment.AdminHomeScreen;
import com.example.shishir.blood.Fragment.MemberHomeScreen;
import com.example.shishir.blood.Network;
import com.example.shishir.blood.R;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    TextView userName;
    LocalDatabase localDatabase;
    NavigationView navigationView;
    private boolean isAdmin, loggedIn;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BLOOD+");
        setSupportActionBar(toolbar);

        init();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavigationActivity.this, RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        localDatabase = new LocalDatabase(this);
        isAdmin = localDatabase.getAdmin();
        loggedIn = localDatabase.getLoggedIn();
        ToastMessage(localDatabase.getUserName());

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        userName = (TextView) v.findViewById(R.id.userName);

        userName.setText(localDatabase.getUserName());

        if (isAdmin)
            ToastMessage("ADMIN");
        else
            ToastMessage("MEMBER");
        ///If the user is a a normal member.....................................................................................
        fragmentManager.beginTransaction().add(R.id.navigationLayout, new MemberHomeScreen()).commit();
        //If the user is a admin then the admin HOme screen will be appeared......................................
        //transaction.add(R.id.navigationLayout, new AdminHomeScreen()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (loggedIn) {
                new AlertDialog.Builder(this).setMessage("Sure to exit ?").setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
            } else
                super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            new AlertDialog.Builder(this).setMessage("Sure to logout ?")
                    .setCancelable(false)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            localDatabase.setLoggedIn(false);
                            localDatabase.setAdmin(0);
                            localDatabase.setUserName("");
                            localDatabase.setUserBloodGroup("");
                            startActivity(new Intent(NavigationActivity.this, FirstActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent(this, NavigationMenuActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        switch (id) {
            case R.id.allDonor: {
                startActivity(new Intent(this, AllDonorActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;

            }
            case R.id.admin: {
                if (Network.isNetAvailable(this)) {
                    intent.putExtra("frg", 3);
                    startActivity(intent);
                    break;
                } else {
                    Network.showInternetAlertDialog(this);
                    break;
                }

            }
            case R.id.setting: {
                ToastMessage("Setting");

            }
            case R.id.facebookPage: {
                ToastMessage("Facebook");

            }
            case R.id.feedback: {
                ToastMessage("FeedBact");

            }
            case R.id.about: {
                intent.putExtra("frg", 7);
                startActivity(intent);
                break;
            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
