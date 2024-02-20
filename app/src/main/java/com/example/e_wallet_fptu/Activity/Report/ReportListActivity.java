package com.example.e_wallet_fptu.Activity.Report;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Adapter.ReportAdapter;
import com.example.e_wallet_fptu.Entity.Report;
import com.example.e_wallet_fptu.databinding.ActivityReportListBinding;
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
    }

    private void handleButton() {
        binding.btnReportListBack.setOnClickListener(v -> startActivity(new Intent(ReportListActivity.this, MainActivity.class)));
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

    private void loadData() {

    }
}