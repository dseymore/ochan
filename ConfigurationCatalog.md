# Ochan:name=PluginJob,type=job #
```
Attributes:
 Cron: Cron expression for firing job (type=java.lang.String)
 PluginConfiguration: Plugin Configuration is a json string (type=java.lang.String)
Operations:
 getPluginConfiguration: Plugin Configuration is a json string
  Parameters 0, return type=java.lang.String
 setPluginConfiguration: Plugin Configuration is a json string
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
 lastRunTime: The last time the job was run.
  Parameters 0, return type=java.lang.String
 getCron: Cron expression for firing job
  Parameters 0, return type=java.lang.String
 setCron: Cron expression for firing job
  Parameters 1, return type=void
   name=p1 type=java.lang.String
```

# Ochan:name=Post,type=rest #
```
Attributes:
 NextGetCount: The number of calls received for a next post. This is used by the ActiveWatcherCounterJob to determine how many thread watches are open. (type=java.lang.Long)
Operations:
 getNextGetCount: The number of calls received for a next post. This is used by the ActiveWatcherCounterJob to determine how many thread watches are open.
  Parameters 0, return type=java.lang.Long
```

# Ochan:utility=RemoteHostList #
```
Attributes:
 ExternalHosts: Returns a list of all hosts (type=java.lang.String)
Operations:
 addHost: Scans a host for categories.
  Parameters 1, return type=void
   name=HostName type=java.lang.String The full hostname
 flushHosts: Removes all external hosts listings
  Parameters 0, return type=void
 getExternalHosts: Returns a list of all hosts
  Parameters 0, return type=java.lang.String
```

# Ochan:name=LocalPostService,service=local #
```
Attributes:
 CreateCount: The number of calls to create a category (type=long)
 DeleteCount: The number of calls to delete a category. (type=long)
 GetCount: The number of calls to get a category. (type=long)
 LastSearchTime: The time in milliseconds of the last call to search for categories. (type=long)
Operations:
 getCreateCount: The number of calls to create a category
  Parameters 0, return type=long
 getGetCount: The number of calls to get a category.
  Parameters 0, return type=long
 getDeleteCount: The number of calls to delete a category.
  Parameters 0, return type=long
 getLastSearchTime: The time in milliseconds of the last call to search for categories.
  Parameters 0, return type=long
 deletePost: Delete a Post!
  Parameters 1, return type=void
   name=identifier type=java.lang.Long The id of the post as a Long object (L at the end)
```

# Ochan:name=LocalAnnouncementService,service=local #
```
Attributes:
 Announcement: Retrieve the current announcement. (type=java.lang.String)
Operations:
 getAnnouncement: Retrieve the current announcement.
  Parameters 0, return type=java.lang.String
 setAnnouncement: Retrieve the current announcement.
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
```

# Ochan:name=LocalBlobService,service=local #
```
Attributes:
 CreateCount: The number of calls to create a blob (type=long)
 DeleteCount: The number of calls to delete a blob. (type=long)
 GetCount: The number of calls to get a blob (type=long)
 LastGetTime: The time in nanoseconds of the last call to get a blob. (type=long)
 LastSaveTime: The time in nanoseconds of the last call to save a blob. (type=long)
 LastSearchTime: The time in nanoseconds of the last call to search for a list of all blobs. (type=long)
Operations:
 getCreateCount: The number of calls to create a blob
  Parameters 0, return type=long
 getGetCount: The number of calls to get a blob
  Parameters 0, return type=long
 getDeleteCount: The number of calls to delete a blob.
  Parameters 0, return type=long
 getLastSearchTime: The time in nanoseconds of the last call to search for a list of all blobs.
  Parameters 0, return type=long
 getLastSaveTime: The time in nanoseconds of the last call to save a blob.
  Parameters 0, return type=long
 getLastGetTime: The time in nanoseconds of the last call to get a blob.
  Parameters 0, return type=long
```

