package com.example.e_wallet_fptu.Entity;

public class Bank {
    private int id;
    private String name;
    private String code;
    private String bin;
    private String shortName;
    private String logo;
    private int transferSupported;
    private int lookupSupported;
    private String swiftCode;

    public Bank() {
    }

    public Bank(int id, String name, String code, String bin, String shortName, String logo, int transferSupported, int lookupSupported, String swiftCode) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.bin = bin;
        this.shortName = shortName;
        this.logo = logo;
        this.transferSupported = transferSupported;
        this.lookupSupported = lookupSupported;
        this.swiftCode = swiftCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getBin() {
        return bin;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLogo() {
        return logo;
    }

    public int getTransferSupported() {
        return transferSupported;
    }

    public int getLookupSupported() {
        return lookupSupported;
    }

    public String getSwiftCode() {
        return swiftCode;
    }
}
