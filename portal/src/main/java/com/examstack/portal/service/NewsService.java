package com.examstack.portal.service;

import com.exammanagement.common.domain.news.News;
import com.exammanagement.common.util.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {

    public List<News> getNewsList(Page<News> page);

    public News getNewsById(int newsId);

}
