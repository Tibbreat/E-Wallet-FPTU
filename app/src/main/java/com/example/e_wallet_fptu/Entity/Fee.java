package com.example.e_wallet_fptu.Entity;

public class Fee {
    private long additional_dormitory_fee;
    private long dormitory_fee;
    private long library_fines;
    private long re_study_fee;
    private long scholarship_penalty_fee;
    private long semester_fee;

    public Fee(long additionalDormitoryFee, long dormitoryFee, long libraryFines, long reStudyFee, long scholarshipPenaltyFee, long semesterFee) {
        additional_dormitory_fee = additionalDormitoryFee;
        dormitory_fee = dormitoryFee;
        library_fines = libraryFines;
        re_study_fee = reStudyFee;
        scholarship_penalty_fee = scholarshipPenaltyFee;
        semester_fee = semesterFee;
    }

    public Fee() {
    }

    public long getAdditional_dormitory_fee() {
        return additional_dormitory_fee;
    }

    public long getDormitory_fee() {
        return dormitory_fee;
    }

    public long getLibrary_fines() {
        return library_fines;
    }

    public long getRe_study_fee() {
        return re_study_fee;
    }

    public long getScholarship_penalty_fee() {
        return scholarship_penalty_fee;
    }

    public long getSemester_fee() {
        return semester_fee;
    }
}
