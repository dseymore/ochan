# Introduction #


# Example Configuration #
The PluginJob is responsible for configuring all plugins, and has a configuration attribute for the shared json configuraiton string.

An example configration string:
```
   {'ochan-rss':{'categories':[{'1':['http://ochan.tumblr.com/rss','http://dseymore.tumblr.com/rss','http://www.engadget.com/rss.xml','http://rss.slashdot.org/Slashdot/slashdot']}]}}
```

# Installation #
**Place the ochan-rss.jar plugin jar into the running directory of Ochan.**

# Change Log #
## 0.2 ##
**Switched to Eddie RSS library (informa failed some common rss feeds)**

## 0.1 ##
**Json configuration** Informa rss library