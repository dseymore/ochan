package org.ochan.service;

import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

@WebService
public interface BlobService {
	
	@XmlMimeType("application/octet-stream")
	public Long saveBlob(Byte[] byteArray, Long id);
	
	@XmlMimeType("application/octet-stream")
	public Byte[] getBlob(Long identifier);

	public void deleteBlob(Long identifier);
	
	public List<Long> getAllIds();
	
	public int getBlobSize(Long identifier);
}
