package com.examstack.management.service;

import com.exammanagement.common.domain.news.News;
import com.exammanagement.common.util.Page;
import com.examstack.management.persistence.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> getNewsList(Page<News> page) {
        // TODO Auto-generated method stub
        return newsMapper.getNewsList(page);
    }

    @Override
    public News getNewsById(int newsId) {
        // TODO Auto-generated method stub
        return newsMapper.getNewsById(newsId);
    }

    @Override
    public void addNews(News news) {
        // TODO Auto-generated method stub
        newsMapper.addNews(news);
    }

}
