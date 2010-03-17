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
package org.ochan.job;

import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.SleepyEnvironment;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sleepycat.je.EnvironmentStats;
import com.sleepycat.je.StatsConfig;

@ManagedResource(description = "Sleepycat Statistics Generation", objectName = "Ochan:type=job,name=SleepycatStatistics", logFile = "jmx.log")
public class SleepyStatisticsJob extends ManagedQuartzJobBean {

	private static final Log LOG = LogFactory.getLog(SleepyStatisticsJob.class);
	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(SleepyStatisticsJob.class);

	@Override
	public void executeOnSchedule(JobExecutionContext context) {
		try {
			ApplicationContext appCtx = getApplicationContext(context);
			SleepyEnvironment environment = (SleepyEnvironment) appCtx.getBean("sleepy");
			StatsConfig statsConfig = new StatsConfig();
			EnvironmentStats stats = environment.getEnvironment().getStats(statsConfig);
			adminBytes = stats.getAdminBytes();
			bufferBytes = stats.getBufferBytes();
			cacheTotalBytes = stats.getCacheTotalBytes();
			cleanerBacklog = stats.getCleanerBacklog();
			cursorsBins = stats.getCursorsBins();
			dataBytes = stats.getDataBytes();
			dbClosedBins = stats.getDbClosedBins();
			endOfLog = stats.getEndOfLog();
			inCompQueueSize = stats.getInCompQueueSize();
			lastCheckpointEnd = stats.getLastCheckpointEnd();
			lastCheckpointId = stats.getLastCheckpointId();
			lastCheckpointStart = stats.getLastCheckpointStart();
			lockBytes = stats.getLockBytes();
			nBINsStripped = stats.getNBINsStripped();
			nCacheMiss = stats.getNCacheMiss();
			nCheckpoints = stats.getNCheckpoints();
			nCleanerDeletions = stats.getNCleanerDeletions();
			nCleanerEntriesRead = stats.getNCleanerEntriesRead();
			nCleanerRuns = stats.getNCleanerRuns();
			nClusterLNsProcessed = stats.getNClusterLNsProcessed();
			nDeltaINFlush = stats.getNDeltaINFlush();
			nEvictPasses = stats.getNEvictPasses();
			nFileOpens = stats.getNFileOpens();
			nFSyncRequests = stats.getNFSyncRequests();
			nFSyncs = stats.getNFSyncs();
			nFSyncTimeouts = stats.getNFSyncTimeouts();
			nFullBINFlush = stats.getNFullBINFlush();
			nFullINFlush = stats.getNFullINFlush();
			nINsCleaned = stats.getNINsCleaned();
			nINsDead = stats.getNINsDead();
			nINsMigrated = stats.getNINsMigrated();
			nINsObsolete = stats.getNINsObsolete();
			nLNQueueHits = stats.getNLNQueueHits();
			nLNsCleaned = stats.getNLNsCleaned();
			nLNsDead = stats.getNLNsDead();
			nLNsLocked = stats.getNLNsLocked();
			nLNsMarked = stats.getNLNsMarked();
			nLNsMigrated = stats.getNLNsMigrated();
			nLNsObsolete = stats.getNLNsObsolete();
			nLogBuffers = stats.getNLogBuffers();
			nMarkedLNsProcessed = stats.getNMarkedLNsProcessed();
			nNodesExplicitlyEvicted = stats.getNNodesExplicitlyEvicted();
			nNodesScanned = stats.getNNodesScanned();
			nNodesSelected = stats.getNNodesSelected();
			nNotResident = stats.getNNotResident();
			nonEmptyBins = stats.getNonEmptyBins();
			nOpenFiles = stats.getNOpenFiles();
			nPendingLNsLocked = stats.getNPendingLNsLocked();
			nPendingLNsProcessed = stats.getNPendingLNsProcessed();
			nRandomReadBytes = stats.getNRandomReadBytes();
			nRandomReads = stats.getNRandomReads();
			nRandomWriteBytes = stats.getNRandomWriteBytes();
			nRandomWrites = stats.getNRandomWrites();
			nRepeatFaultReads = stats.getNRepeatFaultReads();
			nRepeatIteratorReads = stats.getNRepeatIteratorReads();
			nRootNodesEvicted = stats.getNRootNodesEvicted();
			nSequentialReadBytes = stats.getNSequentialReadBytes();
			nSequentialReads = stats.getNSequentialReads();
			nSequentialWriteBytes = stats.getNSequentialWriteBytes();
			nSequentialWrites = stats.getNSequentialWrites();
			nTempBufferWrites = stats.getNTempBufferWrites();
			nToBeCleanedLNsProcessed = stats.getNToBeCleanedLNsProcessed();
			processedBins = stats.getProcessedBins();
			requiredEvictBytes = stats.getRequiredEvictBytes();
			sharedCacheTotalBytes = stats.getSharedCacheTotalBytes();
			splitBins = stats.getSplitBins();
			stats.getTotalLogSize();

		} catch (Exception e) {
			LOG.fatal("Unable to get spring context for services.");
		}
	}

