package com.spa.service;

import com.spa.model.CourseOnlyName;
import com.spa.model.Grade;
import com.spa.model.Honors;
import com.spa.model.Student;
import com.spa.model.TermList;

import java.util.List;

public interface DataService {
    int deleteAllData();

    int insertGradeDataByList(List<Grade> rowDataList);

    List<CourseOnlyName> selectCourseByName(String keyword);

    List<TermList> selectYearAndSemesterByCourseName(String course_name);

    List<Grade> getEnrolledData(String course_name, int year, int semester);

    int confirmStudentCodeIfExists(String student_code);

    Double getAvgGPAwithF(String student_code);

    Double getAvgGPAwithoutF(String student_code);

    List<TermList> selectYearAndSemesterByStudentCode(String student_code);

    List<Grade> selectGradeByStudentCodeAndTerm(String student_code, int year, int semester);

    Double getTermGPAByStudentCodeAndTerm(String student_code, int year, int semester);

    List<Honors> getHonorsList();
    
    int checkisCS(String student_code, int honors_count);

    List<Student> getStudentDataByRisk(String risk);

    List<Grade> selectGradeByStudentCode(String studentCode);

    int checkisHonors(String student_code);
}
