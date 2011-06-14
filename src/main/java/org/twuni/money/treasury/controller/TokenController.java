package org.twuni.money.treasury.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.twuni.money.common.SimpleToken;
import org.twuni.money.common.Token;
import org.twuni.money.common.Treasury;

import com.google.gson.Gson;

@Controller
public class TokenController {

	@Autowired
	private Treasury treasury;

	@RequestMapping( method = RequestMethod.POST, value = "/merge" )
	public void mergeDollars( @RequestParam final String id1, @RequestParam final String secret1, @RequestParam final String id2, @RequestParam final String secret2, HttpServletResponse response ) {

		Token a = new SimpleToken( id1, secret1 );
		Token b = new SimpleToken( id2, secret2 );

		try {

			Token token = treasury.merge( a, b );

			sendAsJson( token, response );

		} catch( IllegalArgumentException exception ) {
			response.setStatus( 400 );
		}

	}

	@RequestMapping( method = RequestMethod.POST, value = "/split" )
	public void splitDollar( @RequestParam final String id, @RequestParam final String secret, @RequestParam int value, HttpServletResponse response ) {

		Token token = new SimpleToken( id, secret );

		try {

			Set<Token> tokens = treasury.split( token, value );

			sendAsJson( tokens, response );

		} catch( IllegalArgumentException exception ) {
			response.setStatus( 400 );
		}

	}

	@RequestMapping( method = RequestMethod.GET, value = "/value" )
	public void getDollarValue( @RequestParam String id, HttpServletResponse response ) {

		Token token = new SimpleToken( id, null );

		int value = treasury.getValue( token );

		sendAsJson( Integer.valueOf( value ), response );

	}

	private <T> void sendAsJson( T object, HttpServletResponse response ) {

		try {

			String json = new Gson().toJson( object );
			response.setContentType( "application/json" );
			Writer writer = response.getWriter();
			writer.write( json );
			writer.flush();
			writer.close();

		} catch( IOException exception ) {
			System.err.println( exception.getMessage() );
		}

	}

}
