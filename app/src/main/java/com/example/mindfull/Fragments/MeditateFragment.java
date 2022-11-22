package com.example.mindfull.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mindfull.Adapter.MeditateAdapter;
import com.example.mindfull.Models.Exercises;
import com.example.mindfull.Models.Music;
import com.example.mindfull.R;
import com.example.mindfull.databinding.FragmentMeditateBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MeditateFragment extends Fragment {


    public MeditateFragment() {
        // Required empty public constructor
    }

    FragmentMeditateBinding binding;
    ArrayList<Music> list = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentMeditateBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();

        MeditateAdapter adapter = new MeditateAdapter(list,getContext());
        binding.meditateRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.meditateRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("Music").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Music music = dataSnapshot.getValue(Music.class);
                    list.add(music);
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