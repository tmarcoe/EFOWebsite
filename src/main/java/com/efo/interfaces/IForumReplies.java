package com.efo.interfaces;

import java.util.List;

import com.efo.entity.ForumReplies;

public interface IForumReplies {
	
	public void create(ForumReplies forumReplies);
	public ForumReplies retrieve(Long id);
	public List<ForumReplies> retrieveRawList(Long reference);
	public void update(ForumReplies forumReplies);
	public void delete(ForumReplies forumReplies);

}
