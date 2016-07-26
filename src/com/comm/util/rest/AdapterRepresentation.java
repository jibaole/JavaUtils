package com.comm.util.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.restlet.representation.Representation;

public class AdapterRepresentation extends Representation {

	
	private String content =null;
	@Override
	public ReadableByteChannel getChannel() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(Writer arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(WritableByteChannel arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(OutputStream arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public void setText(String text){
		content=text;
	}
	
	@Override
    public String getText() throws IOException {
        return content;
    }

}
