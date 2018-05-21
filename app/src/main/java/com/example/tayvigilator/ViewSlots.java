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

public class ViewSlots extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slots);
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
                String extra = "";
                if (endTimeChecker(end, date)) {
                    if (startTimeChecker(start, date))
                        extra = " (In Progress)";
                    else
                        extra = timeCalc(start, date);
                    String slot =   role + "\n" +
                                    start + "\n" +
                                    end + "\n" +
                                    date + "\n" +
                                    venue + "\n" +
                                    extra + "\n";
                TextView textView = new TextView(this);
                textView.setLayoutParams(dim);
                tvList.add(textView);
                tvList.get(i).setText(slot);
                i++;
                linearLayout.addView(textView);
                }
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

    public Boolean endTimeChecker(String time, String date) {
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


    public Boolean startTimeChecker(String time, String date) {
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
}
