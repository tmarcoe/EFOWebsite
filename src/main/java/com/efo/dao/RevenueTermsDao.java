package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.RevenueTerms;
import com.efo.interfaces.IRevenueTerms;

@Transactional
@Repository
public class RevenueTermsDao implements IRevenueTerms {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(RevenueTerms revenueTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(revenueTerms);
		tx.commit();
		session.disconnect();
	}

	@Override
	public RevenueTerms retrieve(Long reference) {
		Session session = session();
		RevenueTerms revenueTerms = (RevenueTerms) session.createCriteria(RevenueTerms.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		
		return revenueTerms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RevenueTerms> retrieveRawList() {
		Session session = session();
		List<RevenueTerms> revList = session.createCriteria(RevenueTerms.class).list();
		session.disconnect();
		
		return revList;
	}

	@Override
	public void merge(RevenueTerms revenueTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(revenueTerms);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(RevenueTerms revenueTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(revenueTerms);
		tx.commit();
		session.disconnect();
	}

}
