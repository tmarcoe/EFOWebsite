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
	
	public boolean checkIfItemExists(String scId, String productId) {
		String hql = "SELECT COUNT(*) FROM ShoppingCartItems WHERE reference = :scId AND product_id = :productId";
		Session session = session();
		Long count = (Long) session.createQuery(hql).setString("scId", scId).setString("productId", productId).uniqueResult();
		
		session.disconnect();
		
		return (count > 0);
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
	
	public void deleteShoppingCartItem(String scId, String prdId) {
		String hql = "DELETE FROM ShoppingCartItems WHERE reference = :scId AND product_id = :prdId";
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.createQuery(hql).setString("scId", scId).setString("prdId", prdId).executeUpdate();
		tx.commit();
		session.disconnect();
	}

	public Long countScItems(String scId) {
		String hql = "SELECT COUNT(*) FROM ShoppingCartItems WHERE reference = :scId";
		Session session = session();
		Long count = (Long) session.createSQLQuery(hql).setString("scId", scId).uniqueResult();
		session.disconnect();
		
		return count;
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
