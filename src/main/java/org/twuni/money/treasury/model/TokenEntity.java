package org.twuni.money.treasury.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.twuni.money.common.SimpleToken;

@Entity( name = "token" )
public class TokenEntity extends SimpleToken {

	@Id
	@Override
	public String getId() {
		return super.getId();
	}

	@Override
	public String getSecret() {
		return super.getSecret();
	}

	@Override
	public int getValue() {
		return super.getValue();
	}

}