	@Override
	public Preferences getPreferences() {
		return PREFERENCES;
	}

	@Override
	public String getTriggerName() {
		return "sleepyStatsTrigger";
	}

	// WHAT FOLLOWS IS A COPY OF ENVIRONMENTSTATS.. I DONT WANT TO HOLD ONTO A
	// REFERENCE

	/* INCompressor */

	/**
	 * The number of bins encountered by the INCompressor that were split
	 * between the time they were put on the compressor queue and when the
	 * compressor ran.
	 */
	private static long splitBins;

	/**
	 * The number of bins encountered by the INCompressor that had their
	 * database closed between the time they were put on the compressor queue
	 * and when the compressor ran.
	 */
	private static long dbClosedBins;

	/**
	 * The number of bins encountered by the INCompressor that had cursors
	 * referring to them when the compressor ran.
	 */
	private static long cursorsBins;

	/**
	 * The number of bins encountered by the INCompressor that were not actually
	 * empty when the compressor ran.
	 */
	private static long nonEmptyBins;

	/**
	 * The number of bins that were successfully processed by the IN Compressor.
	 */
	private static long processedBins;

	/**
	 * The number of entries in the INCompressor queue when the getStats() call
	 * was made.
	 */
	private static long inCompQueueSize;

	/* Evictor */

	/**
	 * The number of passes made to the evictor.
	 */
	private static long nEvictPasses;

	/**
	 * The accumulated number of nodes selected to evict.
	 */
	private static long nNodesSelected;

	/**
	 * The accumulated number of nodes scanned in order to select the eviction
	 * set.
	 */
	private static long nNodesScanned;

	/**
	 * The accumulated number of nodes evicted.
	 */
	private static long nNodesExplicitlyEvicted;

	/**
	 * The accumulated number of database root nodes evicted.
	 */
	private static long nRootNodesEvicted;

	/**
	 * The number of BINs stripped by the evictor.
	 */
	private static long nBINsStripped;

	/**
	 * The number of bytes we need to evict in order to get under budget.
	 */
	private static long requiredEvictBytes;

	/* Checkpointer */

	/**
	 * The total number of checkpoints run so far.
	 */
	private static long nCheckpoints;

	/**
	 * The Id of the last checkpoint.
	 */
	private static long lastCheckpointId;

	/**
	 * The accumulated number of full INs flushed to the log.
	 */
	private static long nFullINFlush;

	/**
	 * The accumulated number of full BINs flushed to the log.
	 */
	private static long nFullBINFlush;

	/**
	 * The accumulated number of Delta INs flushed to the log.
	 */
	private static long nDeltaINFlush;

	/**
	 * The location in the log of the last checkpoint start.
	 */
	private static long lastCheckpointStart;

	/**
	 * The location in the log of the last checkpoint end.
	 */
	private static long lastCheckpointEnd;

