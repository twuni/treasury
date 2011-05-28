package org.twuni.money.treasury.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.twuni.money.treasury.model.Dollar;
import org.twuni.money.treasury.service.DollarService;

@Controller
public class DollarController extends AbstractJsonController {

	@Autowired
	private DollarService dollarService;

	@RequestMapping( method = RequestMethod.POST, value = "/create" )
	public void createDollar( @RequestParam int value, HttpServletResponse response ) {
		try {
			Dollar dollar = dollarService.create( value );
			sendAsJson( dollar, response );
		} catch( IllegalArgumentException exception ) {
			response.setStatus( 400 );
		}
	}

	@RequestMapping( method = RequestMethod.POST, value = "/merge" )
	public void mergeDollars( @RequestParam String id1, @RequestParam String secret1, @RequestParam String id2, @RequestParam String secret2, HttpServletResponse response ) {

		Dollar a = new Dollar( id1, secret1 );
		Dollar b = new Dollar( id2, secret2 );

		try {
			Dollar dollar = dollarService.merge( a, b );
			sendAsJson( dollar, response );
		} catch( IllegalArgumentException exception ) {
			response.setStatus( 400 );
		}

	}

	@RequestMapping( method = RequestMethod.POST, value = "/split" )
	public void splitDollar( @RequestParam String id, @RequestParam String secret, @RequestParam int value, HttpServletResponse response ) {

		Dollar dollar = new Dollar( id, secret );

		try {
			List<Dollar> dollars = dollarService.split( dollar, value );
			sendAsJson( dollars, response );
		} catch( IllegalArgumentException exception ) {
			response.setStatus( 400 );
		}

	}

	@RequestMapping( method = RequestMethod.GET, value = "/value" )
	public void getDollarValue( @RequestParam String id, HttpServletResponse response ) {
		int value = dollarService.evaluate( id );
		sendAsJson( value, response );
	}

}
