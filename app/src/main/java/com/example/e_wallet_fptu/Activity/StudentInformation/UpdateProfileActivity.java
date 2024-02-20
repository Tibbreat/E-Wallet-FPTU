package com.example.e_wallet_fptu.Activity.StudentInformation;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Security.EnterCodeActivity;
import com.example.e_wallet_fptu.Helper.SendOTP;
import com.example.e_wallet_fptu.databinding.ActivityUpdateProfileBinding;

import java.util.Objects;

public class UpdateProfileActivity extends BaseActivity {
    ActivityUpdateProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleButton();
    }

    private void handleButton() {
        // Handle btn back to ProfileActivity
        binding.btnUpdateProfileBack.setOnClickListener(v -> {
            Intent intent_back = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
            startActivity(intent_back);
        });

        // Handle btn confirm to update profile
        binding.btnUpdateProfileCf.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
            String student_email = preferences.getString("student_email", "");
            String verificationCode = dataEncode.generateRandomCode();

            new SendMailTask().execute(student_email, "Xác minh cập nhật thông tin cá nhân",
                    "Mã xác minh cập nhật thông tin cá nhân của bạn là: " + verificationCode);

            //Save new student information to sharedPre
            SharedPreferences profileUpdate = getSharedPreferences("profileUpdate", MODE_PRIVATE);
            SharedPreferences.Editor editor = profileUpdate.edit();
            editor.putString("fullnameUpdate", Objects.requireNonNull(binding.edtUpdateProfileActivityStudentFullname).getText().toString().trim());
            editor.putString("phoneUpdate", Objects.requireNonNull(binding.edtUpdateProfileActivityStudentPhone.getText()).toString().trim());
            editor.apply();
            Intent intent = new Intent(UpdateProfileActivity.this, EnterCodeActivity.class);
            intent.putExtra("code", verificationCode);
            intent.putExtra("type", "update profile");
            startActivity(intent);

        });
    }

    private class SendMailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String receiveEmail = params[0];
            String emailSubject = params[1];
            String text = params[2];

            SendOTP sendMail = new SendOTP();
            sendMail.sendMail(receiveEmail, emailSubject, text);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }
}
