package com.example.e_wallet_fptu.Activity.Security;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_wallet_fptu.Activity.Authentication.ChangePasswordActivity;
import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Authentication.SetupPasswordActivity;
import com.example.e_wallet_fptu.Activity.StudentInformation.UserActivity;
import com.example.e_wallet_fptu.databinding.ActivityEnterCodeactivityBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EnterCodeActivity extends BaseActivity {
    ActivityEnterCodeactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterCodeactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();

    }

    private void setVariable() {

        String code_verify = getIntent().getStringExtra("code");
        binding.btnVerify.setOnClickListener(v -> {
            String code_enter_by_user = binding.edtVerifyCode.getText().toString().trim();
            if (code_enter_by_user.equalsIgnoreCase(code_verify)) {
                getIntent().removeExtra("code");
                String email = getIntent().getStringExtra("email");
                String type = getIntent().getStringExtra("type");
                if (type != null) {
                    switch (type) {
                        case "forgot password":
                            Intent i = new Intent(EnterCodeActivity.this, ChangePasswordActivity.class);
                            i.putExtra("email", email);
                            startActivity(i);
                            finish();
                            break;
                        case "update profile":
                            updateInformation();
                            startActivity(new Intent(EnterCodeActivity.this, UserActivity.class));
                            finish();
                            break;
                    }
                }
            } else {
                Toast.makeText(EnterCodeActivity.this, "Mã xác minh không đúng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateInformation() {
        // Get new information to update
        SharedPreferences profileUpdate = getSharedPreferences("profileUpdate", MODE_PRIVATE);
        String fullnameUpdate = profileUpdate.getString("fullnameUpdate", "");
        String phoneUpdate = profileUpdate.getString("phoneUpdate", "");

        //Request to data of student
        DatabaseReference stuRef = database.getReference("Student");
        Query query = stuRef.orderByChild("student_roll_number")
                .equalTo(getSharedPreferences("currentStudent", MODE_PRIVATE).getString("student_roll_number", ""));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Update new information
                    DataSnapshot studentSnapshot = snapshot.getChildren().iterator().next();
                    studentSnapshot.getRef().child("student_phone").setValue(phoneUpdate);
                    studentSnapshot.getRef().child("student_name").setValue(fullnameUpdate);
                    //Delete profileUpdate preferences
                    SharedPreferences preferences = getSharedPreferences("profileUpdate", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear(); // Xóa tất cả các giá trị
                    editor.apply();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}