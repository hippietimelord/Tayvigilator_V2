package com.example.tayvigilator;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.view.View;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Add.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinLay1);
        LinearLayout.LayoutParams dim=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        try {
            File path = getApplicationContext().getFilesDir();
            File file = new File (path, "file.txt");
            FileInputStream fis = new FileInputStream(file);
            Scanner reader = new Scanner(fis);
            ArrayList<String> slot = new ArrayList<>();
            ArrayList<TextView> tvList = new ArrayList<>();
            while (reader.hasNext()) {
                String role = reader.nextLine(); //separate by tokens
                String start = reader.nextLine();
                String end = reader.nextLine();
                String date = reader.nextLine();
                String venue = reader.nextLine();
                String extra = "";

                if (aTimeChecker(end, date)) {
                    if (bTimeChecker(start, date))
                        extra = " (In Progress)";
                    else
                        extra = "(" + timeCalc(start, date) + ")";
                    slot.add(date + "\n" +
                            start + "-" +
                            end + "\n" +
                            role + "\n" +
                            venue + " " + extra + "\n");
                }
            }
            Collections.sort(slot);
            reader.close();
            for (int i = 0; i<slot.size(); i++) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(dim);
                tvList.add(textView);
                tvList.get(i).setText(slot.get(i));
                linearLayout.addView(textView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TextView textView = new TextView(this);
            textView.setLayoutParams(dim);
            textView.setText("Nothing to display");
            linearLayout.addView(textView);
        }
    }

    public Boolean aTimeChecker(String time, String date) {
        SimpleDateFormat datetime = new SimpleDateFormat("dd MMM, yyyy (EEE) h:mm a");
        Date currentDT = Calendar.getInstance().getTime();
        try {
            Date current = datetime.parse(datetime.format(currentDT));
            Date check = datetime.parse(date + " " + time);
            if (check.after(current))
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Boolean bTimeChecker(String time, String date) {
        SimpleDateFormat datetime = new SimpleDateFormat("dd MMM, yyyy (EEE) h:mm a");
        Date currentDT = Calendar.getInstance().getTime();
        try {
            Date current = datetime.parse(datetime.format(currentDT));
            Date check = datetime.parse(date + " " + time);
            if (check.before(current))
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String timeCalc(String time, String date) {
        SimpleDateFormat datetime = new SimpleDateFormat("dd MMM, yyyy (EEE) h:mm a");
        Date currentDT = Calendar.getInstance().getTime();
        try {
            Date current = datetime.parse(datetime.format(currentDT));
            Date check = datetime.parse(date + " " + time);
            long eta = check.getTime() - current.getTime();
            long etaM = eta / ( 60 * 1000 ) % 60;
            long etaH = eta / ( 60 * 60 * 1000 ) % 60 ;
            long etaD = eta / ( 24 * 60 * 60 * 1000 ) % 60 ;
            String etaS = "";
            if (etaD >= 1 )
                etaS = etaD + " days " + etaH + " hours left";
            else if (etaH >= 1)
                etaS = etaH + " hours " + etaM + " minutes left";
            else
                etaS = etaM + " minutes left";

            return etaS;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            Intent addpage = new Intent(Home.this,Add.class);
            startActivity(addpage);

        } else if (id == R.id.nav_view) {
            Intent viewpage = new Intent(Home.this,ViewSlots.class);
            startActivity(viewpage);

        } else if (id == R.id.nav_ann) {
            Intent annpg = new Intent(Home.this,Announcements.class);
            startActivity(annpg);

        } else if (id == R.id.nav_history) {
            Intent annpg = new Intent(Home.this,History.class);
            startActivity(annpg);

        } else if (id == R.id.nav_about) {
            Intent annpg = new Intent(Home.this,About.class);
            startActivity(annpg);

        } else if (id == R.id.nav_help) {
            Intent helppg = new Intent(Home.this,Help.class);
            startActivity(helppg);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
