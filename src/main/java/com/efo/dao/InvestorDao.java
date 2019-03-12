package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Investor;
import com.efo.interfaces.IInvestor;

@Transactional
@Repository
public class InvestorDao implements IInvestor {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
 	@Override
	public void create(Investor investor) {
 		Session session = session();
 		Transaction tx = session.beginTransaction();
 		session.save(investor);
 		tx.commit();
 		session.disconnect();
	}

	@Override
	public Investor retrieve(Long user_id) {
 		Session session = session();
 		Investor investor = (Investor) session.createCriteria(Investor.class).add(Restrictions.idEq(user_id)).uniqueResult();
 		session.disconnect();
 		
		return investor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Investor> retrieveRawList() {
 		Session session = session();
 		List<Investor> investList = session.createCriteria(Investor.class).list();
 		session.disconnect();
 		
		return investList;
	}

	@Override
	public void update(Investor investor) {
 		Session session = session();
 		Transaction tx = session.beginTransaction();
 		session.update(investor);
 		tx.commit();
 		session.disconnect();
	}

	@Override
	public void delete(Investor investor) {
 		Session session = session();
 		Transaction tx = session.beginTransaction();
 		session.delete(investor);
 		tx.commit();
 		session.disconnect();
	}

}
