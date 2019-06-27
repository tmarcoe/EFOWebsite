package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.MarketPlaceSales;
import com.efo.interfaces.IMarketPlaceSales;

@Transactional
@Repository
public class MarketPlaceSalesDao implements IMarketPlaceSales {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(MarketPlaceSales marketPlaceSales) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(marketPlaceSales);
		tx.commit();
		session.close();
	}

	@Override
	public MarketPlaceSales retrieve(Long id) {
		Session session = session();
		MarketPlaceSales mSales = (MarketPlaceSales) session.createCriteria(MarketPlaceSales.class).add(Restrictions.idEq(id)).uniqueResult();
		session.close();
		
		return mSales;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketPlaceSales> retrieveRawList(Long product_reference) {
		Session session = session();
		List<MarketPlaceSales> mList = session.createCriteria(MarketPlaceSales.class)
				.add(Restrictions.eq("product_reference", product_reference)).list();
		session.close();
		
		return mList;
	}

	@Override
	public void update(MarketPlaceSales marketPlaceSales) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(marketPlaceSales);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(MarketPlaceSales marketPlaceSales) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(marketPlaceSales);
		tx.commit();
		session.close();
	}

}
