package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.MarketPlaceVendors;
import com.efo.interfaces.IMarketPlaceVendors;

@Transactional
@Repository
public class MarketPlaceVendorsDao implements IMarketPlaceVendors {

	@Autowired
	SessionFactory sessionFactory;

	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void create(MarketPlaceVendors marketPlaceVendors) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(marketPlaceVendors);
		tx.commit();
		session.disconnect();
	}

	@Override
	public MarketPlaceVendors retrieve(Long reference) {
		Session session = session();
		MarketPlaceVendors marketPlaceVendors = (MarketPlaceVendors) session.createCriteria(MarketPlaceVendors.class)
				.add(Restrictions.idEq(reference))
				.uniqueResult();
		session.disconnect();
		
		return marketPlaceVendors;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketPlaceVendors> retrieveRawList() {
		Session session = session();
		List<MarketPlaceVendors> mList = session.createCriteria(MarketPlaceVendors.class).list();
		session.disconnect();
		
		return mList;
	}

	@Override
	public void merge(MarketPlaceVendors marketPlaceVendors) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(marketPlaceVendors);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(MarketPlaceVendors marketPlaceVendors) {
		Session session = session();
		Transaction tx = session.beginTransaction();

		tx.commit();
		session.disconnect();
	}

}
