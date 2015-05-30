# Introduction #
Ochan is a no-install embedded-run-a-ma-jobber that gets generated everytime you build the  application. Checking out the source includes everything you need to build AND run, besides apache ant.

# Details #
Dependencies are handled using Ivy, and the ivy dependencies are included in the code path, no configuration neccessary.

Once you've done that, you can simply use the following command, against jdk 1.6:

:>ant


## Deploying (normal/Winstone) ##
1. Start it

`java -jar scratch/Ochan.jar`

or, in debug/jmx mode

`java -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n -Dcom.sun.management.jmxremote -jar scratch/Ochan.jar`

## Deploying to Jetty ##
1. Copy the war to jetty

`cp scratch/Ochan.war /...../jetty-6.1.3/webapps/Ochan.war`

2. Start a local database (not currently starting internal to the war)

`java -cp hsqldb-1.8.0.7.jar org.hsqldb.Server -database.0 file:mydb -dbname.0 xdb`

3. Start jetty with JMX enabled and in debug mode

`java -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n -Dcom.sun.management.jmxremote -jar start.jar etc/jetty-jmx.xml etc/jetty.xml`

4. For good measure, and configuration, start jconsole

`jconsole`