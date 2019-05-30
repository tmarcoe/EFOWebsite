package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efo.dao.ForumRepliesDao;
import com.efo.entity.ForumReplies;
import com.efo.interfaces.IForumReplies;

@Service
public class ForumRepliesService implements IForumReplies {
	
	@Autowired
	private ForumRepliesDao forumRepliesDao;

	@Override
	public void create(ForumReplies forumReplies) {
		forumRepliesDao.create(forumReplies);

	}

	@Override
	public ForumReplies retrieve(Long id) {
		return forumRepliesDao.retrieve(id);
	}

	@Override
	public List<ForumReplies> retrieveRawList(Long reference) {
		return forumRepliesDao.retrieveRawList(reference);
	}

	@Override
	public void update(ForumReplies forumReplies) {
		forumRepliesDao.update(forumReplies);
	}

	@Override
	public void delete(ForumReplies forumReplies) {
		forumRepliesDao.delete(forumReplies);
	}
	
	public void delete(Long id) {
		forumRepliesDao.delete(id);
	}
}
