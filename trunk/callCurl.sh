#!/bin/bash

#1 = file to upload
#2 = url to call
#3 = parent id
#4 = category Id

echo "Posting file:"$1" to "$2
curl -s --form "parent=$3" --form "subject=subject" --form "categoryIdentifier=$4" --form "author=Curl" --form "comment=test" --form "file=@$1" $2  > /dev/null
