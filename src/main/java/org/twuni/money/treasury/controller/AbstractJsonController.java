package org.twuni.money.treasury.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import flexjson.JSONSerializer;

class AbstractJsonController {

	private static final String CONTENT_TYPE_JSON = "application/json";

	protected void sendAsJson( Object object, HttpServletResponse response ) {
		try {
			String json = serializeToJson( object );
			response.setContentType( CONTENT_TYPE_JSON );
			commit( json, response.getWriter() );
		} catch( IOException exception ) {
			System.err.println( exception.getMessage() );
		}
	}

	private String serializeToJson( Object object ) {
		JSONSerializer serializer = new JSONSerializer();
		serializer.exclude( "*.class" );
		return serializer.serialize( object );
	}

	private void commit( String message, Writer writer ) throws IOException {
		writer.write( message );
		writer.flush();
		writer.close();
	}

}