	/**
	 * The location of the next entry to be written to the log.
	 */
	private static long endOfLog;

	/* Cleaner */

	/** The number of files to be cleaned to reach the target utilization. */
	private int cleanerBacklog;

	/** The number of cleaner runs this session. */
	private static long nCleanerRuns;

	/** The number of cleaner file deletions this session. */
	private static long nCleanerDeletions;

	/**
	 * The accumulated number of INs obsolete.
	 */
	private static long nINsObsolete;

	/**
	 * The accumulated number of INs cleaned.
	 */
	private static long nINsCleaned;

	/**
	 * The accumulated number of INs that were not found in the tree anymore
	 * (deleted).
	 */
	private static long nINsDead;

	/**
	 * The accumulated number of INs migrated.
	 */
	private static long nINsMigrated;

	/**
	 * The accumulated number of LNs obsolete.
	 */
	private static long nLNsObsolete;

	/**
	 * The accumulated number of LNs cleaned.
	 */
	private static long nLNsCleaned;

	/**
	 * The accumulated number of LNs that were not found in the tree anymore
	 * (deleted).
	 */
	private static long nLNsDead;

	/**
	 * The accumulated number of LNs encountered that were locked.
	 */
	private static long nLNsLocked;

	/**
	 * The accumulated number of LNs encountered that were migrated forward in
	 * the log.
	 */
	private static long nLNsMigrated;

	/**
	 * The accumulated number of LNs that were marked for migration during
	 * cleaning.
	 */
	private static long nLNsMarked;

	/**
	 * The accumulated number of LNs processed without a tree lookup.
	 */
	private static long nLNQueueHits;

	/**
	 * The accumulated number of LNs processed because they were previously
	 * locked.
	 */
	private static long nPendingLNsProcessed;

	/**
	 * The accumulated number of LNs processed because they were previously
	 * marked for migration.
	 */
	private static long nMarkedLNsProcessed;

	/**
	 * The accumulated number of LNs processed because they are soon to be
	 * cleaned.
	 */
	private static long nToBeCleanedLNsProcessed;

	/**
	 * The accumulated number of LNs processed because they qualify for
	 * clustering.
	 */
	private static long nClusterLNsProcessed;

	/**
	 * The accumulated number of pending LNs that could not be locked for
	 * migration because of a long duration application lock.
	 */
	private static long nPendingLNsLocked;

	/**
	 * The accumulated number of log entries read by the cleaner.
	 */
	private static long nCleanerEntriesRead;

	/*
	 * Cache
	 */
	private int nSharedCacheEnvironments; // num of envs sharing the cache
	private static long sharedCacheTotalBytes; // shared cache consumed, in bytes
	private static long cacheTotalBytes; // local cache consumed, in bytes
	private static long bufferBytes; // cache consumed by the log buffers, in bytes
	private static long dataBytes; // cache consumed by the Btree, in bytes
	private static long adminBytes; // part of cache used by log cleaner metadata,
	// and other administrative structures
	private static long lockBytes; // part of cache used by locks and txns
	private static long nNotResident; // had to be instantiated from an LSN
	private static long nCacheMiss; // had to retrieve from disk
	private int nLogBuffers; // number of existing log buffers

	/*
	 * Random vs Sequential IO and byte counts.
	 */
	private static long nRandomReads;
	private static long nRandomWrites;
	private static long nSequentialReads;
	private static long nSequentialWrites;
	private static long nRandomReadBytes;
	private static long nRandomWriteBytes;
	private static long nSequentialReadBytes;
	private static long nSequentialWriteBytes;

	/*
	 * Log activity
	 */
	private static long nFSyncs; // Number of fsyncs issued. May be less than
	// nFSyncRequests because of group commit
	private static long nFSyncRequests; // Number of fsyncs requested.
	private static long nFSyncTimeouts; // Number of group fsync requests that
	// turned into singleton fsyncs.
	/*
	 * Number of reads which had to be repeated when faulting in an object from
	 * disk because the read chunk size controlled by je.log.faultReadSize is
	 * too small.
	 */
	private static long nRepeatFaultReads;

