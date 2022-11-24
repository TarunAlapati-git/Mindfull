package com.example.mindfull;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mindfull.databinding.ActivityExerciseDetailBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ExerciseDetailActivity extends AppCompatActivity {

    ActivityExerciseDetailBinding binding;
    FirebaseDatabase database;
    private Button addRepButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExerciseDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        String exerciseImage = getIntent().getStringExtra("exerciseImage");
        String exerciseDescription = getIntent().getStringExtra("exerciseDescription");

        binding.exerciseDesc.setText(exerciseDescription);
        Picasso.get().load(exerciseImage).placeholder(R.drawable.avatar).into(binding.exerciseImage);

    }
}