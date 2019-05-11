package com.efo.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Revenues;
import com.efo.interfaces.IRevenues;

@Transactional
@Repository
public class RevenuesDao implements IRevenues {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(revenues);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Revenues retrieve(Long reference) {
		Session session = session();
		Revenues revenues = (Revenues) session.createCriteria(Revenues.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		
		return revenues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Revenues> retrieveRawList() {
		Session session = session();
		List<Revenues> revList = session.createCriteria(Revenues.class).list();
		session.disconnect();
		
		return revList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> listCashRevenue(Date begin, Date end) {
		String hql = "SELECT YEAR(r.received), MONTH(r.received), SUM(r.amount) FROM revenues r " +
					 "WHERE received BETWEEN :begin AND :end AND " + 
					 "reference NOT IN(SELECT NULL FROM revenue_terms t WHERE r.reference = t.reference ) " + 
					 "GROUP BY YEAR(r.received), MONTH(r.received)";
		Session session = session();
		
		List<Object[]> result = session.createSQLQuery(hql).setDate("begin", begin).setDate("end", end).list();
		session.disconnect();
		
		return result;
	}

	@Override
	public void merge(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(revenues);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(revenues);
		tx.commit();
		session.disconnect();
	}

}
