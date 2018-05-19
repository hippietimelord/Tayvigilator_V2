package com.example.tayvigilator;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    public void playSound(View v){

        soundThread = new Thread(Announcements.this);

        switch(v.getId()){
            case R.id.BTAn1 :
                Toast.makeText(Announcements.this,"Announcement 1 is selected",Toast.LENGTH_SHORT).show();
                mp = MediaPlayer.create(Announcements.this.getBaseContext(),R.raw.ann1);
                displaytext.setText("Before Examination Rules");
                soundThread.start();
                break;
            case R.id.BTAn2 :
                Toast.makeText(Announcements.this,"Announcement 2 is selected",Toast.LENGTH_SHORT).show();
                mp = MediaPlayer.create(Announcements.this.getBaseContext(),R.raw.ann2);
                displaytext.setText("MCQs Announcement rules");
                soundThread.start();
                break;
            case R.id.BTAn3 :
                Toast.makeText(Announcements.this,"Announcement 3 is selected",Toast.LENGTH_SHORT).show();
                mp = MediaPlayer.create(Announcements.this.getBaseContext(),R.raw.ann3);
                displaytext.setText("End of examination reminder");
                soundThread.start();
                break;
            case R.id.BTAn4 :
                Toast.makeText(Announcements.this,"Announcement 4 is selected",Toast.LENGTH_SHORT).show();
                mp = MediaPlayer.create(Announcements.this.getBaseContext(),R.raw.ann4);
                displaytext.setText("Exam Completion Announcement");
                soundThread.start();
                break;

        }
    }

}
