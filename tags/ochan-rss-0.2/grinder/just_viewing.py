# The Grinder 3.2
# HTTP script recorded by TCPProxy at Mar 21, 2009 6:13:53 PM

from net.grinder.script import Test
from net.grinder.script.Grinder import grinder
from net.grinder.plugin.http import HTTPPluginControl, HTTPRequest
from HTTPClient import NVPair
connectionDefaults = HTTPPluginControl.getConnectionDefaults()
httpUtilities = HTTPPluginControl.getHTTPUtilities()

# To use a proxy server, uncomment the next line and set the host and port.
# connectionDefaults.setProxyServer("localhost", 8001)

# These definitions at the top level of the file are evaluated once,
# when the worker process is started.

connectionDefaults.defaultHeaders = \
  ( NVPair('Accept-Language', 'en'),
    NVPair('Accept-Charset', 'iso-8859-1, utf-8, utf-16, *;q=0.1'),
    NVPair('Accept-Encoding', 'deflate, gzip, x-gzip, identity, *;q=0'),
    NVPair('User-Agent', 'Opera/9.63 (X11; Linux i686; U; en) Presto/2.1.1'),
    NVPair('Accept', 'text/html, application/xml;q=0.9, application/xhtml+xml, image/png, image/jpeg, image/gif, image/x-xbitmap, */*;q=0.1'), )

headers0= \
  ( )

headers1= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/categoryList.Ochan'), )

headers2= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/1'), )

headers3= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/thread/1'), )

url0 = 'http://192.168.1.110:8080'
url1 = 'http://sitecheck2.opera.com:80'

# Create an HTTPRequest for each request, then replace the
# reference to the HTTPRequest with an instrumented version.
# You can access the unadorned instance using request101.__target__.
request101 = HTTPRequest(url=url0, headers=headers0)
request101 = Test(101, 'GET /').wrap(request101)

request102 = HTTPRequest(url=url0, headers=headers0)
request102 = Test(102, 'GET categoryList.Ochan').wrap(request102)

request103 = HTTPRequest(url=url0, headers=headers1)
request103 = Test(103, 'GET favicon.png').wrap(request103)

request104 = HTTPRequest(url=url0, headers=headers1)
request104 = Test(104, 'GET ext-base.js').wrap(request104)

request105 = HTTPRequest(url=url0, headers=headers1)
request105 = Test(105, 'GET ext-all.js').wrap(request105)

request201 = HTTPRequest(url=url1, headers=headers0)
request201 = Test(201, 'GET /').wrap(request201)

request301 = HTTPRequest(url=url0, headers=headers1)
request301 = Test(301, 'GET ext-all.css').wrap(request301)

request302 = HTTPRequest(url=url0, headers=headers1)
request302 = Test(302, 'GET 15px-Feed-icon.png').wrap(request302)

request401 = HTTPRequest(url=url0, headers=headers1)
request401 = Test(401, 'GET 135').wrap(request401)

request501 = HTTPRequest(url=url0, headers=headers1)
request501 = Test(501, 'GET 1').wrap(request501)

request601 = HTTPRequest(url=url0, headers=headers2)
request601 = Test(601, 'GET 134').wrap(request601)

request602 = HTTPRequest(url=url0, headers=headers2)
request602 = Test(602, 'GET top-bottom.gif').wrap(request602)

request603 = HTTPRequest(url=url0, headers=headers2)
request603 = Test(603, 'GET left-right.gif').wrap(request603)

request604 = HTTPRequest(url=url0, headers=headers2)
request604 = Test(604, 'GET bg.gif').wrap(request604)

request605 = HTTPRequest(url=url0, headers=headers2)
request605 = Test(605, 'GET grid-blue-split.gif').wrap(request605)

request606 = HTTPRequest(url=url0, headers=headers2)
request606 = Test(606, 'GET btn-sprite.gif').wrap(request606)

request607 = HTTPRequest(url=url0, headers=headers2)
request607 = Test(607, 'GET tb-sprite.gif').wrap(request607)

request608 = HTTPRequest(url=url0, headers=headers2)
request608 = Test(608, 'GET tab-strip-bg.gif').wrap(request608)

request609 = HTTPRequest(url=url0, headers=headers2)
request609 = Test(609, 'GET text-bg.gif').wrap(request609)

request610 = HTTPRequest(url=url0, headers=headers2)
request610 = Test(610, 'GET btn-arrow.gif').wrap(request610)

request611 = HTTPRequest(url=url0, headers=headers2)
request611 = Test(611, 'GET tip-sprite.gif').wrap(request611)

request701 = HTTPRequest(url=url0, headers=headers2)
request701 = Test(701, 'GET 1').wrap(request701)

request702 = HTTPRequest(url=url0, headers=headers3)
request702 = Test(702, 'GET quoting.js').wrap(request702)

request801 = HTTPRequest(url=url0, headers=headers3)
request801 = Test(801, 'GET 3').wrap(request801)

request901 = HTTPRequest(url=url0, headers=headers3)
request901 = Test(901, 'GET 2').wrap(request901)

request1001 = HTTPRequest(url=url0, headers=headers3)
request1001 = Test(1001, 'GET 4').wrap(request1001)

request1101 = HTTPRequest(url=url0, headers=headers3)
request1101 = Test(1101, 'GET 5').wrap(request1101)

request1201 = HTTPRequest(url=url0, headers=headers3)
request1201 = Test(1201, 'GET 8').wrap(request1201)

request1301 = HTTPRequest(url=url0, headers=headers3)
request1301 = Test(1301, 'GET 9').wrap(request1301)

request1401 = HTTPRequest(url=url0, headers=headers3)
request1401 = Test(1401, 'GET 6').wrap(request1401)

request1501 = HTTPRequest(url=url0, headers=headers3)
request1501 = Test(1501, 'GET 11').wrap(request1501)

request1601 = HTTPRequest(url=url0, headers=headers3)
request1601 = Test(1601, 'GET 13').wrap(request1601)

request1701 = HTTPRequest(url=url0, headers=headers3)
request1701 = Test(1701, 'GET 14').wrap(request1701)

request1801 = HTTPRequest(url=url0, headers=headers3)
request1801 = Test(1801, 'GET 15').wrap(request1801)

request1901 = HTTPRequest(url=url0, headers=headers3)
request1901 = Test(1901, 'GET 16').wrap(request1901)

request2001 = HTTPRequest(url=url0, headers=headers3)
request2001 = Test(2001, 'GET 17').wrap(request2001)

request2101 = HTTPRequest(url=url0, headers=headers3)
request2101 = Test(2101, 'GET 18').wrap(request2101)

