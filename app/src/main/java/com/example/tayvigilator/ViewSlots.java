package com.example.tayvigilator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

public class ViewSlots extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slots);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void onStart() {
        TextView tv = (TextView)  findViewById(R.id.testOutput);
        File path = getApplicationContext().getFilesDir();
        File file = new File (path, "file.txt");//creates file if not already exists
        try {
            FileInputStream fis = openFileInput("file.txt");
            int c;
            String temp = "";
            while ((c = fis.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
            if (temp.isEmpty())
                tv.setText("Nothing to show");
            else
                tv.setText(temp);
            Toast.makeText(getBaseContext(), "file read", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
        super.onStart();
    }
}
