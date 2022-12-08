package com.example.mindfull;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mindfull.databinding.ActivityMeditateDetailBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MeditateDetailActivity extends AppCompatActivity {

    ActivityMeditateDetailBinding binding;
    FirebaseDatabase database;

    Button btnPlay;
    SeekBar seekMusicBar;

    static MediaPlayer mediaPlayer;

    TextView mSongEnd,mSongStart;

    Thread updateSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeditateDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        String musicImage = getIntent().getStringExtra("musicImage");
        String musicName = getIntent().getStringExtra("musicName");
        String musicUrl = getIntent().getStringExtra("musicUrl");

        binding.musicName.setText(musicName);
        Picasso.get().load(musicImage).placeholder(R.drawable.avatar).into(binding.musicImage);

        btnPlay = findViewById(R.id.btnPlay);
        seekMusicBar = findViewById(R.id.seekBar);
        mSongEnd = findViewById(R.id.txtSongEnd);
        mSongStart = findViewById(R.id.txtSongStart);

        if(mediaPlayer!=null) {
            mediaPlayer.start();
            mediaPlayer.release();
        }

        Uri uri = Uri.parse(musicUrl);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();

        updateSeekbar = new Thread() {
            @Override
            public void run() {
                int totalDuration  = mediaPlayer.getDuration();
                int currentPosition = 0;
                while(currentPosition<totalDuration) {
                    try {

                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekMusicBar.setProgress(currentPosition);

                    }catch(InterruptedException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        seekMusicBar.setMax(mediaPlayer.getDuration());
        updateSeekbar.start();
        seekMusicBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.purple_500), PorterDuff.Mode.MULTIPLY);
        seekMusicBar.getThumb().setColorFilter(getResources().getColor(R.color.purple_500),PorterDuff.Mode.SRC_IN);

        seekMusicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String endTime = createTime(mediaPlayer.getDuration());
        mSongEnd.setText(endTime);

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                mSongStart.setText(currentTime);
                handler.postDelayed(this,delay);
            }
        },delay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                } else {
                    mediaPlayer.start();
                    btnPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });

    }

    public String createTime(int duration) {
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        time = time + min + ":";
        if(sec<10) {
            time += "0";
        }
        time += sec;
        return time;
    }

}