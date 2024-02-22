package com.example.e_wallet_fptu.Activity.Transaction.TransactionView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.e_wallet_fptu.Activity.Base.BaseActivity;
import com.example.e_wallet_fptu.Activity.Base.MainActivity;
import com.example.e_wallet_fptu.Adapter.TransactionAdapter;
import com.example.e_wallet_fptu.Entity.Transaction;
import com.example.e_wallet_fptu.databinding.ActivityListAllTransactionBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ListAllTransactionActivity extends BaseActivity {
    ActivityListAllTransactionBinding binding;
    private TransactionAdapter adapter;
    private List<Transaction> transactions = new ArrayList<>();
    private DatabaseReference transactionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListAllTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = getSharedPreferences("currentStudent", MODE_PRIVATE);
        String currentStudentRollNumber = preferences.getString("student_roll_number", "");
        transactionRef = database.getReference("Transaction").child(currentStudentRollNumber);
        initRecyclerView();
        listenForTransactionChanges();
        handleSearchEdt();
        handleButton();
    }

    private void initRecyclerView() {
        adapter = new TransactionAdapter(transactions);
        binding.rvTransactionHistoryView.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTransactionHistoryView.setAdapter(adapter);
    }

    private void listenForTransactionChanges() {
        transactionRef.orderByChild("time").addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                Transaction transaction = snapshot.getValue(Transaction.class);
                if (transaction != null) {
                    transactions.add(0, transaction);
                    adapter.notifyDataSetChanged();
                    binding.rvTransactionHistoryView.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void filterTransactions(String searchText) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getFrom().toLowerCase().contains(searchText.toLowerCase()) ||
                    transaction.getTo().toLowerCase().contains(searchText.toLowerCase()) ||
                        transaction.getCategory().toLowerCase().contains(searchText.toLowerCase())) {
                filteredTransactions.add(transaction);
            }
        }
        adapter.setTransactions(filteredTransactions);
        adapter.notifyDataSetChanged();
    }

    private void handleSearchEdt() {
        binding.edtTransactionHistorySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().trim();
                filterTransactions(searchText);
            }
        });
    }

    private void handleButton() {
        binding.btnAllTransactionBack.setOnClickListener(v -> startActivity(new Intent(ListAllTransactionActivity.this, MainActivity.class)));
    }

}