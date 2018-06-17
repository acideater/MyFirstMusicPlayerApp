package com.example.android.musicplayerapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public static int oneTimeOnly = 0;
    // global variables
    private double startTime = 0;
    private double finalTime = 0;
    private MediaPlayer mediaPlayer;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private Handler myHandler = new Handler();
    private SeekBar seekbar;
    private TextView time;
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            time.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
            );
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // defining buttons, textviews and other stuff
        Button play = this.findViewById(R.id.play);
        Button pause = this.findViewById(R.id.pause);
        Button rewind = this.findViewById(R.id.rewind);
        Button forward = this.findViewById(R.id.forward);
        time = findViewById(R.id.time);
        mediaPlayer = MediaPlayer.create(this, R.raw.politics);
        seekbar = findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        // setting up seekbar
        if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }
        seekbar.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);


        // play button
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        // pause button
        pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        // rewind button
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;

                if ((temp - backwardTime) > 0) {
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "You have Jumped backward 5 seconds",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ff button
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;

                if ((temp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "You have Jumped forward 5 seconds",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
