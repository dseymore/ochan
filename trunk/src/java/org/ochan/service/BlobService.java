package org.ochan.service;

import java.util.List;

import javax.jws.WebService;

@WebService
public interface BlobService {
	
	public Long saveBlob(Byte[] byteArray, Long id);
	
	public Byte[] getBlob(Long identifier);

	public void deleteBlob(Long identifier);
	
	public List<Long> getAllIds();
	
	public int getBlobSize(Long identifier);
}
