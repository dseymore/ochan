## Release 1.0.0 ##
  * 70! commits since last release
  * Clustering: replicated datastore for large scale deployments (2 or more nodes)
  * Sharding: proxy service model to break off to any size shard; and a syncrho service for even larger scale deployments.
  * Middle tier proxy level caching.. used in both small and large scale deployments
  * robots.txt to avoid crawlers deleting the world
  * 404 error page
  * Application based thread watching; allowing users to know when a thread is commented on without Atom Feeds or keeping that tab open.
  * Javascript minification and proper cache headers for assets.
  * Updated main page for consistent look & feel
  * Recent Images automatically scrolls through images as they are posted :D
  * License to GPL V2 applied to all source code.

## Release 0.2.5 ##
  * Thread level atom feeds
  * File size captured & displayed to users
  * PDF file upload/thumbnail support
  * Live statistics updated on the main page
  * UTF internationalization support (UTF-8)
  * Allows user to enable or disable auto-scrolling
  * Saving remote file names for url-based submissions

## Release 0.2.3 ##
  * No API changes
  * Refactored frontend to be YUI & JQuery instead of extjs
  * Keyboard controls
  * Category level paging

## Release 0.2.3 ##
  * Job type plugin support
  * Long/large post truncation on category view
  * Users can no longer post 'nothing' (empty Zip, no image, or no content)

## Release 0.2.2 ##
  * Tripcodes are working again
  * Thread Export throttling capable
  * Atom feed exposed through html link

## Release 0.2.1 (was 0.1.6) ##
"Goodbye Database"

  * The HSQLDB & H2 database backends leveraging JPA & Hibernate have been replaced with BerkeleyDB (JE or Sleepycat. lotsa names)
  * Configurable Request Throttling for thumbnail generation.
  * BerkeleyDB self-management, and monitoring through JMX
  * External Categories regressioned, and presentation corrected.
  * Blob Statistics persistence, saving the trouble of runtime re-computation of disk usage
  * Job last-run-time in a human readable format

## Rev 100 "Where'd my Images go!" ##
As part of [Issue #10](http://code.google.com/p/ochan/issues/detail?id=10)

The ImagePost entity was a heavily cached entity. however, that caching was inadvertently caching the entire binary data.. but, not in a useful way.. it was only being used for the atom feeds and the front page... and not even to retrieve the binary data.. just wasting memory.

With the config of "-Xmx2056m -Xms1024m -XX:MaxPermSize=512m" and the same sample set of posts.. ram usage was down from 1,000 to 250 megabytes.

### Before ###

![http://ochan.googlecode.com/files/ochan-0.0.3-mem.png](http://ochan.googlecode.com/files/ochan-0.0.3-mem.png)

### After ###

![http://ochan.googlecode.com/files/ochan-r100-mem.png](http://ochan.googlecode.com/files/ochan-r100-mem.png)