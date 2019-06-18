package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Products;
import com.efo.interfaces.IProducts;

@Transactional
@Repository
public class ProductsDao implements IProducts {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void create(Products products) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(products);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Products retrieve(String product_id) {
		Session session = session();
		Products products = (Products) session.createCriteria(Products.class).add(Restrictions.idEq(product_id)).uniqueResult();
		session.disconnect();
		
		return products;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Products> retrieveRawList() {
		Session session = session();
		List<Products> pList = session.createCriteria(Products.class).list();
		session.disconnect();
		
		return pList;
	}

	@Override
	public void update(Products products) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(products);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(String product_id) {
		String hql = "DELETE FROM Products WHERE product_id = :product_id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setString("product_id", product_id).executeUpdate();
		tx.commit();
		session.disconnect();
	}
	
}
