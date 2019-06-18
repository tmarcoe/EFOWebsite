package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.ShoppingCart;
import com.efo.interfaces.IShoppngCart;

@Transactional
@Repository
public class ShoppingCartDao implements IShoppngCart {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(ShoppingCart shoppingCart) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(shoppingCart);
		tx.commit();
		session.disconnect();
	}

	@Override
	public ShoppingCart retrieve(String reference) {
		Session session = session();
		ShoppingCart cart = (ShoppingCart) session.createCriteria(ShoppingCart.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		
		return cart;
	}

	@Override
	public ShoppingCart retrieveByUserId(Long user_id) {
		Session session = session();
		ShoppingCart cart = (ShoppingCart) session.createCriteria(ShoppingCart.class)
							.add(Restrictions.eq("user_id", user_id)).setMaxResults(1).uniqueResult();
		session.disconnect();
		
		return cart;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShoppingCart> retrieveRawList() {
		Session session = session();
		List<ShoppingCart> cList = session.createCriteria(ShoppingCart.class).list();
		session.disconnect();
		
		return cList;
	}

	@Override
	public void merge(ShoppingCart shoppingCart) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(shoppingCart);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(String reference) {
		String itemsHql = "DELETE FROM ShoppingCartItems WHERE reference = :reference";
		String cartHql = "DELETE FROM ShoppingCart WHERE reference = :reference";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(itemsHql).setString("reference", reference).executeUpdate();
		session.createQuery(cartHql).setString("reference", reference).executeUpdate();
		tx.commit();
		session.disconnect();
	}

}
