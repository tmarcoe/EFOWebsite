package com.efo.dao;

import javax.transaction.Transactional;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.InvoiceNum;
import com.efo.interfaces.IInvoiceNum;

@Transactional
@Repository
public class InvoiceNumDao implements IInvoiceNum {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(InvoiceNum invoice_num) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(invoice_num);
		tx.commit();
		session.close();
	}

	@Override
	public InvoiceNum retrieve(String invoice_num) {
		Session session = session();
		InvoiceNum inv = (InvoiceNum) session.createCriteria(InvoiceNum.class).add(Restrictions.idEq(invoice_num)).uniqueResult();
		session.close();
		
		return inv;
	}

	@Override
	public void update(InvoiceNum invoice_num) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(invoice_num);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(InvoiceNum invoice_num) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(invoice_num);
		tx.commit();
		session.close();
	}
	
	public String getNextKey() {
		LocalDate today = new LocalDate();
		String keySearch = String.format("%04d%03d%%", today.getYear(), today.getDayOfYear());
		Long keySeq = 0L;
		Session session = session();
		Transaction tx = session.beginTransaction();
		String hql = "FROM InvoiceNum inv WHERE invoice_num LIKE :keySearch ORDER BY invoice_num DESC";
		InvoiceNum inv = (InvoiceNum) session.createQuery(hql).setLockMode("inv", LockMode.PESSIMISTIC_WRITE)
											 .setString("keySearch", keySearch).setMaxResults(1).uniqueResult();
		if (inv == null) {
			keySeq = 1L;
		}else{
			keySeq = Long.valueOf(inv.getInvoice_num().substring(7));
			keySeq++;
		}
		String key = String.format("%04d%03d%05d", today.getYear(), today.getDayOfYear(), keySeq);
		
		InvoiceNum inventory_num = new InvoiceNum(key);
		
		session.save(inventory_num);
		
		tx.commit();
		
		session.close();
		
		return key;
	}

}
