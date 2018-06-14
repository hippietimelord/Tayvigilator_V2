package com.mad.tayvigilator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDB = new DatabaseHelper(this);

        roleDialog = (TextView) findViewById(R.id.editRole_ID);
        startTimeDialog = (TextView) findViewById(R.id.editStart_ID);
        endTimeDialog = (TextView) findViewById(R.id.editEnd_ID);
        examDateDialog = (TextView) findViewById(R.id.editDate_ID);
        Venue =(TextView)findViewById(R.id.editVenue_ID);
        buttonSubmit = (Button) findViewById(R.id.submit_ID);


        if(roleDialog.getText().toString().isEmpty())
            roleDialog.setError("Error: This cannot be blank!");

        roleDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoleRadioButton dialog = new RoleRadioButton();
                dialog.show(getSupportFragmentManager(), roleList);
            }
        });

        if (startTimeDialog.getText().toString().isEmpty())
            startTimeDialog.setError("Error: This cannot be blank!");

        startTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTimePickerFragment dialog = new sTimePickerFragment();
                dialog.show(getSupportFragmentManager(), sTimePicker);
            }
        });

        if (endTimeDialog.getText().toString().isEmpty())
            endTimeDialog.setError("Error: This cannot be blank!");

        endTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTimePickerFragment dialog = new eTimePickerFragment();
                dialog.show(getSupportFragmentManager(), eTimePicker);
            }
        });

        if (examDateDialog.getText().toString().isEmpty())
            examDateDialog.setError("Error: This cannot be blank!");

        examDateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(getSupportFragmentManager(), examDate);
            }
        });

        Venue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(Venue.getText().toString().isEmpty())
                    Venue.setError("Error: This cannot be blank!");
            }
        });

        //Storing the data into database
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()) {
                    String role = roleDialog.getText().toString();
                    String start = startTimeDialog.getText().toString();
                    String end = endTimeDialog.getText().toString();
                    String date = examDateDialog.getText().toString();
                    String venue = Venue.getText().toString();

                    myDB.insertData(role, start, end, date, venue);
                    Intent hmpg = new Intent(Add.this, ViewSlots.class);
                    startActivity(hmpg);
                    Toast.makeText(Add.this, role + " Added!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Add.this,"There are unresolved errors. Pease make " +
                            "sure all fields are filled correctly before proceeding.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onFinishDialogRole(String role) {
        roleDialog.setText(role);
        roleDialog.setError(null);
    }

    @Override
    public void onFinishDialogStart(String time) {
        startTimeDialog.setText(time);
        checkDT();
    }

    @Override
    public void onFinishDialogEnd(String time) {
        endTimeDialog.setText(time);
        checkDT();
    }

    @Override
    public void onFinishDialog(Date date) {
        examDateDialog.setText(formatDate(date));
        checkDT();
    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy (EEE)");
        String hireDate = sdf.format(date);
        return hireDate;
    }

    public void checkDT() {
        String start = startTimeDialog.getText().toString();
        String end = endTimeDialog.getText().toString();
        String date = examDateDialog.getText().toString();

        try {
            SimpleDateFormat datetime = new SimpleDateFormat("dd MMM, yyyy (EEE) h:mm a");
            Date currentDT = Calendar.getInstance().getTime();
            Date sdfDT = datetime.parse(datetime.format(currentDT));
            Date comStart = datetime.parse(date + " " + start);
            Date comEnd = datetime.parse(date + " " + end);

            if (comStart.after(comEnd)) {
                startTimeDialog.setError("Start time must be before End Time!");
                endTimeDialog.setError("Start time must be before End Time!");
            } else if (comStart.before(sdfDT)) {
                startTimeDialog.setError("Start time must be before current time!");
                examDateDialog.setError("Date must be before current date!");
            } else {
                startTimeDialog.setError(null);
                endTimeDialog.setError(null);
                examDateDialog.setError(null);
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    ///Reject invalid data
    public Boolean validation() {
        /* if (role.isEmpty() || start.isEmpty() || end.isEmpty() || date.isEmpty() || venue.isEmpty()) {
            Toast.makeText(Add.this, "One or more fields must not be blank!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {
                SimpleDateFormat datetime = new SimpleDateFormat("dd MMM, yyyy (EEE) h:mm a");
                Date currentDT = Calendar.getInstance().getTime();
                Date sdfDT = datetime.parse(datetime.format(currentDT));
                Date comStart = datetime.parse(date + " " + start);
                Date comEnd = datetime.parse(date + " " + end);

                if (comStart.after(comEnd)) {
                    Toast.makeText(Add.this, "End Time must be after Start Time!", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (comStart.before(sdfDT)) {
                    Toast.makeText(Add.this, "Start time has already passed!", Toast.LENGTH_SHORT).show();
                    return false;
                } else
                    return true;
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                Toast.makeText(Add.this, "Unknown error has occurred", Toast.LENGTH_SHORT).show();
                return false;
            }
        } */
        if (roleDialog.getError() != null)
            return false;
        else if (startTimeDialog.getError() != null)
            return false;
        else if (endTimeDialog.getError() != null)
            return false;
        else if (examDateDialog.getError() != null)
            return false;
        else if (Venue.getError() != null)
            return false;
        else
            return true;
    }

   /* //File part
    public void saveFile(String role, String start, String end, String date, String venue) {
        File path = getApplicationContext().getFilesDir();
        File file = new File (path, "file.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }//creates file if not already exists
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(role.getBytes());
            fos.write("\n".getBytes());//add tokens
            fos.write(start.getBytes());
            fos.write("\n".getBytes());
            fos.write(end.getBytes());
            fos.write("\n".getBytes());
            fos.write(date.getBytes());
            fos.write("\n".getBytes());
            fos.write(venue.getBytes());
            fos.write("\n".getBytes());
            fos.close();

            setAlert(this, start, date);
            Toast.makeText(Add.this, "Slot Added !", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Add.this, "Unable to Add slot", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void setAlert(Context context, String time, String date) {
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),
                1253, intent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);

        AlarmManager manager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        Calendar alert = Calendar.getInstance();
        SimpleDateFormat datetime = new SimpleDateFormat("dd:MMMM:yyyy:h:mm");

        try {
            Date comTime = datetime.parse(date + " " + time);
            String dt = comTime.toString();
            String[] dmyhm = dt.split(":");
            int day = Integer.parseInt(dmyhm[0].trim());
            int month = Integer.parseInt(dmyhm[1].trim());
            int year = Integer.parseInt(dmyhm[2].trim());
            int hour = Integer.parseInt (dmyhm[3].trim());
            int minute = Integer.parseInt (dmyhm[4].trim());
            alert.set(Calendar.HOUR_OF_DAY, hour-1);
            alert.set(Calendar.MINUTE, minute);
            alert.set(Calendar.DATE, day);
            alert.set(Calendar.MONTH, month);
            alert.set(Calendar.YEAR, year);
            manager.set(AlarmManager.RTC_WAKEUP, alert.getTimeInMillis(), pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
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