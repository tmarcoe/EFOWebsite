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
	
	@SuppressWarnings("unchecked")
	public Map<String, Double> sumMontlyPayments(Date begin, Date end) {
		String headingPattern = "yyyy-MM";
		SimpleDateFormat df = new SimpleDateFormat(headingPattern);

		Map<String, Double> loanMap = new HashMap<String, Double>();
		Session session = session();
		String hql = "SELECT payment_date, SUM(payment_made) FROM LoanPayments "
				   + "WHERE payment_date BETWEEN :begin AND :end";

		List<Object[]> items = session.createQuery(hql).setDate("begin", begin).setDate("end", end).list();
		for(Object[] item : items) {
			loanMap.put(df.format((Date) item[0]), (Double) item[1]); 
		}

		return loanMap;
	}

}
