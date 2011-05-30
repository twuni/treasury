package org.twuni.money.treasury.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Dollar {

	private String treasury;
	private String id;
	private String secret;
	private int worth;

	public Dollar( Dollar copy ) {
		this( copy.treasury, copy.id, copy.secret, copy.worth );
	}

	public Dollar() {
		this( 1 );
	}

	public Dollar( int worth ) {
		this.worth = worth;
	}

	public Dollar( String treasury, String id, String secret, int worth ) {
		this.treasury = treasury;
		this.id = id;
		this.secret = secret;
		this.worth = worth;
	}

	public Dollar( String id, String secret ) {
		this.id = id;
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

	public void setTreasury( String treasury ) {
		this.treasury = treasury;
	}

	@Transient
	public String getTreasury() {
		return treasury;
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
