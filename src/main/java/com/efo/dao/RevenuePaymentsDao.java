package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.RevenuePayments;
import com.efo.interfaces.IRevenuePayments;

@Transactional
@Repository
public class RevenuePaymentsDao implements IRevenuePayments {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(RevenuePayments revenuePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(revenuePayments);
		tx.commit();
		session.disconnect();
	}

	@Override
	public RevenuePayments retrieve(Long id) {
		Session session = session();
		RevenuePayments revenuePayments = (RevenuePayments) session.createCriteria(RevenuePayments.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return revenuePayments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RevenuePayments> retrieveRawList(Long reference) {
		Session session = session();
		List<RevenuePayments> revList = session.createCriteria(RevenuePayments.class).add(Restrictions.eq("reference", reference)).list();
		session.disconnect();
		
		return revList;
	}

	@Override
	public void merge(RevenuePayments revenuePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(revenuePayments);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(RevenuePayments revenuePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(revenuePayments);
		tx.commit();
		session.disconnect();
	}

}
