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
package org.ochan.service;

import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

import org.ochan.dpl.BlobType;

@WebService
public interface BlobService {
	
	@XmlMimeType("application/octet-stream")
	public Long saveBlob(Byte[] byteArray, Long id, BlobType blobType);
	
	@XmlMimeType("application/octet-stream")
	public Byte[] getBlob(Long identifier);

	public void deleteBlob(Long identifier);
	
	public List<Long> getAllIds();
	
	public int getBlobSize(Long identifier);
	
	public List<Long> getLast50Blobs(BlobType blobType);
}
