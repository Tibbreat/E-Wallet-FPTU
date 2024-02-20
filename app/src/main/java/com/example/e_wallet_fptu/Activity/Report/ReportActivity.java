package com.example.e_wallet_fptu.Activity.Report;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Entity.Report;
import com.example.e_wallet_fptu.Helper.DataEncode;
import com.example.e_wallet_fptu.databinding.ActivityReportBinding;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

public class ReportActivity extends BaseActivity {
    ActivityReportBinding binding;

    DataEncode dataEncode = new DataEncode();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnReportCf.setEnabled(false);
        binding.tvReportErr1.setVisibility(View.GONE);
        binding.tvReportErr2.setVisibility(View.GONE);
        binding.tvReportErr3.setVisibility(View.GONE);
        setVariable();
        handleTextChange();
        handleButton();
    }

    private void setVariable() {
        String[] options = {"Lỗi giao dịch", "Vấn đề khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spReportCategory.setAdapter(adapter);
    }

    private void handleButton() {
        binding.btnReportBack.setOnClickListener(v -> startActivity(new Intent(ReportActivity.this, MainActivity.class)));

        binding.btnReportCf.setOnClickListener(v -> {
            //Get data from view
            SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
            String report_category = binding.spReportCategory.getSelectedItem().toString().trim();
            String report_transaction_id = binding.edtReportTransactionId.getText().toString().trim();
            String report_title = Objects.requireNonNull(binding.edtReportTitle.getText()).toString().trim();
            String report_description = binding.edtReportDescription.getText().toString().trim();

            //Get current student roll number
            String student_roll_number = preferences.getString("student_roll_number", "");

            //Save data to DB
            DatabaseReference report_reference = database.getReference("Report").child(student_roll_number);
            report_reference.push().setValue(new Report(report_category, report_transaction_id,
                            report_title, report_description,
                            dataEncode.getCurrentTime(), 0))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Lưu dữ liệu thành công
                            // Hiển thị dialog thông báo
                            showDialogAndNavigateToMainActivity();
                        } else {
                            // Xử lý khi lưu dữ liệu không thành công (nếu cần)
                        }
                    });
        });
    }

    private void showDialogAndNavigateToMainActivity() {
        // Hiển thị dialog thông báo
        AlertDialog.Builder builder = new AlertDialog.Builder(ReportActivity.this);
        builder.setTitle("Success")
                .setMessage("Data saved successfully.")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Chuyển về MainActivity
                    startActivity(new Intent(ReportActivity.this, MainActivity.class));
                    finish(); // Đóng ReportActivity để ngăn người dùng quay lại từ MainActivity
                }).show();
    }


    private void handleTextChange() {
        binding.edtReportTransactionId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String report_transaction_id = s.toString().trim();
                boolean isTransactionIdValid = report_transaction_id.matches("^(-N.{18})$");
                binding.tvReportErr1.setVisibility(isTransactionIdValid ? View.GONE : View.VISIBLE);
                enableReportButton();
            }
        });

        binding.edtReportTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String report_title = s.toString().trim();
                boolean isTitleValid = report_title.matches("^.{1,49}$");
                binding.tvReportErr2.setVisibility(isTitleValid ? View.GONE : View.VISIBLE);
                enableReportButton();
            }
        });

        binding.edtReportDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String report_description = s.toString().trim();
                boolean isDescriptionValid = report_description.matches("^.{1,199}$");
                binding.tvReportErr3.setVisibility(isDescriptionValid ? View.GONE : View.VISIBLE);
                enableReportButton();
            }
        });
    }

    private void enableReportButton() {
        String report_transaction_id = binding.edtReportTransactionId.getText().toString().trim();
        String report_title = Objects.requireNonNull(binding.edtReportTitle.getText()).toString().trim();
        String report_description = Objects.requireNonNull(binding.edtReportDescription.getText()).toString().trim();
        boolean isTransactionIdValid = report_transaction_id.matches("^(-N.{18})$");
        boolean isTitleValid = report_title.matches("^.{1,49}$");
        boolean isDescriptionValid = report_description.matches("^.{1,199}$");
        binding.btnReportCf.setEnabled(isTransactionIdValid && isTitleValid && isDescriptionValid);
    }

}