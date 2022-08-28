package com.spa.dao;

import com.spa.model.CourseOnlyName;
import com.spa.model.TermList;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.spa.model.Grade;

@Mapper
public interface dataDao {

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

    int checkisCS(String student_code, int honors_count);

    List<Grade> getData();

    List<Grade> selectGradeByStudentCode(String student_code);

    int checkisHonors(String student_code);
}
