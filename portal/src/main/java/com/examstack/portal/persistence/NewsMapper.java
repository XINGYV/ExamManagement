package com.examstack.portal.persistence;

import java.util.List;

import com.exammanagement.common.domain.news.News;
import com.exammanagement.common.util.Page;

public interface NewsMapper {

	public List<News> getNewsList(Page<News> page);
	
	public News getNewsById(int newsId);
}
