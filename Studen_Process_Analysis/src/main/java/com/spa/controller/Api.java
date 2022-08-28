package com.spa.controller;

import com.alibaba.fastjson.JSONObject;
import com.spa.model.CourseOnlyName;
import com.spa.model.Grade;
import com.spa.model.Honors;
import com.spa.model.Student;
import com.spa.model.TermList;
import com.spa.service.DataService;
import com.spa.tool.GradeTransform;
import de.siegmar.fastcsv.reader.NamedCsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping({"/api"})
public class Api {

    @Autowired
    private DataService dataService;

    @RequestMapping(value="/uploadCsvData", method= RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadCsvData(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject result = new JSONObject();
        HttpSession session = request.getSession();
        Boolean admin = (Boolean) session.getAttribute("admin");
        Boolean uploadstatus = true;
        Set<Integer> cs_classlist = new HashSet<Integer>(Arrays.asList(1102, 1103, 1105, 1106, 2000, 2006, 2103, 2201, 2207, 3004, 3006, 3310, 3311, 3312, 3313, 1104, 2008, 3020));
        HashMap<String, Integer> map = new HashMap<>();
        HashMap<Integer, Integer> course_index = new HashMap<>();
        course_index.put(1102,1);
        course_index.put(1106,2);
        course_index.put(2006,2);
        course_index.put(2000,4);
        course_index.put(2103,8);
        course_index.put(1103,8);
        course_index.put(2201,16);
        course_index.put(2207,32);
        course_index.put(1105,32);
        course_index.put(3004,64);
        course_index.put(3006,128);
        course_index.put(3310,128);
        course_index.put(3311,128);
        course_index.put(3312,128);
        course_index.put(3313,128);
        course_index.put(1104,256);
        course_index.put(2008,512);
        course_index.put(3020,1024);

        if (!admin) {
            result.put("code", 401);
            result.put("msg", "Permission Forbidden");
            result.put("data", null);
        } else {
            Boolean isReplace = data.getBoolean("isReplace");
            if (isReplace) {
                dataService.deleteAllData();
            }
            String csvString = data.get("csvData").toString();
            List<Grade> rowDataList = new ArrayList<Grade>();

            try{
                NamedCsvReader.builder().build(csvString).forEach(
                row -> {
                    String student_code = row.getField("SCODE").toString();
                    String course_code = row.getField("code").toString();
                    String course_name = row.getField("name").toString();
                    int units = Integer.valueOf(row.getField("Units").toString());
                    Boolean ismaster = Integer.valueOf(row.getField("code").toString()) >= 7000 ? true : false;
                    double grade = GradeTransform.GradeTransformFunction(row.getField("Grade").toString());
                    int year = (int) Math.floor(Integer.valueOf(row.getField("psid").toString())/100) + 1980;
                    int semester = (Integer.valueOf(row.getField("psid").toString())/10) % 2 == 0 ? 2 : 1;
                    int cscourse_count = 0;
                    if(cs_classlist.contains(Integer.valueOf(course_code)) && grade > 3){
                        int current_course_code = course_index.get(Integer.valueOf(course_code));
                        if (map.containsKey(student_code)){
                            int count = map.get(student_code);
                            map.put(student_code, count | current_course_code);
                            cscourse_count = count | current_course_code;
                        }else{
                            map.put(student_code, current_course_code);
                            cscourse_count = current_course_code;
                        }
                    }
                    //
                    Grade temp = new Grade(student_code, course_code, course_name, units, ismaster, grade, year, semester, cscourse_count);
                    rowDataList.add(temp);
                    if(rowDataList.size() == 30) {
                        dataService.insertGradeDataByList(rowDataList);
                        rowDataList.clear();
                    }
                }
            );} catch (Exception e){
                uploadstatus = false;
            }

            if(rowDataList.size() != 0) {
                dataService.insertGradeDataByList(rowDataList);
                rowDataList.clear();
            }
            result.put("code", 200);
            if(uploadstatus){
                result.put("msg", "Data Upload Succeeded!");
            }else{
                result.put("msg", "Data Upload Failed!");
            }
            result.put("data", null);
        }
        return result;
    }

    @RequestMapping(value="/searchCourseByKeyword", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject searchCourseByKeyword(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject result = new JSONObject();
        String keyword = data.getString("keyword");
        keyword = '%' + keyword + '%';
        List<CourseOnlyName> CourseList = dataService.selectCourseByName(keyword);
        result.put("code", 200);
        result.put("msg", "Succeeded!");
        result.put("data", CourseList);
        return result;
    }

    @RequestMapping(value="/selectYearAndSemesterByCourseName", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject selectYearAndSemesterByCourseName(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject result = new JSONObject();
        String course_name = data.getString("course_name");
        List<TermList> timeList = dataService.selectYearAndSemesterByCourseName(course_name);
        result.put("code", 200);
        result.put("msg", "Succeeded!");
        result.put("data", timeList);
        return result;
    }

    @RequestMapping(value="/getCourseDataByNameAndTime", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject getCourseDataByNameAndTime(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject result = new JSONObject();
        List<Grade> gradeList = dataService.getEnrolledData(data.getString("course_name"), data.getIntValue("year"), data.getIntValue("semester"));
        result.put("code", 200);
        result.put("msg", "Succeeded!");
        result.put("data", gradeList);
        return result;
    }

    @RequestMapping(value="/searchStudentByCode", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject searchStudentByCode(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject result = new JSONObject();
        int gradeCount = dataService.confirmStudentCodeIfExists(data.getString("student_code"));
        result.put("code", 200);
        result.put("msg", "Succeeded!");
        result.put("data", gradeCount);
        return result;
    }

    @RequestMapping(value="/getHonorsList", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject getHonorsList(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject result = new JSONObject();
        List<Honors> students = dataService.getHonorsList();
        result.put("code", 200);
        result.put("msg", "Succeeded!");
        result.put("data", students);
        return result;
    }

    @RequestMapping(value="/getStudentDataByRisk", method=RequestMethod.POST)
    @ResponseBody
    public JSONObject getStudentDataByRisk(HttpServletRequest request, @RequestBody JSONObject data) {
        JSONObject result = new JSONObject();

        String risk = data.getString("risk");

        List<Student> students = dataService.getStudentDataByRisk(risk);
        result.put("code", 200);
        result.put("msg", "Succeeded!");
        result.put("data", students);
        return result;
    }

}
