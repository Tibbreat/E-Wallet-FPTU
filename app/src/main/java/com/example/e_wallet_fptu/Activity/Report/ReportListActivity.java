package com.example.e_wallet_fptu.Activity.Report;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Adapter.ReportAdapter;
import com.example.e_wallet_fptu.Entity.Report;
import com.example.e_wallet_fptu.databinding.ActivityReportListBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ReportListActivity extends BaseActivity {
    ActivityReportListBinding binding;
    private ReportAdapter adapter;
    private List<Report> reportList = new ArrayList<>();
    private DatabaseReference reportRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
        String currentStudentRollNumber = preferences.getString("student_roll_number", "");
        reportRef = database.getReference("Report").child(currentStudentRollNumber);
        initRecyclerView();
        listenForReportChanges();
        handleButton();
    }

    private void handleButton() {
        binding.btnReportListBack.setOnClickListener(v -> finish());
        binding.btnReportListAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.btnReportListProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.btnReportListProcessed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initRecyclerView() {
        adapter = new ReportAdapter(reportList);
        binding.rvReportList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvReportList.setAdapter(adapter);
    }

    private void listenForReportChanges() {
        reportRef.orderByChild("time").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Report report = snapshot.getValue(Report.class);
                if (report != null) {
                    reportList.add(0, report);
                    adapter.notifyDataSetChanged();
                    binding.rvReportList.smoothScrollToPosition(0);
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}