#! /bin/bash

###ARGUMENTS
###1 - file path (or the word text for text posts)
###2 - the url
###3 - the parent id (for thread posts (use viewThread.Ochan), otherwise (openCategory.Ochan))
###4 - number of text posts to create (only if type text)

###FIXME -make this better.. ugh.. annoying.. numbered parameters. 

###FIXME - make backgrounding optional, and also.. make it cofnigurable the number to be backgrounded at 1 time.. (which.. might be very hard)

### Creating text entries
##./createThreads_curl.sh text localhost:8080/viewThread.Ochan 63 1000
### Creating file entries from a directory of files
##./createThreads_curl.sh /mnt/raid/4chan localhost:8080/viewThread.Ochan 54

if [ -z $1 ]
then
echo "You forgot the command line argument.. evil things will happen."
fi

url="localhost:8080/openCategory.Ochan"
if [ -n $2 ] 
then
	url=$2
fi

parent=0
if [ -n $3 ]
then
	parent=$3
fi

if [ "$1" = "text" ]
then
	for i in `seq 0 $4`
	do
		echo $i" of "$4" "`date +%T`
		curl --form "parent=$parent" --form "subject=subject" --form "categoryIdentifier=1" --form "author=Curl" --form "comment=test" $url  &
	done
	
else 
if [ -n $1 ]
then
	cd $1 
	##limitting to less than 400k files 
	find . ! -name . -prune -type f -size -100k |  while read line
	do
		echo $line"--"`date +%T`
		curl --form "parent=$parent" --form "subject=subject" --form "categoryIdentifier=1" --form "author=Curl" --form "comment=test" --form "file=@$line" $url 
	done
fi
fi
