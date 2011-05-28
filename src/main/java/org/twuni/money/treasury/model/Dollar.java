package org.twuni.money.treasury.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Dollar {

	private String id;
	private String secret;
	private int worth;

	public Dollar( Dollar copy ) {
		this( copy.id, copy.worth, copy.secret );
	}

	public Dollar() {
		this( null, 1, null );
	}

	public Dollar( int worth ) {
		this( null, worth, null );
	}

	public Dollar( String id, String secret ) {
		this( id, 0, secret );
	}

	public Dollar( String id, int worth, String secret ) {
		this.id = id;
		this.worth = worth;
		this.secret = secret;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret( String secret ) {
		this.secret = secret;
	}

	public int getWorth() {
		return worth;
	}

	public void setWorth( int worth ) {
		if( 0 >= worth ) {
			throw new IllegalArgumentException( "A dollar's worth must be greater than zero." );
		}
		this.worth = worth;
	}

	@Override
	public boolean equals( Object object ) {

		if( !getClass().isInstance( object ) ) {
			return false;
		}

		Dollar dollar = getClass().cast( object );

		return id.equals( dollar.id );

	}

}
