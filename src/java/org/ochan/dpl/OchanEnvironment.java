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

import com.sleepycat.je.Environment;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;

public interface OchanEnvironment {

	public PrimaryIndex<Long, CategoryDPL> categoryByIdentifier();

	public SecondaryIndex<String, Long, CategoryDPL> categoryByCode();

	public PrimaryIndex<Long, ThreadDPL> threadByIdentifier();

	public SecondaryIndex<Long, Long, ThreadDPL> threadByCategory();

	public PrimaryIndex<Long, PostDPL> postByIdentifier();

	public SecondaryIndex<Long, Long, PostDPL> postByThread();
	
	public SecondaryIndex<Long, Long, PostDPL> postByImage();
	
	public SecondaryIndex<Long, Long, PostDPL> postByThumbnail();

	public PrimaryIndex<Long, BlobDPL> blobByIdentifier();

	public PrimaryIndex<Long, ExternalCategoryDPL> externalCategoryByIdentifier();

	public PrimaryIndex<Long, BlobStatDPL> blobStatisticsByIdentifier();
	
	public SecondaryIndex<BlobType, Long, BlobStatDPL> blobStatisticsByBlobType();

	public SecondaryIndex<Long, Long, BlobStatDPL> blobStatisticsByBlobIdentifier();

	public PrimaryIndex<Long, SynchroDPL> synchroByIdentifier();

	public Environment getEnvironment();

	public void close();

}
