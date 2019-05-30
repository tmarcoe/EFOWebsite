package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.ForumDao;
import com.efo.entity.Forum;
import com.efo.interfaces.IForum;

@Service
public class ForumService implements IForum {
	
	@Autowired
	private ForumDao forumDao;

	@Override
	public void create(Forum forum) {
		forumDao.create(forum);
		
	}

	@Override
	public Forum retrieve(Long reference) {
		return forumDao.retrieve(reference);
	}

	@Override
	public List<Forum> retrieveRawList() {
		return forumDao.retrieveRawList();
	}

	@Override
	public void merge(Forum forum) {
		forumDao.merge(forum);
		
	}

	@Override
	public void delete(Forum forum) {
		forumDao.delete(forum);
		
	}
	public void deletePost(Long reference) {
		forumDao.deletePost(reference);
	}
}
