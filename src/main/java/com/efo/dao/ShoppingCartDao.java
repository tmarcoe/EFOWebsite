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
	private SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.openSession();
	}
	
	@Override
	public void create(ShoppingCart shoppingCart) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(shoppingCart);
		tx.commit();
		session.close();
	}

	@Override
	public ShoppingCart retrieve(String reference) {
		Session session = session();
		ShoppingCart cart = (ShoppingCart) session.createCriteria(ShoppingCart.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.close();
		
		return cart;
	}
	
	public Long getShoppingCartCount(String username) {
		String hql = "SELECT SUM(i.qty) FROM ShoppingCart s, ShoppingCartItems i, User u "
				   + "WHERE u.username = :username AND u.user_id = s.user_id AND "
				   + "s.time_ordered IS NOT NULL AND s.time_processed IS NULL AND "
				   + "s.reference = i.reference";
		Session session = session();
		Long result = (Long) session.createQuery(hql).setString("username", username).uniqueResult();
		session.close();
		
		return result;
	}

	@Override
	public ShoppingCart retrieveByUserId(Long user_id) {
		Session session = session();
		ShoppingCart cart = (ShoppingCart) session.createCriteria(ShoppingCart.class)
							.add(Restrictions.eq("user_id", user_id))
							.add(Restrictions.isNull("time_processed"))
							.add(Restrictions.isNotNull("time_ordered"))
							.setMaxResults(1).uniqueResult();
		session.close();
		if (cart == null) {
			cart = new ShoppingCart();
		}
		
		return cart;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShoppingCart> retrieveRawList() {
		Session session = session();
		List<ShoppingCart> cList = session.createCriteria(ShoppingCart.class).list();
		session.close();
		
		return cList;
	}
	
	public void closeCart(String reference) {
		String hql = "UPDATE ShoppingCart SET time_processed = CURDATE() WHERE reference = :reference";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setString("reference", reference).executeUpdate();
		tx.commit();
		session.close();
	}
	
	public boolean checkHistory(String prdId, Long user_id) {
		String hql = "SELECT COUNT(*) FROM ShoppingCart s, ShoppingCartItems i WHERE s.user_id = :user_id "
				   + "AND i.product_id = :prdId AND s.time_processed IS NOT NULL AND s.reference = i.reference";
		Session session = session();
		Long count = (Long) session.createQuery(hql).setString("prdId", prdId).setLong("user_id", user_id).uniqueResult();
		
		session.close();
		
		return (count > 0L);
	}

	@Override
	public void merge(ShoppingCart shoppingCart) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(shoppingCart);
		tx.commit();
		session.close();
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
		session.close();
	}

}
