package com.spa.model;

public class Honors {
    private String student_code;
    private String major;
    private String adv;
    private int total_unit;
    private double total_grade;
    private String gpa;
    private String start_date;
    private String end_date;
    private int start_date_int;
    private int end_date_int;
    private boolean not_honors;

    public boolean isNot_honors() {
        return not_honors;
    }

    public void setNot_honors(boolean not_honors) {
        this.not_honors = not_honors;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getStart_date_int() {
        return start_date_int;
    }

    public void setStart_date_int(int start_date_int) {
        this.start_date_int = start_date_int;
    }

    public int getEnd_date_int() {
        return end_date_int;
    }

    public void setEnd_date_int(int end_date_int) {
        this.end_date_int = end_date_int;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public Honors() {
        super();
    }

    public Honors(String student_code, int total_unit, double total_grade) {
        this.student_code = student_code;
        this.total_unit = total_unit;
        this.total_grade = total_grade;
    }

    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAdv() {
        return adv;
    }

    public void setAdv(String adv) {
        this.adv = adv;
    }

    public int getTotal_unit() {
        return total_unit;
    }

    public void setTotal_unit(int total_unit) {
        this.total_unit = total_unit;
    }

    public double getTotal_grade() {
        return total_grade;
    }

    public void setTotal_grade(double total_grade) {
        this.total_grade = total_grade;
    }

    public String getGPA() {
        return gpa;
    }

    public void setGPA(String gPA) {
        gpa = gPA;
    }

}