request2201 = HTTPRequest(url=url0, headers=headers3)
request2201 = Test(2201, 'GET 21').wrap(request2201)

request2301 = HTTPRequest(url=url0, headers=headers3)
request2301 = Test(2301, 'GET 20').wrap(request2301)

request2401 = HTTPRequest(url=url0, headers=headers3)
request2401 = Test(2401, 'GET 24').wrap(request2401)

request2501 = HTTPRequest(url=url0, headers=headers3)
request2501 = Test(2501, 'GET 26').wrap(request2501)

request2601 = HTTPRequest(url=url0, headers=headers3)
request2601 = Test(2601, 'GET 25').wrap(request2601)

request2701 = HTTPRequest(url=url0, headers=headers3)
request2701 = Test(2701, 'GET 22').wrap(request2701)

request2801 = HTTPRequest(url=url0, headers=headers3)
request2801 = Test(2801, 'GET 27').wrap(request2801)

request2901 = HTTPRequest(url=url0, headers=headers3)
request2901 = Test(2901, 'GET 29').wrap(request2901)

request3001 = HTTPRequest(url=url0, headers=headers3)
request3001 = Test(3001, 'GET 28').wrap(request3001)

request3101 = HTTPRequest(url=url0, headers=headers3)
request3101 = Test(3101, 'GET 32').wrap(request3101)

request3201 = HTTPRequest(url=url0, headers=headers3)
request3201 = Test(3201, 'GET 31').wrap(request3201)

request3301 = HTTPRequest(url=url0, headers=headers3)
request3301 = Test(3301, 'GET 33').wrap(request3301)

request3401 = HTTPRequest(url=url0, headers=headers3)
request3401 = Test(3401, 'GET 36').wrap(request3401)

request3501 = HTTPRequest(url=url0, headers=headers3)
request3501 = Test(3501, 'GET 35').wrap(request3501)

request3601 = HTTPRequest(url=url0, headers=headers3)
request3601 = Test(3601, 'GET 34').wrap(request3601)

request3701 = HTTPRequest(url=url0, headers=headers3)
request3701 = Test(3701, 'GET 37').wrap(request3701)

request3801 = HTTPRequest(url=url0, headers=headers3)
request3801 = Test(3801, 'GET 39').wrap(request3801)

request3901 = HTTPRequest(url=url0, headers=headers3)
request3901 = Test(3901, 'GET 38').wrap(request3901)

request4001 = HTTPRequest(url=url0, headers=headers3)
request4001 = Test(4001, 'GET 40').wrap(request4001)

request4101 = HTTPRequest(url=url0, headers=headers3)
request4101 = Test(4101, 'GET 41').wrap(request4101)

request4201 = HTTPRequest(url=url0, headers=headers3)
request4201 = Test(4201, 'GET 42').wrap(request4201)

request4301 = HTTPRequest(url=url0, headers=headers3)
request4301 = Test(4301, 'GET 44').wrap(request4301)

request4401 = HTTPRequest(url=url0, headers=headers3)
request4401 = Test(4401, 'GET 43').wrap(request4401)

request4501 = HTTPRequest(url=url0, headers=headers3)
request4501 = Test(4501, 'GET 46').wrap(request4501)

request4601 = HTTPRequest(url=url0, headers=headers3)
request4601 = Test(4601, 'GET 48').wrap(request4601)

request4701 = HTTPRequest(url=url0, headers=headers3)
request4701 = Test(4701, 'GET 50').wrap(request4701)

request4801 = HTTPRequest(url=url0, headers=headers3)
request4801 = Test(4801, 'GET 51').wrap(request4801)

request4901 = HTTPRequest(url=url0, headers=headers3)
request4901 = Test(4901, 'GET 54').wrap(request4901)

request5001 = HTTPRequest(url=url0, headers=headers3)
request5001 = Test(5001, 'GET 53').wrap(request5001)

request5101 = HTTPRequest(url=url0, headers=headers3)
request5101 = Test(5101, 'GET 52').wrap(request5101)

request5201 = HTTPRequest(url=url0, headers=headers3)
request5201 = Test(5201, 'GET 56').wrap(request5201)

request5301 = HTTPRequest(url=url0, headers=headers3)
request5301 = Test(5301, 'GET 57').wrap(request5301)

request5401 = HTTPRequest(url=url0, headers=headers3)
request5401 = Test(5401, 'GET 58').wrap(request5401)

request5501 = HTTPRequest(url=url0, headers=headers3)
request5501 = Test(5501, 'GET 59').wrap(request5501)

request5601 = HTTPRequest(url=url0, headers=headers3)
request5601 = Test(5601, 'GET 60').wrap(request5601)

request5701 = HTTPRequest(url=url0, headers=headers3)
request5701 = Test(5701, 'GET 62').wrap(request5701)

request5801 = HTTPRequest(url=url0, headers=headers3)
request5801 = Test(5801, 'GET 63').wrap(request5801)

request5901 = HTTPRequest(url=url0, headers=headers3)
request5901 = Test(5901, 'GET 65').wrap(request5901)

request6001 = HTTPRequest(url=url0, headers=headers3)
request6001 = Test(6001, 'GET 64').wrap(request6001)

request6101 = HTTPRequest(url=url0, headers=headers3)
request6101 = Test(6101, 'GET 67').wrap(request6101)

request6201 = HTTPRequest(url=url0, headers=headers3)
request6201 = Test(6201, 'GET 68').wrap(request6201)

request6301 = HTTPRequest(url=url0, headers=headers3)
request6301 = Test(6301, 'GET 70').wrap(request6301)

request6401 = HTTPRequest(url=url0, headers=headers3)
request6401 = Test(6401, 'GET 69').wrap(request6401)

request6501 = HTTPRequest(url=url0, headers=headers3)
request6501 = Test(6501, 'GET 71').wrap(request6501)

request6601 = HTTPRequest(url=url0, headers=headers3)
request6601 = Test(6601, 'GET 72').wrap(request6601)

request6701 = HTTPRequest(url=url0, headers=headers3)
request6701 = Test(6701, 'GET 74').wrap(request6701)

request6801 = HTTPRequest(url=url0, headers=headers3)
request6801 = Test(6801, 'GET 73').wrap(request6801)

request6901 = HTTPRequest(url=url0, headers=headers3)
request6901 = Test(6901, 'GET 75').wrap(request6901)

request7001 = HTTPRequest(url=url0, headers=headers3)
request7001 = Test(7001, 'GET 78').wrap(request7001)

