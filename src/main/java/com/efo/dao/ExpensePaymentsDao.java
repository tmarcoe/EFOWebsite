package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.ExpensePayments;
import com.efo.interfaces.IExpensePayments;

@Transactional
@Repository
public class ExpensePaymentsDao implements IExpensePayments {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(ExpensePayments expensePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(expensePayments);
		tx.commit();
		session.disconnect();
	}

	@Override
	public ExpensePayments retrieve(Long id) {
		Session session = session();
		ExpensePayments expensePayments = (ExpensePayments) session.createCriteria(ExpensePayments.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return expensePayments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpensePayments> retrieveRawList(Long reference) {
		Session session = session();
		List<ExpensePayments> expList = session.createCriteria(ExpensePayments.class).add(Restrictions.eq("reference", reference)).list();
		session.disconnect();
		
		return expList;
	}

	@Override
	public void merge(ExpensePayments expensePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(expensePayments);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(ExpensePayments expensePayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(expensePayments);
		tx.commit();
		session.disconnect();
	}

}
