Building:

Just type ant :)

For unit testing:
ant -lib tools/apache-ivy-2.0.0-alpha2-incubating/ -lib tools/pmd-4.0/lib/ clean war


Deploying:
I've been deploying to Jetty, and modifying configuration with Jconsole. 

First I start an independent hsqldb:
java -cp hsqldb-1.8.0.7.jar org.hsqldb.Server -database.0 file:mydb -dbname.0 xdb


then start jetty in debug & jmx mode:
java -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n -Dcom.sun.management.jmxremote -jar start.jar etc/jetty-jmx.xml etc/jetty.xml


You've got a working copy of Ochan now! 