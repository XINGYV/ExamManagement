package com.examstack.portal.persistence;

import com.exammanagement.common.domain.question.Comment;
import com.exammanagement.common.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author Ocelot
 * @date 2014年6月8日 下午8:32:33
 */
public interface CommentMapper {

    List<Comment> getCommentByTypeAndReferId(@Param("commentType") int commentType, @Param("referId") int referId, @Param("indexId") int indexId,
                                             @Param("page") Page<Comment> page);

    /**
     * 添加评论
     *
     * @param comment
     */
    public void addComment(Comment comment);

    public Integer getMaxCommentIndexByTypeAndReferId(@Param("commentType") int commentType, @Param("referId") int referId);
}
