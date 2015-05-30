## Atom Feed ##
  * Ochan aggregates all of the threads created across all categories into a single atom feed, with the date of the thread creation as the comparison. Each category and Each thread also has an independent feed.

## Ajax-enabled Thread Watching ##
  * When in 'reply-mode' on a thread, your browser is 'thread-watching'. What this means is that it makes a request for any 'new' posts in the background, and upon discovering one renders it and scrolls your browser to its location on screen. There is also a JMX accessible metric to see how many threads are being watched.

## Ajax-enabled Thread Awareness ##
  * When sitting on the main page, 'Growl' (toast) notifications appears to inform the user of new threads that have just been created. There is also a JMX accessible metric to see how many users are watching the main page.

## Ajax-enabled Thread Watching ##
  * Click 'watch' and continue to browse through the channel, and you'll be notified when someone has posted a response or your thread has been deleted. Live.. rather than with the delay Atom/RSS Feeds can usually have.

## Thread Deletion ##
  * All users can 'nominate' a thread for deletion. All users watching a thread can see the status of the deletion (as it goes back and forth from alive (delete link) to about-to-die (undelete link), and the number of actions) in real time. The idea being 'whomever cares the most about the health or death of a thread will maintain it.
  * Both the number of actions till locking and the queue time before deletion are configurable.

## Thread Images Export & Import ##
  * A thread's images can be exported directly to a zip file.
  * When in a Thread view, you can upload a zipfile of images and a post will be created for each image.

## Announcement & Title ##
  * The announcement section of the main page is configurable via JMX
  * The title of the generated pages is configurable via JMX

## Host encapsulation ##
  * In a step to introduce mirroring, clustering, and service partitioning; host encapsulation  was introduced. This allows ochan to be aware of the categories hosted on an alternate Ochan install, and allow users to be directed to those other hosts to view those categories.

## In JVM image thumbnailing ##
  * Everything that Ochan needs out of the box is in the jar; Including resizing the uploaded images into thumbnails. Even creating thumbnails from PDFs! This cuts down on any installation time or any setup or dependency gathering.

## Recent Images ##
  * A scrolling block of images that shows the users the most recent images posted to the channel. They can be clicked on to take you right to the post.

## Scalability ##
  * Built in sharding, middle tier caching, and replication components allow you to scale your deployment out. Advanced BerkeleyDB/SleepyCat storage engine under a highly scalable architecture guaruntee performance and capacity wont be a problem.

## Basic Features ##
  * Author capturing, with optional email.
  * Keyboard Controls
  * Stateful category paging (No more getting lost in the heavy traffic)
  * Every post can have a subject, an image, and some HTML (WYSIWYG editor) text.
  * Each instance of ochan can have multiple categories. (Configurable via JMX)
  * Currently, there are an unlimited number of categories, and posts within those categories. (Thought, post & thread limits can be conifugred)
  * Simple to start (java -jar Ochan.jar) and simple to use. (create a category, and start posting)
  * Hidden 'non-rich-client' forms for text-based browser support.
  * Tripcodes (Type a username as User#Code)

## What is missing? ##
  * Unique sequences per category
  * Administration (banning)