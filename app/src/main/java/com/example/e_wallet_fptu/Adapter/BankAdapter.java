package com.example.e_wallet_fptu.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.e_wallet_fptu.Entity.Bank;
import com.example.e_wallet_fptu.R;

import java.util.ArrayList;


public class BankAdapter extends RecyclerView.Adapter<BankAdapter.viewholder> {
    ArrayList<Bank> banks;
    Context context;

    public BankAdapter(ArrayList<Bank> banks) {
        this.banks = banks;
    }

    @NonNull
    @Override
    public BankAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_bank, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BankAdapter.viewholder holder, int position) {
        holder.titleTxt.setText(banks.get(position).getName());
        Glide.with(context).load(banks.get(position).getLogo())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.vb_tv);

            pic = itemView.findViewById(R.id.vb_img);
        }
    }
}