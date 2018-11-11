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

import com.efo.entity.PaymentsBilled;
import com.efo.interfaces.IPaymentsBilled;

@Transactional
@Repository
public class PaymentsBilledDao implements IPaymentsBilled {

	@Autowired
	SessionFactory sessionFactory;

	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void create(PaymentsBilled payments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(payments);
		tx.commit();
		session.disconnect();
	}

	@Override
	public PaymentsBilled retreive(Long id) {
		Session session = session();
		PaymentsBilled p = (PaymentsBilled) session.createCriteria(PaymentsBilled.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		return p;
	}

	@Override
	public void update(PaymentsBilled payments) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(payments);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Long id) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		PaymentsBilled p = retreive(id);
		session.delete(p);
		tx.commit();
		session.disconnect();
	}

	public List<PaymentsBilled> retreiveList(Long reference) {
		Session session = session();

		@SuppressWarnings("unchecked")
		List<PaymentsBilled> pList = session.createCriteria(PaymentsBilled.class).add(Restrictions.eq("reference", reference)).list();

		session.disconnect();
		return pList;
	}

	public Date lastestDate(Long reference) {
		Date maxDate = null;
		if (reference != null) {
			String hql = "SELECT MAX(date_due) FROM PaymentsBilled WHERE reference = :reference)";
			Session session = session();
			maxDate = (Date) session.createQuery(hql).setLong("reference", reference).uniqueResult();
			session.disconnect();
		}

		return maxDate;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> totalPayentsByPeriod(Date from, Date to) {
		String hql = "SELECT MONTH(payment_date), SUM(payment) FROM PaymentsBilled "
				   + "WHERE DATE(payment_date) BETWEEN DATE(:from) AND DATE(:to) GROUP BY MONTH(payment_date)";
		Session session = session();
		List<Object[]> overheadList = session.createQuery(hql).setDate("from", from).setDate("to", to).list();
		session.disconnect();
		
		return overheadList;
	}

	
}
