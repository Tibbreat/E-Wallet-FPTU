package com.example.e_wallet_fptu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_wallet_fptu.Entity.Report;
import com.example.e_wallet_fptu.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.viewholder> {
    List<Report> reportList;
    Context context;

    public ReportAdapter(List<Report> reportList) {
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ReportAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_report, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.viewholder holder, int position) {
        Report report = reportList.get(position);
        holder.status.setText(report.getReport_title());
        holder.title.setText(report.getReport_title());
        holder.time.setText(report.getReport_created_time());

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView title, status, time;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_viewholder_report_title);
            status = itemView.findViewById(R.id.tv_viewholder_report_status);
            time = itemView.findViewById(R.id.tv_viewholder_report_time);
        }
    }
}
