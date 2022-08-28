package com.spa.model;

public class Grade {
    private String student_code;
    private String course_code;
    private String course_name;
    private int units;
    private boolean ismaster;
    private double grade;
    private int year;
    private int semester;
    private int honors_count;

    public Grade() {
        super();
    }

    public Grade(String student_code, String course_code, String course_name, int units, boolean ismaster, double grade, int year, int semester, int honors_count) {
        this.student_code = student_code;
        this.course_code = course_code;
        this.course_name = course_name;
        this.units = units;
        this.ismaster = ismaster;
        this.grade = grade;
        this.year = year;
        this.semester = semester;
        this.honors_count = honors_count;
    }

    public String getStudentCode() {
        return this.student_code;
    }

    public String getCourseCode() {
        return this.course_code;
    }

    public String getCourseName() {
        return this.course_name;
    }

    public int getUnits() {
        return this.units;
    }

    public boolean getIsMaster() {
        return this.ismaster;
    }

    public double getGrade() {
        return this.grade;
    }

    public int getYear() {
        return this.year;
    }

    public int getSemester() {
        return this.semester;
    }

    public int getHonorscount() {
        return this.honors_count;
    }

}