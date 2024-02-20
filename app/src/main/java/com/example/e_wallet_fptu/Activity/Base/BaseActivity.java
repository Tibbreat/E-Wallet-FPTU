package com.example.e_wallet_fptu.Activity.Base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.e_wallet_fptu.Helper.DataEncode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {
    public FirebaseDatabase database;
    public FirebaseAuth auth;
    public String TAG = "E-Wallet-Check";
    public DataEncode dataEncode = new DataEncode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }
}