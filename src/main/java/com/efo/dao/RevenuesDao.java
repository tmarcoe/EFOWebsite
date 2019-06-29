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
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Revenues;
import com.efo.interfaces.IRevenues;

@Transactional
@Repository
public class RevenuesDao implements IRevenues {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(revenues);
		tx.commit();
		session.close();
	}

	@Override
	public Revenues retrieve(Long reference) {
		Session session = session();
		Revenues revenues = (Revenues) session.createCriteria(Revenues.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return revenues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Revenues> retrieveRawList() {
		Session session = session();
		List<Revenues> revList = session.createCriteria(Revenues.class).list();
		session.close();
		
		return revList;
	}
	

	@SuppressWarnings("unchecked")
	public Map<String, Object[]> sumCashRevenue(Date begin, Date end) {
		LocalDate jBegin = new LocalDate(begin);
		LocalDate jEnd = new LocalDate(end);
		String headingPattern = "yyyy-MM";
		SimpleDateFormat df = new SimpleDateFormat(headingPattern);
		Map<String, Object[]> revMap = new HashMap<String, Object[]>();
		String cash = "SELECT received, SUM(r.amount) FROM Revenue WHERE ";
		Session session = session();
		while (jBegin.isBefore(jEnd)) {
			List<Object[]> revenues = session.createQuery(cash).setDate("begin", begin).setDate("end", end).list();
			Object[] obj = new Object[2];
			for (Object[] item : revenues) {
				obj[0] = item[1];
				obj[1] = item[2];

				revMap.put(df.format((Date) item[0]), obj);
			}
		}

		session.close();
		
		return revMap;
	}

	@Override
	public void merge(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(revenues);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Revenues revenues) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(revenues);
		tx.commit();
		session.close();
	}

}
