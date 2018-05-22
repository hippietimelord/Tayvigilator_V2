package com.example.tayvigilator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinLay);
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
                slot.add(date + "\n" +
                        start + "-" +
                        end + "\n" +
                        role + "\n" +
                        venue + "\n");
            }
            Collections.sort(slot);
            reader.close();
            for (int i = 0; i<slot.size(); i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(dim);
            textView.setTextSize(22);
            tvList.add(textView);
            tvList.get(i).setText(slot.get(i));
            linearLayout.addView(textView);
        }
        } catch (Exception e) {
            e.printStackTrace();
            TextView textView = new TextView(this);
            textView.setLayoutParams(dim);
            textView.setText("Nothing to display");
            textView.setTextSize(22);
            linearLayout.addView(textView);
        }
    }
}
