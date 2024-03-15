package com.example.e_wallet_fptu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_wallet_fptu.Activity.Transaction.TransactionView.TransactionDetailActivity;
import com.example.e_wallet_fptu.Entity.Transaction;
import com.example.e_wallet_fptu.Helper.DataEncode;
import com.example.e_wallet_fptu.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.viewholder> {
    List<Transaction> list;
    Context context;

    public TransactionAdapter(List<Transaction> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TransactionAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_transaction, parent, false);
        return new viewholder(inflate);
    }

    public void setTransactions(List<Transaction> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.viewholder holder, int position) {
        Transaction transaction = list.get(position);
        DataEncode dataEncode = new DataEncode();

        holder.transaction_type_txt.setText(transaction.getCategory());
        holder.transaction_time_txt.setText(transaction.getTime());

        // Set color based on category
        if (transaction.getCategory().equals("Nạp tiền vào ví") || transaction.getCategory().equals("Nhận tiền từ ví khác")) {
            holder.transaction_amount_txt.setText(String.format("+ %s", dataEncode.formatMoney(transaction.getAmount())));
            holder.transaction_amount_txt.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.main_green));
        } else {
            holder.transaction_amount_txt.setText(String.format("- %s", dataEncode.formatMoney(transaction.getAmount())));
            holder.transaction_amount_txt.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TransactionDetailActivity.class);
            intent.putExtra("object", list.get(position));
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView transaction_type_txt, transaction_amount_txt, transaction_time_txt;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            transaction_type_txt = itemView.findViewById(R.id.tv_transaction_type);
            transaction_amount_txt = itemView.findViewById(R.id.tv_transaction_amount);
            transaction_time_txt = itemView.findViewById(R.id.tv_transaction_time);
        }
    }
}
