package com.spa.serviceImpl;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.spa.dao.dataDao;
import com.spa.enums.RiskEnum;
import com.spa.model.CourseOnlyName;
import com.spa.model.Grade;
import com.spa.model.Honors;
import com.spa.model.Student;
import com.spa.model.TermList;
import com.spa.service.DataService;
import com.spa.tool.StudentRiskCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService{
    @Autowired
    private dataDao dataDao;

    @Override
    public int deleteAllData() {

        return dataDao.deleteAllData();
    }

    @Override
    public int insertGradeDataByList(List<Grade> rowDataList) {

        return dataDao.insertGradeDataByList(rowDataList);
    }

    @Override
    public List<CourseOnlyName> selectCourseByName(String keyword) {
        return dataDao.selectCourseByName(keyword);
    }

    @Override
    public List<TermList> selectYearAndSemesterByCourseName(String course_name) {
        return dataDao.selectYearAndSemesterByCourseName(course_name);
    }

    @Override
    public List<Grade> getEnrolledData(String course_name, int year, int semester) {
        return dataDao.getEnrolledData(course_name, year, semester);
    }

    @Override
    public int confirmStudentCodeIfExists(String student_code) {
        return dataDao.confirmStudentCodeIfExists(student_code);
    }

    @Override
    public Double getAvgGPAwithF(String student_code) {
        return dataDao.getAvgGPAwithF(student_code);
    }

    @Override
    public Double getAvgGPAwithoutF(String student_code) {
        return dataDao.getAvgGPAwithoutF(student_code);
    }

    @Override
    public List<TermList> selectYearAndSemesterByStudentCode(String student_code){
        return dataDao.selectYearAndSemesterByStudentCode(student_code);
    }

    @Override
    public List<Grade> selectGradeByStudentCodeAndTerm(String student_code, int year, int semester){
        return dataDao.selectGradeByStudentCodeAndTerm(student_code, year, semester);
    }

    @Override
    public Double getTermGPAByStudentCodeAndTerm(String student_code, int year, int semester) {
        return dataDao.getTermGPAByStudentCodeAndTerm(student_code, year, semester);
    }

    @Override
    public int checkisCS(String student_code, int honors_count){
        return dataDao.checkisCS(student_code, honors_count);
    }

    @Override
    public int checkisHonors(String student_code){
        return dataDao.checkisHonors(student_code);
    }

    public List<Honors> getHonorsList(){
        List<Grade> currentStudentStudent = dataDao.getData();
        ArrayList<String> csStudents = new ArrayList<>();
        for (Grade student : currentStudentStudent) {
            if(student.getHonorscount() == 255 | student.getHonorscount() == 511 | student.getHonorscount() == 2047){
                csStudents.add(student.getStudentCode());
            }
        }
        List<Grade> CSgradeList = null;
        CSgradeList = currentStudentStudent.stream().filter(x -> csStudents.contains(x.getStudentCode())).collect(Collectors.toList());
        //CSgradeList.forEach(x -> System.out.println(x.getStudentCode() + x.getGrade()));
        Map<String, Honors> map = new HashMap<>();
        for (Grade student : CSgradeList) {
            if (Integer.parseInt(student.getCourseCode()) < 4000) {
                String student_code = student.getStudentCode();
                int currentCoursedate = student.getYear() * 10 + student.getSemester();
                if (map.containsKey(student_code)) {
                    Honors currentStudent = map.get(student_code);
                    switch(student.getCourseCode()) {
                        case "3006":
                        currentStudent.setMajor("Computer Science");
                        break;
                        case "3310":
                        currentStudent.setMajor("Artificial intelligence");
                        break;
                        case "3311":
                        currentStudent.setMajor("Data science");
                        break;
                        case "3312":
                        currentStudent.setMajor("Cybersecurity");
                        break;
                        case "3313":
                        currentStudent.setMajor("Distributed systems and networking");
                        break;
                        case "3020":
                        currentStudent.setAdv(" (Advanced)");
                        break;
                    }
                    currentStudent.setTotal_grade(currentStudent.getTotal_grade() + (student.getGrade() * student.getUnits()));
                    currentStudent.setTotal_unit(currentStudent.getTotal_unit() + student.getUnits());
                    if (currentCoursedate > currentStudent.getEnd_date_int()) {
                        currentStudent.setEnd_date_int(currentCoursedate);
                    }
                    if (currentCoursedate < currentStudent.getStart_date_int()) {
                        currentStudent.setStart_date_int(currentCoursedate);
                    }
                    map.put(student_code, currentStudent);
                } else {
                    Honors currentStudent = new Honors(student_code, student.getUnits(), student.getUnits() * student.getGrade());
                    currentStudent.setAdv("");
                    currentStudent.setStart_date_int(currentCoursedate);
                    currentStudent.setEnd_date_int(currentCoursedate);
                    currentStudent.setNot_honors(true);
                    map.put(student_code, currentStudent);
                }
            } else if (Integer.parseInt(student.getCourseCode()) < 5000){
                String student_code = student.getStudentCode();
                if (map.containsKey(student_code)) {
                    Honors currentStudent = map.get(student_code);
                    currentStudent.setNot_honors(false);
                    map.put(student_code, currentStudent);
                } else {
                    Honors currentStudent = new Honors(student_code,0,0);
                    currentStudent.setNot_honors(false);
                    map.put(student_code, currentStudent);
                }
            }
        }
        List<Honors> csStudentsGPA = new ArrayList<>();
        for (String temp : map.keySet()){
            csStudentsGPA.add(map.get(temp));
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        List<Honors> honorsOutput = new ArrayList<>();
        for (Honors canditate : csStudentsGPA) {
            canditate.setStart_date(canditate.getStart_date_int() / 10 + "s" + canditate.getStart_date_int() % 10);
            canditate.setEnd_date(canditate.getEnd_date_int() / 10 + "s" + canditate.getEnd_date_int() % 10);
            Double GPA = canditate.getTotal_grade() / canditate.getTotal_unit();
            if (GPA >= 5.0 && canditate.isNot_honors()) {
                canditate.setGPA(nf.format(GPA));
                honorsOutput.add(canditate);
            }
        }
        Collections.sort(honorsOutput, new Comparator<Honors>() {
            @Override
            public int compare(Honors h1, Honors h2) {
                int dateEnd1 = h1.getEnd_date_int();
                int dateEnd2 = h2.getEnd_date_int();
                Double gpa1 = Double.parseDouble(h1.getGPA());
                Double gpa2 = Double.parseDouble(h2.getGPA());
                if (dateEnd1 == dateEnd2) {
                    return (int)(gpa2*1000 - gpa1*1000);
                } else {
                    return dateEnd2 - dateEnd1;
                }
            }
        });
        return honorsOutput;
    }

    public List<Student> getStudentDataByRisk(String risk) {
        // student risk
        List<Grade> grades = dataDao.getData();
        List<Student> students = new ArrayList<>();
        Map<String, List<Grade>> studenGradetMap = grades.stream().collect(Collectors.groupingBy(Grade::getStudentCode));
        for (Map.Entry<String, List<Grade>> entry : studenGradetMap.entrySet()) {
            Student student = new Student();
            student.setStudent_code(entry.getKey());
            List<Grade> studentGrades = entry.getValue();
            RiskEnum riskEnum = StudentRiskCalculator.calculateRisk(studentGrades);
            student.setRisk_level(riskEnum.getRisk());
            students.add(student);

        }
        if (!risk.equalsIgnoreCase("All")) {
            AtomicReference<Integer> no = new AtomicReference<>(0);
            return students.stream().filter(x -> x.getRisk_level().equalsIgnoreCase(risk)).sorted(Comparator.comparing(Student::getStudent_code)).map(x -> {
                x.setNo(no.getAndSet(no.get() + 1));
                return x;
            }).collect(Collectors.toList());
        } else {
            AtomicReference<Integer> no = new AtomicReference<>(0);
            return students.stream().filter(x -> !x.getRisk_level().equalsIgnoreCase("normal"))
                    .sorted(Comparator.comparing(Student::getStudent_code)).map(x -> {
                x.setNo(no.getAndSet(no.get() + 1));
                return x;
            }).collect(Collectors.toList());
        }
    }

    @Override
    public List<Grade> selectGradeByStudentCode(String studentCode) {
        return dataDao.selectGradeByStudentCode(studentCode);
    }
}
