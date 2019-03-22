package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.ExpenseTerms;
import com.efo.interfaces.IExpenseTerms;

@Transactional
@Repository
public class ExpenseTermsDao implements IExpenseTerms {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(ExpenseTerms expenseTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.disconnect();
	}

	@Override
	public ExpenseTerms retrieve(Long reference) {
		Session session = session();
		ExpenseTerms revenueTerms = (ExpenseTerms) session.createCriteria(ExpenseTerms.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		
		return revenueTerms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseTerms> retrieveRawList() {
		Session session = session();
		List<ExpenseTerms> expenseList = session.createCriteria(ExpenseTerms.class).list();
		session.disconnect();
		
		return expenseList;
	}

	@Override
	public void merge(ExpenseTerms expenseTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(ExpenseTerms expenseTerms) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.disconnect();
	}

}
