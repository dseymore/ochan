# Requirements #
**Sun JDK 1.6 update 10 or later** Jconsole or jvisualVM (both are in the jdk bin/ directory)

# Steps #
  1. make sure your path is configured correctly so that you have the java command executing the correct version of the jdk. (verify with java -version)
  1. Execute java -jar Ochan.jar (optional add --httpPort=80 to change default port 8080 to port 80, or any port of your choice)
  1. Direct your browser to localhost:8080


## Configuration  (REQUIRED STEPS) ##
  1. Now that you have it deployed, startup the mbean browser of jconsole or jvisualvm.
  1. Go into System->DeploymentConfiguration and set the absolute url (or hostname if you have a dns name setup) that users use to get to the application, and the port.
  1. Go to Ochan->local->CategoryService and Add at least one category to begin your imageboard.
  1. All other defaults are normally acceptable. A configuration catalog will be created in the future.


## Some startup arguments that may help ##
### Bumping up the maximum memory.. 1GiB ###
```
-Xmx1024M
```
### Using concurrent garbage collection ###
```
-XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode 
```
### Flagging the service as headless (maybe required for thumbnails) ###
```
-Djava.awt.headless=true
```
### Opening up remote management without a password (FIREWALL THIS!) ###
```
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.local.only=false
-Dcom.sun.management.jmxremote.authenticate=false 
-Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote 
-Dcom.sun.management.jmxremote.port=8000 -Djava.rmi.server.hostname=<your externally reachable IP>
```

# Advanced Deployments #
## Replication ##
  * TODO: move this configuration to preferences/jmx.
  * Name must be the full path to the root of the ochan deployment
  * For the first node to start, it must have itself as the helper
```
java -Dbdb.rep=true -Dbdb.rep.group=local -Dbdb.rep.name="http://192.168.1.110:8080" -Dbdb.rep.bind="192.168.1.110:5001" -Dbdb.rep.helper="192.168.1.110:5001" -jar Ochan.jar
```
## Sharding ##