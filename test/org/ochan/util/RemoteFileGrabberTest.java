package org.ochan.util;

import junit.framework.TestCase;

public class RemoteFileGrabberTest extends TestCase {

	public void testGetFilenameFromUrl() {
		{
			String file1 = "fourty.jpg";
			String url1 = "http://localhost:8080/"+file1+"?123";
			String filename = RemoteFileGrabber.getFilenameFromUrl(url1);
			assertEquals(filename, file1);
		}
		{
			String file1 = "fourty.jpg";
			String url1 = "http://localhost:8080/"+file1;
			String filename = RemoteFileGrabber.getFilenameFromUrl(url1);
			assertEquals(filename, file1);
		}
		{
			String file1 = "fourty.jpg";
			String url1 = "http://www.google.com/foo/bar/hello/world/what/is/the/file/name/"+file1;
			String filename = RemoteFileGrabber.getFilenameFromUrl(url1);
			assertEquals(filename, file1);
		}
		{
			String file1 = "";
			String url1 = "http://www.google.com/foo/bar/hello/world/what/is/the/file/name/"+file1;
			String filename = RemoteFileGrabber.getFilenameFromUrl(url1);
			assertEquals(filename, file1);
		}
		
	}

}
