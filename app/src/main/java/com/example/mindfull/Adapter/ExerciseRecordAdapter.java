package com.example.mindfull.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindfull.Models.ExerciseRecord;
import com.example.mindfull.Models.Exercises;
import com.example.mindfull.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExerciseRecordAdapter extends RecyclerView.Adapter {

    ArrayList<ExerciseRecord> exerciseRecords;
    Context context;

    public ExerciseRecordAdapter(ArrayList<ExerciseRecord> exerciseRecords, Context context) {
        this.exerciseRecords = exerciseRecords;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_exercise_record,parent,false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ExerciseRecord exerciseRecord = exerciseRecords.get(position);

        ((RecordViewHolder)holder).recordName.setText(exerciseRecord.getRepName());
        ((RecordViewHolder)holder).recordRep.setText(exerciseRecord.getRepCount());

        String currentString = exerciseRecord.getDate();
        String[] separated = currentString.split(" ");
        String res = separated[0] + " " + separated[1] + " " + separated[2];

        ((RecordViewHolder)holder).recordDate.setText(res);


    }

    @Override
    public int getItemCount() {
        return exerciseRecords.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder{


        TextView recordName,recordRep,recordDate;


        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

            recordName = itemView.findViewById(R.id.recordName);
            recordRep = itemView.findViewById(R.id.recordRep);
            recordDate = itemView.findViewById(R.id.recordDate);

        }
    }

}

