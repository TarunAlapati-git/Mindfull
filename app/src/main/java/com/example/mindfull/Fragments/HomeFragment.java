package com.example.mindfull.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mindfull.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
//    ArrayList<StatsModel> list = new ArrayList<>();
//    FirebaseFirestore firestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater,container,false);

//        statsAdapter adapter = new statsAdapter(list,getContext());
//        binding.statsRecyclerView.setAdapter(adapter);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        binding.statsRecyclerView.setLayoutManager(layoutManager);
//
//        firestore = FirebaseFirestore.getInstance();
//
//        firestore.collection("exercise").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                for(DocumentChange documentChange:value.getDocumentChanges()) {
//                    if(documentChange.getType() == DocumentChange.Type.ADDED) {
//                        String id = documentChange.getDocument().getId();
//                        StatsModel statsModel = documentChange.getDocument().toObject(StatsModel.class).withId(id);
//
//                        list.add(statsModel);
//                        adapter.notifyDataSetChanged();
//
//                    }
//                }
//                Collections.reverse(list);
//            }
//        });

        return binding.getRoot();
    }
}