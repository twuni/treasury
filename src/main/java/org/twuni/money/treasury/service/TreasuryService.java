package org.twuni.money.treasury.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.twuni.money.common.Repository;
import org.twuni.money.common.Token;

@Transactional
public class TreasuryService extends org.twuni.money.common.TreasuryService {

	@Service
	public static class Default extends TreasuryService {
	}

	public static class Configuration {

		private int idLength;
		private String treasury;
		private Repository<String, Token> repository;

		public Configuration( int idLength, String treasury, Repository<String, Token> repository ) {
			this.idLength = idLength;
			this.treasury = treasury;
			this.repository = repository;
		}

	}

	public TreasuryService() {
		super( 0, null, null );
	}

	@Autowired
	public void setConfiguration( Configuration configuration ) {
		idLength = configuration.idLength;
		treasury = configuration.treasury;
		repository = configuration.repository;
	}

}

