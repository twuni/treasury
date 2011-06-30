package org.twuni.money.treasury.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.twuni.money.common.Repository;
import org.twuni.money.common.Token;
import org.twuni.money.treasury.model.TokenEntity;

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

	@SuppressWarnings( "unchecked" )
	@Override
	public List<Token> list( int limit ) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria( TokenEntity.class );

		criteria.addOrder( Order.asc( "value" ) );
		criteria.setMaxResults( limit );

		return (List<Token>) criteria.list();

	}

	@Override
	public List<Token> list() {
		return list( Integer.MAX_VALUE );
	}

}
