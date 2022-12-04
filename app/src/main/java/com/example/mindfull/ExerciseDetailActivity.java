package com.example.mindfull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mindfull.Adapter.ExerciseRecordAdapter;
import com.example.mindfull.Models.AnalyticsRecord;
import com.example.mindfull.Models.ExerciseRecord;
import com.example.mindfull.databinding.ActivityExerciseDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;

public class ExerciseDetailActivity extends AppCompatActivity {

//    ActivityExerciseDetailBinding binding;
    FirebaseDatabase database;
    TextView exerciseName,exerciseDesc;
    ImageView exerciseImage;

    FirebaseAuth auth;


    Button saveBtn;
    TextView repCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise_detail);

        database = FirebaseDatabase.getInstance();

        exerciseName = findViewById(R.id.exerciseName);
        exerciseDesc = findViewById(R.id.exerciseDesc);
        exerciseImage = findViewById(R.id.exercise_image);


        String exercise_name = getIntent().getStringExtra("exerciseName");
        String exercise_image = getIntent().getStringExtra("exerciseImage");
        String exercise_description = getIntent().getStringExtra("exerciseDescription");


        exerciseName.setText(exercise_name);
        exerciseDesc.setText(exercise_description);
        Picasso.get().load(exercise_image).placeholder(R.drawable.avatar).into(exerciseImage);


        saveBtn = findViewById(R.id.saveBtn);
        repCount = findViewById(R.id.repCount);

        auth = FirebaseAuth.getInstance();

        String currentString = new Date().toString();
        String[] separated = currentString.split(" ");
        String res = separated[0] + " " + separated[1] + " " + separated[2];

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String repcount = repCount.getText().toString();
                final ExerciseRecord exerciseRecord = new ExerciseRecord(auth.getUid(),exercise_name,repcount);
                exerciseRecord.setDate(res);
                exerciseRecord.setMonth(separated[1]);
                repCount.setText("");

                database.getReference().child("Reps").child(auth.getUid()).push().setValue(exerciseRecord).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

                final AnalyticsRecord analyticsRecord = new AnalyticsRecord(res,separated[1],repcount);

                database.getReference().child("AnalyticsRecord").child(auth.getUid()).child(exercise_name).push().setValue(analyticsRecord).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

            }
        });


    }

}