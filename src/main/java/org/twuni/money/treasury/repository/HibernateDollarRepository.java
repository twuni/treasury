package org.twuni.money.treasury.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.twuni.money.treasury.model.Dollar;

@org.springframework.stereotype.Repository
public class HibernateDollarRepository implements Repository<String, Dollar> {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Dollar findById( String dollarId ) {
		return (Dollar) sessionFactory.getCurrentSession().get( Dollar.class, dollarId );
	}

	@Override
	public void save( Dollar dollar ) {
		sessionFactory.getCurrentSession().save( dollar );
	}

	@Override
	public void delete( Dollar dollar ) {
		sessionFactory.getCurrentSession().delete( dollar );
	}

}
