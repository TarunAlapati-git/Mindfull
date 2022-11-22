package com.example.mindfull.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mindfull.Adapter.ExerciseAdapter;
import com.example.mindfull.Models.Exercises;
import com.example.mindfull.R;
import com.example.mindfull.databinding.FragmentExerciseBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ExerciseFragment extends Fragment {



    public ExerciseFragment() {
        // Required empty public constructor
    }

    FragmentExerciseBinding binding;
    ArrayList<Exercises> list = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentExerciseBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();

        ExerciseAdapter adapter = new ExerciseAdapter(list,getContext());
        binding.exerciseRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.exerciseRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("Exercises").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Exercises exercises = dataSnapshot.getValue(Exercises.class);
                    list.add(exercises);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}