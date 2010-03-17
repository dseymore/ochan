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

public class FileUtils {

	/**
	 * The number of bytes in a kilobyte.
	 */
	public static final long ONE_KB = 1024;

	/**
	 * The number of bytes in a megabyte.
	 */
	public static final long ONE_MB = ONE_KB * ONE_KB;

	/**
	 * The number of bytes in a gigabyte.
	 */
	public static final long ONE_GB = ONE_KB * ONE_MB;

	/**
	 * Returns a human-readable version of the file size, where the input
	 * represents a specific number of bytes.
	 * 
	 * @param size
	 *            the number of bytes
	 * @return a human-readable display value (includes units in binary powers)
	 *         http://en.wikipedia.org/wiki/MiB
	 */
	public static String byteCountToDisplaySize(long size) {
		String displaySize;

		if (size / ONE_GB > 0) {
			float value = Float.valueOf(size).floatValue() / Float.valueOf(ONE_GB).floatValue();
			displaySize = String.valueOf(Round(value, 2)) + " GiB";
		} else if (size / ONE_MB > 0) {
			float value = Float.valueOf(size).floatValue() / Float.valueOf(ONE_MB).floatValue();
			displaySize = String.valueOf(Round(value, 2)) + " MiB";
		} else if (size / ONE_KB > 0) {
			float value = Float.valueOf(size).floatValue() / Float.valueOf(ONE_KB).floatValue();
			displaySize = String.valueOf(Round(value, 2)) + " KiB";
		} else {
			displaySize = String.valueOf(size) + " bytes";
		}
		return displaySize;
	}

	public static float Round(float value, int numberOfPlaces) {
		float p = (float) Math.pow(10, numberOfPlaces);
		value = value * p;
		float tmp = Math.round(value);
		return (float) tmp / p;
	}

}
