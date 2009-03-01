# The Grinder 3.2
# HTTP script recorded by TCPProxy at Mar 1, 2009 8:10:05 AM

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
  ( NVPair('Referer', 'http://192.168.1.110:8080/categoryList.Ochan'), )

headers1= \
  ( NVPair('Cache-Control', 'no-cache'), )

headers2= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/1'), )

headers3= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/thread/5'), )

headers4= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/thread/5'),
    NVPair('Cache-Control', 'no-cache'), )

headers5= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/2'), )

headers6= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/2'),
    NVPair('Cache-Control', 'no-cache'), )

headers7= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/3'), )

headers8= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/thread/37'), )

headers9= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/3'),
    NVPair('Cache-Control', 'no-cache'), )

headers10= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/4'), )

headers11= \
  ( NVPair('Referer', 'http://192.168.1.110:8080/chan/4'),
    NVPair('Cache-Control', 'no-cache'), )

url0 = 'http://192.168.1.110:8080'

# Create an HTTPRequest for each request, then replace the
# reference to the HTTPRequest with an instrumented version.
# You can access the unadorned instance using request101.__target__.
request101 = HTTPRequest(url=url0, headers=headers0)
request101 = Test(101, 'GET /').wrap(request101)

request201 = HTTPRequest(url=url0, headers=headers1)
request201 = Test(201, 'GET categoryList.Ochan').wrap(request201)

request202 = HTTPRequest(url=url0, headers=headers0)
request202 = Test(202, 'GET favicon.png').wrap(request202)

request203 = HTTPRequest(url=url0, headers=headers0)
request203 = Test(203, 'GET ext-base.js').wrap(request203)

request204 = HTTPRequest(url=url0, headers=headers0)
request204 = Test(204, 'GET ext-all.js').wrap(request204)

request205 = HTTPRequest(url=url0, headers=headers0)
request205 = Test(205, 'GET ext-all.css').wrap(request205)

request206 = HTTPRequest(url=url0, headers=headers0)
request206 = Test(206, 'GET 15px-Feed-icon.png').wrap(request206)

request301 = HTTPRequest(url=url0, headers=headers0)
request301 = Test(301, 'GET 1').wrap(request301)

request401 = HTTPRequest(url=url0, headers=headers2)
request401 = Test(401, 'POST 1').wrap(request401)

request402 = HTTPRequest(url=url0, headers=headers2)
request402 = Test(402, 'GET 1').wrap(request402)

request501 = HTTPRequest(url=url0, headers=headers2)
request501 = Test(501, 'GET 7').wrap(request501)

request601 = HTTPRequest(url=url0, headers=headers2)
request601 = Test(601, 'GET 5').wrap(request601)

request701 = HTTPRequest(url=url0, headers=headers3)
request701 = Test(701, 'GET /').wrap(request701)

request801 = HTTPRequest(url=url0, headers=headers3)
request801 = Test(801, 'GET /').wrap(request801)

request901 = HTTPRequest(url=url0, headers=headers3)
request901 = Test(901, 'GET /').wrap(request901)

request1001 = HTTPRequest(url=url0, headers=headers3)
request1001 = Test(1001, 'POST 5').wrap(request1001)

request1002 = HTTPRequest(url=url0, headers=headers3)
request1002 = Test(1002, 'GET /').wrap(request1002)

request1101 = HTTPRequest(url=url0, headers=headers3)
request1101 = Test(1101, 'GET /').wrap(request1101)

request1201 = HTTPRequest(url=url0, headers=headers3)
request1201 = Test(1201, 'GET /').wrap(request1201)

request1301 = HTTPRequest(url=url0, headers=headers3)
request1301 = Test(1301, 'GET /').wrap(request1301)

request1401 = HTTPRequest(url=url0, headers=headers3)
request1401 = Test(1401, 'GET /').wrap(request1401)

request1501 = HTTPRequest(url=url0, headers=headers3)
request1501 = Test(1501, 'GET /').wrap(request1501)

request1601 = HTTPRequest(url=url0, headers=headers3)
request1601 = Test(1601, 'GET /').wrap(request1601)

request1701 = HTTPRequest(url=url0, headers=headers3)
request1701 = Test(1701, 'GET /').wrap(request1701)

request1801 = HTTPRequest(url=url0, headers=headers3)
request1801 = Test(1801, 'POST 5').wrap(request1801)

request1802 = HTTPRequest(url=url0, headers=headers3)
request1802 = Test(1802, 'GET 5').wrap(request1802)

request1901 = HTTPRequest(url=url0, headers=headers3)
request1901 = Test(1901, 'GET 12').wrap(request1901)

request2001 = HTTPRequest(url=url0, headers=headers3)
request2001 = Test(2001, 'GET /').wrap(request2001)

request2101 = HTTPRequest(url=url0, headers=headers3)
request2101 = Test(2101, 'GET /').wrap(request2101)

request2201 = HTTPRequest(url=url0, headers=headers3)
request2201 = Test(2201, 'GET /').wrap(request2201)

request2301 = HTTPRequest(url=url0, headers=headers3)
request2301 = Test(2301, 'GET /').wrap(request2301)

request2401 = HTTPRequest(url=url0, headers=headers3)
request2401 = Test(2401, 'GET /').wrap(request2401)

request2501 = HTTPRequest(url=url0, headers=headers3)
request2501 = Test(2501, 'GET /').wrap(request2501)

request2601 = HTTPRequest(url=url0, headers=headers3)
request2601 = Test(2601, 'GET /').wrap(request2601)

request2701 = HTTPRequest(url=url0, headers=headers3)
request2701 = Test(2701, 'POST 5').wrap(request2701)

request2702 = HTTPRequest(url=url0, headers=headers3)
request2702 = Test(2702, 'GET /').wrap(request2702)

request2801 = HTTPRequest(url=url0, headers=headers3)
request2801 = Test(2801, 'GET /').wrap(request2801)

request2901 = HTTPRequest(url=url0, headers=headers3)
request2901 = Test(2901, 'GET /').wrap(request2901)

request3001 = HTTPRequest(url=url0, headers=headers4)
request3001 = Test(3001, 'GET /').wrap(request3001)

request3002 = HTTPRequest(url=url0, headers=headers3)
request3002 = Test(3002, 'GET categoryList.Ochan').wrap(request3002)

request3101 = HTTPRequest(url=url0, headers=headers0)
request3101 = Test(3101, 'GET 2').wrap(request3101)

request3201 = HTTPRequest(url=url0, headers=headers5)
request3201 = Test(3201, 'POST 2').wrap(request3201)

request3202 = HTTPRequest(url=url0, headers=headers5)
request3202 = Test(3202, 'GET 2').wrap(request3202)

request3301 = HTTPRequest(url=url0, headers=headers5)
request3301 = Test(3301, 'GET 19').wrap(request3301)

request3401 = HTTPRequest(url=url0, headers=headers5)
request3401 = Test(3401, 'POST 2').wrap(request3401)

request3402 = HTTPRequest(url=url0, headers=headers5)
request3402 = Test(3402, 'GET 2').wrap(request3402)

request3501 = HTTPRequest(url=url0, headers=headers5)
request3501 = Test(3501, 'GET 23').wrap(request3501)

request3601 = HTTPRequest(url=url0, headers=headers5)
request3601 = Test(3601, 'POST 2').wrap(request3601)

request3602 = HTTPRequest(url=url0, headers=headers5)
request3602 = Test(3602, 'GET 2').wrap(request3602)

request3701 = HTTPRequest(url=url0, headers=headers5)
request3701 = Test(3701, 'GET 27').wrap(request3701)

request3801 = HTTPRequest(url=url0, headers=headers5)
request3801 = Test(3801, 'GET 2').wrap(request3801)

request3901 = HTTPRequest(url=url0, headers=headers5)
request3901 = Test(3901, 'GET 31').wrap(request3901)

request4001 = HTTPRequest(url=url0, headers=headers5)
request4001 = Test(4001, 'GET 35').wrap(request4001)

request4101 = HTTPRequest(url=url0, headers=headers6)
request4101 = Test(4101, 'GET /').wrap(request4101)

request4102 = HTTPRequest(url=url0, headers=headers5)
request4102 = Test(4102, 'GET categoryList.Ochan').wrap(request4102)

request4201 = HTTPRequest(url=url0, headers=headers0)
request4201 = Test(4201, 'GET 3').wrap(request4201)

request4301 = HTTPRequest(url=url0, headers=headers7)
request4301 = Test(4301, 'GET 3').wrap(request4301)

request4401 = HTTPRequest(url=url0, headers=headers7)
request4401 = Test(4401, 'GET 37').wrap(request4401)

request4501 = HTTPRequest(url=url0, headers=headers8)
request4501 = Test(4501, 'GET /').wrap(request4501)

request4601 = HTTPRequest(url=url0, headers=headers8)
request4601 = Test(4601, 'POST 37').wrap(request4601)

request4602 = HTTPRequest(url=url0, headers=headers8)
request4602 = Test(4602, 'GET 37').wrap(request4602)

request4701 = HTTPRequest(url=url0, headers=headers8)
request4701 = Test(4701, 'GET 3').wrap(request4701)

request4801 = HTTPRequest(url=url0, headers=headers7)
request4801 = Test(4801, 'POST 3').wrap(request4801)

request4802 = HTTPRequest(url=url0, headers=headers7)
request4802 = Test(4802, 'GET 3').wrap(request4802)

