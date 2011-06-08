package org.twuni.money.treasury.repository;

import java.util.HashMap;
import java.util.Map;

import org.twuni.money.treasury.model.Dollar;

public class MemoryDollarRepository implements Repository<String, Dollar> {

	private final Map<String, Dollar> dollars = new HashMap<String, Dollar>();

	@Override
	public Dollar findById( String dollarId ) {
		return dollars.get( dollarId );
	}

	@Override
	public void save( Dollar dollar ) {
		dollars.put( dollar.getId(), dollar );
	}

	@Override
	public void delete( Dollar dollar ) {
		dollars.put( dollar.getId(), null );
	}

}
