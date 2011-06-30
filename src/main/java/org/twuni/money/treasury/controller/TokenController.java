package org.twuni.money.treasury.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.twuni.money.common.SimpleToken;
import org.twuni.money.common.Token;
import org.twuni.money.common.TreasuryService;

import com.google.gson.Gson;

@Controller
public class TokenController {

	private final Logger log = LoggerFactory.getLogger( getClass() );

	@Autowired
	private TreasuryService treasury;
	
	@RequestMapping( method = RequestMethod.POST, value = "/create" )
	public void create( @RequestParam final int value, HttpServletRequest request, HttpServletResponse response ) {
		try {
			if( !request.getRemoteAddr().equals( request.getLocalAddr() ) ) {
				throw new IllegalAccessError( String.format( "%s != %s", request.getRemoteAddr(), request.getLocalAddr() ) );
			}
			Token token = treasury.create( value );
			sendAsJson( token, request, response );
		} catch( IllegalAccessError exception ) {
			log.warn( String.format( "[%s] Illegal attempt to create token.", request.getRemoteAddr() ) );
			response.setStatus( 401 );
		} catch( Exception exception ) {
			failGracefully( request, response, exception );
		}
	}

	@RequestMapping( method = RequestMethod.POST, value = "/merge" )
	public void merge( @RequestParam final String id1, @RequestParam final String secret1, @RequestParam final String id2, @RequestParam final String secret2, HttpServletRequest request, HttpServletResponse response ) {
		try {
			Token a = new SimpleToken( id1, secret1 );
			Token b = new SimpleToken( id2, secret2 );
			Token token = treasury.merge( a, b );
			sendAsJson( token, request, response );
		} catch( IllegalArgumentException exception ) {
			log.info( String.format( "[%s] Error merging token: %s", request.getRemoteAddr(), exception.getMessage() ) );
			response.setStatus( 400 );
		} catch( Exception exception ) {
			failGracefully( request, response, exception );
		}
	}

	@RequestMapping( method = RequestMethod.POST, value = "/split" )
	public void split( @RequestParam final String id, @RequestParam final String secret, @RequestParam int value, HttpServletRequest request, HttpServletResponse response ) {
		try {
			Token token = new SimpleToken( id, secret );
			Set<Token> tokens = treasury.split( token, value );
			sendAsJson( tokens, request, response );
		} catch( IllegalArgumentException exception ) {
			log.info( String.format( "[%s] Error splitting token: %s", request.getRemoteAddr(), exception.getMessage() ) );
			response.setStatus( 400 );
		} catch( Exception exception ) {
			failGracefully( request, response, exception );
		}
	}

	@RequestMapping( method = RequestMethod.GET, value = "/value" )
	public void evaluate( @RequestParam String id, HttpServletRequest request, HttpServletResponse response ) {
		try {
			Token token = new SimpleToken( id, null );
			int value = treasury.getValue( token );
			sendAsJson( Integer.valueOf( value ), request, response );
		} catch( Exception exception ) {
			failGracefully( request, response, exception );
		}
	}

	private <T> void sendAsJson( T object, HttpServletRequest request, HttpServletResponse response ) {
		try {
			String json = new Gson().toJson( object );
			response.setContentType( "application/json" );
			Writer writer = response.getWriter();
			writer.write( json );
			writer.flush();
			writer.close();
		} catch( IOException exception ) {
			log.warn( String.format( "[%s] Error sending response: %s", request.getRemoteAddr(), exception.getMessage() ) );
		}
	}

	private void failGracefully( HttpServletRequest request, HttpServletResponse response, Exception exception ) {
	    log.error( String.format( "[%s] %s", request.getRemoteAddr(), exception.getMessage() ) );
	    response.setStatus( 500 );
    }

}
