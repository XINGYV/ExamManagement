package com.examstack.portal.service;

import com.exammanagement.common.domain.question.Comment;
import com.exammanagement.common.util.Page;

import java.util.List;


public interface CommentService {

    public List<Comment> getCommentByTypeAndReferId(int referType, int referId, int indexId, Page<Comment> page);

    public void addComment(Comment comment);
}