request4901 = HTTPRequest(url=url0, headers=headers7)
request4901 = Test(4901, 'POST 3').wrap(request4901)

request4902 = HTTPRequest(url=url0, headers=headers7)
request4902 = Test(4902, 'GET 3').wrap(request4902)

request5001 = HTTPRequest(url=url0, headers=headers7)
request5001 = Test(5001, 'POST 3').wrap(request5001)

request5002 = HTTPRequest(url=url0, headers=headers9)
request5002 = Test(5002, 'GET /').wrap(request5002)

request5003 = HTTPRequest(url=url0, headers=headers7)
request5003 = Test(5003, 'GET categoryList.Ochan').wrap(request5003)

request5101 = HTTPRequest(url=url0, headers=headers10)
request5101 = Test(5101, 'GET 4').wrap(request5101)

request5201 = HTTPRequest(url=url0, headers=headers10)
request5201 = Test(5201, 'POST 4').wrap(request5201)

request5202 = HTTPRequest(url=url0, headers=headers10)
request5202 = Test(5202, 'GET 4').wrap(request5202)

request5301 = HTTPRequest(url=url0, headers=headers10)
request5301 = Test(5301, 'POST 4').wrap(request5301)

request5401 = HTTPRequest(url=url0, headers=headers10)
request5401 = Test(5401, 'POST 4').wrap(request5401)

request5501 = HTTPRequest(url=url0, headers=headers10)
request5501 = Test(5501, 'POST 4').wrap(request5501)

request5601 = HTTPRequest(url=url0, headers=headers10)
request5601 = Test(5601, 'POST 4').wrap(request5601)

request5602 = HTTPRequest(url=url0, headers=headers10)
request5602 = Test(5602, 'GET 4').wrap(request5602)

request5701 = HTTPRequest(url=url0, headers=headers11)
request5701 = Test(5701, 'GET /').wrap(request5701)

request5702 = HTTPRequest(url=url0, headers=headers10)
request5702 = Test(5702, 'GET categoryList.Ochan').wrap(request5702)


