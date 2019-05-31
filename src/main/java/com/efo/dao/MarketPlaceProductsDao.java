package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.MarketPlaceProducts;
import com.efo.interfaces.IMarketPlaceProducts;

@Transactional
@Repository
public class MarketPlaceProductsDao implements IMarketPlaceProducts {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(MarketPlaceProducts marketPlaceProducts) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(marketPlaceProducts);
		tx.commit();
		session.disconnect();
	}

	@Override
	public MarketPlaceProducts retrieve(Long product_reference) {
		Session session = session();
		MarketPlaceProducts mProducts = (MarketPlaceProducts) session.createCriteria(MarketPlaceProducts.class)
				.add(Restrictions.idEq(product_reference)).uniqueResult();
		session.disconnect();
		
		return mProducts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketPlaceProducts> retrieveRawList() {
		Session session = session();
		List<MarketPlaceProducts> mList = session.createCriteria(MarketPlaceProducts.class).list();
		
		session.disconnect();
		return mList;
	}

	@Override
	public void merge(MarketPlaceProducts marketPlaceProducts) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(marketPlaceProducts);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(MarketPlaceProducts marketPlaceProducts) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		tx.commit();
		session.disconnect();
	}

}
