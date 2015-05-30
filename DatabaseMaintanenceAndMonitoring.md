# Introduction #
There are some tricks to maintaining an install of ochan. While it constantly keeps the database in a consistent, ready to go state; that can be backed up live, you'll probably want to clean some of its files up so that it doesn't take up too much disk space.

Also, some statistics for the health of the are exposed for you to better fine tune your deployment to your load.

# Details #

## Seeing where the data is ##
  1. Download the je distrubtion from the oracle site. It's embedded in ochan, and you're welcome to take it out of there, but, It wont be distributed by this project.
  1. execute: java -classpath ".:/absolute/je/path/to/je-3.3.75.jar" com.sleepycat.je.util.DbSpace  -h /absolute/ochan/deployment/path/

You should see output like:
```
  File    Size (KB)  % Used
--------  ---------  ------
00000000       7414       0
00000001       8515       0
00000002       9449       0
00000003       8822       0
00000004       9381       0
00000005       9597       0
00000006       9474       0
00000007       8543       0
00000008       8683       0
00000009       9628       0
0000000a       9462       0
0000000b       6576       0
0000000c       9548       0
0000000d       9401       0
0000000e       9559       0
0000000f       9034       0
00000010       9646       0
00000011       8138       0
00000012       9741       0
00000013       9477       0
00000014       6664       0
00000015       9548       0
00000016       9401       0
00000017       9559       0
00000018       9034       0
00000019       9646       0
0000001a       9138       0
0000001b       7825       0
0000001c       9512       0
0000001d       8544       0
0000001e       8734       0
0000001f       9628      12
00000020       9474     100
00000021       8543     100
00000022       8683     100
00000023       9628     100
00000024       9462     100
00000025       9700      99
00000026       9752     100
00000027       2946      96
 TOTALS      355527      19
```

The next time the cleaner/checkpoint job runs, all of the 0% utilization files will be deleted, freeing your disk's extraneous obligations.

# Reference Documentation #
  * [JE Getting Started Guide](http://www.oracle.com/technology/documentation/berkeley-db/je/GettingStartedGuide/index.html)
  * [JE Javadoc](http://www.oracle.com/technology/documentation/berkeley-db/je/java/index.html)