package org.twuni.money.treasury.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.twuni.money.treasury.model.Dollar;
import org.twuni.money.treasury.repository.Repository;

@Transactional
public class DollarService {

	@Service
	public static class Default extends DollarService {
	}

	public static class Configuration {

		private int idLength;
		private String treasury;
		private Repository<String, Dollar> repository;

		public Configuration( int idLength, String treasury, Repository<String, Dollar> repository ) {
			this.idLength = idLength;
			this.treasury = treasury;
			this.repository = repository;
		}

	}

	@Autowired
	private Configuration configuration;

	private final SecureRandom random = new SecureRandom();

	public void setConfiguration( Configuration configuration ) {
		this.configuration = configuration;
	}

	public Dollar create( int worth ) {

		if( configuration == null ) {
			throw new IllegalStateException( "The service has not yet been configured." );
		}

		if( worth <= 0 ) {
			throw new IllegalArgumentException( "A dollar's worth must be greater than zero." );
		}

		String id = generateUniqueId( configuration.idLength );
		String secret = generateRandomBase64String( configuration.idLength );

		Dollar dollar = new Dollar( configuration.treasury, id, secret, worth );

		save( dollar );

		return new Dollar( dollar );

	}

	private String generateRandomBase64String( int length ) {
		byte [] buffer = new byte [length];
		random.nextBytes( buffer );
		String result = Base64.encodeBase64String( buffer );
		return result;
	}

	private String generateUniqueId( int length ) {
		String id;
		do {
			id = generateRandomBase64String( length );
		} while( findById( id ) != null );
		return id;
	}

	public List<Dollar> split( Dollar original, int amount ) {
		original = lookup( original );
		Dollar a = create( amount );
		Dollar b = create( original.getWorth() - amount );
		delete( original );
		return Arrays.asList( a, b );
	}

	public Dollar merge( Dollar a, Dollar b ) {
		a = lookup( a );
		b = lookup( b );
		if( a.equals( b ) ) {
			throw new IllegalArgumentException( "Cannot merge a dollar with itself." );
		}
		delete( a );
		delete( b );
		return create( a.getWorth() + b.getWorth() );
	}

	public int evaluate( Dollar dollar ) {
		return evaluate( dollar.getId() );
	}

	public int evaluate( String dollarId ) {
		Dollar dollar = findById( dollarId );
		return dollar == null ? 0 : dollar.getWorth();
	}

	protected Dollar lookup( Dollar dollar ) {
		Dollar actual = null;
		try {
			actual = findById( dollar.getId() );
		} catch( ObjectNotFoundException exception ) {
			throw new IllegalArgumentException( exception.getMessage() );
		}
		if( actual == null || !actual.getSecret().equals( dollar.getSecret() ) ) {
			throw new IllegalArgumentException( String.format( "Dollar %s is invalid.", dollar.getId() ) );
		}
		return actual;
	}

	private void delete( Dollar dollar ) {
		configuration.repository.delete( dollar );
	}

	private void save( Dollar dollar ) {
		configuration.repository.save( dollar );
	}

	private Dollar findById( String id ) {
		return configuration.repository.findById( id );
	}

}
