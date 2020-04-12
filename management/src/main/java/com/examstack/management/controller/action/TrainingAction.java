package com.examstack.management.controller.action;

import com.exammanagement.common.util.file.FileUploadUtil;
import com.examstack.management.security.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TrainingAction {

    @RequestMapping(value = "/secure/upload-uploadify-file", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFile(HttpServletRequest request, HttpServletResponse response) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> filePathList = new ArrayList<String>();
        try {
            filePathList = FileUploadUtil.uploadFile(request, response, userInfo.getUsername());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (filePathList.size() == 0) {
            return "系统错误";
        }

        return filePathList.get(0);
    }
}
