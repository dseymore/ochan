/*
Ochan - image board/anonymous forum
Copyright (C) 2010  David Seymore

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package org.ochan.service.remote.webservice;

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