# Ochan:name=LocalThreadService,service=local #
```
Attributes:
 CreateCount: The number of calls to create a thread (type=long)
 DeleteCount: The number of calls to delete a thread. (type=long)
 GetCount: The number of calls to get a thread. (type=long)
 LastSearchTime: The time in milliseconds of the last call to search for threads. (type=long)
Operations:
 getCreateCount: The number of calls to create a thread
  Parameters 0, return type=long
 getGetCount: The number of calls to get a thread.
  Parameters 0, return type=long
 getDeleteCount: The number of calls to delete a thread.
  Parameters 0, return type=long
 getLastSearchTime: The time in milliseconds of the last call to search for threads.
  Parameters 0, return type=long
 deleteThread: Delete a Thread (delete the posts first...)!
  Parameters 1, return type=void
   name=identifier type=java.lang.Long The id of the thread as a Long object (L at the end)
 unlockAndResetThread: Unlock a locked thread & reset it's count
  Parameters 1, return type=void
   name=identifier type=java.lang.Long The id of the thread as a Long object (L at the end)
```


# Ochan:name=LocalCategoryService,service=local #
```
Attributes:
 CreateCount: The number of calls to create a category (type=long)
 DeleteCount: The number of calls to delete a category. (type=long)
 GetCount: The number of calls to get a category. (type=long)
 LastSearchTime: The time in milliseconds of the last call to search for categories. (type=long)
Operations:
 getCreateCount: The number of calls to create a category
  Parameters 0, return type=long
 getGetCount: The number of calls to get a category.
  Parameters 0, return type=long
 getDeleteCount: The number of calls to delete a category.
  Parameters 0, return type=long
 getLastSearchTime: The time in milliseconds of the last call to search for categories.
  Parameters 0, return type=long
 createCategory: Create a new Category!
  Parameters 2, return type=void
   name=name type=java.lang.String The name of the category
   name=description type=java.lang.String The description of the category
 deleteCategory: Delete a Category!
  Parameters 1, return type=void
   name=identifier type=java.lang.Long The id of the category as a Long object (L at the end)
```

# Ochan:name=StatisticsGeneration,type=job #
```
Attributes:
 Cron: Cron expression for firing job (type=java.lang.String)
 DataSize: The size in bytes of all files stored. (type=java.lang.Long)
 FileCount: The number of files stored. (type=java.lang.Long)
 ImagePostCount: The number of image posts in the system. (type=java.lang.Long)
 LastSearchTime: The time in milliseconds of the last call to search for a list of all blobs. (type=long)
 PostCount: The number of posts in the system. (type=java.lang.Long)
 ThreadCount: The number of threads in the system. (type=java.lang.Long)
Operations:
 getThreadCount: The number of threads in the system.
  Parameters 0, return type=java.lang.Long
 getLastSearchTime: The time in milliseconds of the last call to search for a list of all blobs.
  Parameters 0, return type=long
 getFileCount: The number of files stored.
  Parameters 0, return type=java.lang.Long
 getPostCount: The number of posts in the system.
  Parameters 0, return type=java.lang.Long
 getImagePostCount: The number of image posts in the system.
  Parameters 0, return type=java.lang.Long
 getDataSize: The size in bytes of all files stored.
  Parameters 0, return type=java.lang.Long
 getCron: Cron expression for firing job
  Parameters 0, return type=java.lang.String
 setCron: Cron expression for firing job
  Parameters 1, return type=void
   name=p1 type=java.lang.String
```

# Ochan:name=ErrorStatistics,utility=logging #
```
Attributes:
 Status: Makes a report of all excpetions. (type=java.lang.String)
Operations:
 getStatus: Makes a report of all excpetions.
  Parameters 0, return type=java.lang.String
```

# Ochan:name=ThreadCollector,type=feed #
```
Attributes:
 GetCount: The number of calls to get the feed (type=long)
 LastSearchTime: The time in milliseconds of the last call to search for threads. (type=long)
Operations:
 getGetCount: The number of calls to get the feed
  Parameters 0, return type=long
 getLastSearchTime: The time in milliseconds of the last call to search for threads.
  Parameters 0, return type=long
```

