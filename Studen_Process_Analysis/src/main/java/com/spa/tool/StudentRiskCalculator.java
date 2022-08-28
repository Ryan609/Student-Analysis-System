package com.spa.tool;


import com.spa.enums.RiskEnum;
import com.spa.model.Grade;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class StudentRiskCalculator {
    public static RiskEnum calculateRisk(List<Grade> studentGrades) {
        List<Grade> fGrades = studentGrades.stream().filter(x -> x.getGrade() < 2.0).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(fGrades)) {
            return RiskEnum.NORMAL;
        }

        Integer riskNum = 0;
        Map<String, List<Grade>> ysGradeMap = studentGrades.stream().collect(Collectors.groupingBy(x -> x.getYear() + "-" + x.getSemester()));
        Map<String, Integer> unitsMap = new HashMap<>();
        for (Map.Entry<String, List<Grade>> gradeEntry : ysGradeMap.entrySet()) {
            Integer totalUnits = 0;
            List<Grade> values = gradeEntry.getValue();
            for (Grade value : values) {
                totalUnits += value.getUnits();
            }
            unitsMap.put(gradeEntry.getKey(), totalUnits);
        }

        // obtain last semester
        List<String> allSemester = studentGrades.stream().map(x -> x.getYear() + "-" + x.getSemester()).sorted().collect(Collectors.toList());
        String maxSemester = allSemester.get(allSemester.size() - 1);

        // sort by semester order
        List<String> fGradeList = fGrades.stream().map(x -> x.getYear() + "-" + x.getSemester()).distinct().sorted().collect(Collectors.toList());
        // grouping in different semesters
        Map<String, List<Grade>> fGradeMap = fGrades.stream().collect(Collectors.groupingBy(x -> x.getYear() + "-" + x.getSemester()));

        Set<String> gradeCodeSet = new HashSet<>();

        Boolean isLastSemester = false;
        for (String s : fGradeList) {
            List<Grade> grades = fGradeMap.get(s);
            Integer total = unitsMap.get(s);

            Integer fUnits = 0;
            Boolean isHas = false;
            for (Grade curSGrade : grades) {
                fUnits += curSGrade.getUnits();
                if (gradeCodeSet.contains(curSGrade.getCourseCode())) {
                    riskNum ++;
                    isHas = true;
                    if (s.equalsIgnoreCase(maxSemester)) {
                        isLastSemester = true;
                    }
                    break;
                }
            }
            gradeCodeSet.addAll(grades.stream().map(x -> x.getCourseCode()).collect(Collectors.toSet()));
            if (isHas) continue;
            if (fUnits*1.0 / total >= 0.5 ) {
                if (s.equalsIgnoreCase(maxSemester)) {
                    isLastSemester = true;
                }
                riskNum ++;
            }
        }
        if (riskNum == 1) {
            if (!isLastSemester){
                return RiskEnum.NORMAL;
            }
            return RiskEnum.RISK1;
        } else if(riskNum == 2) {
            return RiskEnum.RISK2;
        } else if(riskNum > 2){
            return RiskEnum.UNSATISFACTORY;
        }
        return RiskEnum.NORMAL;

    }
}
