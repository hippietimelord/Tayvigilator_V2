package com.example.tayvigilator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Announcements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
