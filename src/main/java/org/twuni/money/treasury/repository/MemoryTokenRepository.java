package org.twuni.money.treasury.repository;

import java.util.HashMap;
import java.util.Map;

import org.twuni.money.common.Repository;
import org.twuni.money.common.Token;

@org.springframework.stereotype.Repository
public class MemoryTokenRepository implements Repository<String, Token> {

	private final Map<String, Token> tokens = new HashMap<String, Token>();

	@Override
	public Token findById( String id ) {
		return tokens.get( id );
	}

	@Override
	public void save( Token token ) {
		tokens.put( token.getId(), token );
	}

	@Override
	public void delete( Token token ) {
		tokens.put( token.getId(), null );
	}

}
