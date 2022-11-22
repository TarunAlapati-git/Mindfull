package com.example.mindfull.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mindfull.MeditateDetailActivity;
import com.example.mindfull.Models.Music;
import com.example.mindfull.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MeditateAdapter extends RecyclerView.Adapter<MeditateAdapter.ViewHolder>{

    ArrayList<Music> list;
    Context context;

    public MeditateAdapter(ArrayList<Music> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_music,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Music music = list.get(position);
        Picasso.get().load(music.getImage()).placeholder(R.drawable.avatar3).into(holder.image);
        holder.name.setText(music.getName());
        holder.url.setText(music.getUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MeditateDetailActivity.class);
                intent.putExtra("musicName",music.getName());
                intent.putExtra("musicImage",music.getImage());
                intent.putExtra("musicUrl",music.getUrl());
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
        TextView name,url;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.music_image);
            name = itemView.findViewById(R.id.musicNameList);
            url = itemView.findViewById(R.id.musicUrl);

        }
    }

}
