#!/bin/bash

#1 - directory to scan files from

#generate the file
find $1 | while read line
do
	echo \"$line\" \"localhost:8080/openCategory.Ochan\" 1 1 >> script.txt
done

#Pass the arguments to xargs to queue
cat script.txt | xargs -n4 -P8 ./callCurl.sh 

#Cleanup!
rm script.txt


