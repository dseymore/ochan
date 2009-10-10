package org.ochan.util;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class handles 'pinging' google analytics with a notice when ochan is deployed.
 * 
 * This wont track who you are.. and really only fakes the user data to represent a 'hit' on my 
 * google analytics goal. 
 * 
 * You can turn this off, as well, after the first deployment through JMX. 
 *  
 * @author David Seymore 
 * Jan 4, 2009
 */
public class GoogleAnalyticsDeploymentListener implements ServletContextListener {

	public static final String COMMA = ".";
	public static final String URL_1 = "http://www.google-analytics.com/__utm.gif?utmwv=4.3&utmn=";
	public static final String URL_2 = "&utmsr=-&utmsc=-&utmul=-&utmje=0&utmfl=-&utmdt=-&utmhn=";
	public static final String URL_3 = "&utmr=";
	public static final String URL_4 = "&utmp=";
	public static final String URL_5 = "&utmac=";
	public static final String URL_6 = "&utmcc=__utma%3D";
	public static final String URL_7 = COMMA;
	public static final String URL_8 = COMMA;
	public static final String URL_9 = COMMA;
	public static final String URL_10 = COMMA;
	public static final String URL_11 = ".2%3B%2B__utmb%3D";
	public static final String URL_12 = "%3B%2B__utmc%3D";
	public static final String URL_13 = "%3B%2B__utmz%3D";
	public static final String URL_14 = COMMA;
	public static final String URL_15 = ".2.2.utmccn%3D(direct)%7Cutmcsr%3D(direct)%7Cutmcmd%3D(none)%3B%2B__utmv%3D";
	public static final String URL_16 = COMMA;
	public static final String URL_17 = "%3B";
	
	private static final Log LOG = LogFactory.getLog(GoogleAnalyticsDeploymentListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//NOTHING TO DO HERE
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DeploymentConfiguration configuration = new DeploymentConfiguration();
		if (StringUtils.equalsIgnoreCase("true", configuration.getPerformPingBackTracking())){
			Thread todo = new Thread(){
				public void run(){
					try{
						final String utmac = "UA-4347723-3";
						final String utmhn = "code.google.com";
						final String utmn = StringUtils.rightPad(String.valueOf(RandomUtils.nextInt(999999999)), 10, String.valueOf(RandomUtils.nextInt(10)));
						final String cookie = StringUtils.rightPad(String.valueOf(RandomUtils.nextInt(99999999)), 8, String.valueOf(RandomUtils.nextInt(10)));
						final String random = StringUtils.rightPad(String.valueOf(RandomUtils.nextInt(2147483647)),9, String.valueOf(RandomUtils.nextInt(10)));
						final String today = String.valueOf(new Date().getTime());
						final String referer = "http://www.ochannel.org";
						final String uservar = "-";
						final String utmp = "/p/ochan/?deployment=true";
						
						StringBuilder buffer = new StringBuilder();
						buffer.append(URL_1).append(utmn);
						buffer.append(URL_2).append(utmhn);
						buffer.append(URL_3).append(referer);
						buffer.append(URL_4).append(utmp);
						buffer.append(URL_5).append(utmac);
						buffer.append(URL_6).append(cookie);
						buffer.append(URL_7).append(random);
						buffer.append(URL_8).append(today);
						buffer.append(URL_9).append(today);
						buffer.append(URL_10).append(today);
						buffer.append(URL_11).append(cookie);
						buffer.append(URL_12).append(cookie);
						buffer.append(URL_13).append(cookie);
						buffer.append(URL_14).append(today);
						buffer.append(URL_15).append(cookie);
						buffer.append(URL_16).append(uservar);
						buffer.append(URL_17);
						
						LOG.info("About to hit google analytics URL for deployment tracking: " + buffer.toString());
						//make the call!
						RemoteFileGrabber.getDataFromUrl(buffer.toString());
					}catch(Exception e){
						LOG.error("An error here shouldn't stop anything, but, logging for debugging",e);
					}	
				}
			};
			todo.start();
		}
	}

}


/*
 * This PHP code is the basis of this implementation and is available from 
 * http://www.vdgraaf.info/google-analytics-without-javascript.html
 * 
<?php
$var_utmac='UA-000000-1'; //enter the new urchin code
$var_utmhn='yourdomain.com'; //enter your domain
$var_utmn=rand(1000000000,9999999999);//random request number
$var_cookie=rand(10000000,99999999);//random cookie number
$var_random=rand(1000000000,2147483647); //number under 2147483647
$var_today=time(); //today
$var_referer=$_SERVER['HTTP_REFERER']; //referer url

$var_uservar='-'; //enter your own user defined variable
$var_utmp='/rss/'.$_SERVER['REMOTE_ADDR']; //this example adds a fake page request to the (fake) rss directory (the viewer IP to check for absolute unique RSS readers)

$urchinUrl='http://www.google-analytics.com/__utm.gif?utmwv=1&utmn='.$var_utmn.'&utmsr=-&utmsc=-&utmul=-&utmje=0&utmfl=-&utmdt=-&utmhn='.$var_utmhn.'&utmr='.$var_referer.'&utmp='.$var_utmp.'&utmac='.$var_utmac.'&utmcc=__utma%3D'.$var_cookie.'.'.$var_random.'.'.$var_today.'.'.$var_today.'.'.$var_today.'.2%3B%2B__utmb%3D'.$var_cookie.'%3B%2B__utmc%3D'.$var_cookie.'%3B%2B__utmz%3D'.$var_cookie.'.'.$var_today.'.2.2.utmccn%3D(direct)%7Cutmcsr%3D(direct)%7Cutmcmd%3D(none)%3B%2B__utmv%3D'.$var_cookie.'.'.$var_uservar.'%3B';
 
$handle = fopen ($urchinUrl, "r");
$test = fgets($handle);
fclose($handle);
?>

*/