# Ochan:name=ActiveWatcherCounter,type=job #
```
Attributes:
 Cron: Cron expression for firing job (type=java.lang.String)
 LastMainPageWatcherAmount: Based on the time of the job, this rounds out a value of the number of Main Page watchers currently open. (type=java.lang.Long)
 LastThreadWatcherAmount: Based on the time of the job, this rounds out a value of the number of thread watchers currently open. (type=java.lang.Long)
Operations:
 getLastThreadWatcherAmount: Based on the time of the job, this rounds out a value of the number of thread watchers currently open.
  Parameters 0, return type=java.lang.Long
 getLastMainPageWatcherAmount: Based on the time of the job, this rounds out a value of the number of Main Page watchers currently open.
  Parameters 0, return type=java.lang.Long
 getCron: Cron expression for firing job
  Parameters 0, return type=java.lang.String
 setCron: Cron expression for firing job
  Parameters 1, return type=void
   name=p1 type=java.lang.String
```

# Ochan:name=DeleteThreadJob,type=job #
```
Attributes:
 Cron: Cron expression for firing job (type=java.lang.String)
 DeleteMarksBeforeLock: The number of times a thread can be marked deleted, and then marked opposite, before being locked. (type=java.lang.String)
 DeleteWaitTime: The time in milliseconds to wait after a thread is marked for deletion before doing the actual deletion (type=java.lang.String)
Operations:
 getDeleteWaitTime: The time in milliseconds to wait after a thread is marked for deletion before doing the actual deletion
  Parameters 0, return type=java.lang.String
 setDeleteWaitTime: The time in milliseconds to wait after a thread is marked for deletion before doing the actual deletion
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
 getDeleteMarksBeforeLock: The number of times a thread can be marked deleted, and then marked opposite, before being locked.
  Parameters 0, return type=java.lang.String
 setDeleteMarksBeforeLock: The number of times a thread can be marked deleted, and then marked opposite, before being locked.
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
 getCron: Cron expression for firing job
  Parameters 0, return type=java.lang.String
 setCron: Cron expression for firing job
  Parameters 1, return type=void
   name=p1 type=java.lang.String
```

# Ochan:name=Thumnbailer,util=controller #
```
Attributes:
 AverageResizeTimeInMillis: The average time in milliseconds the system is encountering on thumnbail resize requests (type=long)
 AverageTimeInMillis: The average time in milliseconds the system is encountering on thumnbail requests (type=long)
 GenerateWaitTime: The average time a thumbnail generation request waits around being throttled (type=double)
 LastResizeTimeInMillis: The last time we generated a thumb in milliseconds (type=long)
 LastTimeInMIllis: The last time we generated retrieved in milliseconds (type=long)
 NumberOfRequests: The number of images that have been requested (type=long)
 NumberOfThumbs: The number of thumbnails that have been requested (type=long)
 RequestWaitTime: The average time a requests waits around being throttled (type=double)
 RequestsPerMinute: The number of requests the thumbnailer will pump out a minute (type=java.lang.String)
 ThumbHeight: The height of the image in pixels. (type=java.lang.Long)
 ThumbWidth: The width of the image in pixels. (type=java.lang.Long)
 ThumbnailGenerationsPerMinute: The number of thumbnails that can be generated per minute (type=java.lang.String)
 ThumbnailImageQuality: The quality of the thumbnail compression from 0 to 1. (type=java.lang.String)
 TimeLength: The time in milliseconds that is threshold before logging exceptions due to poor performance. (type=java.lang.Long)
 TotalResizeTimeInMIllis: The total time in milliseconds it has taken to generate all thumbs (type=long)
 TotalTimeInMIllis: The total time in milliseconds it has taken to retrieve all images (type=long)
Operations:
 getRequestWaitTime: The average time a requests waits around being throttled
  Parameters 0, return type=double
 getGenerateWaitTime: The average time a thumbnail generation request waits around being throttled
  Parameters 0, return type=double
 getThumbnailImageQuality: The quality of the thumbnail compression from 0 to 1.
  Parameters 0, return type=java.lang.String
 setThumbnailImageQuality: The quality of the thumbnail compression from 0 to 1.
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
 getRequestsPerMinute: The number of requests the thumbnailer will pump out a minute
  Parameters 0, return type=java.lang.String
 setRequestsPerMinute: The number of requests the thumbnailer will pump out a minute
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
 getThumbnailGenerationsPerMinute: The number of thumbnails that can be generated per minute
  Parameters 0, return type=java.lang.String
 setThumbnailGenerationsPerMinute: The number of thumbnails that can be generated per minute
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
 getThumbWidth: The width of the image in pixels.
  Parameters 0, return type=java.lang.Long
 setThumbWidth: setThumbWidth
  Parameters 1, return type=void
   name=value type=java.lang.String Width in pixels
 getThumbHeight: The height of the image in pixels.
  Parameters 0, return type=java.lang.Long
 setThumbHeight: setThumbHeight
  Parameters 1, return type=void
   name=value type=java.lang.String Height in pixels
 getTimeLength: The time in milliseconds that is threshold before logging exceptions due to poor performance.
  Parameters 0, return type=java.lang.Long
 setTimeLength: setTimeLength
  Parameters 1, return type=void
   name=value type=java.lang.String Time in Milliseconds
 getAverageTimeInMillis: The average time in milliseconds the system is encountering on thumnbail requests
  Parameters 0, return type=long
 getNumberOfThumbs: The number of thumbnails that have been requested
  Parameters 0, return type=long
 getLastResizeTimeInMillis: The last time we generated a thumb in milliseconds
  Parameters 0, return type=long
 getTotalResizeTimeInMIllis: The total time in milliseconds it has taken to generate all thumbs
  Parameters 0, return type=long
 getAverageResizeTimeInMillis: The average time in milliseconds the system is encountering on thumnbail resize requests
  Parameters 0, return type=long
 getNumberOfRequests: The number of images that have been requested
  Parameters 0, return type=long
 getLastTimeInMIllis: The last time we generated retrieved in milliseconds
  Parameters 0, return type=long
 getTotalTimeInMIllis: The total time in milliseconds it has taken to retrieve all images
  Parameters 0, return type=long
```

