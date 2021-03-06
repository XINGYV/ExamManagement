package com.examstack.management.service;

import com.exammanagement.common.domain.exam.ExamPaper;
import com.exammanagement.common.domain.exam.Paper;
import com.exammanagement.common.domain.question.QuestionContent;
import com.exammanagement.common.domain.question.QuestionQueryResult;
import com.exammanagement.common.domain.question.QuestionStruts;
import com.exammanagement.common.util.CustomXWPFDocument;
import com.exammanagement.common.util.Page;
import com.examstack.management.persistence.ExamPaperMapper;
import com.examstack.management.persistence.QuestionMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Service("examPaperService")
public class ExamPaperServiceImpl implements ExamPaperService {

    @Autowired
    private ExamPaperMapper examPaperMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;

    @Override
    public List<ExamPaper> getExamPaperList(String searchStr, String paperType, Page<ExamPaper> page) {
        // TODO Auto-generated method stub
        return examPaperMapper.getExamPaperList(searchStr, paperType, page);
    }

    @Override
    public void insertExamPaper(ExamPaper examPaper) {
        // TODO Auto-generated method stub
        examPaperMapper.insertExamPaper(examPaper);
    }

    @Override
    @Transactional
    public void createExamPaper(HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap,
                                HashMap<Integer, Integer> questionTypeNum, HashMap<Integer, Float> questionTypePoint,
                                HashMap<Integer, Float> knowledgePointRate, ExamPaper examPaper) {
        // TODO Auto-generated method stub

        HashMap<Integer, String> knowledgeMap = (HashMap<Integer, String>) questionService.getKnowledgePointMap(0);
        HashMap<Integer, String> typeMap = (HashMap<Integer, String>) questionService.getQuestionTypeMap();
        Paper paper = new Paper(questionMap, questionTypeNum, questionTypePoint, knowledgePointRate, knowledgeMap, typeMap);
        try {
            paper.createPaper();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e1.getMessage());
        }

