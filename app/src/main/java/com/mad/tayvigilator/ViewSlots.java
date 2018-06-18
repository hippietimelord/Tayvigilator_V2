package com.mad.tayvigilator;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ViewSlots extends AppCompatActivity {
    private ListView lv;
    private ListAdapter adapter;
    private AlertDialog.Builder build;
    private long slotID;
    private long ID;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slots);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDB = new DatabaseHelper(this);

        ArrayList<HashMap<String, String>> userList = myDB.GetUsers();
        lv = (ListView) findViewById(R.id.list_view);
        adapter = new SimpleAdapter(ViewSlots.this, userList, R.layout.listrow,new String[]{"ID","DATE","ROLE","VENUE","START_TIME","END_TIME"}, new int[]{R.id.ID,R.id.EXAMDATE, R.id.ROLE, R.id.VENUE,R.id.STARTTIME,R.id.ENDTIME});
        lv.setAdapter(adapter);
        listViewItemLongClick();
    }

    private void listViewItemLongClick(){
        lv = (ListView) findViewById(R.id.list_view);
        adapter = lv.getAdapter();
        lv.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View item, int pos, long id) {
                        TextView idTV = (TextView) item.findViewById(R.id.ID);
                        slotID = Long.parseLong(idTV.getText().toString());
                        ID = id;
                        build.setMessage("What do you wish to do?").setCancelable(false);
                        build.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ViewSlots.this, Update.class);
                                intent.putExtra("ID", slotID);
                                startActivity(intent);
                            }
                        });
                        build.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myDB.deleteRow(slotID);
                                Toast.makeText(ViewSlots.this,"Slot" + (ID+1) + " Deleted!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        build.create();
                        build.setTitle("Slot " + (ID+1));
                        build.show();

                        return true;
                    }

                });

    }

    /* private void populateListView(){
        Cursor cursor = myDB.getAllRows();
    } */

    public void onClick_DeleteTasks(View v){
        lv = (ListView) findViewById(R.id.list_view);
        adapter = lv.getAdapter();
        myDB.deleteAll();
        ((BaseAdapter) adapter).notifyDataSetChanged();
        lv.invalidateViews();
        lv.setAdapter(adapter);
        Toast.makeText(ViewSlots.this,  "All Slots Deleted!", Toast.LENGTH_SHORT).show();
    }

        /*try {
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
                                    venue + " " + extra + "\n" +
                                    "----------------------------------------------------");
                }
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
        }*/

    /*public Boolean aTimeChecker(String time, String date) {
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
    }*/

//    public void viewAll(){
//
//        Cursor res=myDB.getAllData();
//
//        if(res.getCount()==0){
//            showMessage("Error", "No Data Found");
//            return;
//        }
//
//        StringBuffer buffer=new StringBuffer();
//        while(res.moveToNext()){
//            buffer.append("ID: "+res.getString(0)+"\n");
//            buffer.append("Name: "+res.getString(1)+"\n");
//            buffer.append("Time: "+res.getString(2)+ " - " + res.getString(3) + "\n\n");
//            buffer.append("Date: "+res.getString(4)+"\n\n");
//            buffer.append("Venue: "+res.getString(5)+"\n\n");
//
//        }
//        showMessage("Data: ", buffer.toString());
//
//    }
//
//    public void showMessage(String title, String message){
//
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//
//        // builder.setCancelable(true);
//        builder.setTitle(title);
//
//        builder.setPositiveButton("Ok",
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog,
//                                        int which) {
//                        dialog.cancel();
//                    }
//                });
//        builder.setMessage(message);
//        builder.show();
//    }
}
