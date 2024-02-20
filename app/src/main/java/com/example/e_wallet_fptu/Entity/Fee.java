package com.example.e_wallet_fptu.Entity;

public class Fee {
    private int additional_dormitory_fee;
    private int dormitory_fee;
    private int library_fines;
    private int re_study_fee;
    private int scholarship_penalty_fee;
    private int semester_fee;

    public Fee(int additionalDormitoryFee, int dormitoryFee, int libraryFines, int reStudyFee, int scholarshipPenaltyFee, int semesterFee) {
        additional_dormitory_fee = additionalDormitoryFee;
        dormitory_fee = dormitoryFee;
        library_fines = libraryFines;
        re_study_fee = reStudyFee;
        scholarship_penalty_fee = scholarshipPenaltyFee;
        semester_fee = semesterFee;
    }

    public Fee() {
    }

    public int getAdditional_dormitory_fee() {
        return additional_dormitory_fee;
    }

    public int getDormitory_fee() {
        return dormitory_fee;
    }

    public int getLibrary_fines() {
        return library_fines;
    }

    public int getRe_study_fee() {
        return re_study_fee;
    }

    public int getScholarship_penalty_fee() {
        return scholarship_penalty_fee;
    }

    public int getSemester_fee() {
        return semester_fee;
    }
}
