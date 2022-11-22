package com.example.mindfull.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mindfull.Adapter.ExerciseAdapter;
import com.example.mindfull.Adapter.statsAdapter;
import com.example.mindfull.Models.Exercises;
import com.example.mindfull.Models.StatsModel;
import com.example.mindfull.R;
import com.example.mindfull.databinding.FragmentExerciseBinding;
import com.example.mindfull.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ArrayList<StatsModel> list = new ArrayList<>();
    FirebaseFirestore firestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater,container,false);

        statsAdapter adapter = new statsAdapter(list,getContext());
        binding.statsRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.statsRecyclerView.setLayoutManager(layoutManager);

        firestore = FirebaseFirestore.getInstance();

        firestore.collection("exercise").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for(DocumentChange documentChange:value.getDocumentChanges()) {
                    if(documentChange.getType() == DocumentChange.Type.ADDED) {
                        String id = documentChange.getDocument().getId();
                        StatsModel statsModel = documentChange.getDocument().toObject(StatsModel.class).withId(id);

                        list.add(statsModel);
                        adapter.notifyDataSetChanged();

                    }
                }
                Collections.reverse(list);
            }
        });

        return binding.getRoot();
    }
}