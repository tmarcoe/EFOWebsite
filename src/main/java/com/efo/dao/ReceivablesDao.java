package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Receivables;
import com.efo.interfaces.IReceivables;

@Transactional
@Repository
public class ReceivablesDao implements IReceivables {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Receivables receivables) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(receivables);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Receivables retreive(Long reference) {
		Session session = session();
		Receivables r = (Receivables) session.createCriteria(Receivables.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		return r;
	}
	
	public List<Receivables> retreiveList() {
		Session session = session();
		@SuppressWarnings("unchecked")
		List<Receivables> rList = session.createCriteria(Receivables.class)
										 .add(Restrictions.ne("status", "C"))
										 .list();
		return rList;
	}

	@Override
	public void update(Receivables receivables) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(receivables);
		tx.commit();
		session.disconnect();
	}
	
	
	@Override
	public void delete(Long reference) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		Receivables r = retreive(reference);
		session.delete(r);
		tx.commit();
		session.disconnect();
	}
	
}
