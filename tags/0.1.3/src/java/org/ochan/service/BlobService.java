package org.ochan.service;

import java.util.List;

public interface BlobService {
	
	public Long saveBlob(Byte[] byteArray);
	
	public Byte[] getBlob(Long identifier);

	public void deleteBlob(Long identifier);
	
	public List<Long> getAllIds();
}