class TestRunner:
  """A TestRunner instance is created for each worker thread."""

  # A method for each recorded page.
  def page1(self):
    """GET / (request 101)."""
    self.token__dc = \
      '1235913006479'
    result = request101.GET('/remote/rest/thread/next/0/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page2(self):
    """GET categoryList.Ochan (requests 201-206)."""
    result = request201.GET('/categoryList.Ochan')
    self.token_identifier = \
      httpUtilities.valueFromBodyURI('identifier') # ''

    grinder.sleep(30)
    request202.GET('/favicon.png', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    request203.GET('/extjs/adapter/ext/ext-base.js', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    grinder.sleep(50)
    request204.GET('/extjs/ext-all.js', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    grinder.sleep(692)
    request205.GET('/extjs/resources/css/ext-all.css', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    grinder.sleep(141)
    request206.GET('/15px-Feed-icon.png', None,
      ( NVPair('If-Modified-Since', 'Sun, 01 Mar 2009 12:30:33 GMT'), ))

    return result

  def page3(self):
    """GET 1 (request 301)."""
    result = request301.GET('/chan/1')
    self.token_identifier = \
      httpUtilities.valueFromHiddenInput('identifier') # '1'
    self.token_categoryIdentifier = \
      httpUtilities.valueFromHiddenInput('categoryIdentifier') # '1'
    self.token_email = \
      httpUtilities.valueFromHiddenInput('email') # ''
    self.token_url = \
      httpUtilities.valueFromHiddenInput('url') # ''

    return result

  def page4(self):
    """POST 1 (requests 401-402)."""
    
    # Expecting 302 'Found'
    result = request401.POST('/chan/1',
      '''------------JYzTqo9yFzzMrg09jbjX8O\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n1\r\n------------JYzTqo9yFzzMrg09jbjX8O\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n1\r\n------------JYzTqo9yFzzMrg09jbjX8O\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------JYzTqo9yFzzMrg09jbjX8O\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------JYzTqo9yFzzMrg09jbjX8O\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------JYzTqo9yFzzMrg09jbjX8O\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------JYzTqo9yFzzMrg09jbjX8O\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\n?\r\n------------JYzTqo9yFzzMrg09jbjX8O\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------JYzTqo9yFzzMrg09jbjX8O\r\nContent-Disposition: form-data; name=\"fileUrl\"\r\n\r\nhttp://192.168.1.104/wiki/images/1/11/Ochan-logo.png\r\n------------JYzTqo9yFzzMrg09jbjX8O--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------JYzTqo9yFzzMrg09jbjX8O'), ))

    grinder.sleep(24)
    request402.GET('/chan/1')

    return result

  def page5(self):
    """GET 7 (request 501)."""
    self.token_thumb = \
      'true'
    result = request501.GET('/chan/img/7' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page6(self):
    """GET 5 (request 601)."""
    result = request601.GET('/chan/thread/5')
    self.token_parent = \
      httpUtilities.valueFromHiddenInput('parent') # '5'

    return result

  def page7(self):
    """GET / (request 701)."""
    self.token__dc = \
      '1235913018280'
    result = request701.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page8(self):
    """GET / (request 801)."""
    self.token__dc = \
      '1235913019281'
    result = request801.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page9(self):
    """GET / (request 901)."""
    self.token__dc = \
      '1235913020281'
    result = request901.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page10(self):
    """POST 5 (requests 1001-1002)."""
    
    # Expecting 302 'Found'
    result = request1001.POST('/chan/thread/5',
      '''------------ThcHpXFS2lHlLpOCThTgUK\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------ThcHpXFS2lHlLpOCThTgUK\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------ThcHpXFS2lHlLpOCThTgUK\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------ThcHpXFS2lHlLpOCThTgUK\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------ThcHpXFS2lHlLpOCThTgUK\r\nContent-Disposition: form-data; name=\"parent\"\r\n\r\n5\r\n------------ThcHpXFS2lHlLpOCThTgUK\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\ntest?\r\n------------ThcHpXFS2lHlLpOCThTgUK\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------ThcHpXFS2lHlLpOCThTgUK\r\nContent-Disposition: form-data; name=\"fileUrl\"\r\n\r\nhttp://192.168.1.104/wiki/images/1/11/Ochan-logo.png\r\n------------ThcHpXFS2lHlLpOCThTgUK--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------ThcHpXFS2lHlLpOCThTgUK'), ))

    self.token__dc = \
      '1235913022426'
    request1002.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page11(self):
    """GET / (request 1101)."""
    self.token__dc = \
      '1235913023427'
    result = request1101.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page12(self):
    """GET / (request 1201)."""
    self.token__dc = \
      '1235913024426'
    result = request1201.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page13(self):
    """GET / (request 1301)."""
    self.token__dc = \
      '1235913025427'
    result = request1301.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page14(self):
    """GET / (request 1401)."""
    self.token__dc = \
      '1235913026427'
    result = request1401.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page15(self):
    """GET / (request 1501)."""
    self.token__dc = \
      '1235913027427'
    result = request1501.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page16(self):
    """GET / (request 1601)."""
    self.token__dc = \
      '1235913028428'
    result = request1601.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page17(self):
    """GET / (request 1701)."""
    self.token__dc = \
      '1235913029428'
    result = request1701.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page18(self):
    """POST 5 (requests 1801-1802)."""
    
    # Expecting 302 'Found'
    result = request1801.POST('/chan/thread/5',
      '''------------fMvyoLgwGRIWNA3DBnWR1Y\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------fMvyoLgwGRIWNA3DBnWR1Y\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------fMvyoLgwGRIWNA3DBnWR1Y\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------fMvyoLgwGRIWNA3DBnWR1Y\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------fMvyoLgwGRIWNA3DBnWR1Y\r\nContent-Disposition: form-data; name=\"parent\"\r\n\r\n5\r\n------------fMvyoLgwGRIWNA3DBnWR1Y\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\ntest?\r\n------------fMvyoLgwGRIWNA3DBnWR1Y\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------fMvyoLgwGRIWNA3DBnWR1Y\r\nContent-Disposition: form-data; name=\"fileUrl\"\r\n\r\nhttp://192.168.1.104/wiki/images/1/11/Ochan-logo.png\r\n------------fMvyoLgwGRIWNA3DBnWR1Y--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------fMvyoLgwGRIWNA3DBnWR1Y'), ))

    grinder.sleep(14)
    request1802.GET('/chan/thread/5')

    return result

  def page19(self):
    """GET 12 (request 1901)."""
    result = request1901.GET('/chan/img/12' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page20(self):
    """GET / (request 2001)."""
    self.token__dc = \
      '1235913031148'
    result = request2001.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page21(self):
    """GET / (request 2101)."""
    self.token__dc = \
      '1235913032148'
    result = request2101.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page22(self):
    """GET / (request 2201)."""
    self.token__dc = \
      '1235913033149'
    result = request2201.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page23(self):
    """GET / (request 2301)."""
    self.token__dc = \
      '1235913034148'
    result = request2301.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page24(self):
    """GET / (request 2401)."""
    self.token__dc = \
      '1235913035148'
    result = request2401.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page25(self):
    """GET / (request 2501)."""
    self.token__dc = \
      '1235913036148'
    result = request2501.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page26(self):
    """GET / (request 2601)."""
    self.token__dc = \
      '1235913037148'
    result = request2601.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page27(self):
    """POST 5 (requests 2701-2702)."""
    
    # Expecting 302 'Found'
    result = request2701.POST('/chan/thread/5',
      '''------------VhduzBZ7C3izWpKxctz6TD\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------VhduzBZ7C3izWpKxctz6TD\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------VhduzBZ7C3izWpKxctz6TD\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------VhduzBZ7C3izWpKxctz6TD\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------VhduzBZ7C3izWpKxctz6TD\r\nContent-Disposition: form-data; name=\"parent\"\r\n\r\n5\r\n------------VhduzBZ7C3izWpKxctz6TD\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\n12141?\r\n------------VhduzBZ7C3izWpKxctz6TD\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------VhduzBZ7C3izWpKxctz6TD\r\nContent-Disposition: form-data; name=\"fileUrl\"\r\n\r\nhttp://192.168.1.104/wiki/images/1/11/Ochan-logo.png\r\n------------VhduzBZ7C3izWpKxctz6TD--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------VhduzBZ7C3izWpKxctz6TD'), ))

    self.token__dc = \
      '1235913039041'
    request2702.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page28(self):
    """GET / (request 2801)."""
    self.token__dc = \
      '1235913040046'
    result = request2801.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page29(self):
    """GET / (request 2901)."""
    self.token__dc = \
      '1235913042046'
    result = request2901.GET('/remote/rest/thread/status/5/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page30(self):
    """GET / (requests 3001-3002)."""
    
    # Expecting 302 'Found'
    result = request3001.GET('/')

    grinder.sleep(21)
    request3002.GET('/categoryList.Ochan')
    self.token_identifier = \
      httpUtilities.valueFromBodyURI('identifier') # ''

    return result

  def page31(self):
    """GET 2 (request 3101)."""
    result = request3101.GET('/chan/2')
    self.token_identifier = \
      httpUtilities.valueFromHiddenInput('identifier') # '2'
    self.token_categoryIdentifier = \
      httpUtilities.valueFromHiddenInput('categoryIdentifier') # '2'

    return result

  def page32(self):
    """POST 2 (requests 3201-3202)."""
    
    # Expecting 302 'Found'
    result = request3201.POST('/chan/2',
      '''------------9qoXaJp039X4HvwapYLJnN\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n2\r\n------------9qoXaJp039X4HvwapYLJnN\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n2\r\n------------9qoXaJp039X4HvwapYLJnN\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------9qoXaJp039X4HvwapYLJnN\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n!!!\r\n------------9qoXaJp039X4HvwapYLJnN\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------9qoXaJp039X4HvwapYLJnN\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------9qoXaJp039X4HvwapYLJnN\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\n!!!?\r\n------------9qoXaJp039X4HvwapYLJnN\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------9qoXaJp039X4HvwapYLJnN\r\nContent-Disposition: form-data; name=\"fileUrl\"\r\n\r\nhttp://192.168.1.104/wiki/images/1/11/Ochan-logo.png\r\n------------9qoXaJp039X4HvwapYLJnN--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------9qoXaJp039X4HvwapYLJnN'), ))

    grinder.sleep(13)
    request3202.GET('/chan/2')

    return result

  def page33(self):
    """GET 19 (request 3301)."""
    result = request3301.GET('/chan/img/19' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page34(self):
    """POST 2 (requests 3401-3402)."""
    
    # Expecting 302 'Found'
    result = request3401.POST('/chan/2',
      '''------------ClJxm5QMkDsdSzhck7lqVv\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n2\r\n------------ClJxm5QMkDsdSzhck7lqVv\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n2\r\n------------ClJxm5QMkDsdSzhck7lqVv\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------ClJxm5QMkDsdSzhck7lqVv\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------ClJxm5QMkDsdSzhck7lqVv\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------ClJxm5QMkDsdSzhck7lqVv\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------ClJxm5QMkDsdSzhck7lqVv\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\nhttp://192.168.1.104/wiki/images/1/11/Ochan-logo.png?\r\n------------ClJxm5QMkDsdSzhck7lqVv\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------ClJxm5QMkDsdSzhck7lqVv\r\nContent-Disposition: form-data; name=\"fileUrl\"\r\n\r\nhttp://192.168.1.104/wiki/images/1/11/Ochan-logo.png\r\n------------ClJxm5QMkDsdSzhck7lqVv--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------ClJxm5QMkDsdSzhck7lqVv'), ))

    request3402.GET('/chan/2')

    return result

  def page35(self):
    """GET 23 (request 3501)."""
    result = request3501.GET('/chan/img/23' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page36(self):
    """POST 2 (requests 3601-3602)."""
    
    # Expecting 302 'Found'
    result = request3601.POST('/chan/2',
      '''------------weU0SsORBzidOQZOkCFj4D\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n2\r\n------------weU0SsORBzidOQZOkCFj4D\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n2\r\n------------weU0SsORBzidOQZOkCFj4D\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------weU0SsORBzidOQZOkCFj4D\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------weU0SsORBzidOQZOkCFj4D\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------weU0SsORBzidOQZOkCFj4D\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------weU0SsORBzidOQZOkCFj4D\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\n\r\n------------weU0SsORBzidOQZOkCFj4D\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------weU0SsORBzidOQZOkCFj4D\r\nContent-Disposition: form-data; name=\"fileUrl\"\r\n\r\nhttp://192.168.1.104/wiki/images/1/11/Ochan-logo.png\r\n------------weU0SsORBzidOQZOkCFj4D--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------weU0SsORBzidOQZOkCFj4D'), ))

    request3602.GET('/chan/2')

    return result

  def page37(self):
    """GET 27 (request 3701)."""
    result = request3701.GET('/chan/img/27' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page38(self):
    """GET 2 (request 3801)."""
    result = request3801.GET('/chan/2')

    return result

  def page39(self):
    """GET 31 (request 3901)."""
    result = request3901.GET('/chan/img/31' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page40(self):
    """GET 35 (request 4001)."""
    result = request4001.GET('/chan/img/35' +
      '?thumb=' +
      self.token_thumb)

    return result

  def page41(self):
    """GET / (requests 4101-4102)."""
    
    # Expecting 302 'Found'
    result = request4101.GET('/')

    grinder.sleep(12)
    request4102.GET('/categoryList.Ochan')
    self.token_identifier = \
      httpUtilities.valueFromBodyURI('identifier') # ''

    return result

  def page42(self):
    """GET 3 (request 4201)."""
    result = request4201.GET('/chan/3')
    self.token_identifier = \
      httpUtilities.valueFromHiddenInput('identifier') # '3'
    self.token_categoryIdentifier = \
      httpUtilities.valueFromHiddenInput('categoryIdentifier') # '3'

    return result

  def page43(self):
    """GET 3 (request 4301)."""
    result = request4301.GET('/chan/3')

    return result

  def page44(self):
    """GET 37 (request 4401)."""
    result = request4401.GET('/chan/thread/37')
    self.token_parent = \
      httpUtilities.valueFromHiddenInput('parent') # '37'

    return result

  def page45(self):
    """GET / (request 4501)."""
    self.token__dc = \
      '1235913107575'
    result = request4501.GET('/remote/rest/thread/status/37/' +
      '?_dc=' +
      self.token__dc)

    return result

  def page46(self):
    """POST 37 (requests 4601-4602)."""
    
    # Expecting 302 'Found'
    result = request4601.POST('/chan/thread/37',
      '''------------oAfiUWXcGbIWSUoGmQBAJD\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------oAfiUWXcGbIWSUoGmQBAJD\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------oAfiUWXcGbIWSUoGmQBAJD\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------oAfiUWXcGbIWSUoGmQBAJD\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------oAfiUWXcGbIWSUoGmQBAJD\r\nContent-Disposition: form-data; name=\"parent\"\r\n\r\n37\r\n------------oAfiUWXcGbIWSUoGmQBAJD\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sit amet odio. Sed consectetur. Quisque facilisis elit tempor orci. Duis sollicitudin leo sed neque sodales faucibus. Nulla dolor felis, aliquam a, vestibulum vel, vulputate sit amet, felis. Proin sollicitudin, felis vel sagittis hendrerit, elit justo facilisis ipsum, in iaculis nulla odio nec tellus. Donec gravida ante quis lectus. Praesent rhoncus laoreet purus. Nunc ac nibh ultrices arcu commodo scelerisque. Maecenas luctus tortor sed tellus. Aliquam lorem. Proin urna ipsum, ultricies non, venenatis nec, venenatis ut, quam. Aenean semper. Pellentesque at magna vitae enim ornare ultrices. In libero magna, convallis id, sodales congue, suscipit eget, justo.<BR><BR>Fusce hendrerit magna at ligula. Cras arcu turpis, mattis a, viverra eget, euismod gravida, lacus. Cras ligula. Vivamus accumsan, massa non tincidunt venenatis, magna massa blandit ipsum, nec rhoncus felis urna ornare urna. Sed sed nisl at ligula dapibus faucibus. Cras sodales. Ut ultrices. Duis id diam. Duis euismod pede. Maecenas sodales sollicitudin orci. Nam ut sem. Aliquam mollis neque congue dui. Integer ullamcorper. Curabitur euismod, lectus id blandit consequat, augue diam vehicula turpis, faucibus pulvinar sem metus id tellus. Cras suscipit odio vitae nisl. Suspendisse ac nibh a lacus sollicitudin consequat. Maecenas consectetur vulputate sem. Nunc posuere turpis non magna.<BR><BR>In hac habitasse platea dictumst. Cras volutpat. In lobortis nulla sit amet leo. In semper vulputate dui. Fusce tincidunt, erat id placerat pretium, lacus dui imperdiet sapien, a rhoncus diam eros commodo neque. Proin nec massa at diam rutrum suscipit. Aliquam condimentum enim quis urna. Sed nulla tortor, cursus eu, viverra ac, laoreet gravida, purus. Etiam vel justo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque pretium gravida massa. Cras euismod.<BR><BR>Maecenas feugiat sollicitudin enim. Mauris fringilla, ligula quis eleifend scelerisque, lacus ligula condimentum elit, in semper neque leo ac nunc. Duis nibh est, aliquam eu, vehicula vel, facilisis adipiscing, dui. Curabitur justo. Nunc quis metus. Donec tincidunt ultricies ante. Suspendisse semper feugiat diam. Maecenas blandit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla ipsum turpis, lobortis sit amet, tincidunt et, interdum eu, magna. Praesent vestibulum eros vel pede. Etiam enim massa, pellentesque ut, gravida placerat, euismod iaculis, nulla. Maecenas libero orci, iaculis non, laoreet et, eleifend nec, lorem. Phasellus imperdiet blandit ipsum. Phasellus ac elit vitae diam porta facilisis. Proin pellentesque vulputate lacus. In a justo ut justo hendrerit vestibulum.<BR><BR>Aliquam erat volutpat. Sed quis pede vitae lorem scelerisque euismod. Integer feugiat turpis. Aenean semper. Aliquam scelerisque, augue eget malesuada auctor, tellus augue varius ipsum, sit amet placerat nisl quam a lorem. Pellentesque porta odio ac justo. Mauris lacinia convallis dolor. Vivamus et urna. Ut elementum. Pellentesque id pede. Vivamus tincidunt. Maecenas non mauris non nibh gravida sagittis. Donec elit augue, ullamcorper eu, lacinia volutpat, dictum sed, purus. Vivamus euismod libero eget leo. Morbi rhoncus, sapien nec pellentesque lobortis, tellus magna placerat diam, sed auctor mi diam sed odio. Sed tincidunt. Nulla hendrerit fringilla nunc. Mauris sem tellus, ornare in, eleifend at, posuere eu, enim. <BR>?\r\n------------oAfiUWXcGbIWSUoGmQBAJD\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------oAfiUWXcGbIWSUoGmQBAJD--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------oAfiUWXcGbIWSUoGmQBAJD'), ))

    grinder.sleep(13)
    request4602.GET('/chan/thread/37')

    return result

  def page47(self):
    """GET 3 (request 4701)."""
    result = request4701.GET('/chan/3')

    return result

  def page48(self):
    """POST 3 (requests 4801-4802)."""
    
    # Expecting 302 'Found'
    result = request4801.POST('/chan/3',
      '''------------LPS8B2BGKv6OgpwUeEDQPh\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n3\r\n------------LPS8B2BGKv6OgpwUeEDQPh\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n3\r\n------------LPS8B2BGKv6OgpwUeEDQPh\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------LPS8B2BGKv6OgpwUeEDQPh\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------LPS8B2BGKv6OgpwUeEDQPh\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------LPS8B2BGKv6OgpwUeEDQPh\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------LPS8B2BGKv6OgpwUeEDQPh\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sit amet odio. Sed consectetur. Quisque facilisis elit tempor orci. Duis sollicitudin leo sed neque sodales faucibus. Nulla dolor felis, aliquam a, vestibulum vel, vulputate sit amet, felis. Proin sollicitudin, felis vel sagittis hendrerit, elit justo facilisis ipsum, in iaculis nulla odio nec tellus. Donec gravida ante quis lectus. Praesent rhoncus laoreet purus. Nunc ac nibh ultrices arcu commodo scelerisque. Maecenas luctus tortor sed tellus. Aliquam lorem. Proin urna ipsum, ultricies non, venenatis nec, venenatis ut, quam. Aenean semper. Pellentesque at magna vitae enim ornare ultrices. In libero magna, convallis id, sodales congue, suscipit eget, justo.<BR><BR>Fusce hendrerit magna at ligula. Cras arcu turpis, mattis a, viverra eget, euismod gravida, lacus. Cras ligula. Vivamus accumsan, massa non tincidunt venenatis, magna massa blandit ipsum, nec rhoncus felis urna ornare urna. Sed sed nisl at ligula dapibus faucibus. Cras sodales. Ut ultrices. Duis id diam. Duis euismod pede. Maecenas sodales sollicitudin orci. Nam ut sem. Aliquam mollis neque congue dui. Integer ullamcorper. Curabitur euismod, lectus id blandit consequat, augue diam vehicula turpis, faucibus pulvinar sem metus id tellus. Cras suscipit odio vitae nisl. Suspendisse ac nibh a lacus sollicitudin consequat. Maecenas consectetur vulputate sem. Nunc posuere turpis non magna.<BR><BR>In hac habitasse platea dictumst. Cras volutpat. In lobortis nulla sit amet leo. In semper vulputate dui. Fusce tincidunt, erat id placerat pretium, lacus dui imperdiet sapien, a rhoncus diam eros commodo neque. Proin nec massa at diam rutrum suscipit. Aliquam condimentum enim quis urna. Sed nulla tortor, cursus eu, viverra ac, laoreet gravida, purus. Etiam vel justo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque pretium gravida massa. Cras euismod.<BR><BR>Maecenas feugiat sollicitudin enim. Mauris fringilla, ligula quis eleifend scelerisque, lacus ligula condimentum elit, in semper neque leo ac nunc. Duis nibh est, aliquam eu, vehicula vel, facilisis adipiscing, dui. Curabitur justo. Nunc quis metus. Donec tincidunt ultricies ante. Suspendisse semper feugiat diam. Maecenas blandit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla ipsum turpis, lobortis sit amet, tincidunt et, interdum eu, magna. Praesent vestibulum eros vel pede. Etiam enim massa, pellentesque ut, gravida placerat, euismod iaculis, nulla. Maecenas libero orci, iaculis non, laoreet et, eleifend nec, lorem. Phasellus imperdiet blandit ipsum. Phasellus ac elit vitae diam porta facilisis. Proin pellentesque vulputate lacus. In a justo ut justo hendrerit vestibulum.<BR><BR>Aliquam erat volutpat. Sed quis pede vitae lorem scelerisque euismod. Integer feugiat turpis. Aenean semper. Aliquam scelerisque, augue eget malesuada auctor, tellus augue varius ipsum, sit amet placerat nisl quam a lorem. Pellentesque porta odio ac justo. Mauris lacinia convallis dolor. Vivamus et urna. Ut elementum. Pellentesque id pede. Vivamus tincidunt. Maecenas non mauris non nibh gravida sagittis. Donec elit augue, ullamcorper eu, lacinia volutpat, dictum sed, purus. Vivamus euismod libero eget leo. Morbi rhoncus, sapien nec pellentesque lobortis, tellus magna placerat diam, sed auctor mi diam sed odio. Sed tincidunt. Nulla hendrerit fringilla nunc. Mauris sem tellus, ornare in, eleifend at, posuere eu, enim. <BR>?\r\n------------LPS8B2BGKv6OgpwUeEDQPh\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------LPS8B2BGKv6OgpwUeEDQPh--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------LPS8B2BGKv6OgpwUeEDQPh'), ))

    grinder.sleep(13)
    request4802.GET('/chan/3')

    return result

  def page49(self):
    """POST 3 (requests 4901-4902)."""
    
    # Expecting 302 'Found'
    result = request4901.POST('/chan/3',
      '''------------RNbHxycBtrjuPI82iLoYyv\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n3\r\n------------RNbHxycBtrjuPI82iLoYyv\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n3\r\n------------RNbHxycBtrjuPI82iLoYyv\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------RNbHxycBtrjuPI82iLoYyv\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------RNbHxycBtrjuPI82iLoYyv\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------RNbHxycBtrjuPI82iLoYyv\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------RNbHxycBtrjuPI82iLoYyv\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sit amet odio. Sed consectetur. Quisque facilisis elit tempor orci. Duis sollicitudin leo sed neque sodales faucibus. Nulla dolor felis, aliquam a, vestibulum vel, vulputate sit amet, felis. Proin sollicitudin, felis vel sagittis hendrerit, elit justo facilisis ipsum, in iaculis nulla odio nec tellus. Donec gravida ante quis lectus. Praesent rhoncus laoreet purus. Nunc ac nibh ultrices arcu commodo scelerisque. Maecenas luctus tortor sed tellus. Aliquam lorem. Proin urna ipsum, ultricies non, venenatis nec, venenatis ut, quam. Aenean semper. Pellentesque at magna vitae enim ornare ultrices. In libero magna, convallis id, sodales congue, suscipit eget, justo.<BR><BR>Fusce hendrerit magna at ligula. Cras arcu turpis, mattis a, viverra eget, euismod gravida, lacus. Cras ligula. Vivamus accumsan, massa non tincidunt venenatis, magna massa blandit ipsum, nec rhoncus felis urna ornare urna. Sed sed nisl at ligula dapibus faucibus. Cras sodales. Ut ultrices. Duis id diam. Duis euismod pede. Maecenas sodales sollicitudin orci. Nam ut sem. Aliquam mollis neque congue dui. Integer ullamcorper. Curabitur euismod, lectus id blandit consequat, augue diam vehicula turpis, faucibus pulvinar sem metus id tellus. Cras suscipit odio vitae nisl. Suspendisse ac nibh a lacus sollicitudin consequat. Maecenas consectetur vulputate sem. Nunc posuere turpis non magna.<BR><BR>In hac habitasse platea dictumst. Cras volutpat. In lobortis nulla sit amet leo. In semper vulputate dui. Fusce tincidunt, erat id placerat pretium, lacus dui imperdiet sapien, a rhoncus diam eros commodo neque. Proin nec massa at diam rutrum suscipit. Aliquam condimentum enim quis urna. Sed nulla tortor, cursus eu, viverra ac, laoreet gravida, purus. Etiam vel justo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque pretium gravida massa. Cras euismod.<BR><BR>Maecenas feugiat sollicitudin enim. Mauris fringilla, ligula quis eleifend scelerisque, lacus ligula condimentum elit, in semper neque leo ac nunc. Duis nibh est, aliquam eu, vehicula vel, facilisis adipiscing, dui. Curabitur justo. Nunc quis metus. Donec tincidunt ultricies ante. Suspendisse semper feugiat diam. Maecenas blandit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla ipsum turpis, lobortis sit amet, tincidunt et, interdum eu, magna. Praesent vestibulum eros vel pede. Etiam enim massa, pellentesque ut, gravida placerat, euismod iaculis, nulla. Maecenas libero orci, iaculis non, laoreet et, eleifend nec, lorem. Phasellus imperdiet blandit ipsum. Phasellus ac elit vitae diam porta facilisis. Proin pellentesque vulputate lacus. In a justo ut justo hendrerit vestibulum.<BR><BR>Aliquam erat volutpat. Sed quis pede vitae lorem scelerisque euismod. Integer feugiat turpis. Aenean semper. Aliquam scelerisque, augue eget malesuada auctor, tellus augue varius ipsum, sit amet placerat nisl quam a lorem. Pellentesque porta odio ac justo. Mauris lacinia convallis dolor. Vivamus et urna. Ut elementum. Pellentesque id pede. Vivamus tincidunt. Maecenas non mauris non nibh gravida sagittis. Donec elit augue, ullamcorper eu, lacinia volutpat, dictum sed, purus. Vivamus euismod libero eget leo. Morbi rhoncus, sapien nec pellentesque lobortis, tellus magna placerat diam, sed auctor mi diam sed odio. Sed tincidunt. Nulla hendrerit fringilla nunc. Mauris sem tellus, ornare in, eleifend at, posuere eu, enim. <BR>?\r\n------------RNbHxycBtrjuPI82iLoYyv\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------RNbHxycBtrjuPI82iLoYyv--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------RNbHxycBtrjuPI82iLoYyv'), ))

    grinder.sleep(13)
    request4902.GET('/chan/3')

    return result

  def page50(self):
    """POST 3 (requests 5001-5003)."""
    
    # Expecting 302 'Found'
    result = request5001.POST('/chan/3',
      '''------------kzGzgqLsWpSBhS06IAeQ1U\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n3\r\n------------kzGzgqLsWpSBhS06IAeQ1U\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n3\r\n------------kzGzgqLsWpSBhS06IAeQ1U\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------kzGzgqLsWpSBhS06IAeQ1U\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------kzGzgqLsWpSBhS06IAeQ1U\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------kzGzgqLsWpSBhS06IAeQ1U\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------kzGzgqLsWpSBhS06IAeQ1U\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sit amet odio. Sed consectetur. Quisque facilisis elit tempor orci. Duis sollicitudin leo sed neque sodales faucibus. Nulla dolor felis, aliquam a, vestibulum vel, vulputate sit amet, felis. Proin sollicitudin, felis vel sagittis hendrerit, elit justo facilisis ipsum, in iaculis nulla odio nec tellus. Donec gravida ante quis lectus. Praesent rhoncus laoreet purus. Nunc ac nibh ultrices arcu commodo scelerisque. Maecenas luctus tortor sed tellus. Aliquam lorem. Proin urna ipsum, ultricies non, venenatis nec, venenatis ut, quam. Aenean semper. Pellentesque at magna vitae enim ornare ultrices. In libero magna, convallis id, sodales congue, suscipit eget, justo.<BR><BR>Fusce hendrerit magna at ligula. Cras arcu turpis, mattis a, viverra eget, euismod gravida, lacus. Cras ligula. Vivamus accumsan, massa non tincidunt venenatis, magna massa blandit ipsum, nec rhoncus felis urna ornare urna. Sed sed nisl at ligula dapibus faucibus. Cras sodales. Ut ultrices. Duis id diam. Duis euismod pede. Maecenas sodales sollicitudin orci. Nam ut sem. Aliquam mollis neque congue dui. Integer ullamcorper. Curabitur euismod, lectus id blandit consequat, augue diam vehicula turpis, faucibus pulvinar sem metus id tellus. Cras suscipit odio vitae nisl. Suspendisse ac nibh a lacus sollicitudin consequat. Maecenas consectetur vulputate sem. Nunc posuere turpis non magna.<BR><BR>In hac habitasse platea dictumst. Cras volutpat. In lobortis nulla sit amet leo. In semper vulputate dui. Fusce tincidunt, erat id placerat pretium, lacus dui imperdiet sapien, a rhoncus diam eros commodo neque. Proin nec massa at diam rutrum suscipit. Aliquam condimentum enim quis urna. Sed nulla tortor, cursus eu, viverra ac, laoreet gravida, purus. Etiam vel justo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque pretium gravida massa. Cras euismod.<BR><BR>Maecenas feugiat sollicitudin enim. Mauris fringilla, ligula quis eleifend scelerisque, lacus ligula condimentum elit, in semper neque leo ac nunc. Duis nibh est, aliquam eu, vehicula vel, facilisis adipiscing, dui. Curabitur justo. Nunc quis metus. Donec tincidunt ultricies ante. Suspendisse semper feugiat diam. Maecenas blandit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla ipsum turpis, lobortis sit amet, tincidunt et, interdum eu, magna. Praesent vestibulum eros vel pede. Etiam enim massa, pellentesque ut, gravida placerat, euismod iaculis, nulla. Maecenas libero orci, iaculis non, laoreet et, eleifend nec, lorem. Phasellus imperdiet blandit ipsum. Phasellus ac elit vitae diam porta facilisis. Proin pellentesque vulputate lacus. In a justo ut justo hendrerit vestibulum.<BR><BR>Aliquam erat volutpat. Sed quis pede vitae lorem scelerisque euismod. Integer feugiat turpis. Aenean semper. Aliquam scelerisque, augue eget malesuada auctor, tellus augue varius ipsum, sit amet placerat nisl quam a lorem. Pellentesque porta odio ac justo. Mauris lacinia convallis dolor. Vivamus et urna. Ut elementum. Pellentesque id pede. Vivamus tincidunt. Maecenas non mauris non nibh gravida sagittis. Donec elit augue, ullamcorper eu, lacinia volutpat, dictum sed, purus. Vivamus euismod libero eget leo. Morbi rhoncus, sapien nec pellentesque lobortis, tellus magna placerat diam, sed auctor mi diam sed odio. Sed tincidunt. Nulla hendrerit fringilla nunc. Mauris sem tellus, ornare in, eleifend at, posuere eu, enim. <BR>?\r\n------------kzGzgqLsWpSBhS06IAeQ1U\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------kzGzgqLsWpSBhS06IAeQ1U--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------kzGzgqLsWpSBhS06IAeQ1U'), ))

    
    # Expecting 302 'Found'
    request5002.GET('/')

    grinder.sleep(12)
    request5003.GET('/categoryList.Ochan')
    self.token_identifier = \
      httpUtilities.valueFromBodyURI('identifier') # ''

    return result

  def page51(self):
    """GET 4 (request 5101)."""
    result = request5101.GET('/chan/4')
    self.token_identifier = \
      httpUtilities.valueFromHiddenInput('identifier') # '4'
    self.token_categoryIdentifier = \
      httpUtilities.valueFromHiddenInput('categoryIdentifier') # '4'

    return result

  def page52(self):
    """POST 4 (requests 5201-5202)."""
    
    # Expecting 302 'Found'
    result = request5201.POST('/chan/4',
      '''------------XcEnkMnsq7wLkA732zFNmZ\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n4\r\n------------XcEnkMnsq7wLkA732zFNmZ\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n4\r\n------------XcEnkMnsq7wLkA732zFNmZ\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------XcEnkMnsq7wLkA732zFNmZ\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------XcEnkMnsq7wLkA732zFNmZ\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------XcEnkMnsq7wLkA732zFNmZ\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------XcEnkMnsq7wLkA732zFNmZ\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sit amet odio. Sed consectetur. Quisque facilisis elit tempor orci. Duis sollicitudin leo sed neque sodales faucibus. Nulla dolor felis, aliquam a, vestibulum vel, vulputate sit amet, felis. Proin sollicitudin, felis vel sagittis hendrerit, elit justo facilisis ipsum, in iaculis nulla odio nec tellus. Donec gravida ante quis lectus. Praesent rhoncus laoreet purus. Nunc ac nibh ultrices arcu commodo scelerisque. Maecenas luctus tortor sed tellus. Aliquam lorem. Proin urna ipsum, ultricies non, venenatis nec, venenatis ut, quam. Aenean semper. Pellentesque at magna vitae enim ornare ultrices. In libero magna, convallis id, sodales congue, suscipit eget, justo.<BR><BR>Fusce hendrerit magna at ligula. Cras arcu turpis, mattis a, viverra eget, euismod gravida, lacus. Cras ligula. Vivamus accumsan, massa non tincidunt venenatis, magna massa blandit ipsum, nec rhoncus felis urna ornare urna. Sed sed nisl at ligula dapibus faucibus. Cras sodales. Ut ultrices. Duis id diam. Duis euismod pede. Maecenas sodales sollicitudin orci. Nam ut sem. Aliquam mollis neque congue dui. Integer ullamcorper. Curabitur euismod, lectus id blandit consequat, augue diam vehicula turpis, faucibus pulvinar sem metus id tellus. Cras suscipit odio vitae nisl. Suspendisse ac nibh a lacus sollicitudin consequat. Maecenas consectetur vulputate sem. Nunc posuere turpis non magna.<BR><BR>In hac habitasse platea dictumst. Cras volutpat. In lobortis nulla sit amet leo. In semper vulputate dui. Fusce tincidunt, erat id placerat pretium, lacus dui imperdiet sapien, a rhoncus diam eros commodo neque. Proin nec massa at diam rutrum suscipit. Aliquam condimentum enim quis urna. Sed nulla tortor, cursus eu, viverra ac, laoreet gravida, purus. Etiam vel justo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque pretium gravida massa. Cras euismod.<BR><BR>Maecenas feugiat sollicitudin enim. Mauris fringilla, ligula quis eleifend scelerisque, lacus ligula condimentum elit, in semper neque leo ac nunc. Duis nibh est, aliquam eu, vehicula vel, facilisis adipiscing, dui. Curabitur justo. Nunc quis metus. Donec tincidunt ultricies ante. Suspendisse semper feugiat diam. Maecenas blandit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla ipsum turpis, lobortis sit amet, tincidunt et, interdum eu, magna. Praesent vestibulum eros vel pede. Etiam enim massa, pellentesque ut, gravida placerat, euismod iaculis, nulla. Maecenas libero orci, iaculis non, laoreet et, eleifend nec, lorem. Phasellus imperdiet blandit ipsum. Phasellus ac elit vitae diam porta facilisis. Proin pellentesque vulputate lacus. In a justo ut justo hendrerit vestibulum.<BR><BR>Aliquam erat volutpat. Sed quis pede vitae lorem scelerisque euismod. Integer feugiat turpis. Aenean semper. Aliquam scelerisque, augue eget malesuada auctor, tellus augue varius ipsum, sit amet placerat nisl quam a lorem. Pellentesque porta odio ac justo. Mauris lacinia convallis dolor. Vivamus et urna. Ut elementum. Pellentesque id pede. Vivamus tincidunt. Maecenas non mauris non nibh gravida sagittis. Donec elit augue, ullamcorper eu, lacinia volutpat, dictum sed, purus. Vivamus euismod libero eget leo. Morbi rhoncus, sapien nec pellentesque lobortis, tellus magna placerat diam, sed auctor mi diam sed odio. Sed tincidunt. Nulla hendrerit fringilla nunc. Mauris sem tellus, ornare in, eleifend at, posuere eu, enim. <BR>?\r\n------------XcEnkMnsq7wLkA732zFNmZ\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------XcEnkMnsq7wLkA732zFNmZ--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------XcEnkMnsq7wLkA732zFNmZ'), ))

    grinder.sleep(14)
    request5202.GET('/chan/4')

    return result

  def page53(self):
    """POST 4 (request 5301)."""
    
    # Expecting 302 'Found'
    result = request5301.POST('/chan/4',
      '''------------lEoanVRZ5GYyjSN5qSu9wW\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n4\r\n------------lEoanVRZ5GYyjSN5qSu9wW\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n4\r\n------------lEoanVRZ5GYyjSN5qSu9wW\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------lEoanVRZ5GYyjSN5qSu9wW\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------lEoanVRZ5GYyjSN5qSu9wW\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------lEoanVRZ5GYyjSN5qSu9wW\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------lEoanVRZ5GYyjSN5qSu9wW\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sit amet odio. Sed consectetur. Quisque facilisis elit tempor orci. Duis sollicitudin leo sed neque sodales faucibus. Nulla dolor felis, aliquam a, vestibulum vel, vulputate sit amet, felis. Proin sollicitudin, felis vel sagittis hendrerit, elit justo facilisis ipsum, in iaculis nulla odio nec tellus. Donec gravida ante quis lectus. Praesent rhoncus laoreet purus. Nunc ac nibh ultrices arcu commodo scelerisque. Maecenas luctus tortor sed tellus. Aliquam lorem. Proin urna ipsum, ultricies non, venenatis nec, venenatis ut, quam. Aenean semper. Pellentesque at magna vitae enim ornare ultrices. In libero magna, convallis id, sodales congue, suscipit eget, justo.<BR><BR>Fusce hendrerit magna at ligula. Cras arcu turpis, mattis a, viverra eget, euismod gravida, lacus. Cras ligula. Vivamus accumsan, massa non tincidunt venenatis, magna massa blandit ipsum, nec rhoncus felis urna ornare urna. Sed sed nisl at ligula dapibus faucibus. Cras sodales. Ut ultrices. Duis id diam. Duis euismod pede. Maecenas sodales sollicitudin orci. Nam ut sem. Aliquam mollis neque congue dui. Integer ullamcorper. Curabitur euismod, lectus id blandit consequat, augue diam vehicula turpis, faucibus pulvinar sem metus id tellus. Cras suscipit odio vitae nisl. Suspendisse ac nibh a lacus sollicitudin consequat. Maecenas consectetur vulputate sem. Nunc posuere turpis non magna.<BR><BR>In hac habitasse platea dictumst. Cras volutpat. In lobortis nulla sit amet leo. In semper vulputate dui. Fusce tincidunt, erat id placerat pretium, lacus dui imperdiet sapien, a rhoncus diam eros commodo neque. Proin nec massa at diam rutrum suscipit. Aliquam condimentum enim quis urna. Sed nulla tortor, cursus eu, viverra ac, laoreet gravida, purus. Etiam vel justo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque pretium gravida massa. Cras euismod.<BR><BR>Maecenas feugiat sollicitudin enim. Mauris fringilla, ligula quis eleifend scelerisque, lacus ligula condimentum elit, in semper neque leo ac nunc. Duis nibh est, aliquam eu, vehicula vel, facilisis adipiscing, dui. Curabitur justo. Nunc quis metus. Donec tincidunt ultricies ante. Suspendisse semper feugiat diam. Maecenas blandit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla ipsum turpis, lobortis sit amet, tincidunt et, interdum eu, magna. Praesent vestibulum eros vel pede. Etiam enim massa, pellentesque ut, gravida placerat, euismod iaculis, nulla. Maecenas libero orci, iaculis non, laoreet et, eleifend nec, lorem. Phasellus imperdiet blandit ipsum. Phasellus ac elit vitae diam porta facilisis. Proin pellentesque vulputate lacus. In a justo ut justo hendrerit vestibulum.<BR><BR>Aliquam erat volutpat. Sed quis pede vitae lorem scelerisque euismod. Integer feugiat turpis. Aenean semper. Aliquam scelerisque, augue eget malesuada auctor, tellus augue varius ipsum, sit amet placerat nisl quam a lorem. Pellentesque porta odio ac justo. Mauris lacinia convallis dolor. Vivamus et urna. Ut elementum. Pellentesque id pede. Vivamus tincidunt. Maecenas non mauris non nibh gravida sagittis. Donec elit augue, ullamcorper eu, lacinia volutpat, dictum sed, purus. Vivamus euismod libero eget leo. Morbi rhoncus, sapien nec pellentesque lobortis, tellus magna placerat diam, sed auctor mi diam sed odio. Sed tincidunt. Nulla hendrerit fringilla nunc. Mauris sem tellus, ornare in, eleifend at, posuere eu, enim. <BR>?\r\n------------lEoanVRZ5GYyjSN5qSu9wW\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------lEoanVRZ5GYyjSN5qSu9wW--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------lEoanVRZ5GYyjSN5qSu9wW'), ))

    return result

  def page54(self):
    """POST 4 (request 5401)."""
    
    # Expecting 302 'Found'
    result = request5401.POST('/chan/4',
      '''------------jaqrCtQj3wN7Ax5qrtBO5V\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n4\r\n------------jaqrCtQj3wN7Ax5qrtBO5V\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n4\r\n------------jaqrCtQj3wN7Ax5qrtBO5V\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------jaqrCtQj3wN7Ax5qrtBO5V\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------jaqrCtQj3wN7Ax5qrtBO5V\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------jaqrCtQj3wN7Ax5qrtBO5V\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------jaqrCtQj3wN7Ax5qrtBO5V\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sit amet odio. Sed consectetur. Quisque facilisis elit tempor orci. Duis sollicitudin leo sed neque sodales faucibus. Nulla dolor felis, aliquam a, vestibulum vel, vulputate sit amet, felis. Proin sollicitudin, felis vel sagittis hendrerit, elit justo facilisis ipsum, in iaculis nulla odio nec tellus. Donec gravida ante quis lectus. Praesent rhoncus laoreet purus. Nunc ac nibh ultrices arcu commodo scelerisque. Maecenas luctus tortor sed tellus. Aliquam lorem. Proin urna ipsum, ultricies non, venenatis nec, venenatis ut, quam. Aenean semper. Pellentesque at magna vitae enim ornare ultrices. In libero magna, convallis id, sodales congue, suscipit eget, justo.<BR><BR>Fusce hendrerit magna at ligula. Cras arcu turpis, mattis a, viverra eget, euismod gravida, lacus. Cras ligula. Vivamus accumsan, massa non tincidunt venenatis, magna massa blandit ipsum, nec rhoncus felis urna ornare urna. Sed sed nisl at ligula dapibus faucibus. Cras sodales. Ut ultrices. Duis id diam. Duis euismod pede. Maecenas sodales sollicitudin orci. Nam ut sem. Aliquam mollis neque congue dui. Integer ullamcorper. Curabitur euismod, lectus id blandit consequat, augue diam vehicula turpis, faucibus pulvinar sem metus id tellus. Cras suscipit odio vitae nisl. Suspendisse ac nibh a lacus sollicitudin consequat. Maecenas consectetur vulputate sem. Nunc posuere turpis non magna.<BR><BR>In hac habitasse platea dictumst. Cras volutpat. In lobortis nulla sit amet leo. In semper vulputate dui. Fusce tincidunt, erat id placerat pretium, lacus dui imperdiet sapien, a rhoncus diam eros commodo neque. Proin nec massa at diam rutrum suscipit. Aliquam condimentum enim quis urna. Sed nulla tortor, cursus eu, viverra ac, laoreet gravida, purus. Etiam vel justo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque pretium gravida massa. Cras euismod.<BR><BR>Maecenas feugiat sollicitudin enim. Mauris fringilla, ligula quis eleifend scelerisque, lacus ligula condimentum elit, in semper neque leo ac nunc. Duis nibh est, aliquam eu, vehicula vel, facilisis adipiscing, dui. Curabitur justo. Nunc quis metus. Donec tincidunt ultricies ante. Suspendisse semper feugiat diam. Maecenas blandit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla ipsum turpis, lobortis sit amet, tincidunt et, interdum eu, magna. Praesent vestibulum eros vel pede. Etiam enim massa, pellentesque ut, gravida placerat, euismod iaculis, nulla. Maecenas libero orci, iaculis non, laoreet et, eleifend nec, lorem. Phasellus imperdiet blandit ipsum. Phasellus ac elit vitae diam porta facilisis. Proin pellentesque vulputate lacus. In a justo ut justo hendrerit vestibulum.<BR><BR>Aliquam erat volutpat. Sed quis pede vitae lorem scelerisque euismod. Integer feugiat turpis. Aenean semper. Aliquam scelerisque, augue eget malesuada auctor, tellus augue varius ipsum, sit amet placerat nisl quam a lorem. Pellentesque porta odio ac justo. Mauris lacinia convallis dolor. Vivamus et urna. Ut elementum. Pellentesque id pede. Vivamus tincidunt. Maecenas non mauris non nibh gravida sagittis. Donec elit augue, ullamcorper eu, lacinia volutpat, dictum sed, purus. Vivamus euismod libero eget leo. Morbi rhoncus, sapien nec pellentesque lobortis, tellus magna placerat diam, sed auctor mi diam sed odio. Sed tincidunt. Nulla hendrerit fringilla nunc. Mauris sem tellus, ornare in, eleifend at, posuere eu, enim. <BR>?\r\n------------jaqrCtQj3wN7Ax5qrtBO5V\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------jaqrCtQj3wN7Ax5qrtBO5V--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------jaqrCtQj3wN7Ax5qrtBO5V'), ))

    return result

  def page55(self):
    """POST 4 (request 5501)."""
    
    # Expecting 302 'Found'
    result = request5501.POST('/chan/4',
      '''------------mbd48uQQ9yiWSgvL3i2Co8\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n4\r\n------------mbd48uQQ9yiWSgvL3i2Co8\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n4\r\n------------mbd48uQQ9yiWSgvL3i2Co8\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------mbd48uQQ9yiWSgvL3i2Co8\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------mbd48uQQ9yiWSgvL3i2Co8\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------mbd48uQQ9yiWSgvL3i2Co8\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------mbd48uQQ9yiWSgvL3i2Co8\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sit amet odio. Sed consectetur. Quisque facilisis elit tempor orci. Duis sollicitudin leo sed neque sodales faucibus. Nulla dolor felis, aliquam a, vestibulum vel, vulputate sit amet, felis. Proin sollicitudin, felis vel sagittis hendrerit, elit justo facilisis ipsum, in iaculis nulla odio nec tellus. Donec gravida ante quis lectus. Praesent rhoncus laoreet purus. Nunc ac nibh ultrices arcu commodo scelerisque. Maecenas luctus tortor sed tellus. Aliquam lorem. Proin urna ipsum, ultricies non, venenatis nec, venenatis ut, quam. Aenean semper. Pellentesque at magna vitae enim ornare ultrices. In libero magna, convallis id, sodales congue, suscipit eget, justo.<BR><BR>Fusce hendrerit magna at ligula. Cras arcu turpis, mattis a, viverra eget, euismod gravida, lacus. Cras ligula. Vivamus accumsan, massa non tincidunt venenatis, magna massa blandit ipsum, nec rhoncus felis urna ornare urna. Sed sed nisl at ligula dapibus faucibus. Cras sodales. Ut ultrices. Duis id diam. Duis euismod pede. Maecenas sodales sollicitudin orci. Nam ut sem. Aliquam mollis neque congue dui. Integer ullamcorper. Curabitur euismod, lectus id blandit consequat, augue diam vehicula turpis, faucibus pulvinar sem metus id tellus. Cras suscipit odio vitae nisl. Suspendisse ac nibh a lacus sollicitudin consequat. Maecenas consectetur vulputate sem. Nunc posuere turpis non magna.<BR><BR>In hac habitasse platea dictumst. Cras volutpat. In lobortis nulla sit amet leo. In semper vulputate dui. Fusce tincidunt, erat id placerat pretium, lacus dui imperdiet sapien, a rhoncus diam eros commodo neque. Proin nec massa at diam rutrum suscipit. Aliquam condimentum enim quis urna. Sed nulla tortor, cursus eu, viverra ac, laoreet gravida, purus. Etiam vel justo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque pretium gravida massa. Cras euismod.<BR><BR>Maecenas feugiat sollicitudin enim. Mauris fringilla, ligula quis eleifend scelerisque, lacus ligula condimentum elit, in semper neque leo ac nunc. Duis nibh est, aliquam eu, vehicula vel, facilisis adipiscing, dui. Curabitur justo. Nunc quis metus. Donec tincidunt ultricies ante. Suspendisse semper feugiat diam. Maecenas blandit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla ipsum turpis, lobortis sit amet, tincidunt et, interdum eu, magna. Praesent vestibulum eros vel pede. Etiam enim massa, pellentesque ut, gravida placerat, euismod iaculis, nulla. Maecenas libero orci, iaculis non, laoreet et, eleifend nec, lorem. Phasellus imperdiet blandit ipsum. Phasellus ac elit vitae diam porta facilisis. Proin pellentesque vulputate lacus. In a justo ut justo hendrerit vestibulum.<BR><BR>Aliquam erat volutpat. Sed quis pede vitae lorem scelerisque euismod. Integer feugiat turpis. Aenean semper. Aliquam scelerisque, augue eget malesuada auctor, tellus augue varius ipsum, sit amet placerat nisl quam a lorem. Pellentesque porta odio ac justo. Mauris lacinia convallis dolor. Vivamus et urna. Ut elementum. Pellentesque id pede. Vivamus tincidunt. Maecenas non mauris non nibh gravida sagittis. Donec elit augue, ullamcorper eu, lacinia volutpat, dictum sed, purus. Vivamus euismod libero eget leo. Morbi rhoncus, sapien nec pellentesque lobortis, tellus magna placerat diam, sed auctor mi diam sed odio. Sed tincidunt. Nulla hendrerit fringilla nunc. Mauris sem tellus, ornare in, eleifend at, posuere eu, enim. <BR>?\r\n------------mbd48uQQ9yiWSgvL3i2Co8\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------mbd48uQQ9yiWSgvL3i2Co8--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------mbd48uQQ9yiWSgvL3i2Co8'), ))

    return result

  def page56(self):
    """POST 4 (requests 5601-5602)."""
    
    # Expecting 302 'Found'
    result = request5601.POST('/chan/4',
      '''------------fcZAZ9EOjx8ewFq9iVyxEc\r\nContent-Disposition: form-data; name=\"identifier\"\r\n\r\n4\r\n------------fcZAZ9EOjx8ewFq9iVyxEc\r\nContent-Disposition: form-data; name=\"categoryIdentifier\"\r\n\r\n4\r\n------------fcZAZ9EOjx8ewFq9iVyxEc\r\nContent-Disposition: form-data; name=\"author\"\r\n\r\nAnonymous\r\n------------fcZAZ9EOjx8ewFq9iVyxEc\r\nContent-Disposition: form-data; name=\"subject\"\r\n\r\n\r\n------------fcZAZ9EOjx8ewFq9iVyxEc\r\nContent-Disposition: form-data; name=\"email\"\r\n\r\n\r\n------------fcZAZ9EOjx8ewFq9iVyxEc\r\nContent-Disposition: form-data; name=\"url\"\r\n\r\n\r\n------------fcZAZ9EOjx8ewFq9iVyxEc\r\nContent-Disposition: form-data; name=\"comment\"\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sit amet odio. Sed consectetur. Quisque facilisis elit tempor orci. Duis sollicitudin leo sed neque sodales faucibus. Nulla dolor felis, aliquam a, vestibulum vel, vulputate sit amet, felis. Proin sollicitudin, felis vel sagittis hendrerit, elit justo facilisis ipsum, in iaculis nulla odio nec tellus. Donec gravida ante quis lectus. Praesent rhoncus laoreet purus. Nunc ac nibh ultrices arcu commodo scelerisque. Maecenas luctus tortor sed tellus. Aliquam lorem. Proin urna ipsum, ultricies non, venenatis nec, venenatis ut, quam. Aenean semper. Pellentesque at magna vitae enim ornare ultrices. In libero magna, convallis id, sodales congue, suscipit eget, justo.<BR><BR>Fusce hendrerit magna at ligula. Cras arcu turpis, mattis a, viverra eget, euismod gravida, lacus. Cras ligula. Vivamus accumsan, massa non tincidunt venenatis, magna massa blandit ipsum, nec rhoncus felis urna ornare urna. Sed sed nisl at ligula dapibus faucibus. Cras sodales. Ut ultrices. Duis id diam. Duis euismod pede. Maecenas sodales sollicitudin orci. Nam ut sem. Aliquam mollis neque congue dui. Integer ullamcorper. Curabitur euismod, lectus id blandit consequat, augue diam vehicula turpis, faucibus pulvinar sem metus id tellus. Cras suscipit odio vitae nisl. Suspendisse ac nibh a lacus sollicitudin consequat. Maecenas consectetur vulputate sem. Nunc posuere turpis non magna.<BR><BR>In hac habitasse platea dictumst. Cras volutpat. In lobortis nulla sit amet leo. In semper vulputate dui. Fusce tincidunt, erat id placerat pretium, lacus dui imperdiet sapien, a rhoncus diam eros commodo neque. Proin nec massa at diam rutrum suscipit. Aliquam condimentum enim quis urna. Sed nulla tortor, cursus eu, viverra ac, laoreet gravida, purus. Etiam vel justo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque pretium gravida massa. Cras euismod.<BR><BR>Maecenas feugiat sollicitudin enim. Mauris fringilla, ligula quis eleifend scelerisque, lacus ligula condimentum elit, in semper neque leo ac nunc. Duis nibh est, aliquam eu, vehicula vel, facilisis adipiscing, dui. Curabitur justo. Nunc quis metus. Donec tincidunt ultricies ante. Suspendisse semper feugiat diam. Maecenas blandit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla ipsum turpis, lobortis sit amet, tincidunt et, interdum eu, magna. Praesent vestibulum eros vel pede. Etiam enim massa, pellentesque ut, gravida placerat, euismod iaculis, nulla. Maecenas libero orci, iaculis non, laoreet et, eleifend nec, lorem. Phasellus imperdiet blandit ipsum. Phasellus ac elit vitae diam porta facilisis. Proin pellentesque vulputate lacus. In a justo ut justo hendrerit vestibulum.<BR><BR>Aliquam erat volutpat. Sed quis pede vitae lorem scelerisque euismod. Integer feugiat turpis. Aenean semper. Aliquam scelerisque, augue eget malesuada auctor, tellus augue varius ipsum, sit amet placerat nisl quam a lorem. Pellentesque porta odio ac justo. Mauris lacinia convallis dolor. Vivamus et urna. Ut elementum. Pellentesque id pede. Vivamus tincidunt. Maecenas non mauris non nibh gravida sagittis. Donec elit augue, ullamcorper eu, lacinia volutpat, dictum sed, purus. Vivamus euismod libero eget leo. Morbi rhoncus, sapien nec pellentesque lobortis, tellus magna placerat diam, sed auctor mi diam sed odio. Sed tincidunt. Nulla hendrerit fringilla nunc. Mauris sem tellus, ornare in, eleifend at, posuere eu, enim. <BR>?\r\n------------fcZAZ9EOjx8ewFq9iVyxEc\r\nContent-Disposition: form-data; name=\"file\"; filename=\"\"\r\n\r\n\r\n------------fcZAZ9EOjx8ewFq9iVyxEc--\r\n''',
      ( NVPair('Content-Type', 'multipart/form-data; boundary=----------fcZAZ9EOjx8ewFq9iVyxEc'), ))

    grinder.sleep(13)
    request5602.GET('/chan/4')

    return result

  def page57(self):
    """GET / (requests 5701-5702)."""
    
    # Expecting 302 'Found'
    result = request5701.GET('/')

    grinder.sleep(12)
    request5702.GET('/categoryList.Ochan')
    self.token_identifier = \
      httpUtilities.valueFromBodyURI('identifier') # ''

    return result

  def __call__(self):
    """This method is called for every run performed by the worker thread."""
    self.page1()      # GET / (request 101)

    grinder.sleep(1422)
    self.page2()      # GET categoryList.Ochan (requests 201-206)

    grinder.sleep(1649)
    self.page3()      # GET 1 (request 301)

    grinder.sleep(3973)
    self.page4()      # POST 1 (requests 401-402)

    grinder.sleep(214)
    self.page5()      # GET 7 (request 501)

    grinder.sleep(1418)
    self.page6()      # GET 5 (request 601)

    grinder.sleep(1185)
    self.page7()      # GET / (request 701)

    grinder.sleep(986)
    self.page8()      # GET / (request 801)

    grinder.sleep(992)
    self.page9()      # GET / (request 901)

    grinder.sleep(848)
    self.page10()     # POST 5 (requests 1001-1002)

    grinder.sleep(992)
    self.page11()     # GET / (request 1101)

    grinder.sleep(988)
    self.page12()     # GET / (request 1201)

    grinder.sleep(992)
    self.page13()     # GET / (request 1301)

    grinder.sleep(992)
    self.page14()     # GET / (request 1401)

    grinder.sleep(992)
    self.page15()     # GET / (request 1501)

    grinder.sleep(992)
    self.page16()     # GET / (request 1601)

    grinder.sleep(992)
    self.page17()     # GET / (request 1701)

    grinder.sleep(398)
    self.page18()     # POST 5 (requests 1801-1802)

    grinder.sleep(301)
    self.page19()     # GET 12 (request 1901)

    grinder.sleep(761)
    self.page20()     # GET / (request 2001)

    grinder.sleep(990)
    self.page21()     # GET / (request 2101)

    grinder.sleep(994)
    self.page22()     # GET / (request 2201)

    grinder.sleep(986)
    self.page23()     # GET / (request 2301)

    grinder.sleep(992)
    self.page24()     # GET / (request 2401)

    grinder.sleep(992)
    self.page25()     # GET / (request 2501)

    grinder.sleep(985)
    self.page26()     # GET / (request 2601)

    grinder.sleep(636)
    self.page27()     # POST 5 (requests 2701-2702)

    grinder.sleep(1009)
    self.page28()     # GET / (request 2801)
    self.page29()     # GET / (request 2901)

    grinder.sleep(930)
    self.page30()     # GET / (requests 3001-3002)

    grinder.sleep(1551)
    self.page31()     # GET 2 (request 3101)

    grinder.sleep(5618)
    self.page32()     # POST 2 (requests 3201-3202)

    grinder.sleep(202)
    self.page33()     # GET 19 (request 3301)

    grinder.sleep(5426)
    self.page34()     # POST 2 (requests 3401-3402)

    grinder.sleep(312)
    self.page35()     # GET 23 (request 3501)

    grinder.sleep(4935)
    self.page36()     # POST 2 (requests 3601-3602)

    grinder.sleep(196)
    self.page37()     # GET 27 (request 3701)
    self.page38()     # GET 2 (request 3801)

    grinder.sleep(304)
    self.page39()     # GET 31 (request 3901)
    self.page40()     # GET 35 (request 4001)

    grinder.sleep(2186)
    self.page41()     # GET / (requests 4101-4102)

    grinder.sleep(1317)
    self.page42()     # GET 3 (request 4201)
    self.page43()     # GET 3 (request 4301)

    grinder.sleep(1628)
    self.page44()     # GET 37 (request 4401)

    grinder.sleep(1220)
    self.page45()     # GET / (request 4501)

    grinder.sleep(925)
    self.page46()     # POST 37 (requests 4601-4602)

    grinder.sleep(1002)
    self.page47()     # GET 3 (request 4701)

    grinder.sleep(3913)
    self.page48()     # POST 3 (requests 4801-4802)

    grinder.sleep(2535)
    self.page49()     # POST 3 (requests 4901-4902)

    grinder.sleep(2426)
    self.page50()     # POST 3 (requests 5001-5003)
    self.page51()     # GET 4 (request 5101)

    grinder.sleep(2555)
    self.page52()     # POST 4 (requests 5201-5202)

    grinder.sleep(1990)
    self.page53()     # POST 4 (request 5301)
    self.page54()     # POST 4 (request 5401)
    self.page55()     # POST 4 (request 5501)
    self.page56()     # POST 4 (requests 5601-5602)

    grinder.sleep(2208)
    self.page57()     # GET / (requests 5701-5702)


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
