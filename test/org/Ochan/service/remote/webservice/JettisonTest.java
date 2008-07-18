package org.Ochan.service.remote.webservice;

import javax.xml.stream.XMLStreamConstants;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.AbstractXMLStreamReader;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;

public class JettisonTest extends TestCase {

	private static final Log LOG = LogFactory.getLog(JettisonTest.class);
	
	public void testJettisonConversion(){
		try{
			//TODO - this is just a test, remove it.
			JSONObject obj = new JSONObject("{ \"env.getNextPost\": { \"ochan.thread\" : \"2\", \"ochan.lastPost\" :\"4\" }}");
			AbstractXMLStreamReader reader = new MappedXMLStreamReader(obj);
			while(reader.getEventType() != XMLStreamConstants.END_DOCUMENT){
				System.out.println(reader.getEventType() + " " + reader.getName() + " " + reader.getText());	
				reader.next();
			}
			//jump out.. the END is still not written
			System.out.println(reader.getEventType() + " " + reader.getName() + " " + reader.getText());
			
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("ugh",e);
		}
		
	}
}