	/*
	 * Number of times we have to use the temporary marshalling buffer to write
	 * to the log.
	 */
	private static long nTempBufferWrites;

	/*
	 * Number of times we try to read a log entry larger than the read buffer
	 * size and can't grow the log buffer to accomodate the large object. This
	 * happens during scans of the log during activities like environment open
	 * or log cleaning. Implies that the the read chunk size controlled by
	 * je.log.iteratorReadSize is too small.
	 */
	private static long nRepeatIteratorReads;

	/* FileManager open file cache stats. */
	private int nFileOpens;
	private int nOpenFiles;

	/*
	 * Approximation of the total log size in bytes.
	 */
	private static long totalLogSize;

	/**
	 * @return the splitBins
	 */
	@ManagedAttribute
	public long getSplitBins() {
		return splitBins;
	}

	/**
	 * @return the dbClosedBins
	 */
	@ManagedAttribute
	public long getDbClosedBins() {
		return dbClosedBins;
	}

	/**
	 * @return the cursorsBins
	 */
	@ManagedAttribute
	public long getCursorsBins() {
		return cursorsBins;
	}

	/**
	 * @return the nonEmptyBins
	 */
	@ManagedAttribute
	public long getNonEmptyBins() {
		return nonEmptyBins;
	}

	/**
	 * @return the processedBins
	 */
	@ManagedAttribute
	public long getProcessedBins() {
		return processedBins;
	}

	/**
	 * @return the inCompQueueSize
	 */
	@ManagedAttribute
	public long getInCompQueueSize() {
		return inCompQueueSize;
	}

	/**
	 * @return the nEvictPasses
	 */
	@ManagedAttribute
	public long getNEvictPasses() {
		return nEvictPasses;
	}

	/**
	 * @return the nNodesSelected
	 */
	@ManagedAttribute
	public long getNNodesSelected() {
		return nNodesSelected;
	}

	/**
	 * @return the nNodesScanned
	 */
	@ManagedAttribute
	public long getNNodesScanned() {
		return nNodesScanned;
	}

	/**
	 * @return the nNodesExplicitlyEvicted
	 */
	@ManagedAttribute
	public long getNNodesExplicitlyEvicted() {
		return nNodesExplicitlyEvicted;
	}

	/**
	 * @return the nRootNodesEvicted
	 */
	@ManagedAttribute
	public long getNRootNodesEvicted() {
		return nRootNodesEvicted;
	}

	/**
	 * @return the nBINsStripped
	 */
	@ManagedAttribute
	public long getNBINsStripped() {
		return nBINsStripped;
	}

	/**
	 * @return the requiredEvictBytes
	 */
	@ManagedAttribute
	public long getRequiredEvictBytes() {
		return requiredEvictBytes;
	}

	/**
	 * @return the nCheckpoints
	 */
	@ManagedAttribute
	public long getNCheckpoints() {
		return nCheckpoints;
	}

	/**
	 * @return the lastCheckpointId
	 */
	@ManagedAttribute
	public long getLastCheckpointId() {
		return lastCheckpointId;
	}

	/**
	 * @return the nFullINFlush
	 */
	@ManagedAttribute
	public long getNFullINFlush() {
		return nFullINFlush;
	}

	/**
	 * @return the nFullBINFlush
	 */
	@ManagedAttribute
	public long getNFullBINFlush() {
		return nFullBINFlush;
	}

	/**
	 * @return the nDeltaINFlush
	 */
	@ManagedAttribute
	public long getNDeltaINFlush() {
		return nDeltaINFlush;
	}

	/**
	 * @return the lastCheckpointStart
	 */
	@ManagedAttribute
	public long getLastCheckpointStart() {
		return lastCheckpointStart;
	}