        try {
            HashMap<Integer, QuestionStruts> paperQuestionMap = paper.getPaperQuestionMap();

            Iterator<Integer> it = paperQuestionMap.keySet().iterator();
            List<Integer> idList = new ArrayList<Integer>();
            while (it.hasNext()) {
                idList.add(it.next());
            }
            List<QuestionQueryResult> questionList = questionMapper.getQuestionAnalysisListByIdList(idList);
            for (QuestionQueryResult q : questionList) {
                q.setQuestionPoint(questionTypePoint.get(q.getQuestionTypeId()));
            }
            Gson gson = new Gson();
            examPaper.setContent(gson.toJson(questionList));
            examPaperMapper.insertExamPaper(examPaper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public ExamPaper getExamPaperById(int examPaperId) {
        // TODO Auto-generated method stub
        return examPaperMapper.getExamPaperById(examPaperId);
    }

    @Override
    public void updateExamPaper(ExamPaper examPaper) {
        // TODO Auto-generated method stub
        examPaperMapper.updateExamPaper(examPaper);
    }

    @Override
    public void deleteExamPaper(int id) {
        // TODO Auto-generated method stub
        examPaperMapper.deleteExamPaper(id);
    }

    @Override
    public List<ExamPaper> getEnabledExamPaperList(String userName, Page<ExamPaper> page) {
        // TODO Auto-generated method stub
        return examPaperMapper.getEnabledExamPaperList(userName, page);
    }

    @Override
    public void generateDoc(ExamPaper examPaper, String path) throws Exception {
        // TODO Auto-generated method stub
        String basePath = System.getProperty("catalina.base") + ",webapps,";
        //String filePath = basePath + "Management,resources,template,doc_tmp.docx";
        String filePath = "C:/Users/XINGYU/Desktop/ExamManagement/management/src/main/webapp/resources/template/doc_tmp.docx";
        filePath = filePath.replace(',', File.separatorChar);
        System.out.println(1);
        OPCPackage pack = POIXMLDocument.openPackage(filePath);
        System.out.println(2);
        CustomXWPFDocument doc = new CustomXWPFDocument(pack);
        Gson gson = new Gson();
        String content = examPaper.getContent();
        List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>() {
        }.getType());
        // 设置标题
        XWPFParagraph p1 = doc.createParagraph();
        // 设置字体对齐方式
        p1.setAlignment(ParagraphAlignment.CENTER);
        p1.setVerticalAlignment(TextAlignment.TOP);
        // 第一页要使用p1所定义的属性
        XWPFRun r1 = p1.createRun();
        // 设置字体是否加粗
        r1.setBold(true);
        r1.setFontSize(20);
        // 设置使用何种字体
        r1.setFontFamily("Courier");
        // 设置上下两行之间的间距
        r1.setTextPosition(40);
        r1.setText(examPaper.getName());
        int i = 0;
        for (QuestionQueryResult question : questionList) {
            QuestionContent questionContent = gson.fromJson(question.getContent(), QuestionContent.class);
            // 设置试题标题
            XWPFParagraph t = doc.createParagraph();
            // 设置字体对齐方式
            t.setAlignment(ParagraphAlignment.LEFT);
            t.setVerticalAlignment(TextAlignment.TOP);
            i++;
            XWPFRun rt = t.createRun();
            // 设置字体是否加粗
            rt.setBold(false);
            rt.setFontSize(15);
            // 设置使用何种字体
            rt.setFontFamily("Courier");
            // 设置上下两行之间的间距
            rt.setTextPosition(40);
            rt.setText(i + "、" + questionContent.getTitle() + "(" + question.getQuestionPoint() + "分)");
            if (!"".equals(questionContent.getTitleImg()) && questionContent.getTitleImg() != null) {
                String titlePicPath = basePath.replace(',', File.separatorChar);
                File titlePic = new File(titlePicPath + questionContent.getTitleImg());
                BufferedImage sourceImg = ImageIO.read(new FileInputStream(titlePic));

                String ind = doc.addPictureData(new FileInputStream(titlePic),
                        XWPFDocument.PICTURE_TYPE_JPEG);
                doc.createPicture(doc.getAllPictures().size() - 1,
                        sourceImg.getWidth() / 2, sourceImg.getHeight() / 2);
                sourceImg.flush();
            }
            XWPFParagraph crt = doc.createParagraph();
            XWPFRun cr = crt.createRun();
            cr.setText("");
            //选择题和判断题增加选项
            if (question.getQuestionTypeId() == 1 || question.getQuestionTypeId() == 2 || question.getQuestionTypeId() == 3) {
                for (Map.Entry<String, String> entry : questionContent.getChoiceList().entrySet()) {
                    // 设置试题标题
                    XWPFParagraph c = doc.createParagraph();
                    // 设置字体对齐方式
                    c.setAlignment(ParagraphAlignment.LEFT);
                    c.setVerticalAlignment(TextAlignment.TOP);

                    XWPFRun rc = c.createRun();
                    // 设置字体是否加粗
                    rc.setBold(false);
                    rc.setFontSize(15);
                    // 设置使用何种字体
                    rc.setFontFamily("Courier");
                    // 设置上下两行之间的间距
                    rc.setTextPosition(40);
                    rc.setText(entry.getKey() + " " + entry.getValue());

                    if (questionContent.getChoiceImgList().containsKey(entry.getKey())) {
                        String picPath = basePath.replace(',', File.separatorChar) + questionContent.getChoiceImgList().get(entry.getKey());
                        System.out.println(picPath);
                        File picture = new File(picPath);
                        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));

                        String ind = doc.addPictureData(new FileInputStream(picture),
                                XWPFDocument.PICTURE_TYPE_JPEG);
                        doc.createPicture(doc.getAllPictures().size() - 1,
                                sourceImg.getWidth() / 2, sourceImg.getHeight() / 2);
                        sourceImg.flush();
                    }
                    XWPFParagraph crta = doc.createParagraph();
                    XWPFRun cra = crta.createRun();
                    cra.setText("");
                }
            }


        }

        FileOutputStream out;
        try {
            //导出的路径
            String path1 = "C:/Users/XINGYU/Desktop";
            File f = new File(path1);
            if (!f.exists())
                f.mkdirs();
            System.out.println(path1);
            System.out.println(5);
            System.out.println(File.separatorChar + examPaper.getName() + ".docx");
            out = new FileOutputStream(path1 + File.separatorChar + examPaper.getName() + ".docx");
            doc.write(out);
            out.close();
            System.out.println("success");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
