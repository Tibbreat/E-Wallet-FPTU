package com.example.e_wallet_fptu.Activity.Transaction.QR;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.e_wallet_fptu.databinding.ActivityQrgenarateBinding;

public class QRGenerateActivity extends AppCompatActivity {
    ActivityQrgenarateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrgenarateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        QRGenerate();
        handleButton();
    }

    private void QRGenerate() {
        String QR_URL = "https://img.vietqr.io/image/TPB-03952302901-qr_only.png";
        Glide.with(this).load(QR_URL).override(350, 350).
                transform(new CenterCrop(), new RoundedCorners(5))
                .into(binding.imgQR);
    }

    private void handleButton() {
        binding.btnQrGenerateBack.setOnClickListener(v -> finish());
        binding.btnQRGenerateScanQR.setOnClickListener(v -> startActivity(new Intent(QRGenerateActivity.this, QRScanActivity.class)));
    }
}