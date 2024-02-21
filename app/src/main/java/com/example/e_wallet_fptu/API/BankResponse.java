package com.example.e_wallet_fptu.API;

import com.example.e_wallet_fptu.Entity.Banks;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankResponse {
    @SerializedName("code")
    private String code;

    @SerializedName("desc")
    private String description;

    @SerializedName("data")
    private List<Banks> bankList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Banks> getBankList() {
        return bankList;
    }

    public void setBankList(List<Banks> bankList) {
        this.bankList = bankList;
    }
}
