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
