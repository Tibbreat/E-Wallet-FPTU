package com.example.e_wallet_fptu.Activity.Security;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Activity.StudentInformation.UserActivity;
import com.example.e_wallet_fptu.Helper.DataEncode;
import com.example.e_wallet_fptu.databinding.ActivitySetUpPincodeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SetUpPINCodeActivity extends BaseActivity {
    ActivitySetUpPincodeBinding binding;
    Button btn_setUpPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetUpPincodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btn_setUpPIN = binding.btnSetUpPIN;
        btn_setUpPIN.setEnabled(false);

        setUpOTPInput();
        setUpOTPReInput();
        savePIN();
        binding.btnSetUpPinBack.setOnClickListener(v -> {
            startActivity(new Intent(SetUpPINCodeActivity.this, UserActivity.class));
        });

    }

    private void setUpOTPInput() {
        binding.inputPinCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    binding.inputPinCode2.requestFocus();
                }
                enableButtonIfMatch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.inputPinCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    binding.inputPinCode3.requestFocus();
                }
                enableButtonIfMatch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.inputPinCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    binding.inputPinCode4.requestFocus();
                }
                enableButtonIfMatch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.inputPinCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enableButtonIfMatch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setUpOTPReInput() {
        binding.rePINCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    binding.rePINCode2.requestFocus();
                }
                enableButtonIfMatch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.rePINCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    binding.rePINCode3.requestFocus();
                }
                enableButtonIfMatch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.rePINCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    binding.rePINCode4.requestFocus();
                }
                enableButtonIfMatch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.rePINCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enableButtonIfMatch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void enableButtonIfMatch() {
        String PIN = binding.inputPinCode1.getText().toString().trim()
                + binding.inputPinCode2.getText().toString().trim()
                + binding.inputPinCode3.getText().toString().trim()
                + binding.inputPinCode4.getText().toString().trim();

        String rePIN = binding.rePINCode1.getText().toString().trim()
                + binding.rePINCode2.getText().toString().trim()
                + binding.rePINCode3.getText().toString().trim()
                + binding.rePINCode4.getText().toString().trim();

        btn_setUpPIN.setEnabled(PIN.equals(rePIN) && PIN.length() == 4);
    }

    private void savePIN() {
        String PINCheck = binding.inputPinCode1.getText().toString().trim()
                + binding.inputPinCode2.getText().toString().trim()
                + binding.inputPinCode3.getText().toString().trim()
                + binding.inputPinCode4.getText().toString().trim();

        String rePINCheck = binding.rePINCode1.getText().toString().trim()
                + binding.rePINCode2.getText().toString().trim()
                + binding.rePINCode3.getText().toString().trim()
                + binding.rePINCode4.getText().toString().trim();

        if (PINCheck.length() == 4 && rePINCheck.length() == 4) {
            btn_setUpPIN.setEnabled(true);
        }

        binding.btnSetUpPIN.setOnClickListener(v -> {
            String PIN = binding.inputPinCode1.getText().toString().trim()
                    + binding.inputPinCode2.getText().toString().trim()
                    + binding.inputPinCode3.getText().toString().trim()
                    + binding.inputPinCode4.getText().toString().trim();
            // Show a loading dialog
            ProgressDialog progressDialog = new ProgressDialog(SetUpPINCodeActivity.this);
            progressDialog.setMessage("Đang xử lý...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            // Save PIN to DB
            SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
            String currentStudentRollNumber = preferences.getString("student_roll_number", "");
            DatabaseReference reference = database.getReference("Student");
            Query query = reference.orderByChild("student_roll_number").equalTo(currentStudentRollNumber);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        DataEncode de = new DataEncode();
                        DataSnapshot studentSnapshot = snapshot.getChildren().iterator().next();

                        // Update or create the PIN node
                        String newPinHash = de.hashData(PIN);
                        Log.d(TAG, newPinHash);
                        studentSnapshot.getRef().child("student_PIN").setValue(newPinHash)
                                .addOnSuccessListener(aVoid -> {
                                    // Dismiss the loading dialog
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("student_PIN", newPinHash);
                                    editor.apply();
                                    progressDialog.dismiss();
                                    Toast.makeText(SetUpPINCodeActivity.this, "Thiết lập mã PIN thành công, vui lòng thực hiện lại giao dịch", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SetUpPINCodeActivity.this, MainActivity.class));

                                })
                                .addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(SetUpPINCodeActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                    Toast.makeText(SetUpPINCodeActivity.this, "Lỗi khi đọc dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}