# Ochan:name=ThreadZip,util=controller #
```
Attributes:
 AverageGenerationTimeInMillis: The average time in milliseconds the system is encountering on generating zip requests (type=long)
 AverageTimeInMillis: The average time in milliseconds the system is encountering on zip requests (type=long)
 LastGenerationTimeInMillis: The last time we generated a zip in milliseconds (type=long)
 LastTimeInMIllis: The last time we generated & transferred a zip in milliseconds (type=long)
 NumberOfZips: The number of zips that have been requested (type=long)
 TimeLength: The time in milliseconds that is threshold before logging exceptions due to poor performance. (type=java.lang.Long)
 TotalGenerationTimeInMIllis: The total time in milliseconds it has taken to generate all zips (type=long)
 TotalTimeInMIllis: The total time in milliseconds it has taken to generate & transferred all zips (type=long)
Operations:
 getTimeLength: The time in milliseconds that is threshold before logging exceptions due to poor performance.
  Parameters 0, return type=java.lang.Long
 setTimeLength: setTimeLength
  Parameters 1, return type=void
   name=value type=java.lang.String Time in Milliseconds
 getAverageTimeInMillis: The average time in milliseconds the system is encountering on zip requests
  Parameters 0, return type=long
 getLastTimeInMIllis: The last time we generated & transferred a zip in milliseconds
  Parameters 0, return type=long
 getTotalTimeInMIllis: The total time in milliseconds it has taken to generate & transferred all zips
  Parameters 0, return type=long
 getAverageGenerationTimeInMillis: The average time in milliseconds the system is encountering on generating zip requests
  Parameters 0, return type=long
 getNumberOfZips: The number of zips that have been requested
  Parameters 0, return type=long
 getLastGenerationTimeInMillis: The last time we generated a zip in milliseconds
  Parameters 0, return type=long
 getTotalGenerationTimeInMIllis: The total time in milliseconds it has taken to generate all zips
  Parameters 0, return type=long
```

# System:name=UserCounter,type=statistics #
```
Attributes:
 SessionCount: retrieves the current number of sessions the server is holding on to. (type=int)
Operations:
 getSessionCount: retrieves the current number of sessions the server is holding on to.
  Parameters 0, return type=int
```

