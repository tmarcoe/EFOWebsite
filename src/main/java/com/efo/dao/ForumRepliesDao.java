package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.ForumReplies;
import com.efo.interfaces.IForumReplies;

@Transactional
@Repository
public class ForumRepliesDao implements IForumReplies {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(ForumReplies forumReplies) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(forumReplies);
		tx.commit();
		session.disconnect();
	}

	@Override
	public ForumReplies retrieve(Long id) {
		Session session = session();
		ForumReplies fr = (ForumReplies) session.createCriteria(ForumReplies.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return fr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ForumReplies> retrieveRawList(Long reference) {
		Session session = session();
		List<ForumReplies> repliesList = session.createCriteria(ForumReplies.class).add(Restrictions.eq("reference", reference)).list();
		session.disconnect();
		
		return repliesList;
	}

	@Override
	public void update(ForumReplies forumReplies) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(forumReplies);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(ForumReplies forumReplies) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(forumReplies);
		tx.commit();
		session.disconnect();
	}

	public void delete(Long id) {
		String hql = "DELETE FROM ForumReplies WHERE id = :id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setLong("id", id).executeUpdate();
		tx.commit();
		session.disconnect();
	}

}
