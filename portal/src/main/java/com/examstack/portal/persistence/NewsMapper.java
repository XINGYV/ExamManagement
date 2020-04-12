package com.examstack.portal.persistence;

import com.exammanagement.common.domain.news.News;
import com.exammanagement.common.util.Page;

import java.util.List;

public interface NewsMapper {

    public List<News> getNewsList(Page<News> page);

    public News getNewsById(int newsId);
}
