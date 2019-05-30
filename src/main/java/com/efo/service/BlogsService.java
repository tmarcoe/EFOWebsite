package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.BlogsDao;
import com.efo.entity.Blogs;
import com.efo.interfaces.IBlogs;

@Service
public class BlogsService implements IBlogs {
	
	@Autowired
	private BlogsDao blogsDao;

	@Override
	public void create(Blogs blogs) {
		blogsDao.create(blogs);
	}

	@Override
	public Blogs retreive(Long id) {
		return blogsDao.retreive(id);
	}

	@Override
	public List<Blogs> retreiveRawList() {
		return blogsDao.retreiveRawList();
	}
	
	public PagedListHolder<Blogs> retreiveList() {
		
		return new PagedListHolder<Blogs>(blogsDao.retreiveRawList());
	}

	@Override
	public void update(Blogs blogs) {
		blogsDao.update(blogs);
	}

	@Override
	public void delete(Blogs blogs) {
		blogsDao.delete(blogs);
	}
	
	public void delete(Long id) {
		blogsDao.delete(id);
	}

}
