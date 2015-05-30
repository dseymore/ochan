# Introduction #

Ochan has a very specific code layout to accomplish the goal of sweet sweet scalability and modularity to fit whatever hardware stack you have to throw at it.

# Deployment Models #
At the root of the deployment model is the Ochan artifact, which forms the basis of any of the nodes in the larger deployment. One executable jar file can act like any piece of the deployment, as well as serve as an entire deployment on a single piece of hardware.. anywhere from a old machine you have laying around to a server deployment. I've had great experience with a small amazon ec2 node holding on to ~ 100 simultaneous users.

<a href='http://ochan.googlecode.com/svn/trunk/src/styling/design/container.png'><img src='http://ochan.googlecode.com/svn/trunk/src/styling/design/container.png' /></a>

Inside of the Ochan artifact we have things broken up into the three tiers. The frontend/Web component is made of:
  * Velocity template engine
  * Spring MVC controllers
  * Restful Ajax/JSON enabled services connected by CXF
  * Restful urls for html content routed by carbonfive's pathparameter springmvc extension
  * CXF exposed SOAP webservices
  * Upload support from Apache Commons-upload
  * YUI and JQuery javascript components
  * All served up by the embedded Winstone servlet container
  * PDF & Image processing through pdfbox and the jre provdided resources

In our Service component we have:
  * Spring managed interface based services
  * JSON & XML parsing through jettison and xerces for our CXF exposed services
  * Caching provided by EhCache

In the DB component we have:
  * BerkeleyDB (Sleepycat) java edition persistence with Direct Persistence Layer entities


All three of the components use Commons logging with Log4j output, and management through JMX exposed by the spring framework annotations.

A simple one node deployment would look like this:

<a href='http://ochan.googlecode.com/svn/trunk/src/styling/design/1node.png'><img src='http://ochan.googlecode.com/svn/trunk/src/styling/design/1node.png' /></a>

Nothing special about it.. all communication between the components is done internal to the application, and performance is generally quite staggering. There is nothing else to deploy but this one thing, and all configuration is done through JMX with realtime impact, so the system can be running in seconds. This layout is at the heart of why this application is open sourced and free. The idea of having a simple to start, maintain, and use service for a small group or community of users is what drove the creation of Ochan.

But, lets say we want to scale up a bit.. Either the system is slow, or we want to gain some fault tolerance; we can introduce the replication.

<a href='http://ochan.googlecode.com/svn/trunk/src/styling/design/3nodes.png'><img src='http://ochan.googlecode.com/svn/trunk/src/styling/design/3nodes.png' /></a>

The above image shows how three nodes can work together as a single deployment. A load balancer at the top distributes the traffic load. We can either just use a simple load balancer to stick users on different hosts, or we can push reads out to the slave nodes, and writes to the master node (marked with an M).

Understanding why that would be important is a big deal. Take a look at the arrows between the service and db components of each deployment. Walk through an example of a user posting content to Ochan. If the post goes to a slave (far left or far right), then the traffic will be proxied to the master for the write (transfer #1) and then the db replication will send that data to both slaves (transfer #2). If the post goes to the master node, then the only transfer is from db to the replicated slaves.

While it may seem a trivial concept, it is very important concept to grasp when thinking about a large deployment of Ochan; sometimes just routing the traffic properly will stop you for needing more resources.

Now, lets say that replication is working fine, and you're able to handle the load no problem, but, you have an issue with the size of the content. The machines aren't able to keep everything in memory (check berkeleydb statistics). Or maybe you have so many writes that replication traffic is taking up too many resources on the master.

Sharding may be your answer (if you have the best hardware you can get). Here is a 6 node 2 shard deployment:

<a href='http://ochan.googlecode.com/svn/trunk/src/styling/design/6nodes.png'><img src='http://ochan.googlecode.com/svn/trunk/src/styling/design/6nodes.png' /></a>

Sharding is basically taking your data, and deciding to chop it into a certain number of pieces. We're not logically seperating it, but rather chopping it up by nothing more than the order it was created. But, there is a trade off here.. we need another part of Ochan to be deployed (either within the shards, or externally) to help orchestrate the direction of the data traffic. Introducing the Synchro service; In this environment we cannot rely on a single database to properly syncrhonize the data into a proper order; we have to replicate that functionality by seperating the db's syncrhonization property from the meat & potatoes of Ochan persistance.

And, here we have options as well. There is nothing stopping you from deploying a single synchro service node. It will work. However, since you've already bought yourself high availability in each shard of the deployment, strongly consider having atleast a single backup. And, again, leaning heavily to the master synchro service deployment will be your best friend.

Next stop, crazytown:

<a href='http://ochan.googlecode.com/svn/trunk/src/styling/design/31nodes.png'><img src='http://ochan.googlecode.com/svn/trunk/src/styling/design/31nodes.png' /></a>

Earlier in this document mentioned how each component could be broken up. We can have our web tier seperate from our service tier, and our service tier seperate from our db tier (with a service tier still attached for communication). Heck, we can have 3 layers of services between our web and the database. This diagram shows a little farther off the deep end in terms of how to scale.

The web nodes are our highest population in this diagram, their local service tier has a cache that will allow them to take a large amount of the read traffic away from the sharded & replicated database side of things. We have more shards, allowing us to isolate 25% of the writes to each replicated group; upping the amount of data and modifications we can do at once. Our cluster size is kept at 3, to maintain high availability, and cut down on internal network traffic for replication that isn't really being used; remember those caches. And our synchro service is left at a load balanced 3.

Now, these numbers are totally up in the air. Its an example of what you could do, and what Ochan does for you out of the box. Your mileage will definitely vary. Different load characteristics, configuration, and your users will make all the difference. Also, this doesn't even take into account what you could do with more caching in front of the Ochan deployment; A Distributed Squid cache may make our 6 node example just as powerful as the 31 node example.


# Design Model #
<a href='http://ochan.googlecode.com/svn/trunk/src/styling/design/pattern_proxy.png'><img src='http://ochan.googlecode.com/svn/trunk/src/styling/design/pattern_proxy.png' /></a>

Behind all of these deployment possibilities is a simple pattern to the design inside of Ochan. The proxy pattern is used on all 5 of the properly encapsulated services that make up Ochan.