package org.twuni.money.treasury.repository;

import java.util.HashMap;
import java.util.Map;

import org.twuni.money.common.Token;
import org.twuni.money.treasury.model.TokenEntity;

@org.springframework.stereotype.Repository( "tokenRepository" )
public class TokenRepository extends HibernateTokenRepository {

	private final Map<String, Token> tokens = new HashMap<String, Token>();

	@Override
	public void save( Token token ) {
		super.save( toTokenEntity( token ) );
	}

	@Override
	public void delete( Token token ) {
		super.delete( toTokenEntity( token ) );
		tokens.put( token.getId(), null );
	}

	private Token toTokenEntity( Token token ) {
		Token entity = tokens.get( token.getId() );
		if( entity == null ) {
			entity = new TokenEntity( token );
			tokens.put( token.getId(), entity );
		}
		return entity;
	}

}
