package com.examstack.management.controller.action.teacher;

import com.exammanagement.common.domain.question.Field;
import com.examstack.management.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class DashBoardPageTeacher {
    @Autowired
    private QuestionService questionService;


    @RequestMapping(value = "/teacher/dashboard", method = RequestMethod.GET)
    public String dashboardPage(Model model) {

        List<Field> fieldList = questionService.getAllField(null);


        model.addAttribute("fieldList", fieldList);
        return "dashboard";
    }

}