	/**
	 * @return the lastCheckpointEnd
	 */
	@ManagedAttribute
	public long getLastCheckpointEnd() {
		return lastCheckpointEnd;
	}

	/**
	 * @return the endOfLog
	 */
	@ManagedAttribute
	public long getEndOfLog() {
		return endOfLog;
	}

	/**
	 * @return the cleanerBacklog
	 */
	@ManagedAttribute
	public int getCleanerBacklog() {
		return cleanerBacklog;
	}

	/**
	 * @return the nCleanerRuns
	 */
	@ManagedAttribute
	public long getNCleanerRuns() {
		return nCleanerRuns;
	}

	/**
	 * @return the nCleanerDeletions
	 */
	@ManagedAttribute
	public long getNCleanerDeletions() {
		return nCleanerDeletions;
	}

	/**
	 * @return the nINsObsolete
	 */
	@ManagedAttribute
	public long getNINsObsolete() {
		return nINsObsolete;
	}

	/**
	 * @return the nINsCleaned
	 */
	@ManagedAttribute
	public long getNINsCleaned() {
		return nINsCleaned;
	}

	/**
	 * @return the nINsDead
	 */
	@ManagedAttribute
	public long getNINsDead() {
		return nINsDead;
	}

	/**
	 * @return the nINsMigrated
	 */
	@ManagedAttribute
	public long getNINsMigrated() {
		return nINsMigrated;
	}

	/**
	 * @return the nLNsObsolete
	 */
	@ManagedAttribute
	public long getNLNsObsolete() {
		return nLNsObsolete;
	}

	/**
	 * @return the nLNsCleaned
	 */
	@ManagedAttribute
	public long getNLNsCleaned() {
		return nLNsCleaned;
	}

	/**
	 * @return the nLNsDead
	 */
	@ManagedAttribute
	public long getNLNsDead() {
		return nLNsDead;
	}

	/**
	 * @return the nLNsLocked
	 */
	@ManagedAttribute
	public long getNLNsLocked() {
		return nLNsLocked;
	}

	/**
	 * @return the nLNsMigrated
	 */
	@ManagedAttribute
	public long getNLNsMigrated() {
		return nLNsMigrated;
	}

	/**
	 * @return the nLNsMarked
	 */
	@ManagedAttribute
	public long getNLNsMarked() {
		return nLNsMarked;
	}

	/**
	 * @return the nLNQueueHits
	 */
	@ManagedAttribute
	public long getNLNQueueHits() {
		return nLNQueueHits;
	}

	/**
	 * @return the nPendingLNsProcessed
	 */
	@ManagedAttribute
	public long getNPendingLNsProcessed() {
		return nPendingLNsProcessed;
	}

	/**
	 * @return the nMarkedLNsProcessed
	 */
	@ManagedAttribute
	public long getNMarkedLNsProcessed() {
		return nMarkedLNsProcessed;
	}

	/**
	 * @return the nToBeCleanedLNsProcessed
	 */
	@ManagedAttribute
	public long getNToBeCleanedLNsProcessed() {
		return nToBeCleanedLNsProcessed;
	}

	/**
	 * @return the nClusterLNsProcessed
	 */
	@ManagedAttribute
	public long getNClusterLNsProcessed() {
		return nClusterLNsProcessed;
	}

	/**
	 * @return the nPendingLNsLocked
	 */
	@ManagedAttribute
	public long getNPendingLNsLocked() {
		return nPendingLNsLocked;
	}

	/**
	 * @return the nCleanerEntriesRead
	 */
	@ManagedAttribute
	public long getNCleanerEntriesRead() {
		return nCleanerEntriesRead;
	}

	/**
	 * @return the nSharedCacheEnvironments
	 */
	@ManagedAttribute
	public int getNSharedCacheEnvironments() {
		return nSharedCacheEnvironments;
	}

	/**
	 * @return the sharedCacheTotalBytes
	 */
	@ManagedAttribute
	public long getSharedCacheTotalBytes() {
		return sharedCacheTotalBytes;
	}

