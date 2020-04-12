package com.examstack.portal.service;

import com.exammanagement.common.domain.exam.ExamPaper;
import com.examstack.portal.persistence.ExamPaperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("examPaperService")
public class ExamPaperServiceImpl implements ExamPaperService {

    @Autowired
    private ExamPaperMapper examPaperMapper;

    @Override
    public ExamPaper getExamPaperById(int examPaperId) {
        // TODO Auto-generated method stub
        return examPaperMapper.getExamPaperById(examPaperId);
    }

}
