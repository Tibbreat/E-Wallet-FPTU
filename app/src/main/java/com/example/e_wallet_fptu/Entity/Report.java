package com.example.e_wallet_fptu.Entity;

public class Report {
    private String report_category;
    private String report_transaction_id;
    private String report_title;
    private String report_description;
    private String report_created_time;
    private int report_status;

    public Report(String report_category, String report_transaction_id, String report_title, String report_description, String report_created_time, int report_status) {
        this.report_category = report_category;
        this.report_transaction_id = report_transaction_id;
        this.report_title = report_title;
        this.report_description = report_description;
        this.report_created_time = report_created_time;
        this.report_status = report_status;
    }

    public Report() {
    }

    public String getReport_category() {
        return report_category;
    }

    public void setReport_category(String report_category) {
        this.report_category = report_category;
    }

    public String getReport_transaction_id() {
        return report_transaction_id;
    }

    public void setReport_transaction_id(String report_transaction_id) {
        this.report_transaction_id = report_transaction_id;
    }

    public String getReport_title() {
        return report_title;
    }

    public void setReport_title(String report_title) {
        this.report_title = report_title;
    }

    public String getReport_description() {
        return report_description;
    }

    public void setReport_description(String report_description) {
        this.report_description = report_description;
    }

    public String getReport_created_time() {
        return report_created_time;
    }

    public void setReport_created_time(String report_created_time) {
        this.report_created_time = report_created_time;
    }

    public int getReport_status() {
        return report_status;
    }

    public void setReport_status(int report_status) {
        this.report_status = report_status;
    }
}