request7101 = HTTPRequest(url=url0, headers=headers3)
request7101 = Test(7101, 'GET 77').wrap(request7101)

request7201 = HTTPRequest(url=url0, headers=headers3)
request7201 = Test(7201, 'GET 80').wrap(request7201)

request7301 = HTTPRequest(url=url0, headers=headers3)
request7301 = Test(7301, 'GET 81').wrap(request7301)

request7401 = HTTPRequest(url=url0, headers=headers3)
request7401 = Test(7401, 'GET 82').wrap(request7401)

request7501 = HTTPRequest(url=url0, headers=headers3)
request7501 = Test(7501, 'GET 83').wrap(request7501)

request7601 = HTTPRequest(url=url0, headers=headers3)
request7601 = Test(7601, 'GET 84').wrap(request7601)

request7701 = HTTPRequest(url=url0, headers=headers3)
request7701 = Test(7701, 'GET 86').wrap(request7701)

request7801 = HTTPRequest(url=url0, headers=headers3)
request7801 = Test(7801, 'GET 88').wrap(request7801)

request7901 = HTTPRequest(url=url0, headers=headers3)
request7901 = Test(7901, 'GET 87').wrap(request7901)

request8001 = HTTPRequest(url=url0, headers=headers3)
request8001 = Test(8001, 'GET 89').wrap(request8001)

request8101 = HTTPRequest(url=url0, headers=headers3)
request8101 = Test(8101, 'GET 90').wrap(request8101)

request8201 = HTTPRequest(url=url0, headers=headers3)
request8201 = Test(8201, 'GET 91').wrap(request8201)

request8301 = HTTPRequest(url=url0, headers=headers3)
request8301 = Test(8301, 'GET 92').wrap(request8301)

request8401 = HTTPRequest(url=url0, headers=headers3)
request8401 = Test(8401, 'GET 93').wrap(request8401)

request8501 = HTTPRequest(url=url0, headers=headers3)
request8501 = Test(8501, 'GET 94').wrap(request8501)

request8601 = HTTPRequest(url=url0, headers=headers3)
request8601 = Test(8601, 'GET 95').wrap(request8601)

request8701 = HTTPRequest(url=url0, headers=headers3)
request8701 = Test(8701, 'GET 96').wrap(request8701)

request8801 = HTTPRequest(url=url0, headers=headers3)
request8801 = Test(8801, 'GET 100').wrap(request8801)

request8901 = HTTPRequest(url=url0, headers=headers3)
request8901 = Test(8901, 'GET 102').wrap(request8901)

request9001 = HTTPRequest(url=url0, headers=headers3)
request9001 = Test(9001, 'GET 101').wrap(request9001)

request9101 = HTTPRequest(url=url0, headers=headers3)
request9101 = Test(9101, 'GET 99').wrap(request9101)

request9201 = HTTPRequest(url=url0, headers=headers3)
request9201 = Test(9201, 'GET 103').wrap(request9201)

request9301 = HTTPRequest(url=url0, headers=headers3)
request9301 = Test(9301, 'GET 105').wrap(request9301)

request9401 = HTTPRequest(url=url0, headers=headers3)
request9401 = Test(9401, 'GET 104').wrap(request9401)

request9501 = HTTPRequest(url=url0, headers=headers3)
request9501 = Test(9501, 'GET 106').wrap(request9501)

request9601 = HTTPRequest(url=url0, headers=headers3)
request9601 = Test(9601, 'GET 107').wrap(request9601)

request9701 = HTTPRequest(url=url0, headers=headers3)
request9701 = Test(9701, 'GET 109').wrap(request9701)

request9801 = HTTPRequest(url=url0, headers=headers3)
request9801 = Test(9801, 'GET 108').wrap(request9801)

request9901 = HTTPRequest(url=url0, headers=headers3)
request9901 = Test(9901, 'GET 110').wrap(request9901)

request10001 = HTTPRequest(url=url0, headers=headers3)
request10001 = Test(10001, 'GET 111').wrap(request10001)

request10101 = HTTPRequest(url=url0, headers=headers3)
request10101 = Test(10101, 'GET 112').wrap(request10101)

request10201 = HTTPRequest(url=url0, headers=headers3)
request10201 = Test(10201, 'GET 113').wrap(request10201)

request10301 = HTTPRequest(url=url0, headers=headers3)
request10301 = Test(10301, 'GET 114').wrap(request10301)

request10401 = HTTPRequest(url=url0, headers=headers3)
request10401 = Test(10401, 'GET 116').wrap(request10401)

request10501 = HTTPRequest(url=url0, headers=headers3)
request10501 = Test(10501, 'GET 117').wrap(request10501)

request10601 = HTTPRequest(url=url0, headers=headers3)
request10601 = Test(10601, 'GET 118').wrap(request10601)

request10701 = HTTPRequest(url=url0, headers=headers3)
request10701 = Test(10701, 'GET 119').wrap(request10701)

request10801 = HTTPRequest(url=url0, headers=headers3)
request10801 = Test(10801, 'GET 120').wrap(request10801)

request10901 = HTTPRequest(url=url0, headers=headers3)
request10901 = Test(10901, 'GET 121').wrap(request10901)

request11001 = HTTPRequest(url=url0, headers=headers3)
request11001 = Test(11001, 'GET 123').wrap(request11001)

request11101 = HTTPRequest(url=url0, headers=headers3)
request11101 = Test(11101, 'GET 122').wrap(request11101)

request11201 = HTTPRequest(url=url0, headers=headers3)
request11201 = Test(11201, 'GET 124').wrap(request11201)

request11301 = HTTPRequest(url=url0, headers=headers3)
request11301 = Test(11301, 'GET 125').wrap(request11301)

request11401 = HTTPRequest(url=url0, headers=headers3)
request11401 = Test(11401, 'GET 126').wrap(request11401)

request11501 = HTTPRequest(url=url0, headers=headers3)
request11501 = Test(11501, 'GET 129').wrap(request11501)

request11601 = HTTPRequest(url=url0, headers=headers3)
request11601 = Test(11601, 'GET 128').wrap(request11601)

request11701 = HTTPRequest(url=url0, headers=headers3)
request11701 = Test(11701, 'GET 127').wrap(request11701)

request11801 = HTTPRequest(url=url0, headers=headers3)
request11801 = Test(11801, 'GET 130').wrap(request11801)

request11901 = HTTPRequest(url=url0, headers=headers3)
request11901 = Test(11901, 'GET 131').wrap(request11901)

request12001 = HTTPRequest(url=url0, headers=headers3)
request12001 = Test(12001, 'GET 132').wrap(request12001)

