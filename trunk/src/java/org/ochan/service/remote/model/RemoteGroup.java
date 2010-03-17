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
package org.ochan.service.remote.model;

import java.lang.reflect.Field;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@XmlRootElement(name = "RemoteGroup")
public class RemoteGroup {

	// made this list with
	// for i in `seq 1 5`; do a=`perl -e 'private join("", map { chr($_ + 96) }
	// @ARGV), "\n"' $i`; for j in `seq 0 9`; do echo "private Long "$a$j";";
	// done; done
	private static final Log LOG = LogFactory.getLog(RemoteGroup.class);
	private Long a0 = null;
	private Long a1 = null;
	private Long a2 = null;
	private Long a3 = null;
	private Long a4 = null;
	private Long a5 = null;
	private Long a6 = null;
	private Long a7 = null;
	private Long a8 = null;
	private Long a9 = null;
	private Long b0 = null;
	private Long b1 = null;
	private Long b2 = null;
	private Long b3 = null;
	private Long b4 = null;
	private Long b5 = null;
	private Long b6 = null;
	private Long b7 = null;
	private Long b8 = null;
	private Long b9 = null;
	private Long c0 = null;
	private Long c1 = null;
	private Long c2 = null;
	private Long c3 = null;
	private Long c4 = null;
	private Long c5 = null;
	private Long c6 = null;
	private Long c7 = null;
	private Long c8 = null;
	private Long c9 = null;
	private Long d0 = null;
	private Long d1 = null;
	private Long d2 = null;
	private Long d3 = null;
	private Long d4 = null;
	private Long d5 = null;
	private Long d6 = null;
	private Long d7 = null;
	private Long d8 = null;
	private Long d9 = null;
	private Long e0 = null;
	private Long e1 = null;
	private Long e2 = null;
	private Long e3 = null;
	private Long e4 = null;
	private Long e5 = null;
	private Long e6 = null;
	private Long e7 = null;
	private Long e8 = null;
	private Long e9 = null;

	public RemoteGroup() {

	}

	/**
	 * Determines if the group is empty.. maximum 50 elements.
	 * 
	 * @return
	 */
	public boolean isFull() {
		boolean empty = false;
		try {
			for (Field f : this.getClass().getDeclaredFields()) {
				if (f.getType().equals(Long.class) && f.get(this) == null) {
					empty = true;
				}
				// breaking the loop.
				if (empty) {
					return false;
				}
			}
		} catch (Exception e) {
			LOG.error("Unable to get value.", e);
		}
		return empty;
	}

	public void add(Long value) {
		if (isFull()) {
			return;
		}
		boolean set = false;
		try {
			for (Field f : this.getClass().getDeclaredFields()) {
				if (f.getType().equals(Long.class) && f.get(this) == null && !set) {
					f.set(this, value);
					set = true;
				}
				if (set) {
					return;
				}
			}
		} catch (Exception e) {
			LOG.error("Unable to set value.", e);
		} finally {
			if (!set) {
				LOG.error("Unable to set value!!!");
			}
		}
	}

	public Long getA0() {
		return a0;
	}

	public Long getA1() {
		return a1;
	}

	public Long getA2() {
		return a2;
	}

	public Long getA3() {
		return a3;
	}

	public Long getA4() {
		return a4;
	}

	public Long getA5() {
		return a5;
	}

	public Long getA6() {
		return a6;
	}

	public Long getA7() {
		return a7;
	}

	public Long getA8() {
		return a8;
	}

	public Long getA9() {
		return a9;
	}

	public Long getB0() {
		return b0;
	}

	public Long getB1() {
		return b1;
	}

	public Long getB2() {
		return b2;
	}

	public Long getB3() {
		return b3;
	}

	public Long getB4() {
		return b4;
	}

	public Long getB5() {
		return b5;
	}

	public Long getB6() {
		return b6;
	}

	public Long getB7() {
		return b7;
	}

	public Long getB8() {
		return b8;
	}

	public Long getB9() {
		return b9;
	}

	public Long getC0() {
		return c0;
	}

	public Long getC1() {
		return c1;
	}

	public Long getC2() {
		return c2;
	}

	public Long getC3() {
		return c3;
	}

	public Long getC4() {
		return c4;
	}

	public Long getC5() {
		return c5;
	}

	public Long getC6() {
		return c6;
	}

	public Long getC7() {
		return c7;
	}

	public Long getC8() {
		return c8;
	}

	public Long getC9() {
		return c9;
	}

