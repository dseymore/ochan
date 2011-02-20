Building:

Just type mvn clean install

(And, for the dependencies that aren't in maven's repo... do these commands:)
mvn install:install-file -DgroupId=jquery -DartifactId=jgrowl -Dversion=1.2.0 -Dpackaging=war -Dfile=./jgrowl-1.2.0.war
mvn install:install-file -DgroupId=javax.activation -DartifactId=activation -Dversion=1.0.2 -Dpackaging=jar -Dfile=./activation-1.1.jar
mvn install:install-file -DgroupId=yahoo -DartifactId=yui -Dversion=2.7.0 -Dpackaging=war -Dfile=./yui-2.7.0.war
mvn install:install-file -DgroupId=com.sleepycat -DartifactId=je -Dversion=3.3.75 -Dpackaging=jar -Dfile=./je-3.3.75.jar
mvn install:install-file -DgroupId=carbonfive -DartifactId=pathparameter -Dversion=r686 -Dpackaging=jar -Dfile=./pathparameter-r686.jar
mvn install:install-file -DgroupId=jquery -DartifactId=jquery -Dversion=1.3.2 -Dpackaging=war -Dfile=./jquery-1.3.2.war
mvn install:install-file -DgroupId=sencha -DartifactId=sencha-touch -Dversion=1.0.1a -Dpackaging=war -Dfile=./sencha-touch-1.0.1a.war


Deploying:

just type java -jar target/*.jar

You've got a working copy of Ochan now! 
