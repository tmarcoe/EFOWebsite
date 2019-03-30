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

import com.efo.entity.Transactions;
import com.efo.interfaces.ITransactions;

@Transactional
@Repository
public class TransactionsDao implements ITransactions {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Transactions transactions) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(transactions);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Transactions retrieve(Long reference) {
		Session session = session();
		Transactions transactions = (Transactions) session.createCriteria(Transactions.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		
		return transactions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transactions> retrieveRawList(Date begin, Date end) {
		String hql = "From Transactions WHERE DATE(timestamp) BETWEEN DATE(:begin) AND DATE(:end)";
		Session session = session();
		List<Transactions> transList = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		session.disconnect();
		
		return transList;
	}

	@Override
	public void update(Transactions transactions) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(transactions);
		tx.commit();
		session.disconnect();

	}

	@Override
	public void delete(Transactions transactions) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(transactions);
		tx.commit();
		session.disconnect();

	}

}
