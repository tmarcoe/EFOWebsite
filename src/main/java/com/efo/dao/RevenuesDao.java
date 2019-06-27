package com.efo.dao;

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
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(revenues);
		tx.commit();
		session.close();
	}

	@Override
	public Revenues retrieve(Long reference) {
		Session session = session();
		Revenues revenues = (Revenues) session.createCriteria(Revenues.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return revenues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Revenues> retrieveRawList() {
		Session session = session();
		List<Revenues> revList = session.createCriteria(Revenues.class).list();
		session.close();
		
		return revList;
	}
	

	public Double sumCashRevenue(int month, int year) {
		String hql = "SELECT SUM(r.amount) FROM revenues r WHERE MONTH(received) = :month AND YEAR(received) = :year";
		Session session = session();
		
		Double result = (Double) session.createSQLQuery(hql).setInteger("month", month).setInteger("year", year).uniqueResult();
		if (result == null) result = 0.0;
		session.close();
		
		return result;
	}

	@Override
	public void merge(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(revenues);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(revenues);
		tx.commit();
		session.close();
	}

}