	/**
	 * @return the cacheTotalBytes
	 */
	@ManagedAttribute
	public long getCacheTotalBytes() {
		return cacheTotalBytes;
	}

	/**
	 * @return the bufferBytes
	 */
	@ManagedAttribute
	public long getBufferBytes() {
		return bufferBytes;
	}

	/**
	 * @return the dataBytes
	 */
	@ManagedAttribute
	public long getDataBytes() {
		return dataBytes;
	}

	/**
	 * @return the adminBytes
	 */
	@ManagedAttribute
	public long getAdminBytes() {
		return adminBytes;
	}

	/**
	 * @return the lockBytes
	 */
	@ManagedAttribute
	public long getLockBytes() {
		return lockBytes;
	}

	/**
	 * @return the nNotResident
	 */
	@ManagedAttribute
	public long getNNotResident() {
		return nNotResident;
	}

	/**
	 * @return the nCacheMiss
	 */
	@ManagedAttribute
	public long getNCacheMiss() {
		return nCacheMiss;
	}

	/**
	 * @return the nLogBuffers
	 */
	@ManagedAttribute
	public int getNLogBuffers() {
		return nLogBuffers;
	}

	/**
	 * @return the nRandomReads
	 */
	@ManagedAttribute
	public long getNRandomReads() {
		return nRandomReads;
	}

	/**
	 * @return the nRandomWrites
	 */
	@ManagedAttribute
	public long getNRandomWrites() {
		return nRandomWrites;
	}

	/**
	 * @return the nSequentialReads
	 */
	@ManagedAttribute
	public long getNSequentialReads() {
		return nSequentialReads;
	}

	/**
	 * @return the nSequentialWrites
	 */
	@ManagedAttribute
	public long getNSequentialWrites() {
		return nSequentialWrites;
	}

	/**
	 * @return the nRandomReadBytes
	 */
	@ManagedAttribute
	public long getNRandomReadBytes() {
		return nRandomReadBytes;
	}

	/**
	 * @return the nRandomWriteBytes
	 */
	@ManagedAttribute
	public long getNRandomWriteBytes() {
		return nRandomWriteBytes;
	}

	/**
	 * @return the nSequentialReadBytes
	 */
	@ManagedAttribute
	public long getNSequentialReadBytes() {
		return nSequentialReadBytes;
	}

	/**
	 * @return the nSequentialWriteBytes
	 */
	@ManagedAttribute
	public long getNSequentialWriteBytes() {
		return nSequentialWriteBytes;
	}

	/**
	 * @return the nFSyncs
	 */
	@ManagedAttribute
	public long getNFSyncs() {
		return nFSyncs;
	}

	/**
	 * @return the nFSyncRequests
	 */
	@ManagedAttribute
	public long getNFSyncRequests() {
		return nFSyncRequests;
	}

	/**
	 * @return the nFSyncTimeouts
	 */
	@ManagedAttribute
	public long getNFSyncTimeouts() {
		return nFSyncTimeouts;
	}

	/**
	 * @return the nRepeatFaultReads
	 */
	@ManagedAttribute
	public long getNRepeatFaultReads() {
		return nRepeatFaultReads;
	}

	/**
	 * @return the nTempBufferWrites
	 */
	@ManagedAttribute
	public long getNTempBufferWrites() {
		return nTempBufferWrites;
	}

	/**
	 * @return the nRepeatIteratorReads
	 */
	@ManagedAttribute
	public long getNRepeatIteratorReads() {
		return nRepeatIteratorReads;
	}

	/**
	 * @return the nFileOpens
	 */
	@ManagedAttribute
	public int getNFileOpens() {
		return nFileOpens;
	}

	/**
	 * @return the nOpenFiles
	 */
	@ManagedAttribute
	public int getNOpenFiles() {
		return nOpenFiles;
	}

	/**
	 * @return the totalLogSize
	 */
	@ManagedAttribute
	public long getTotalLogSize() {
		return totalLogSize;
	}

}
