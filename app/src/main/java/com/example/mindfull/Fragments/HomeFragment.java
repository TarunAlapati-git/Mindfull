package com.example.mindfull.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mindfull.Adapter.ExerciseRecordAdapter;
import com.example.mindfull.Models.ExerciseRecord;
import com.example.mindfull.R;
import com.example.mindfull.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater,container,false);


        final ArrayList<ExerciseRecord> exerciseRecords = new ArrayList<>();
        final ExerciseRecordAdapter exerciseRecordAdapter = new ExerciseRecordAdapter(exerciseRecords,getContext());
        binding.exerciseRecordRecyclerView.setAdapter(exerciseRecordAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.exerciseRecordRecyclerView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        database.getReference().child("Reps").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseRecords.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    ExerciseRecord exerciseRecord = snapshot1.getValue(ExerciseRecord.class);
                    exerciseRecord.setRepId(snapshot1.getKey());
                    exerciseRecords.add(exerciseRecord);
                }
                exerciseRecordAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return binding.getRoot();
    }
}