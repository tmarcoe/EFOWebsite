package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Forum;
import com.efo.interfaces.IForum;

@Transactional
@Repository
public class ForumDao implements IForum {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Forum forum) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(forum);
		tx.commit();
		session.close();
	}

	@Override
	public Forum retrieve(Long reference) {
		Session session = session();
		Forum forum = (Forum) session.createCriteria(Forum.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return forum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> retrieveRawList() {
		Session session = session();
		List<Forum> forumList = session.createCriteria(Forum.class).addOrder(Order.desc("post_created")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		
		return forumList;
	}

	@Override
	public void merge(Forum forum) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(forum);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Forum forum) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(forum);
		tx.commit();
		session.close();
	}
	
	public void deletePost(Long reference) {
		String replies = "DELETE FROM ForumReplies WHERE reference = :reference";
		String post = "DELETE FROM Forum WHERE reference = :reference";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(replies).setLong("reference", reference).executeUpdate();
		session.createQuery(post).setLong("reference", reference).executeUpdate();
		tx.commit();
		session.close();
	}

}
