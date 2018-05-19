package com.example.tayvigilator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add extends AppCompatActivity implements
        RoleRadioButton.RoleDialogListener,
        DatePickerFragment.DateDialogListener,
        sTimePickerFragment.TimeDialogListener,
        eTimePickerFragment.TimeDialogListener {

    private static final String roleList = "MainActivity.RoleRadioButton";
    private static final String sTimePicker = "MainActivity.sTimePickerFragment";
    private static final String eTimePicker = "MainActivity.eTimePickerFragment";
    private static final String examDate = "MainActivity.DatePickerFragment";

    private TextView roleDialog;
    private TextView startTimeDialog;
    private TextView endTimeDialog;
    private TextView examDateDialog;
    private TextView Venue;

    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        roleDialog = (TextView) findViewById(R.id.editRole_ID);
        startTimeDialog = (TextView) findViewById(R.id.editStart_ID);
        endTimeDialog = (TextView) findViewById(R.id.editEnd_ID);
        examDateDialog = (TextView) findViewById(R.id.editDate_ID);
        Venue =(TextView)findViewById(R.id.editVenue_ID);
        buttonSubmit = (Button) findViewById(R.id.submit_ID);

        roleDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoleRadioButton dialog = new RoleRadioButton();
                dialog.show(getSupportFragmentManager(), roleList);
            }
        });

        startTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTimePickerFragment dialog = new sTimePickerFragment();
                dialog.show(getSupportFragmentManager(), sTimePicker);
            }
        });

        endTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTimePickerFragment dialog = new eTimePickerFragment();
                dialog.show(getSupportFragmentManager(), eTimePicker);
            }
        });

        examDateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(getSupportFragmentManager(), examDate);
            }
        });

        //Storing the data into file
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFile(roleDialog.getText().toString(), startTimeDialog.getText().toString(),
                        endTimeDialog.getText().toString(), examDateDialog.getText().toString(),
                        Venue.getText().toString());
            }
        });


    }

    @Override
    public void onFinishDialogRole(String role) {
        roleDialog.setText(role);
    }

    @Override
    public void onFinishDialogStart(String time) {
        startTimeDialog.setText(time);
    }

    @Override
    public void onFinishDialogEnd(String time) {
        endTimeDialog.setText(time);
    }

    @Override
    public void onFinishDialog(Date date) {
        examDateDialog.setText(formatDate(date));
    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy (EEE)");
        String hireDate = sdf.format(date);
        return hireDate;
    }

    //File part
    public void saveFile(String role, String start, String end, String date, String text) {
        File path = getApplicationContext().getFilesDir();
        File file = new File (path, "file.txt");
        FileOutputStream fos; //Create txt. file
        try {
            fos = openFileOutput("file.txt", Context.MODE_PRIVATE);
            fos.write(role.getBytes());
            fos.write(start.getBytes());
            fos.write(end.getBytes());
            fos.write(date.getBytes());
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(Add.this, "Slot Added !", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Add.this, "Unable to Add slot", Toast.LENGTH_SHORT).show();
        }
    }

//    public String readFile(String file){
//        String text ="";
//
//        try{
//            FileInputStream fis = openFileInput(file);
//            int size = fis.available();
//            byte[] buffer = new byte[size];
//            fis.read(buffer);
//            fis.close();
//            text=new String(buffer);
//
//        }catch (Exception e){
//           e.printStackTrace();
//            Toast.makeText(Add.this, "Unable to retrieve slots", Toast.LENGTH_SHORT).show();
//        }
//        return text;
//    }
}