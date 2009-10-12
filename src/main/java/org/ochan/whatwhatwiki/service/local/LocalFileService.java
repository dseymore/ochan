package org.ochan.whatwhatwiki.service.local;

import java.util.List;

import org.ochan.whatwhatwiki.dpl.SleepyEnvironment;
import org.ochan.whatwhatwiki.service.FileService;

public class LocalFileService implements FileService {

        private SleepyEnvironment sleepyEnvironment;

    public void setSleepyEnvironment(SleepyEnvironment sleepyEnvironment) {
        this.sleepyEnvironment = sleepyEnvironment;
    }
        
        
    
	@Override
	public void createOrUpdate(String key, Byte[] data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public Byte[] get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllFileKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}
