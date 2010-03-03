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
