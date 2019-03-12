package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.TransactionProfiles;
import com.efo.interfaces.ITransactionProfiles;

@Transactional
@Repository
public class TransactionProfilesDao implements ITransactionProfiles {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(TransactionProfiles profiles) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(profiles);
		tx.commit();
		session.disconnect();
	}

	@Override
	public TransactionProfiles retrieve(String name) {
		Session session = session();
		TransactionProfiles profiles = (TransactionProfiles) session.createCriteria(TransactionProfiles.class)
																	.add(Restrictions.idEq(name)).uniqueResult();
		session.disconnect();
		
		return profiles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionProfiles> retrieveRawList() {
		Session session = session();
		List<TransactionProfiles> transList = session.createCriteria(TransactionProfiles.class).list();
		
		return transList;
	}

	@Override
	public void update(TransactionProfiles profiles) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(profiles);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(TransactionProfiles profiles) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(profiles);
		tx.commit();
		session.disconnect();
	}

}
