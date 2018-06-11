package com.mad.tayvigilator;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Announcements extends AppCompatActivity implements Runnable {
    private ImageButton playButton, pauseButton,stopButton;
    private Button Ann1, Ann2, Ann3, Ann4;
    private SeekBar soundSeekBar;
    private TextView displaytext;
    private MediaPlayer mp;
    private Thread soundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playButton = (ImageButton) findViewById(R.id.ppButton);
        pauseButton = (ImageButton) findViewById(R.id.psButton);
        stopButton =(ImageButton) findViewById(R.id.spButton);
        Ann1 = (Button) findViewById(R.id.BTAn1);
        Ann2 = (Button) findViewById(R.id.BTAn2);
        Ann3 = (Button) findViewById(R.id.BTAn3);
        Ann4 = (Button) findViewById(R.id.BTAn4);
        soundSeekBar = (SeekBar) findViewById(R.id.soundbar);
        displaytext = (TextView) findViewById(R.id.AnnDesc);

        mp = new MediaPlayer();
        TextView textView = (TextView) findViewById(R.id.AnnDesc);
        textView.setMovementMethod(new ScrollingMovementMethod());

        setupListeners();
    }

    private void setupListeners() {
        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mp.stop();
                mp = MediaPlayer.create(getBaseContext(),R.raw.ann1); /*How to reset without going to only ann1 ?*/
            }
        });
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mp.start();
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mp.pause();
            }
        });
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    /**
     * Required since we are implementing a runnable
     * Allows the seekbar to update
     */

    @Override
    public void run() {
        int currentPosition = 0;
        int soundTotal = mp.getDuration();
        soundSeekBar.setMax(soundTotal);

        while(mp != null && currentPosition<soundTotal){
            try{
                Thread.sleep(300);
                currentPosition=mp.getCurrentPosition();
            }catch(InterruptedException e) {
                return;
            }catch(Exception e){
                return;
            }
            soundSeekBar.setProgress(currentPosition);
        }
    }

    @SuppressLint("SetTextI18n")
    public void playSound(View v){

        soundThread = new Thread(Announcements.this);

        switch(v.getId()){
            case R.id.BTAn1 :
                Toast.makeText(Announcements.this,"Announcement 1 is selected",Toast.LENGTH_SHORT).show();
                if (mp.isPlaying()) {
                    mp.stop();
                    mp = MediaPlayer.create(getBaseContext(),R.raw.ann1);
                }else{
                    mp = MediaPlayer.create(Announcements.this.getBaseContext(),R.raw.ann1);
                }
                displaytext.setText("Before Examination Rules\n"+
                        "\n- Please be reminded of these rules.\n- Please wait for instruction before you turn over your question papers.\n" +
                        "- Examination misconduct carries severe penalties. Please be reminded that you should not be carrying on your person any material other than stationery. If you have accidentally brought in anything, please hand it over to the invigilator now.\n" +
                        "- All valuables (e.g. wallet, handphone, smartwatches and including all the latest gadgets (e.g. smart glasses, pda, etc.) must not be left in your bags.\n" +
                        "- All handphones and smartwatches must be switched off and placed face-down on the floor under your chair.\n" +
                        "- Washroom visit is not allowed in the first ½ hour and last 15 minutes of the examination. You are required to remove your jacket if you wish to go to washroom.\n" +
                        "- No candidate is allowed to leave the hall 15 minutes before the examination finishes.\n" +
                        "- The examination will be conducted according to the clock in this examination hall.\n" +
                        "- Raise your hand if you need any extra material or assistance.\n" +
                        "- Fill in your attendance slips in the answer booklet and tear it off and place it at the top left-hand corner of your table together with your Student ID.\n" +
                        "- You may turn over your question paper now.\n" +
                        "- Please check that you have the correct examination question which is (subject) for (programme).The duration is _____. The exam paper must have printed sheets");
                displaytext.setTextSize(18);
                soundThread.start();
                break;
            case R.id.BTAn2 :
                Toast.makeText(Announcements.this,"Announcement 2 is selected",Toast.LENGTH_SHORT).show();
                if (mp.isPlaying()) {
                    mp.stop();
                    mp = MediaPlayer.create(getBaseContext(),R.raw.ann2);
                }else{
                    mp = MediaPlayer.create(Announcements.this.getBaseContext(),R.raw.ann2);
                }
                displaytext.setText("Before Examination Rules + MCQs rules\n"+
                        "\n- Please be reminded of these rules.\n- Please wait for instruction before you turn over your question papers.\n" +
                        "- Examination misconduct carries severe penalties. Please be reminded that you should not be carrying on your person any material other than stationery. If you have accidentally brought in anything, please hand it over to the invigilator now.\n" +
                        "- All valuables (e.g. wallet, handphone, smartwatches and including all the latest gadgets (e.g. smart glasses, pda, etc.) must not be left in your bags.\n" +
                        "- All handphones and smartwathces must be switched off and placed face-down on the floor under your chair.\n" +
                        "- Washroom visit is not allowed in the first ½ hour and last 15 minutes of the examination. You are required to remove your jacket if you wish to go to washroom.\n" +
                        "- No candidate is allowed to leave the hall 15 minutes before the examination finishes.\n" +
                        "- The examination will be conducted according to the clock in this examination hall.\n" +
                        "- Raise your hand if you need any extra material or assistance.\n" +
                        "- Fill in your attendance slips in the answer booklet and tear it off and place it at the top left-hand corner of your table together with your Student ID.\n" +
                        "- You may turn over your question paper now.\n" +
                        "- Please check that you have the correct examination question which is (subject) for (programme).The duration is _____. The exam paper must have printed sheets\n\n" +
                        "Multiple-choice Answer Sheet (MCQ):\n" +
                        "Candidates will write their names as per Student ID.\n" +
                        "Fill in your complete student number.\n" +
                        "Marking directions:\n" +
                        "1. Please use 2B or BB pencil only.\n" +
                        "2. Do NOT use ink or ball point pen.\n" +
                        "3. Shade circle completely with heavy black marks.\n" +
                        "4. Erase cleanly any answer you wish to change.\n" +
                        "5. Make no stray marks on the answer sheet.\n" +
                        "You may begin now.");
                displaytext.setTextSize(18);
                soundThread.start();
                break;
            case R.id.BTAn3 :
                Toast.makeText(Announcements.this,"Announcement 3 is selected",Toast.LENGTH_SHORT).show();
                if (mp.isPlaying()) {
                    mp.stop();
                    mp = MediaPlayer.create(getBaseContext(),R.raw.ann3);
                }else{
                    mp = MediaPlayer.create(Announcements.this.getBaseContext(),R.raw.ann3);
                }
                displaytext.setText("End of examination reminder\n\n- You have another 20 minutes before the end of the examination.\n-Please note that you are not allowed to leave the hall for whatever reason in the last 15 minutes.");
                displaytext.setTextSize(18);
                soundThread.start();
                break;
            case R.id.BTAn4 :
                Toast.makeText(Announcements.this,"Announcement 4 is selected",Toast.LENGTH_SHORT).show();
                if (mp.isPlaying()) {
                    mp.stop();
                    mp = MediaPlayer.create(getBaseContext(),R.raw.ann4);
                }else{
                    mp = MediaPlayer.create(Announcements.this.getBaseContext(),R.raw.ann4);
                }
                displaytext.setText("Exam Completion Announcement\n\n- The time is up. Please stop writing.\n" +
                        "- Be seated and remain quiet until you are allowed to leave the hall.\n" +
                        "- Make sure that your name, student number and table number are written on all answer booklets.\n" +
                        "- Invigilators will collect your question papers and answer scripts now.\n" +
                        "- Leave all unused booklet on the table. Crush all used paper and leave it on the table.");
                displaytext.setTextSize(18);
                soundThread.start();
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mp.isPlaying())
        {
            mp.stop();
            mp.release();
        }
    }
}
