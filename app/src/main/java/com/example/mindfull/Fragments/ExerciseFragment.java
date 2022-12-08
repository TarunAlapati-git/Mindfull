package com.example.mindfull.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mindfull.Adapter.ExerciseAdapter;
import com.example.mindfull.Models.Exercises;
import com.example.mindfull.databinding.FragmentExerciseBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


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

        binding.searchView.clearFocus();
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                ArrayList<Exercises> filteredlist = new ArrayList<>();
                for(Exercises exercise: list) {
                    if(exercise.getName().toLowerCase().contains(text.toLowerCase())) {
                        filteredlist.add(exercise);
                    }
                }

                if(filteredlist.isEmpty()) {
                    Toast.makeText(getContext(),"No data found",Toast.LENGTH_SHORT).show();
                } else {
                    adapter.setFilteredList(filteredlist,getContext());
                }

                return true;
            }
        });


        return binding.getRoot();
    }
}