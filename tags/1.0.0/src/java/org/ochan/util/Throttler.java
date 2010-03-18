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

/**
 * Class that throttles requests. Ensures that the StartRequest cannot be called
 * more than a given amount of time in any given interval.
 * 
 * StartRequest blocks until this requirement is satisfied.
 * 
 * TryStartRequest returns 0 if the request was cleared, or a non-0 number of
 * millisecond to sleep before the next attempt.
 * 
 * Simple usage, 10 requests per second:
 * 
 * Throttler t = new Throttler(10, 1000); ... ServiceRequest(Throtter t, ...) {
 * t.StartRequest(); .. do work ..
 * 
 * @author sergey@solyanik.com (Sergey Solyanik)
 * @url http://1-800-magic.blogspot.com/2008/02/throttling-requests-java.html
 */
public class Throttler {
	/**
	 * The interval within we're ensuring max number of calls.
	 */
	private long interval;

	/**
	 * The maximum number of calls that can be made within the interval.
	 */
	private int max_calls;

	/**
	 * Previous calls within the interval.
	 */
	private long[] ticks;

	/**
	 * Available element at the insertion point (back of the queue).
	 */
	private int tick_next;

	/**
	 * Element at the removal point (front of the queue).
	 */
	private int tick_last;

	/**
	 * Constructor.
	 * 
	 * @param max_calls
	 *            Max number of calls that can be made within the interval.
	 * @param interval
	 *            The interval.
	 */
	public Throttler(int max_calls, long interval) {
		this.interval = interval;
		this.max_calls = max_calls + 1;
		this.ticks = new long[this.max_calls];
		this.tick_last = this.tick_next = 0;
	}

	/**
	 * Returns the next element in the queue.
	 * 
	 * @param index
	 *            The element for which to compute the next.
	 * @return
	 */
	private int Next(int index) {
		index += 1;
		return index < max_calls ? index : 0;
	}

	/**
	 * Attempts to clear the request.
	 * 
	 * @return Returns 0 if successful, or a time hint (ms) in which we should
	 *         attempt to clear it again.
	 */
	public synchronized long TryStartRequest() {
		long result = 0;
		long now = System.currentTimeMillis();
		while (tick_last != tick_next) {
			if (now - ticks[tick_last] < interval)
				break;
			tick_last = Next(tick_last);
		}

		int next = Next(tick_next);
		if (next != tick_last) {
			ticks[tick_next] = now;
			tick_next = next;
		} else {
			result = interval - (now - ticks[tick_last]);
		}
		return result;
	}

	/**
	 * Clears the request. Blocks until the request can execute.
	 */
	public void StartRequest() {
		long sleep;
		while ((sleep = TryStartRequest()) > 0) {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}
