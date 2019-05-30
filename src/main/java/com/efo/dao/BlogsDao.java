package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.efo.entity.Blogs;
import com.efo.interfaces.IBlogs;

@Transactional
@Repository
public class BlogsDao implements IBlogs {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Blogs blogs) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(blogs);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Blogs retreive(Long id) {
		Session session = session();
		Blogs blogs = (Blogs) session.createCriteria(Blogs.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return blogs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Blogs> retreiveRawList() {
		Session session = session();
		List<Blogs> blogList = session.createCriteria(Blogs.class).addOrder(Order.desc("timestamp")).setMaxResults(10).list();
		session.disconnect();
		
		return blogList;
	}

	@Override
	public void update(Blogs blogs) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(blogs);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Blogs blogs) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(blogs);
		tx.commit();
		session.disconnect();
	}
	
	public void delete(Long id) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		String hql = "DELETE FROM Blogs WHERE id= :id";
		session.createQuery(hql).setLong("id", id).executeUpdate();
		tx.commit();
		session.disconnect();
	}

}
