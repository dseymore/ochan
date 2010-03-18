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

public class FileUtilsTest extends TestCase {

	public void testByteCountToDisplaySize() {
		{
			long val = 1024;
			String size = FileUtils.byteCountToDisplaySize(val);
			assertEquals("1.0 KiB", size);
		}
		{
			long val = (1024*1024*2*3)+280768;
			String size = FileUtils.byteCountToDisplaySize(val);
			assertEquals("6.27 MiB", size);
		}
		{
			//madness happens when we get into the gigibyte territory because of overflowing the long
			long val = (1024*1024*1024*2*3)+280768;
			String size = FileUtils.byteCountToDisplaySize(val);
			//no assert
		}
		{
			long val = (1024*1024*2*3)+880768;
			String size = FileUtils.byteCountToDisplaySize(val);
			assertEquals("6.84 MiB", size);
		}
		
	}

}
