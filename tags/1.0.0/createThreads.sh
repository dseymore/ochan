#!/bin/bash

#lets create some threads

NUM_THREADS=0
STARTING_ID=1302
CATEGORY_ID=101

#echo $(($STARTING_ID + $NUM_THREADS))

for i in `seq $STARTING_ID $(($STARTING_ID + $NUM_THREADS))`;
do
	java -jar /opt/cmdline-jmxclient-0.10.3.jar - localhost:8000 Ochan:name=LoaderUtility,type=util createThread=$CATEGORY_ID,"X"
done

echo "Done creating $NUM_THREADS threads."

find /mnt/raid/4chan/ | while read file; 
do
	for i in `seq $STARTING_ID $(($STARTING_ID + $NUM_THREADS))`;
	do
		java -jar /opt/cmdline-jmxclient-0.10.3.jar - localhost:8000 Ochan:name=LoaderUtility,type=util createPost=$i,"$file"; 
		du -h "$file";
	done;
done
