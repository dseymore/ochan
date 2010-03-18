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
