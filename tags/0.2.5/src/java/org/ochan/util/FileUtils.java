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
