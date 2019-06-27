package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.LoanPayments;
import com.efo.interfaces.ILoanPayments;

@Transactional
@Repository
public class LoanPaymentsDao implements ILoanPayments {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(LoanPayments loanPayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(loanPayments);
		tx.commit();
		session.close();
	}

	@Override
	public LoanPayments retreive(Long id) {
		Session session = session();
		LoanPayments loanPayments = (LoanPayments) session.createCriteria(LoanPayments.class)
														  .add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return loanPayments;
	}

	@Override
	public List<LoanPayments> retreiveRawList(Long reference) {
		Session session = session();
		@SuppressWarnings("unchecked")
		List<LoanPayments> payList = session.createCriteria(LoanPayments.class)
											.add(Restrictions.eq("reference", reference)).list();
		session.close();
		
		return payList;
	}

	@Override
	public void update(LoanPayments loanPayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(loanPayments);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(LoanPayments loanPayments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(loanPayments);
		tx.commit();
		session.close();
	}
	
	public Double sumMontlyPayments(int month, int year) {
		Session session = session();
		String hql = "SELECT SUM(payment_made) FROM LoanPayments WHERE MONTH(payment_date) = :month AND YEAR(payment_date) = :year";
		
		Double sum = (Double) session.createQuery(hql).setInteger("month", month).setInteger("year", year).uniqueResult();
		
		if (sum == null) sum = 0.0;
		
		return sum;
	}

}