	public Long getD0() {
		return d0;
	}

	public Long getD1() {
		return d1;
	}

	public Long getD2() {
		return d2;
	}

	public Long getD3() {
		return d3;
	}

	public Long getD4() {
		return d4;
	}

	public Long getD5() {
		return d5;
	}

	public Long getD6() {
		return d6;
	}

	public Long getD7() {
		return d7;
	}

	public Long getD8() {
		return d8;
	}

	public Long getD9() {
		return d9;
	}

	public Long getE0() {
		return e0;
	}

	public Long getE1() {
		return e1;
	}

	public Long getE2() {
		return e2;
	}

	public Long getE3() {
		return e3;
	}

	public Long getE4() {
		return e4;
	}

	public Long getE5() {
		return e5;
	}

	public Long getE6() {
		return e6;
	}

	public Long getE7() {
		return e7;
	}

	public Long getE8() {
		return e8;
	}

	public Long getE9() {
		return e9;
	}

	public void setA0(Long a0) {
		this.a0 = a0;
	}

	public void setA1(Long a1) {
		this.a1 = a1;
	}

	public void setA2(Long a2) {
		this.a2 = a2;
	}

	public void setA3(Long a3) {
		this.a3 = a3;
	}

	public void setA4(Long a4) {
		this.a4 = a4;
	}

	public void setA5(Long a5) {
		this.a5 = a5;
	}

	public void setA6(Long a6) {
		this.a6 = a6;
	}

	public void setA7(Long a7) {
		this.a7 = a7;
	}

	public void setA8(Long a8) {
		this.a8 = a8;
	}

	public void setA9(Long a9) {
		this.a9 = a9;
	}

	public void setB0(Long b0) {
		this.b0 = b0;
	}

	public void setB1(Long b1) {
		this.b1 = b1;
	}

	public void setB2(Long b2) {
		this.b2 = b2;
	}

	public void setB3(Long b3) {
		this.b3 = b3;
	}

	public void setB4(Long b4) {
		this.b4 = b4;
	}

	public void setB5(Long b5) {
		this.b5 = b5;
	}

	public void setB6(Long b6) {
		this.b6 = b6;
	}

	public void setB7(Long b7) {
		this.b7 = b7;
	}

	public void setB8(Long b8) {
		this.b8 = b8;
	}

	public void setB9(Long b9) {
		this.b9 = b9;
	}

	public void setC0(Long c0) {
		this.c0 = c0;
	}

	public void setC1(Long c1) {
		this.c1 = c1;
	}

	public void setC2(Long c2) {
		this.c2 = c2;
	}

	public void setC3(Long c3) {
		this.c3 = c3;
	}

	public void setC4(Long c4) {
		this.c4 = c4;
	}

	public void setC5(Long c5) {
		this.c5 = c5;
	}

	public void setC6(Long c6) {
		this.c6 = c6;
	}

	public void setC7(Long c7) {
		this.c7 = c7;
	}

	public void setC8(Long c8) {
		this.c8 = c8;
	}

	public void setC9(Long c9) {
		this.c9 = c9;
	}

	public void setD0(Long d0) {
		this.d0 = d0;
	}

	public void setD1(Long d1) {
		this.d1 = d1;
	}

	public void setD2(Long d2) {
		this.d2 = d2;
	}

	public void setD3(Long d3) {
		this.d3 = d3;
	}

	public void setD4(Long d4) {
		this.d4 = d4;
	}

	public void setD5(Long d5) {
		this.d5 = d5;
	}

	public void setD6(Long d6) {
		this.d6 = d6;
	}

	public void setD7(Long d7) {
		this.d7 = d7;
	}

	public void setD8(Long d8) {
		this.d8 = d8;
	}

	public void setD9(Long d9) {
		this.d9 = d9;
	}

	public void setE0(Long e0) {
		this.e0 = e0;
	}

	public void setE1(Long e1) {
		this.e1 = e1;
	}

	public void setE2(Long e2) {
		this.e2 = e2;
	}

	public void setE3(Long e3) {
		this.e3 = e3;
	}

	public void setE4(Long e4) {
		this.e4 = e4;
	}

	public void setE5(Long e5) {
		this.e5 = e5;
	}

	public void setE6(Long e6) {
		this.e6 = e6;
	}

	public void setE7(Long e7) {
		this.e7 = e7;
	}

	public void setE8(Long e8) {
		this.e8 = e8;
	}

	public void setE9(Long e9) {
		this.e9 = e9;
	}

}
