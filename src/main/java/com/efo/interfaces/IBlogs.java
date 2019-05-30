package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Blogs;

public interface IBlogs {
	public void create(Blogs blogs);
	public Blogs retreive(Long id);
	public List<Blogs> retreiveRawList();
	public void update(Blogs blogs);
	public void delete(Blogs blogs);
	

}
