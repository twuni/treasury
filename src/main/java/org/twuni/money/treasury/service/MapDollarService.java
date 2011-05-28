package org.twuni.money.treasury.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.twuni.money.treasury.model.Dollar;

@Service
public class MapDollarService extends DollarService {

	private final Map<String, Dollar> dollars = new HashMap<String, Dollar>();

	public MapDollarService() {
		super();
	}

	public MapDollarService( int securityBitLength ) {
		super( securityBitLength );
	}

	@Override
	protected Dollar findById( String dollarId ) {
		return dollars.get( dollarId );
	}

	@Override
	protected void save( Dollar dollar ) {
		dollars.put( dollar.getId(), dollar );
	}

	@Override
	protected void delete( Dollar dollar ) {
		dollars.put( dollar.getId(), null );
	}

}