# System:name=LoggingConfiguration,type=config #
```
Operations:
 changeLogFile: Changes the file of the rolling logger, pass in the absolute WRITEABLE path
  Parameters 1, return type=void
   name=absolutePath type=java.lang.String Log4j will start using the logfile passed in. The user running the code must have write access to the path.
 changeLogDepth: Changes the length of log files, and the maximum number to store. numberOfFiles is an INTEGER, sizeOfFiles is a STRING (You can specify the value with the suffixes "KB", "MB" or "GB" so that the integer is interpreted being expressed respectively in kilobytes, megabytesor gigabytes. For example, the value "10KB" will be interpretedas 10240.)
  Parameters 2, return type=void
   name=Number of log files type=java.lang.String Log4j will store this many log files before it starts deleting.
   name=Size of log file type=java.lang.String Log4j will keep using one file until it reaches the size passed in, before moving to a new file.
 changeLogLevel: Changes the threshold of the log file, pass in one of these options (DEBUG, INFO, WARN, ERROR, FATAL) (From greatest to least amount of logs)
  Parameters 1, return type=void
   name=Level type=java.lang.String Log4j level, from most to lease verbose: (DEBUG, INFO, WARN, ERROR, FATAL)
 addRemoteLogger: Adds a remote socket appender.
  Parameters 2, return type=void
   name=ipAddress type=java.lang.String The IP Address of the socket client.
   name=port type=java.lang.String The Port number for the client.
 fetchRemoteLoggerNames: Gets a list of the remote logger names
  Parameters 0, return type=java.util.List
 removeRemoteLogger: Removes a remote socket appender.
  Parameters 1, return type=void
   name=name type=java.lang.String Name of the appender to remove
```

# System:name=UploadConfiguration,type=config #
```
Attributes:
 MaxUploadSize: The filesize in bytes for the maximum upload size (type=java.lang.String)
Operations:
 getMaxUploadSize: The filesize in bytes for the maximum upload size
  Parameters 0, return type=java.lang.String
 setMaxUploadSize: The filesize in bytes for the maximum upload size
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
```

# System:name=DeploymentConfiguration,type=config #
```
Attributes:
 Hostname: The hostname the server is reachable at (type=java.lang.String)
 Port: The port the server is reachable at (type=java.lang.String)
 PostLimit: The maximum number of posts in a thread before disabling posting (type=java.lang.String)
 ThreadLimit: The maximum number of threads in a category before disabling posting (type=java.lang.String)
 Title: The title of the application deployment (type=java.lang.String)
Operations:
 getHostname: The hostname the server is reachable at
  Parameters 0, return type=java.lang.String
 setHostname: The hostname the server is reachable at
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
 getPostLimit: The maximum number of posts in a thread before disabling posting
  Parameters 0, return type=java.lang.String
 setPostLimit: The maximum number of posts in a thread before disabling posting
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
 getThreadLimit: The maximum number of threads in a category before disabling posting
  Parameters 0, return type=java.lang.String
 setThreadLimit: The maximum number of threads in a category before disabling posting
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
 getPort: The port the server is reachable at
  Parameters 0, return type=java.lang.String
 getTitle: The title of the application deployment
  Parameters 0, return type=java.lang.String
 setTitle: The title of the application deployment
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
 setPort: The port the server is reachable at
  Parameters 1, return type=void
   name=p1 type=java.lang.String 
```


