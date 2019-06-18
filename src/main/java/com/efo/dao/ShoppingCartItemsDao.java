package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.ShoppingCartItems;
import com.efo.interfaces.IShoppingCartItems;

@Transactional
@Repository
public class ShoppingCartItemsDao implements IShoppingCartItems {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(ShoppingCartItems item) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(item);
		tx.commit();
		session.disconnect();
	}

	@Override
	public ShoppingCartItems retrieve(Long id) {
		Session session = session();
		ShoppingCartItems items = (ShoppingCartItems) session.createCriteria(ShoppingCartItems.class)
								  .add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return items;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShoppingCartItems> retrieveRawList(String reference) {
		Session session = session();
		List<ShoppingCartItems> iList = session.createCriteria(ShoppingCartItems.class)
											   .add(Restrictions.eq("reference", reference)).list();
		session.disconnect();
		
		return iList;
	}

	@Override
	public void changeShoppingCartQty(Long id, Integer qty) {
		String hql = "UPDATE ShoppingCartItems SET qty = :qty WHERE id = :id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setInteger("qty", qty).setLong("id", id).executeUpdate();
		tx.commit();
		session.disconnect();
	}

	@Override
	public void update(ShoppingCartItems item) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(item);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Long id) {
		String hql = "DELETE FROM ShoppingCart WHERE id = :id";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setLong("id", id).executeUpdate();
		tx.commit();
		session.disconnect();
	}

}
