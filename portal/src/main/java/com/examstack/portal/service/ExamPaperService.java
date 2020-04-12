package com.examstack.portal.service;


import com.exammanagement.common.domain.exam.ExamPaper;

public interface ExamPaperService {

    /**
     * 获取一张试卷
     *
     * @param examPaperId
     * @return
     */
    public ExamPaper getExamPaperById(int examPaperId);


}
