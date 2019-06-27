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
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(MarketPlaceProducts marketPlaceProducts) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(marketPlaceProducts);
		tx.commit();
		session.close();
	}

	@Override
	public MarketPlaceProducts retrieve(Long product_reference) {
		Session session = session();
		MarketPlaceProducts mProducts = (MarketPlaceProducts) session.createCriteria(MarketPlaceProducts.class)
				.add(Restrictions.idEq(product_reference)).uniqueResult();
		session.close();
		
		return mProducts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketPlaceProducts> retrieveRawList() {
		Session session = session();
		List<MarketPlaceProducts> mList = session.createCriteria(MarketPlaceProducts.class).list();
		
		session.close();
		return mList;
	}
	

	@SuppressWarnings("unchecked")
	public List<MarketPlaceProducts> keywordSearch(String search) {
		search = "%" + search + "%";
		Session session = session();
		String hql = "FROM MarketPlaceProducts WHERE UPPER(CONCAT(product_name, ' ', keywords)) LIKE :search";
		List<MarketPlaceProducts> mList = session.createQuery(hql).setString("search", search.toUpperCase()).list();
		
		session.close();
		
		return mList;
	}

	@Override
	public void merge(MarketPlaceProducts marketPlaceProducts) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(marketPlaceProducts);
		tx.commit();
		session.close();
	}

	@Override
	public void delete(Long product_reference) {
		String hql = "DELETE FROM MarketPlaceProducts WHERE product_reference = :product_reference";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setLong("product_reference", product_reference).executeUpdate();
		
		tx.commit();
		session.close();
	}

}
