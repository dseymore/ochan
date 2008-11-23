package org.ochan.service;

public interface BlobService {
	
	public Long saveBlob(Byte[] byteArray);
	
	public Byte[] getBlob(Long identifier);

	public void deleteBlob(Long identifier);
}
