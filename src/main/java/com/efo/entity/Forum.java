package com.efo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Forum implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reference;
	private Date post_created;
	private Date post_read;
	private String title;
	private String subject;
	private String text;
	private Long author;
	private String name;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "forum", cascade = CascadeType.ALL)
	private Set<ForumReplies> forumReplies = new HashSet<ForumReplies>(0);
	
	public Forum() {
	}
	
	public Forum(Date post_created) {
		this.post_created = post_created;
	}

	public Long getReference() {
		return reference;
	}
	public void setReference(Long reference) {
		this.reference = reference;
	}
	public Date getPost_created() {
		return post_created;
	}
	public void setPost_created(Date post_created) {
		this.post_created = post_created;
	}
	public Date getPost_read() {
		return post_read;
	}
	public void setPost_read(Date post_read) {
		this.post_read = post_read;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getAuthor() {
		return author;
	}
	public void setAuthor(Long author) {
		this.author = author;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ForumReplies> getForumReplies() {
		return forumReplies;
	}
	public void setForumReplies(Set<ForumReplies> forumReplies) {
		this.forumReplies = forumReplies;
	}
}