request12101 = HTTPRequest(url=url0, headers=headers3)
request12101 = Test(12101, 'GET 133').wrap(request12101)

request12201 = HTTPRequest(url=url0, headers=headers3)
request12201 = Test(12201, 'GET /').wrap(request12201)

request12301 = HTTPRequest(url=url0, headers=headers3)
request12301 = Test(12301, 'GET /').wrap(request12301)

request12401 = HTTPRequest(url=url0, headers=headers3)
request12401 = Test(12401, 'GET /').wrap(request12401)

request12501 = HTTPRequest(url=url0, headers=headers3)
request12501 = Test(12501, 'GET /').wrap(request12501)

request12601 = HTTPRequest(url=url0, headers=headers3)
request12601 = Test(12601, 'GET /').wrap(request12601)


class TestRunner:
  """A TestRunner instance is created for each worker thread."""

  # A method for each recorded page.
  def page1(self):
    """GET / (requests 101-105)."""
    
    # Expecting 302 'Found'
    result = request101.GET('/')

    grinder.sleep(23)
    request102.GET('/categoryList.Ochan')
    self.token_identifier = \
      httpUtilities.valueFromBodyURI('identifier') # ''

    request103.GET('/favicon.png')

    grinder.sleep(57)
    request104.GET('/extjs/adapter/ext/ext-base.js', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 13:07:04 GMT'), ))

    grinder.sleep(23)
    request105.GET('/extjs/ext-all.js', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 13:07:04 GMT'), ))

    return result

  def page2(self):
    """GET / (request 201)."""
    self.token_host = \
      '192.168.1.110'
    self.token_hdn = \
      'kItWlgUuX1sVPEc3JlIa5g=='
    result = request201.GET('/' +
      '?host=' +
      self.token_host +
      '&hdn=' +
      self.token_hdn)

    return result

  def page3(self):
    """GET ext-all.css (requests 301-302)."""
    result = request301.GET('/extjs/resources/css/ext-all.css', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 13:07:04 GMT'), ))

    grinder.sleep(190)
    request302.GET('/15px-Feed-icon.png', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 13:07:04 GMT'), ))

    return result

  def page4(self):
    """GET 135 (request 401)."""
    self.token_thumb = \
      'true'
    result = request401.GET('/chan/img/135' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page5(self):
    """GET 1 (request 501)."""
    result = request501.GET('/chan/1')
    self.token_identifier = \
      httpUtilities.valueFromHiddenInput('identifier') # '1'
    self.token_categoryIdentifier = \
      httpUtilities.valueFromHiddenInput('categoryIdentifier') # '1'
    self.token_email = \
      httpUtilities.valueFromHiddenInput('email') # ''
    self.token_url = \
      httpUtilities.valueFromHiddenInput('url') # ''

    return result

  def page6(self):
    """GET 134 (requests 601-611)."""
    result = request601.GET('/chan/img/134' +
      '?thumb=' +
      self.token_thumb)

    request602.GET('/extjs/resources/images/default/panel/top-bottom.gif', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    request603.GET('/extjs/resources/images/default/panel/left-right.gif', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    request604.GET('/extjs/resources/images/default/toolbar/bg.gif', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    request605.GET('/extjs/resources/images/default/grid/grid-blue-split.gif', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    request606.GET('/extjs/resources/images/default/button/btn-sprite.gif', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    request607.GET('/extjs/resources/images/default/editor/tb-sprite.gif', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    request608.GET('/extjs/resources/images/default/tabs/tab-strip-bg.gif', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    request609.GET('/extjs/resources/images/default/form/text-bg.gif', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    request610.GET('/extjs/resources/images/default/toolbar/btn-arrow.gif', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    request611.GET('/extjs/resources/images/default/qtip/tip-sprite.gif', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    return result

  def page7(self):
    """GET 1 (requests 701-702)."""
    result = request701.GET('/chan/thread/1')
    self.token_parent = \
      httpUtilities.valueFromHiddenInput('parent') # '1'

    grinder.sleep(278)
    request702.GET('/quoting.js', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    return result

  def page8(self):
    """GET 3 (request 801)."""
    result = request801.GET('/chan/img/3' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page9(self):
    """GET 2 (request 901)."""
    result = request901.GET('/chan/img/2' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page10(self):
    """GET 4 (request 1001)."""
    result = request1001.GET('/chan/img/4' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page11(self):
    """GET 5 (request 1101)."""
    result = request1101.GET('/chan/img/5' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page12(self):
    """GET 8 (request 1201)."""
    result = request1201.GET('/chan/img/8' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page13(self):
    """GET 9 (request 1301)."""
    result = request1301.GET('/chan/img/9' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page14(self):
    """GET 6 (request 1401)."""
    result = request1401.GET('/chan/img/6' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page15(self):
    """GET 11 (request 1501)."""
    result = request1501.GET('/chan/img/11' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page16(self):
    """GET 13 (request 1601)."""
    result = request1601.GET('/chan/img/13' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page17(self):
    """GET 14 (request 1701)."""
    result = request1701.GET('/chan/img/14' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page18(self):
    """GET 15 (request 1801)."""
    result = request1801.GET('/chan/img/15' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page19(self):
    """GET 16 (request 1901)."""
    result = request1901.GET('/chan/img/16' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page20(self):
    """GET 17 (request 2001)."""
    result = request2001.GET('/chan/img/17' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page21(self):
    """GET 18 (request 2101)."""
    result = request2101.GET('/chan/img/18' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page22(self):
    """GET 21 (request 2201)."""
    result = request2201.GET('/chan/img/21' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page23(self):
    """GET 20 (request 2301)."""
    result = request2301.GET('/chan/img/20' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page24(self):
    """GET 24 (request 2401)."""
    result = request2401.GET('/chan/img/24' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page25(self):
    """GET 26 (request 2501)."""
    result = request2501.GET('/chan/img/26' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page26(self):
    """GET 25 (request 2601)."""
    result = request2601.GET('/chan/img/25' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page27(self):
    """GET 22 (request 2701)."""
    result = request2701.GET('/chan/img/22' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page28(self):
    """GET 27 (request 2801)."""
    result = request2801.GET('/chan/img/27' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page29(self):
    """GET 29 (request 2901)."""
    result = request2901.GET('/chan/img/29' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page30(self):
    """GET 28 (request 3001)."""
    result = request3001.GET('/chan/img/28' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page31(self):
    """GET 32 (request 3101)."""
    result = request3101.GET('/chan/img/32' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page32(self):
    """GET 31 (request 3201)."""
    result = request3201.GET('/chan/img/31' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page33(self):
    """GET 33 (request 3301)."""
    result = request3301.GET('/chan/img/33' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page34(self):
    """GET 36 (request 3401)."""
    result = request3401.GET('/chan/img/36' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page35(self):
    """GET 35 (request 3501)."""
    result = request3501.GET('/chan/img/35' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page36(self):
    """GET 34 (request 3601)."""
    result = request3601.GET('/chan/img/34' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page37(self):
    """GET 37 (request 3701)."""
    result = request3701.GET('/chan/img/37' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page38(self):
    """GET 39 (request 3801)."""
    result = request3801.GET('/chan/img/39' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page39(self):
    """GET 38 (request 3901)."""
    result = request3901.GET('/chan/img/38' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page40(self):
    """GET 40 (request 4001)."""
    result = request4001.GET('/chan/img/40' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page41(self):
    """GET 41 (request 4101)."""
    result = request4101.GET('/chan/img/41' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page42(self):
    """GET 42 (request 4201)."""
    result = request4201.GET('/chan/img/42' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page43(self):
    """GET 44 (request 4301)."""
    result = request4301.GET('/chan/img/44' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page44(self):
    """GET 43 (request 4401)."""
    result = request4401.GET('/chan/img/43' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page45(self):
    """GET 46 (request 4501)."""
    result = request4501.GET('/chan/img/46' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page46(self):
    """GET 48 (request 4601)."""
    result = request4601.GET('/chan/img/48' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page47(self):
    """GET 50 (request 4701)."""
    result = request4701.GET('/chan/img/50' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page48(self):
    """GET 51 (request 4801)."""
    result = request4801.GET('/chan/img/51' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page49(self):
    """GET 54 (request 4901)."""
    result = request4901.GET('/chan/img/54' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page50(self):
    """GET 53 (request 5001)."""
    result = request5001.GET('/chan/img/53' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page51(self):
    """GET 52 (request 5101)."""
    result = request5101.GET('/chan/img/52' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page52(self):
    """GET 56 (request 5201)."""
    result = request5201.GET('/chan/img/56' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page53(self):
    """GET 57 (request 5301)."""
    result = request5301.GET('/chan/img/57' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page54(self):
    """GET 58 (request 5401)."""
    result = request5401.GET('/chan/img/58' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page55(self):
    """GET 59 (request 5501)."""
    result = request5501.GET('/chan/img/59' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page56(self):
    """GET 60 (request 5601)."""
    result = request5601.GET('/chan/img/60' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page57(self):
    """GET 62 (request 5701)."""
    result = request5701.GET('/chan/img/62' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page58(self):
    """GET 63 (request 5801)."""
    result = request5801.GET('/chan/img/63' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page59(self):
    """GET 65 (request 5901)."""
    result = request5901.GET('/chan/img/65' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page60(self):
    """GET 64 (request 6001)."""
    result = request6001.GET('/chan/img/64' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page61(self):
    """GET 67 (request 6101)."""
    result = request6101.GET('/chan/img/67' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page62(self):
    """GET 68 (request 6201)."""
    result = request6201.GET('/chan/img/68' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page63(self):
    """GET 70 (request 6301)."""
    result = request6301.GET('/chan/img/70' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page64(self):
    """GET 69 (request 6401)."""
    result = request6401.GET('/chan/img/69' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page65(self):
    """GET 71 (request 6501)."""
    result = request6501.GET('/chan/img/71' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page66(self):
    """GET 72 (request 6601)."""
    result = request6601.GET('/chan/img/72' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page67(self):
    """GET 74 (request 6701)."""
    result = request6701.GET('/chan/img/74' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page68(self):
    """GET 73 (request 6801)."""
    result = request6801.GET('/chan/img/73' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page69(self):
    """GET 75 (request 6901)."""
    result = request6901.GET('/chan/img/75' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page70(self):
    """GET 78 (request 7001)."""
    result = request7001.GET('/chan/img/78' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page71(self):
    """GET 77 (request 7101)."""
    result = request7101.GET('/chan/img/77' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page72(self):
    """GET 80 (request 7201)."""
    result = request7201.GET('/chan/img/80' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page73(self):
    """GET 81 (request 7301)."""
    result = request7301.GET('/chan/img/81' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page74(self):
    """GET 82 (request 7401)."""
    result = request7401.GET('/chan/img/82' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page75(self):
    """GET 83 (request 7501)."""
    result = request7501.GET('/chan/img/83' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page76(self):
    """GET 84 (request 7601)."""
    result = request7601.GET('/chan/img/84' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page77(self):
    """GET 86 (request 7701)."""
    result = request7701.GET('/chan/img/86' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page78(self):
    """GET 88 (request 7801)."""
    result = request7801.GET('/chan/img/88' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page79(self):
    """GET 87 (request 7901)."""
    result = request7901.GET('/chan/img/87' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page80(self):
    """GET 89 (request 8001)."""
    result = request8001.GET('/chan/img/89' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page81(self):
    """GET 90 (request 8101)."""
    result = request8101.GET('/chan/img/90' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page82(self):
    """GET 91 (request 8201)."""
    result = request8201.GET('/chan/img/91' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page83(self):
    """GET 92 (request 8301)."""
    result = request8301.GET('/chan/img/92' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page84(self):
    """GET 93 (request 8401)."""
    result = request8401.GET('/chan/img/93' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page85(self):
    """GET 94 (request 8501)."""
    result = request8501.GET('/chan/img/94' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page86(self):
    """GET 95 (request 8601)."""
    result = request8601.GET('/chan/img/95' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page87(self):
    """GET 96 (request 8701)."""
    result = request8701.GET('/chan/img/96' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page88(self):
    """GET 100 (request 8801)."""
    result = request8801.GET('/chan/img/100' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page89(self):
    """GET 102 (request 8901)."""
    result = request8901.GET('/chan/img/102' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page90(self):
    """GET 101 (request 9001)."""
    result = request9001.GET('/chan/img/101' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page91(self):
    """GET 99 (request 9101)."""
    result = request9101.GET('/chan/img/99' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page92(self):
    """GET 103 (request 9201)."""
    result = request9201.GET('/chan/img/103' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page93(self):
    """GET 105 (request 9301)."""
    result = request9301.GET('/chan/img/105' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page94(self):
    """GET 104 (request 9401)."""
    result = request9401.GET('/chan/img/104' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page95(self):
    """GET 106 (request 9501)."""
    result = request9501.GET('/chan/img/106' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page96(self):
    """GET 107 (request 9601)."""
    result = request9601.GET('/chan/img/107' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page97(self):
    """GET 109 (request 9701)."""
    result = request9701.GET('/chan/img/109' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page98(self):
    """GET 108 (request 9801)."""
    result = request9801.GET('/chan/img/108' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page99(self):
    """GET 110 (request 9901)."""
    result = request9901.GET('/chan/img/110' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page100(self):
    """GET 111 (request 10001)."""
    result = request10001.GET('/chan/img/111' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page101(self):
    """GET 112 (request 10101)."""
    result = request10101.GET('/chan/img/112' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page102(self):
    """GET 113 (request 10201)."""
    result = request10201.GET('/chan/img/113' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page103(self):
    """GET 114 (request 10301)."""
    result = request10301.GET('/chan/img/114' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page104(self):
    """GET 116 (request 10401)."""
    result = request10401.GET('/chan/img/116' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page105(self):
    """GET 117 (request 10501)."""
    result = request10501.GET('/chan/img/117' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page106(self):
    """GET 118 (request 10601)."""
    result = request10601.GET('/chan/img/118' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page107(self):
    """GET 119 (request 10701)."""
    result = request10701.GET('/chan/img/119' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page108(self):
    """GET 120 (request 10801)."""
    result = request10801.GET('/chan/img/120' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page109(self):
    """GET 121 (request 10901)."""
    result = request10901.GET('/chan/img/121' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page110(self):
    """GET 123 (request 11001)."""
    result = request11001.GET('/chan/img/123' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page111(self):
    """GET 122 (request 11101)."""
    result = request11101.GET('/chan/img/122' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page112(self):
    """GET 124 (request 11201)."""
    result = request11201.GET('/chan/img/124' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page113(self):
    """GET 125 (request 11301)."""
    result = request11301.GET('/chan/img/125' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page114(self):
    """GET 126 (request 11401)."""
    result = request11401.GET('/chan/img/126' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page115(self):
    """GET 129 (request 11501)."""
    result = request11501.GET('/chan/img/129' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page116(self):
    """GET 128 (request 11601)."""
    result = request11601.GET('/chan/img/128' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page117(self):
    """GET 127 (request 11701)."""
    result = request11701.GET('/chan/img/127' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page118(self):
    """GET 130 (request 11801)."""
    result = request11801.GET('/chan/img/130' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page119(self):
    """GET 131 (request 11901)."""
    result = request11901.GET('/chan/img/131' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page120(self):
    """GET 132 (request 12001)."""
    result = request12001.GET('/chan/img/132' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page121(self):
    """GET 133 (request 12101)."""
    result = request12101.GET('/chan/img/133' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page122(self):
    """GET / (request 12201)."""
    self.token__dc = \
      '1237673641828'
    result = request12201.GET('/remote/rest/thread/status/1/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page123(self):
    """GET / (request 12301)."""
    self.token__dc = \
      '1237673640824'
    result = request12301.GET('/remote/rest/thread/status/1/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page124(self):
    """GET / (request 12401)."""
    self.token__dc = \
      '1237673642828'
    result = request12401.GET('/remote/rest/thread/status/1/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page125(self):
    """GET / (request 12501)."""
    self.token__dc = \
      '1237673643829'
    result = request12501.GET('/remote/rest/thread/status/1/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page126(self):
    """GET / (request 12601)."""
    self.token__dc = \
      '1237673644829'
    result = request12601.GET('/remote/rest/thread/status/1/' +
      '?_dc=' +
      self.token__dc)

    return result

  def __call__(self):
    """This method is called for every run performed by the worker thread."""
    self.page1()      # GET / (requests 101-105)

    grinder.sleep(21)
    self.page2()      # GET / (request 201)

    grinder.sleep(696)
    self.page3()      # GET ext-all.css (requests 301-302)
    self.page4()      # GET 135 (request 401)

    grinder.sleep(913)
    self.page5()      # GET 1 (request 501)

    grinder.sleep(193)
    self.page6()      # GET 134 (requests 601-611)

    grinder.sleep(1988)
    self.page7()      # GET 1 (requests 701-702)

    grinder.sleep(338)
    self.page8()      # GET 3 (request 801)
    self.page9()      # GET 2 (request 901)
    self.page10()     # GET 4 (request 1001)

    grinder.sleep(53)
    self.page11()     # GET 5 (request 1101)
    self.page12()     # GET 8 (request 1201)
    self.page13()     # GET 9 (request 1301)
    self.page14()     # GET 6 (request 1401)
    self.page15()     # GET 11 (request 1501)
    self.page16()     # GET 13 (request 1601)
    self.page17()     # GET 14 (request 1701)
    self.page18()     # GET 15 (request 1801)
    self.page19()     # GET 16 (request 1901)

    grinder.sleep(31)
    self.page20()     # GET 17 (request 2001)
    self.page21()     # GET 18 (request 2101)
    self.page22()     # GET 21 (request 2201)
    self.page23()     # GET 20 (request 2301)
    self.page24()     # GET 24 (request 2401)
    self.page25()     # GET 26 (request 2501)
    self.page26()     # GET 25 (request 2601)
    self.page27()     # GET 22 (request 2701)
    self.page28()     # GET 27 (request 2801)

    grinder.sleep(89)
    self.page29()     # GET 29 (request 2901)
    self.page30()     # GET 28 (request 3001)
    self.page31()     # GET 32 (request 3101)

    grinder.sleep(25)
    self.page32()     # GET 31 (request 3201)
    self.page33()     # GET 33 (request 3301)
    self.page34()     # GET 36 (request 3401)
    self.page35()     # GET 35 (request 3501)
    self.page36()     # GET 34 (request 3601)
    self.page37()     # GET 37 (request 3701)

    grinder.sleep(35)
    self.page38()     # GET 39 (request 3801)
    self.page39()     # GET 38 (request 3901)
    self.page40()     # GET 40 (request 4001)
    self.page41()     # GET 41 (request 4101)
    self.page42()     # GET 42 (request 4201)
    self.page43()     # GET 44 (request 4301)
    self.page44()     # GET 43 (request 4401)
    self.page45()     # GET 46 (request 4501)
    self.page46()     # GET 48 (request 4601)
    self.page47()     # GET 50 (request 4701)

    grinder.sleep(62)
    self.page48()     # GET 51 (request 4801)
    self.page49()     # GET 54 (request 4901)
    self.page50()     # GET 53 (request 5001)
    self.page51()     # GET 52 (request 5101)
    self.page52()     # GET 56 (request 5201)
    self.page53()     # GET 57 (request 5301)
    self.page54()     # GET 58 (request 5401)

    grinder.sleep(58)
    self.page55()     # GET 59 (request 5501)
    self.page56()     # GET 60 (request 5601)
    self.page57()     # GET 62 (request 5701)
    self.page58()     # GET 63 (request 5801)
    self.page59()     # GET 65 (request 5901)
    self.page60()     # GET 64 (request 6001)

    grinder.sleep(40)
    self.page61()     # GET 67 (request 6101)
    self.page62()     # GET 68 (request 6201)
    self.page63()     # GET 70 (request 6301)
    self.page64()     # GET 69 (request 6401)
    self.page65()     # GET 71 (request 6501)
    self.page66()     # GET 72 (request 6601)
    self.page67()     # GET 74 (request 6701)
    self.page68()     # GET 73 (request 6801)

    grinder.sleep(55)
    self.page69()     # GET 75 (request 6901)
    self.page70()     # GET 78 (request 7001)
    self.page71()     # GET 77 (request 7101)
    self.page72()     # GET 80 (request 7201)
    self.page73()     # GET 81 (request 7301)
    self.page74()     # GET 82 (request 7401)

    grinder.sleep(32)
    self.page75()     # GET 83 (request 7501)
    self.page76()     # GET 84 (request 7601)
    self.page77()     # GET 86 (request 7701)
    self.page78()     # GET 88 (request 7801)
    self.page79()     # GET 87 (request 7901)
    self.page80()     # GET 89 (request 8001)
    self.page81()     # GET 90 (request 8101)

    grinder.sleep(37)
    self.page82()     # GET 91 (request 8201)
    self.page83()     # GET 92 (request 8301)
    self.page84()     # GET 93 (request 8401)
    self.page85()     # GET 94 (request 8501)
    self.page86()     # GET 95 (request 8601)
    self.page87()     # GET 96 (request 8701)

    grinder.sleep(71)
    self.page88()     # GET 100 (request 8801)
    self.page89()     # GET 102 (request 8901)
    self.page90()     # GET 101 (request 9001)
    self.page91()     # GET 99 (request 9101)
    self.page92()     # GET 103 (request 9201)

    grinder.sleep(11)
    self.page93()     # GET 105 (request 9301)
    self.page94()     # GET 104 (request 9401)
    self.page95()     # GET 106 (request 9501)

    grinder.sleep(19)
    self.page96()     # GET 107 (request 9601)
    self.page97()     # GET 109 (request 9701)
    self.page98()     # GET 108 (request 9801)
    self.page99()     # GET 110 (request 9901)
    self.page100()    # GET 111 (request 10001)
    self.page101()    # GET 112 (request 10101)
    self.page102()    # GET 113 (request 10201)
    self.page103()    # GET 114 (request 10301)
    self.page104()    # GET 116 (request 10401)
    self.page105()    # GET 117 (request 10501)
    self.page106()    # GET 118 (request 10601)

    grinder.sleep(12)
    self.page107()    # GET 119 (request 10701)
    self.page108()    # GET 120 (request 10801)
    self.page109()    # GET 121 (request 10901)
    self.page110()    # GET 123 (request 11001)
    self.page111()    # GET 122 (request 11101)
    self.page112()    # GET 124 (request 11201)
    self.page113()    # GET 125 (request 11301)

    grinder.sleep(49)
    self.page114()    # GET 126 (request 11401)
    self.page115()    # GET 129 (request 11501)
    self.page116()    # GET 128 (request 11601)
    self.page117()    # GET 127 (request 11701)
    self.page118()    # GET 130 (request 11801)
    self.page119()    # GET 131 (request 11901)
    self.page120()    # GET 132 (request 12001)
    self.page121()    # GET 133 (request 12101)

    grinder.sleep(64)
    self.page122()    # GET / (request 12201)
    self.page123()    # GET / (request 12301)

    grinder.sleep(721)
    self.page124()    # GET / (request 12401)

    grinder.sleep(995)
    self.page125()    # GET / (request 12501)

    grinder.sleep(994)
    self.page126()    # GET / (request 12601)


def instrumentMethod(test, method_name, c=TestRunner):
  """Instrument a method with the given Test."""
  unadorned = getattr(c, method_name)
  import new
  method = new.instancemethod(test.wrap(unadorned), None, c)
  setattr(c, method_name, method)

# Replace each method with an instrumented version.
# You can call the unadorned method using self.page1.__target__().
instrumentMethod(Test(100, 'Page 1'), 'page1')
instrumentMethod(Test(200, 'Page 2'), 'page2')
instrumentMethod(Test(300, 'Page 3'), 'page3')
instrumentMethod(Test(400, 'Page 4'), 'page4')
instrumentMethod(Test(500, 'Page 5'), 'page5')
instrumentMethod(Test(600, 'Page 6'), 'page6')
instrumentMethod(Test(700, 'Page 7'), 'page7')
instrumentMethod(Test(800, 'Page 8'), 'page8')
instrumentMethod(Test(900, 'Page 9'), 'page9')
instrumentMethod(Test(1000, 'Page 10'), 'page10')
instrumentMethod(Test(1100, 'Page 11'), 'page11')
instrumentMethod(Test(1200, 'Page 12'), 'page12')
instrumentMethod(Test(1300, 'Page 13'), 'page13')
instrumentMethod(Test(1400, 'Page 14'), 'page14')
instrumentMethod(Test(1500, 'Page 15'), 'page15')
instrumentMethod(Test(1600, 'Page 16'), 'page16')
instrumentMethod(Test(1700, 'Page 17'), 'page17')
instrumentMethod(Test(1800, 'Page 18'), 'page18')
instrumentMethod(Test(1900, 'Page 19'), 'page19')
instrumentMethod(Test(2000, 'Page 20'), 'page20')
instrumentMethod(Test(2100, 'Page 21'), 'page21')
instrumentMethod(Test(2200, 'Page 22'), 'page22')
instrumentMethod(Test(2300, 'Page 23'), 'page23')
instrumentMethod(Test(2400, 'Page 24'), 'page24')
instrumentMethod(Test(2500, 'Page 25'), 'page25')
instrumentMethod(Test(2600, 'Page 26'), 'page26')
instrumentMethod(Test(2700, 'Page 27'), 'page27')
instrumentMethod(Test(2800, 'Page 28'), 'page28')
instrumentMethod(Test(2900, 'Page 29'), 'page29')
instrumentMethod(Test(3000, 'Page 30'), 'page30')
instrumentMethod(Test(3100, 'Page 31'), 'page31')
instrumentMethod(Test(3200, 'Page 32'), 'page32')
instrumentMethod(Test(3300, 'Page 33'), 'page33')
instrumentMethod(Test(3400, 'Page 34'), 'page34')
instrumentMethod(Test(3500, 'Page 35'), 'page35')
instrumentMethod(Test(3600, 'Page 36'), 'page36')
instrumentMethod(Test(3700, 'Page 37'), 'page37')
instrumentMethod(Test(3800, 'Page 38'), 'page38')
instrumentMethod(Test(3900, 'Page 39'), 'page39')
instrumentMethod(Test(4000, 'Page 40'), 'page40')
instrumentMethod(Test(4100, 'Page 41'), 'page41')
instrumentMethod(Test(4200, 'Page 42'), 'page42')
instrumentMethod(Test(4300, 'Page 43'), 'page43')
instrumentMethod(Test(4400, 'Page 44'), 'page44')
instrumentMethod(Test(4500, 'Page 45'), 'page45')
instrumentMethod(Test(4600, 'Page 46'), 'page46')
instrumentMethod(Test(4700, 'Page 47'), 'page47')
instrumentMethod(Test(4800, 'Page 48'), 'page48')
instrumentMethod(Test(4900, 'Page 49'), 'page49')
instrumentMethod(Test(5000, 'Page 50'), 'page50')
instrumentMethod(Test(5100, 'Page 51'), 'page51')
instrumentMethod(Test(5200, 'Page 52'), 'page52')
instrumentMethod(Test(5300, 'Page 53'), 'page53')
instrumentMethod(Test(5400, 'Page 54'), 'page54')
instrumentMethod(Test(5500, 'Page 55'), 'page55')
instrumentMethod(Test(5600, 'Page 56'), 'page56')
instrumentMethod(Test(5700, 'Page 57'), 'page57')
instrumentMethod(Test(5800, 'Page 58'), 'page58')
instrumentMethod(Test(5900, 'Page 59'), 'page59')
instrumentMethod(Test(6000, 'Page 60'), 'page60')
instrumentMethod(Test(6100, 'Page 61'), 'page61')
instrumentMethod(Test(6200, 'Page 62'), 'page62')
instrumentMethod(Test(6300, 'Page 63'), 'page63')
instrumentMethod(Test(6400, 'Page 64'), 'page64')
instrumentMethod(Test(6500, 'Page 65'), 'page65')
instrumentMethod(Test(6600, 'Page 66'), 'page66')
instrumentMethod(Test(6700, 'Page 67'), 'page67')
instrumentMethod(Test(6800, 'Page 68'), 'page68')
instrumentMethod(Test(6900, 'Page 69'), 'page69')
instrumentMethod(Test(7000, 'Page 70'), 'page70')
instrumentMethod(Test(7100, 'Page 71'), 'page71')
instrumentMethod(Test(7200, 'Page 72'), 'page72')
instrumentMethod(Test(7300, 'Page 73'), 'page73')
instrumentMethod(Test(7400, 'Page 74'), 'page74')
instrumentMethod(Test(7500, 'Page 75'), 'page75')
instrumentMethod(Test(7600, 'Page 76'), 'page76')
instrumentMethod(Test(7700, 'Page 77'), 'page77')
instrumentMethod(Test(7800, 'Page 78'), 'page78')
instrumentMethod(Test(7900, 'Page 79'), 'page79')
instrumentMethod(Test(8000, 'Page 80'), 'page80')
instrumentMethod(Test(8100, 'Page 81'), 'page81')
instrumentMethod(Test(8200, 'Page 82'), 'page82')
instrumentMethod(Test(8300, 'Page 83'), 'page83')
instrumentMethod(Test(8400, 'Page 84'), 'page84')
instrumentMethod(Test(8500, 'Page 85'), 'page85')
instrumentMethod(Test(8600, 'Page 86'), 'page86')
instrumentMethod(Test(8700, 'Page 87'), 'page87')
instrumentMethod(Test(8800, 'Page 88'), 'page88')
instrumentMethod(Test(8900, 'Page 89'), 'page89')
instrumentMethod(Test(9000, 'Page 90'), 'page90')
instrumentMethod(Test(9100, 'Page 91'), 'page91')
instrumentMethod(Test(9200, 'Page 92'), 'page92')
instrumentMethod(Test(9300, 'Page 93'), 'page93')
instrumentMethod(Test(9400, 'Page 94'), 'page94')
instrumentMethod(Test(9500, 'Page 95'), 'page95')
instrumentMethod(Test(9600, 'Page 96'), 'page96')
instrumentMethod(Test(9700, 'Page 97'), 'page97')
instrumentMethod(Test(9800, 'Page 98'), 'page98')
instrumentMethod(Test(9900, 'Page 99'), 'page99')
instrumentMethod(Test(10000, 'Page 100'), 'page100')
instrumentMethod(Test(10100, 'Page 101'), 'page101')
instrumentMethod(Test(10200, 'Page 102'), 'page102')
instrumentMethod(Test(10300, 'Page 103'), 'page103')
instrumentMethod(Test(10400, 'Page 104'), 'page104')
instrumentMethod(Test(10500, 'Page 105'), 'page105')
instrumentMethod(Test(10600, 'Page 106'), 'page106')
instrumentMethod(Test(10700, 'Page 107'), 'page107')
instrumentMethod(Test(10800, 'Page 108'), 'page108')
instrumentMethod(Test(10900, 'Page 109'), 'page109')
instrumentMethod(Test(11000, 'Page 110'), 'page110')
instrumentMethod(Test(11100, 'Page 111'), 'page111')
instrumentMethod(Test(11200, 'Page 112'), 'page112')
instrumentMethod(Test(11300, 'Page 113'), 'page113')
instrumentMethod(Test(11400, 'Page 114'), 'page114')
instrumentMethod(Test(11500, 'Page 115'), 'page115')
instrumentMethod(Test(11600, 'Page 116'), 'page116')
instrumentMethod(Test(11700, 'Page 117'), 'page117')
instrumentMethod(Test(11800, 'Page 118'), 'page118')
instrumentMethod(Test(11900, 'Page 119'), 'page119')
instrumentMethod(Test(12000, 'Page 120'), 'page120')
instrumentMethod(Test(12100, 'Page 121'), 'page121')
instrumentMethod(Test(12200, 'Page 122'), 'page122')
instrumentMethod(Test(12300, 'Page 123'), 'page123')
instrumentMethod(Test(12400, 'Page 124'), 'page124')
instrumentMethod(Test(12500, 'Page 125'), 'page125')
instrumentMethod(Test(12600, 'Page 126'), 'page126')
