package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Expenses;
import com.efo.interfaces.IExpenses;

@Transactional
@Repository
public class ExpensesDao implements IExpenses {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Expenses expenses) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(expenses);
		tx.commit();
		session.close();
	}

	@Override
	public Expenses retrieve(Long reference) {
		Session session = session();
		Expenses expenses = (Expenses) session.createCriteria(Expenses.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return expenses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Expenses> retrieveRawList() {
		Session session = session();
		List<Expenses> expList = session.createCriteria(Expenses.class).list();
		session.close();
		
		return expList;
	}

	@Override
	public void merge(Expenses expenses) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(expenses);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Expenses expenses) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(expenses);
		tx.commit();
		session.close();
	}
	
	public Double sumMonthlyExpenses(int month, int year) {
		Session session = session();
		String hql = "SELECT SUM(amount) FROM Expenses WHERE MONTH(paid) = :month AND YEAR(paid) = :year";
		
		Double sum = (Double) session.createQuery(hql).setInteger("month", month).setInteger("year", year).uniqueResult();
		if (sum == null) sum = 0.0;
		
		return sum;
	}

}
