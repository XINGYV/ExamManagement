package com.examstack.management.persistence;

import com.exammanagement.common.domain.training.Training;
import com.exammanagement.common.domain.training.TrainingSection;
import com.exammanagement.common.domain.training.TrainingSectionProcess;
import com.exammanagement.common.domain.training.UserTraining;
import com.exammanagement.common.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TrainingMapper {

    /**
     * 获取培训列表
     *
     * @param userId 为-1时，获取所有培训，否则获取userId发布的培训
     * @param page
     * @return
     */
    public List<Training> getTrainingList(@Param("userId") int userId, @Param("page") Page<Training> page);

    /**
     * 添加培训
     *
     * @param training
     */
    public void addTraining(Training training);

    /**
     * 添加章节
     *
     * @param section
     */
    public void addTrainingSection(TrainingSection section);

    /**
     * 获取培训章节
     *
     * @param trainingId
     * @param userId     不等于-1则为该用户发布的培训
     * @param page
     * @return
     */
    public List<TrainingSection> getTrainingSectionByTrainingId(@Param("trainingId") int trainingId, @Param("userId") int userId, @Param("page") Page<TrainingSection> page);

    /**
     * 获取培训章节
     *
     * @param sectionId
     * @param userId    不等于-1则为该用户发布的培训
     * @param page
     * @return
     */
    public List<TrainingSection> getTrainingSectionById(@Param("sectionId") int sectionId, @Param("userId") int userId, @Param("page") Page<TrainingSection> page);

    /**
     * @param sectionId
     * @param userId    不等于-1则为该用户发布的培训
     */
    public void deleteTrainingSection(@Param("sectionId") int sectionId, @Param("userId") int userId);

    /**
     * 获取培训id下每个用户的章节培训进度
     *
     * @param trainingId
     * @param idList     学员id
     * @param searchStr
     * @param page
     * @return
     */
    public List<TrainingSectionProcess> getTrainingSectionProcessListByTrainingId(@Param("trainingId") int trainingId, @Param("array") List<Integer> idList, @Param("searchStr") String searchStr, @Param("page") Page<TrainingSectionProcess> page);

    /**
     * 获取培训id下用户培训清单
     *
     * @param trainingId
     * @param userId
     * @param searchStr
     * @param page
     * @return
     */
    public List<UserTraining> getUserTrainingList(@Param("trainingId") int trainingId, @Param("userId") int userId, @Param("searchStr") String searchStr, @Param("page") Page<UserTraining> page);
}
