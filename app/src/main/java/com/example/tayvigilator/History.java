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
            File path = getApplicationContext().getExternalFilesDir(null);
            File file = new File (path, "file.txt");
            FileInputStream fis = new FileInputStream(file);
            Scanner reader = new Scanner(fis);
            ArrayList<TextView> tvList = new ArrayList<>();
            int i = 0;
            while (reader.hasNext()) {
                String role = reader.nextLine(); //separate by tokens
                String start = reader.nextLine();
                String end = reader.nextLine();
                String date = reader.nextLine();
                String venue = reader.nextLine();
                    String slot =   role + "\n" +
                                    start + "\n" +
                                    end + "\n" +
                                    date + "\n" +
                                    venue + "\n\n";
                    TextView textView = new TextView(this);
                    textView.setLayoutParams(dim);
                    tvList.add(textView);
                    tvList.get(i).setText(slot);
                    i++;
                    linearLayout.addView(textView);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            TextView textView = new TextView(this);
            textView.setLayoutParams(dim);
            textView.setText("Nothing to display");
            linearLayout.addView(textView);
        }
    }
}
