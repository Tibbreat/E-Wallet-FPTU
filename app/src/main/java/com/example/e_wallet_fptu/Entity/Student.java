package com.example.e_wallet_fptu.Entity;

import java.io.Serializable;

public class Student implements Serializable {
    private String student_roll_number;
    private String student_name;
    private String student_email;
    private String student_phone;
    private int student_amount;
    private String student_password;
    private String created_date;
    private String modified_date;
    private String student_PIN;
    private boolean status;

    public Student() {
    }

    public String getStudent_PIN() {
        return student_PIN;
    }


    public String getStudent_phone() {
        return student_phone;
    }


    public String getStudent_roll_number() {
        return student_roll_number;
    }

    public String getStudent_name() {
        return student_name;
    }


    public String getStudent_email() {
        return student_email;
    }


    public String getStudent_password() {
        return student_password;
    }


    public boolean getStatus() {
        return status;
    }

}
