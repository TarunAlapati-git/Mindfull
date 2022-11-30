package com.example.mindfull;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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

        if(mediaPlayer!=null) {
            mediaPlayer.start();
            mediaPlayer.release();
        }

        Uri uri = Uri.parse(musicUrl);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()) {
                    btnPlay.setText("PLAY");
                    mediaPlayer.pause();
                } else {
                    btnPlay.setText("PAUSE");
                    mediaPlayer.start();
                }
            }
        });
    }
}