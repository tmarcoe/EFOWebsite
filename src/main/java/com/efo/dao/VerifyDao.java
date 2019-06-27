package com.efo.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Verify;
import com.efo.interfaces.IVerify;

@Transactional
@Repository
public class VerifyDao implements IVerify {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void Create(Verify verify) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(verify);
		tx.commit();
		session.close();
	}

	@Override
	public Verify retrieve(String id) {
		Session session = session();
		Verify verify = (Verify) session.createCriteria(Verify.class).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return verify;
	}

	@Override
	public void delete(Verify verify) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		String hql = "DELETE FROM Verify WHERE id = :id";
		session.createQuery(hql).setString("id", verify.getId()).executeUpdate();
		tx.commit();
		session.close();
	}

}
