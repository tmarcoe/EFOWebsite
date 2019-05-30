package com.efo.interfaces;

import java.util.List;

import com.efo.entity.Forum;

public interface IForum {
	
	public void create(Forum forum);
	public Forum retrieve(Long reference);
	public List<Forum> retrieveRawList();
	public void merge(Forum forum);
	public void delete(Forum forum);

}
