package com.spa.tool;

public class GradeTransform {

    public static double GradeTransformFunction(String grade) {
        switch (grade) {
            case "HD":
                return 7.0;
            case "D":
                return 6.0;
            case "C":
                return 5.0;
            case "P":
                return 4.0;
            default:
                return 1.5;
        }
    }
}
