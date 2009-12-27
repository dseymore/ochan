package org.ochan.dpl.replication;

import javax.transaction.TransactionRolledbackException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.OchanEnvironment;

import com.sleepycat.je.Transaction;
import com.sleepycat.je.rep.ReplicaWriteException;
import com.sleepycat.je.rep.ReplicatedEnvironment;

/**
 * Template for doing sleepyenvironment work. 
 * @author David Seymore 
 * Dec 19, 2009
 */
public abstract class TransactionTemplate {

	private static final Log LOG = LogFactory.getLog(TransactionTemplate.class);

	private OchanEnvironment environment;
	
	public void setup(){
		LOG.trace("Starting transaction.");
	}
	
	public TransactionTemplate(OchanEnvironment sleepyEnvironment){
		this.environment = sleepyEnvironment;
	}
	
	public void cleanup(){
		LOG.trace("Cleaning up transaction.");
	}
	
	public abstract void doInTransaction() throws Exception;
	
	public void run() throws Exception{
		if (environment.getEnvironment() instanceof ReplicatedEnvironment){
			this.setup();		
			Transaction transaction = null;
			try{
				transaction = environment.getEnvironment().beginTransaction(null, null);
				this.doInTransaction();
				transaction.commit();
			}catch(ReplicaWriteException replicaWriteException){
				if (transaction != null){
					transaction.abort();
					throw new TransactionRolledbackException();
				}
			}catch(Exception e){
				LOG.error("Exception wasn't a transactional one, was it?",e);
				throw e;
			}finally{
				this.cleanup();
			}
		}else{
			//just run it, without a transaction
			this.doInTransaction();
		}
	}
	
	
}
