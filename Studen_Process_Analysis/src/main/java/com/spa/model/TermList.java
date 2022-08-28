package com.spa.model;


public class TermList {
    private int year;
    private int semester;

    public TermList() {
        super();
    }

    public TermList(int year, int semester) {
        super();
        this.year = year;
        this.semester = semester;
    }

    public int getYear() {
        return this.year;
    }

    public int getSemester() {
        return this.semester;
    }
}
