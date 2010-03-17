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
package org.ochan.dpl;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;


@Entity(version=1)
public class BlobStatDPL {

	@PrimaryKey(sequence="BLOB_STAT")
	private Long identifier;
	
	@SecondaryKey(relate=Relationship.ONE_TO_ONE)
	private Long blobIdentifier;
	private int size;
	
	@SecondaryKey(relate=Relationship.MANY_TO_ONE)
	private BlobType blobType;
	
	/**
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return identifier;
	}
	/**
	 * @return the blobIdentifier
	 */
	public Long getBlobIdentifier() {
		return blobIdentifier;
	}
	/**
	 * @param blobIdentifier the blobIdentifier to set
	 */
	public void setBlobIdentifier(Long blobIdentifier) {
		this.blobIdentifier = blobIdentifier;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	public BlobType getBlobType() {
		return blobType;
	}
	
	public void setBlobType(BlobType blobType) {
		this.blobType = blobType;
	}

	
}
