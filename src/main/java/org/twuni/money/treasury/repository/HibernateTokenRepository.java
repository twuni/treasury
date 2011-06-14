package org.twuni.money.treasury.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.twuni.money.common.Repository;
import org.twuni.money.common.Token;
import org.twuni.money.treasury.model.TokenEntity;

@org.springframework.stereotype.Repository
public class HibernateTokenRepository implements Repository<String, Token> {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Token findById( String dollarId ) {
		return (Token) sessionFactory.getCurrentSession().get( TokenEntity.class, dollarId );
	}

	@Override
	public void save( Token dollar ) {
		sessionFactory.getCurrentSession().save( dollar );
	}

	@Override
	public void delete( Token dollar ) {
		sessionFactory.getCurrentSession().delete( dollar );
	}

}
