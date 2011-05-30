package org.twuni.money.treasury.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twuni.money.treasury.model.Dollar;

@Service
public class HibernateDollarService extends DollarService {

	@Autowired
	private SessionFactory sessionFactory;

	public HibernateDollarService() {
	}

	public HibernateDollarService( int securityBitLength ) {
		super( securityBitLength );
	}

	@Override
	protected Dollar findById( String dollarId ) {
		return (Dollar) sessionFactory.getCurrentSession().get( Dollar.class, dollarId );
	}

	@Override
	protected void save( Dollar dollar ) {
		sessionFactory.getCurrentSession().save( dollar );
	}

	@Override
	protected void delete( Dollar dollar ) {
		sessionFactory.getCurrentSession().delete( dollar );
	}

}
