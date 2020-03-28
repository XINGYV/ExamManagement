package com.examstack.portal.service;

import com.exammanagement.common.domain.exam.AnswerSheet;
import com.exammanagement.common.domain.exam.AnswerSheetItem;
import com.exammanagement.common.domain.exam.ExamHistory;
import com.exammanagement.common.domain.exam.ExamPaper;
import com.examstack.portal.persistence.ExamMapper;
import com.examstack.portal.persistence.ExamPaperMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service("ScoreCalcuService")
public class ScoreCalcuService {

	@Autowired
	private ExamPaperMapper examPaperMapper;

	@Autowired
	private ExamMapper examMapper;

	public void calcuScore(AnswerSheet as) {
		System.out.println("answerSheet is calculating..." + as);

		ExamPaper examPaper = examPaperMapper.getExamPaperById(as.getExamPaperId());

		// TODO 计算得分 结果等数据..
		Gson gson = new Gson();
		AnswerSheet target = gson.fromJson(examPaper.getAnswer_sheet(),AnswerSheet.class);
		HashMap<Integer,AnswerSheetItem> answerMap = new HashMap<Integer,AnswerSheetItem>();
		for(AnswerSheetItem item : target.getAnswerSheetItems()){
			answerMap.put(item.getQuestionId(), item);
		}
		System.out.println("sdasd"+ answerMap.toString());
		as.setPointMax(target.getPointMax());
		//全部是客观题，则状态更改为已阅卷
		int approved = 3;
		for(AnswerSheetItem item : as.getAnswerSheetItems()){
			if(item.getAnswer().equals(answerMap.get(item.getQuestionId()).getAnswer())){
				as.setPointRaw(as.getPointRaw() + answerMap.get(item.getQuestionId()).getPoint());
				item.setPoint(answerMap.get(item.getQuestionId()).getPoint());
				item.setRight(true);
			}
			if(item.getQuestionTypeId() != 1 && item.getQuestionTypeId() != 2 && item.getQuestionTypeId() != 3){
				approved = 2;
				break;
			}
		}
		System.out.println(as);
		System.out.println(approved);

		ExamHistory examHistory = null;
		examMapper.updateUserExamHist(as, gson.toJson(as),approved);

	}


}
