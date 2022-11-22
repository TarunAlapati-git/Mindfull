package com.example.mindfull.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mindfull.ExerciseDetailActivity;
import com.example.mindfull.Models.Exercises;
import com.example.mindfull.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{


    ArrayList<Exercises> list;
    Context context;

    public ExerciseAdapter(ArrayList<Exercises> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_exercise,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Exercises exercises = list.get(position);
        Picasso.get().load(exercises.getImage()).placeholder(R.drawable.avatar3).into(holder.image);
        holder.exerciseName.setText(exercises.getName());
        holder.exerciseDesc.setText(exercises.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExerciseDetailActivity.class);
                intent.putExtra("exerciseImage",exercises.getImage());
                intent.putExtra("exerciseDescription",exercises.getDescription());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView exerciseName,exerciseDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.exercise_image);
            exerciseName = itemView.findViewById(R.id.exerciseNameList);
            exerciseDesc = itemView.findViewById(R.id.exerciseDesc);

        }
    }

}
