package com.example.mindfull.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mindfull.Adapter.ExerciseRecordAdapter;
import com.example.mindfull.Models.ExerciseRecord;
import com.example.mindfull.R;
import com.example.mindfull.databinding.FragmentHomeBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


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


        //Getting the user Exercise Records
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


        String currentString = new Date().toString();
        String[] separated = currentString.split(" ");
        String currentDate = separated[0] + " " + separated[1] + " " + separated[2];
        String currentMonth = separated[1];



        //Getting the Dialy Total
        DatabaseReference Datereference = FirebaseDatabase.getInstance().getReference("Reps").child(auth.getUid());
        Query Datequery = Datereference.orderByChild("date").equalTo(currentDate);
        Datequery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                        binding.totalDay.setText(String.valueOf(totalReps));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Getting the Monthly Total
        DatabaseReference Monthreference = FirebaseDatabase.getInstance().getReference("Reps").child(auth.getUid());
        Query Monthquery = Monthreference.orderByChild("month").equalTo(currentMonth);
        Monthquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                        binding.totalMonth.setText(String.valueOf(totalReps));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Data for Loading the Graph

        ArrayList<PieEntry> Daydata = new ArrayList<>();

        //CLimb the Mountain Day data
        DatabaseReference dayReference1 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Climb the mountain");
        Query dayQuery1 = dayReference1.orderByChild("day").equalTo(currentDate);
        dayQuery1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Daydata.add(new PieEntry(totalReps,"Climb the mountain"));

                    PieDataSet pieDataSet = new PieDataSet(Daydata,"Dialy Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.dayChart.setData(pieData);
                    binding.dayChart.getDescription().setEnabled(false);
                    binding.dayChart.setCenterText("Dialy");
                    binding.dayChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Pullups Day data
        DatabaseReference dayReference2 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Pull ups");
        Query dayQuery2 = dayReference2.orderByChild("day").equalTo(currentDate);
        dayQuery2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Daydata.add(new PieEntry(totalReps,"Pull ups"));

                    PieDataSet pieDataSet = new PieDataSet(Daydata,"Dialy Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.dayChart.setData(pieData);
                    binding.dayChart.getDescription().setEnabled(false);
                    binding.dayChart.setCenterText("Dialy");
                    binding.dayChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Basic crunches data
        DatabaseReference dayReference3 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Basic crunches");
        Query dayQuery3 = dayReference3.orderByChild("day").equalTo(currentDate);
        dayQuery3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Daydata.add(new PieEntry(totalReps,"Basic crunches"));

                    PieDataSet pieDataSet = new PieDataSet(Daydata,"Dialy Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.dayChart.setData(pieData);
                    binding.dayChart.getDescription().setEnabled(false);
                    binding.dayChart.setCenterText("Dialy");
                    binding.dayChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Bicycle crunches data
        DatabaseReference dayReference4 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Bicycle crunches");
        Query dayQuery4 = dayReference4.orderByChild("day").equalTo(currentDate);
        dayQuery4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Daydata.add(new PieEntry(totalReps,"Bicycle crunches"));

                    PieDataSet pieDataSet = new PieDataSet(Daydata,"Dialy Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.dayChart.setData(pieData);
                    binding.dayChart.getDescription().setEnabled(false);
                    binding.dayChart.setCenterText("Dialy");
                    binding.dayChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Leg Raise Day data
        DatabaseReference dayReference5 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Leg Raise");
        Query dayQuery5 = dayReference5.orderByChild("day").equalTo(currentDate);
        dayQuery5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Daydata.add(new PieEntry(totalReps,"Leg Raise"));

                    PieDataSet pieDataSet = new PieDataSet(Daydata,"Dialy Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.dayChart.setData(pieData);
                    binding.dayChart.getDescription().setEnabled(false);
                    binding.dayChart.setCenterText("Dialy");
                    binding.dayChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Alternate heel touch Day data
        DatabaseReference dayReference6 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Alternate Heel Touch");
        Query dayQuery6 = dayReference6.orderByChild("day").equalTo(currentDate);
        dayQuery6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Daydata.add(new PieEntry(totalReps,"Alternate Heel Touch"));

                    PieDataSet pieDataSet = new PieDataSet(Daydata,"Dialy Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.dayChart.setData(pieData);
                    binding.dayChart.getDescription().setEnabled(false);
                    binding.dayChart.setCenterText("Dialy");
                    binding.dayChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Leg up crunches Day data
        DatabaseReference dayReference7 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Leg up crunches");
        Query dayQuery7 = dayReference7.orderByChild("day").equalTo(currentDate);
        dayQuery7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Daydata.add(new PieEntry(totalReps,"Leg up crunches"));

                    PieDataSet pieDataSet = new PieDataSet(Daydata,"Dialy Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.dayChart.setData(pieData);
                    binding.dayChart.getDescription().setEnabled(false);
                    binding.dayChart.setCenterText("Dialy");
                    binding.dayChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Plank Rotation Day data
        DatabaseReference dayReference8 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Plank Rotation");
        Query dayQuery8 = dayReference8.orderByChild("day").equalTo(currentDate);
        dayQuery8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Daydata.add(new PieEntry(totalReps,"Plank Rotation"));

                    PieDataSet pieDataSet = new PieDataSet(Daydata,"Dialy Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.dayChart.setData(pieData);
                    binding.dayChart.getDescription().setEnabled(false);
                    binding.dayChart.setCenterText("Dialy");
                    binding.dayChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Plank with Leg Left Day data
        DatabaseReference dayReference9 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Plank with Leg Left");
        Query dayQuery9 = dayReference9.orderByChild("day").equalTo(currentDate);
        dayQuery9.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Daydata.add(new PieEntry(totalReps,"Plank with Leg Left"));

                    PieDataSet pieDataSet = new PieDataSet(Daydata,"Dialy Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.dayChart.setData(pieData);
                    binding.dayChart.getDescription().setEnabled(false);
                    binding.dayChart.setCenterText("Dialy");
                    binding.dayChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Russian Twist Day data
        DatabaseReference dayReference10 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Russian Twist");
        Query dayQuery10 = dayReference10.orderByChild("day").equalTo(currentDate);
        dayQuery10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Daydata.add(new PieEntry(totalReps,"Russian Twist"));

                    PieDataSet pieDataSet = new PieDataSet(Daydata,"Dialy Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.dayChart.setData(pieData);
                    binding.dayChart.getDescription().setEnabled(false);
                    binding.dayChart.setCenterText("Dialy");
                    binding.dayChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        ArrayList<PieEntry> Monthdata = new ArrayList<>();

        //Climb the mountain Month Data
        DatabaseReference MonthReference1 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Climb the mountain");
        Query MonthQuery1 = MonthReference1.orderByChild("month").equalTo(currentMonth);
        MonthQuery1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Monthdata.add(new PieEntry(totalReps,"Climb the mountain"));

                    PieDataSet pieDataSet = new PieDataSet(Monthdata,"Monthly Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.monthChart.setData(pieData);
                    binding.monthChart.getDescription().setEnabled(false);
                    binding.monthChart.setCenterText("Monthly");
                    binding.monthChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Pull ups Month Data
        DatabaseReference MonthReference2 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Pull ups");
        Query MonthQuery2 = MonthReference2.orderByChild("month").equalTo(currentMonth);
        MonthQuery2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Monthdata.add(new PieEntry(totalReps,"Pull ups"));

                    PieDataSet pieDataSet = new PieDataSet(Monthdata,"Monthly Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.monthChart.setData(pieData);
                    binding.monthChart.getDescription().setEnabled(false);
                    binding.monthChart.setCenterText("Monthly");
                    binding.monthChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Basic crunches Month Data
        DatabaseReference MonthReference3 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Basic crunches");
        Query MonthQuery3 = MonthReference3.orderByChild("month").equalTo(currentMonth);
        MonthQuery3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Monthdata.add(new PieEntry(totalReps,"Basic crunches"));

                    PieDataSet pieDataSet = new PieDataSet(Monthdata,"Monthly Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.monthChart.setData(pieData);
                    binding.monthChart.getDescription().setEnabled(false);
                    binding.monthChart.setCenterText("Monthly");
                    binding.monthChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Bicycle crunches Month Data
        DatabaseReference MonthReference4 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Bicycle crunches");
        Query MonthQuery4 = MonthReference4.orderByChild("month").equalTo(currentMonth);
        MonthQuery4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Monthdata.add(new PieEntry(totalReps,"Bicycle crunches"));

                    PieDataSet pieDataSet = new PieDataSet(Monthdata,"Monthly Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.monthChart.setData(pieData);
                    binding.monthChart.getDescription().setEnabled(false);
                    binding.monthChart.setCenterText("Monthly");
                    binding.monthChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Leg Raise Month Data
        DatabaseReference MonthReference5 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Leg Raise");
        Query MonthQuery5 = MonthReference5.orderByChild("month").equalTo(currentMonth);
        MonthQuery5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Monthdata.add(new PieEntry(totalReps,"Leg Raise"));

                    PieDataSet pieDataSet = new PieDataSet(Monthdata,"Monthly Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.monthChart.setData(pieData);
                    binding.monthChart.getDescription().setEnabled(false);
                    binding.monthChart.setCenterText("Monthly");
                    binding.monthChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Alternate Heel Touch Month Data
        DatabaseReference MonthReference6 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Alternate Heel Touch");
        Query MonthQuery6 = MonthReference6.orderByChild("month").equalTo(currentMonth);
        MonthQuery6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Monthdata.add(new PieEntry(totalReps,"Alternate Heel Touch"));

                    PieDataSet pieDataSet = new PieDataSet(Monthdata,"Monthly Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.monthChart.setData(pieData);
                    binding.monthChart.getDescription().setEnabled(false);
                    binding.monthChart.setCenterText("Monthly");
                    binding.monthChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Leg up crunches Month Data
        DatabaseReference MonthReference7 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Leg up crunches");
        Query MonthQuery7 = MonthReference7.orderByChild("month").equalTo(currentMonth);
        MonthQuery7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Monthdata.add(new PieEntry(totalReps,"Alternate Heel Touch"));

                    PieDataSet pieDataSet = new PieDataSet(Monthdata,"Monthly Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.monthChart.setData(pieData);
                    binding.monthChart.getDescription().setEnabled(false);
                    binding.monthChart.setCenterText("Monthly");
                    binding.monthChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Plank Rotation Month Data
        DatabaseReference MonthReference8 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Plank Rotation");
        Query MonthQuery8 = MonthReference8.orderByChild("month").equalTo(currentMonth);
        MonthQuery8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Monthdata.add(new PieEntry(totalReps,"Plank Rotation"));

                    PieDataSet pieDataSet = new PieDataSet(Monthdata,"Monthly Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.monthChart.setData(pieData);
                    binding.monthChart.getDescription().setEnabled(false);
                    binding.monthChart.setCenterText("Monthly");
                    binding.monthChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Plank with Leg Left Month Data
        DatabaseReference MonthReference9 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Plank with Leg Left");
        Query MonthQuery9 = MonthReference9.orderByChild("month").equalTo(currentMonth);
        MonthQuery9.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Monthdata.add(new PieEntry(totalReps,"Plank with Leg Left"));

                    PieDataSet pieDataSet = new PieDataSet(Monthdata,"Monthly Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.monthChart.setData(pieData);
                    binding.monthChart.getDescription().setEnabled(false);
                    binding.monthChart.setCenterText("Monthly");
                    binding.monthChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Russian Twist Month Data
        DatabaseReference MonthReference10 = FirebaseDatabase.getInstance().getReference("AnalyticsRecord").child(auth.getUid()).child("Plank Rotation");
        Query MonthQuery10 = MonthReference10.orderByChild("month").equalTo(currentMonth);
        MonthQuery10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int totalReps = 0;
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("repCount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalReps += pTotal;
                    }
                    Monthdata.add(new PieEntry(totalReps,"Russian Twist"));

                    PieDataSet pieDataSet = new PieDataSet(Monthdata,"Monthly Analytics");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    binding.monthChart.setData(pieData);
                    binding.monthChart.getDescription().setEnabled(false);
                    binding.monthChart.setCenterText("Monthly");
                    binding.monthChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}