# Ochan:name=SleepycatStatistics,type=job #
```
Attributes:
 NBINsStripped: NBINsStripped (type=long)
 NCacheMiss: NCacheMiss (type=long)
 NCheckpoints: NCheckpoints (type=long)
 NCleanerDeletions: NCleanerDeletions (type=long)
 NCleanerEntriesRead: NCleanerEntriesRead (type=long)
 NCleanerRuns: NCleanerRuns (type=long)
 NClusterLNsProcessed: NClusterLNsProcessed (type=long)
 NDeltaINFlush: NDeltaINFlush (type=long)
 NEvictPasses: NEvictPasses (type=long)
 NFSyncRequests: NFSyncRequests (type=long)
 NFSyncTimeouts: NFSyncTimeouts (type=long)
 NFSyncs: NFSyncs (type=long)
 NFileOpens: NFileOpens (type=int)
 NFullBINFlush: NFullBINFlush (type=long)
 NFullINFlush: NFullINFlush (type=long)
 NINsCleaned: NINsCleaned (type=long)
 NINsDead: NINsDead (type=long)
 NINsMigrated: NINsMigrated (type=long)
 NINsObsolete: NINsObsolete (type=long)
 NLNQueueHits: NLNQueueHits (type=long)
 NLNsCleaned: NLNsCleaned (type=long)
 NLNsDead: NLNsDead (type=long)
 NLNsLocked: NLNsLocked (type=long)
 NLNsMarked: NLNsMarked (type=long)
 NLNsMigrated: NLNsMigrated (type=long)
 NLNsObsolete: NLNsObsolete (type=long)
 NLogBuffers: NLogBuffers (type=int)
 NMarkedLNsProcessed: NMarkedLNsProcessed (type=long)
 NNodesExplicitlyEvicted: NNodesExplicitlyEvicted (type=long)
 NNodesScanned: NNodesScanned (type=long)
 NNodesSelected: NNodesSelected (type=long)
 NNotResident: NNotResident (type=long)
 NOpenFiles: NOpenFiles (type=int)
 NPendingLNsLocked: NPendingLNsLocked (type=long)
 NPendingLNsProcessed: NPendingLNsProcessed (type=long)
 NRandomReadBytes: NRandomReadBytes (type=long)
 NRandomReads: NRandomReads (type=long)
 NRandomWriteBytes: NRandomWriteBytes (type=long)
 NRandomWrites: NRandomWrites (type=long)
 NRepeatFaultReads: NRepeatFaultReads (type=long)
 NRepeatIteratorReads: NRepeatIteratorReads (type=long)
 NRootNodesEvicted: NRootNodesEvicted (type=long)
 NSequentialReadBytes: NSequentialReadBytes (type=long)
 NSequentialReads: NSequentialReads (type=long)
 NSequentialWriteBytes: NSequentialWriteBytes (type=long)
 NSequentialWrites: NSequentialWrites (type=long)
 NSharedCacheEnvironments: NSharedCacheEnvironments (type=int)
 NTempBufferWrites: NTempBufferWrites (type=long)
 NToBeCleanedLNsProcessed: NToBeCleanedLNsProcessed (type=long)
 AdminBytes: adminBytes (type=long)
 BufferBytes: bufferBytes (type=long)
 CacheTotalBytes: cacheTotalBytes (type=long)
 CleanerBacklog: cleanerBacklog (type=int)
 Cron: Cron expression for firing job (type=java.lang.String)
 CursorsBins: cursorsBins (type=long)
 DataBytes: dataBytes (type=long)
 DbClosedBins: dbClosedBins (type=long)
 EndOfLog: endOfLog (type=long)
 InCompQueueSize: inCompQueueSize (type=long)
 LastCheckpointEnd: lastCheckpointEnd (type=long)
 LastCheckpointId: lastCheckpointId (type=long)
 LastCheckpointStart: lastCheckpointStart (type=long)
 LockBytes: lockBytes (type=long)
 NonEmptyBins: nonEmptyBins (type=long)
 ProcessedBins: processedBins (type=long)
 RequiredEvictBytes: requiredEvictBytes (type=long)
 SharedCacheTotalBytes: sharedCacheTotalBytes (type=long)
 SplitBins: splitBins (type=long)
 TotalLogSize: totalLogSize (type=long)
=Ochan:name=LoaderUtility,type=util (Used for some load testing and batch loads)=
Attributes:
 CreateThreadTime: Getting the time it took to create the thread. (type=java.lang.Long)
 ReadFileTime: Getting the time it took to read the file. (type=java.lang.Long)
Operations:
 createThread: Allows threads to be quickly created via jmx
  Parameters 2, return type=void
   name=categoryId type=java.lang.Long Category Id
   name=filepath type=java.lang.String File path
 getReadFileTime: Getting the time it took to read the file.
  Parameters 0, return type=java.lang.Long
 getCreateThreadTime: Getting the time it took to create the thread.
  Parameters 0, return type=java.lang.Long
```

# Ochan:name=CheckpointJob,type=job #
```
Attributes:
 Cron: Cron expression for firing job (type=java.lang.String)
Operations:
 lastRunTime: The last time the job was run.
  Parameters 0, return type=java.lang.String
 getCron: Cron expression for firing job
  Parameters 0, return type=java.lang.String
 setCron: Cron expression for firing job
  Parameters 1, return type=void
   name=p1 type=java.lang.String
```