package com.spa.controller;


import com.spa.enums.RiskEnum;
import com.spa.model.Grade;
import com.spa.model.TermList;
import com.spa.service.DataService;
import com.spa.tool.StudentRiskCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping({"/index","/"})
public class IndexPage {

    @Autowired
	private DataService dataService;

	@RequestMapping(value={"/","/home",""}, method=RequestMethod.GET)
	public String homePage(HttpServletRequest request, Model m) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		Boolean admin = (Boolean) session.getAttribute("admin");
		Boolean logined = username == null;
		m.addAttribute("logined", logined);
		m.addAttribute("admin", admin);
		m.addAttribute("username",username);
		m.addAttribute("page", "home");
		return "index";
	}

	@RequestMapping(value={"/course", "/course/enrolled"}, method=RequestMethod.GET)
	public String courseManagement(HttpServletRequest request, Model m) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		Boolean logined = username == null;
		m.addAttribute("logined",logined);
		m.addAttribute("username", username);
		m.addAttribute("page", "course");
		m.addAttribute("subpage", "enrolled");
		return "index";
	}

	@RequestMapping(value="/course/result", method=RequestMethod.GET)
	public String courseManagmentResult(HttpServletRequest request, Model m) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		Boolean logined = username == null;
		m.addAttribute("logined",logined);
		m.addAttribute("username", username);
		m.addAttribute("page", "course");
		m.addAttribute("subpage", "result");
		return "index";
	}

	@RequestMapping(value={"/student", "/student/academic"}, method=RequestMethod.GET)
	public String studentManagmentAcadmic(HttpServletRequest request, Model m) {
		HttpSession session  = request.getSession();
		String username = (String) session.getAttribute("username");
		Boolean logined = username == null;
		m.addAttribute("logined", logined);
		m.addAttribute("username", username);
		m.addAttribute("page", "student");
		m.addAttribute("subpage", "academic");
		return "index";
	}

	@RequestMapping(value={"/student/honorsoffers"}, method=RequestMethod.GET)
	public String studentManagementHonors(HttpServletRequest request, Model m) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		Boolean logined = username == null;
		m.addAttribute("logined",logined);
		m.addAttribute("username", username);
		m.addAttribute("page", "student");
		m.addAttribute("subpage", "honorsoffers");
		return "index";
	}

    @RequestMapping(value={"/student/riskstudent"}, method=RequestMethod.GET)
	public String studentManagement(HttpServletRequest request, Model m) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		Boolean logined = username == null;
		m.addAttribute("logined",logined);
		m.addAttribute("username", username);
		m.addAttribute("page", "student");
		m.addAttribute("subpage", "riskstudent");
		return "index";
	}
    
    @RequestMapping(value="/detail", method=RequestMethod.GET)
	public String detailPage(HttpServletRequest request, Model m, @RequestParam("student_code") String student_code) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		Boolean logined = username == null;
		Double GPAWithF = dataService.getAvgGPAwithF(student_code);
		Double GPAWithoutF = dataService.getAvgGPAwithoutF(student_code);
		List<TermList> termList = dataService.selectYearAndSemesterByStudentCode(student_code);
		List<Object> gradeList = new ArrayList<Object>();
		for(TermList term : termList) {
			Map<String, Object> grade = new HashMap<String, Object>();
			grade.put("grade", dataService.selectGradeByStudentCodeAndTerm(student_code, term.getYear(), term.getSemester()));
			grade.put("gpa", dataService.getTermGPAByStudentCodeAndTerm(student_code, term.getYear(), term.getSemester()));
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("term", term);
			temp.put("grade", grade);
			gradeList.add(temp);
		}
        String honors_status = "false";
        if (GPAWithF >= 5.0 && (dataService.checkisCS(student_code, 255) != 0 | dataService.checkisCS(student_code, 511) != 0 | dataService.checkisCS(student_code, 2047) != 0)) {
            honors_status = "true";
        }
        if (dataService.checkisHonors(student_code) != 0) {
            honors_status = "honors";
        }        
        
        List<Grade> grades = dataService.selectGradeByStudentCode(student_code);
		RiskEnum riskEnum = StudentRiskCalculator.calculateRisk(grades);
		String riskLevel = riskEnum.getRisk();
		if (riskEnum == RiskEnum.NORMAL) {
			riskLevel = "false";
		}
		m.addAttribute("riskLevel", riskLevel);
		m.addAttribute("logined", logined);
		m.addAttribute("username", username);
		m.addAttribute("page", "detail");
		m.addAttribute("student_code", student_code);
		m.addAttribute("GPAWithF", GPAWithF);
		m.addAttribute("GPAWithoutF", GPAWithoutF);
		m.addAttribute("gradeList", gradeList);
        m.addAttribute("honors_status", honors_status);

		return "index";
	}
}
