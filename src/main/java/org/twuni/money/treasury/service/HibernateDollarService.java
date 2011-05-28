package org.twuni.money.treasury.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twuni.money.treasury.model.Dollar;

@Service
public class HibernateDollarService extends DollarService {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	protected Dollar findById( String dollarId ) {
		Session session = sessionFactory.getCurrentSession();
		Dollar dollar = (Dollar) session.load( Dollar.class, dollarId );
		return dollar;
	}

	@Override
	protected void save( Dollar dollar ) {
		Session session = sessionFactory.getCurrentSession();
		session.save( dollar );
	}

	@Override
	protected void delete( Dollar dollar ) {
		Session session = sessionFactory.getCurrentSession();
		session.delete( dollar );
	}

}
