package com.example.mindfull.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mindfull.MainActivity;
import com.example.mindfull.Models.StatsModel;
import com.example.mindfull.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class statsAdapter extends RecyclerView.Adapter<statsAdapter.ViewHolder> {


    private List<StatsModel> statsList;
    Context context;
    private FirebaseFirestore firestore;


    public statsAdapter(List<StatsModel> statsList, Context context) {
        this.statsList = statsList;
        this.context = context;
    }

    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.exercisetrack,parent,false);
        firestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {

        StatsModel statsModel = statsList.get(position);
        holder.mcheckbox.setText(statsModel.getExercise());

    }

    @Override
    public int getItemCount() {
        return statsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox mcheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mcheckbox = itemView.findViewById(R.id.mcheckbox);


        }

    }






}
