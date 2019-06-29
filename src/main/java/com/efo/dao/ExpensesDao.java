package com.efo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@SuppressWarnings("unchecked")
	public Map<String, Object[]> sumMonthlyExpenses(Date begin, Date end) {
		String headingPattern = "yyyy-MM";
		SimpleDateFormat df = new SimpleDateFormat(headingPattern);

		Map<String, Object[]> expMap = new HashMap<String, Object[]>();
		Session session = session();
		String hql = "SELECT e.paid, SUM(e.amount), SUM(p.payment_made) FROM Expenses e, Expense_Payments p "
				   + "WHERE e.paid BETWEEN :begin AND :end AND MONTH(e.paid) = MONTH(p.payment_date) "
				   + "AND YEAR(e.paid) = YEAR(p.payment_date)";
		
		List<Object[]> exp = session.createSQLQuery(hql).setDate("begin", end).setDate("end", end).list();
		Object[] obj = new Object[2];
		
		for (Object[] item : exp) {
			obj[0] = item[1];
			obj[1] = item[2];
			
			expMap.put(df.format((Date) item[0]), obj);
		}
		session.close();
		
		return expMap;
	}

}
