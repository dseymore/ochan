package org.ochan.service.local;

import org.ochan.dao.BlobDAO;
import org.ochan.entity.Blob;
import org.ochan.service.BlobService;

public class LocalBlobService implements BlobService {

	private BlobDAO blobDAO;
		
	/**
	 * @param blobDAO the blobDAO to set
	 */
	public void setBlobDAO(BlobDAO blobDAO) {
		this.blobDAO = blobDAO;
	}

	@Override
	public void deleteBlob(Long identifier) {
		blobDAO.delete(identifier);
	}

	@Override
	public Byte[] getBlob(Long identifier) {
		Blob blob = blobDAO.getBlob(identifier);
		if (blob == null){
			return null;
		}
		return blob.getData();
	}

	@Override
	public Long saveBlob(Byte[] byteArray) {
		Blob b = new Blob(byteArray, null);
		blobDAO.saveBlob(b);
		return b.getIdentifier();
	}

}
