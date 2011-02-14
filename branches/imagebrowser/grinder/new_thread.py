# The Grinder 3.2
# HTTP script recorded by TCPProxy at Feb 28, 2009 2:48:54 PM

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
  ( NVPair('Accept-Language', 'en, *;q=0.1'),
    NVPair('Accept-Charset', 'us-ascii, ISO-8859-1, ISO-8859-2, ISO-8859-3, ISO-8859-4, ISO-8859-5, ISO-8859-6, ISO-8859-7, ISO-8859-8, ISO-8859-9, ISO-8859-10, ISO-8859-13, ISO-8859-14, ISO-8859-15, ISO-8859-16, windows-1250, windows-1251, windows-1252, windows-1256, windows-1257, cp437, cp737, cp850, cp852, cp866, x-cp866-u, x-mac, x-mac-ce, x-kam-cs, koi8-r, koi8-u, koi8-ru, TCVN-5712, VISCII, utf-8'),
    NVPair('Accept-Encoding', 'bzip2'),
    NVPair('User-Agent', 'Links (2.2; Linux 2.6.27-gentoo-r7 i686; 80x24)'),
    NVPair('Accept', '*/*'), )

headers0= \
  ( )

url0 = 'http://192.168.1.110:8080'

# Create an HTTPRequest for each request, then replace the
# reference to the HTTPRequest with an instrumented version.
# You can access the unadorned instance using request101.__target__.
request101 = HTTPRequest(url=url0, headers=headers0)
request101 = Test(101, 'GET /').wrap(request101)

request102 = HTTPRequest(url=url0, headers=headers0)
request102 = Test(102, 'GET 1').wrap(request102)

request201 = HTTPRequest(url=url0, headers=headers0)
request201 = Test(201, 'POST openCategory.Ochan').wrap(request201)


class TestRunner:
  """A TestRunner instance is created for each worker thread."""

  # A method for each recorded page.
  def page1(self):
    """GET / (requests 101-102)."""
    
    # Expecting 302 'Found'
    result = request101.GET('/')

    request102.GET('/chan/1')
    self.token_identifier = \
      httpUtilities.valueFromHiddenInput('identifier') # '1'
    self.token_categoryIdentifier = \
      httpUtilities.valueFromHiddenInput('categoryIdentifier') # '1'
    self.token_email = \
      httpUtilities.valueFromHiddenInput('email') # ''
    self.token_url = \
      httpUtilities.valueFromHiddenInput('url') # ''

    return result

  def page2(self):
    """POST openCategory.Ochan (request 201)."""
    
    # Expecting 302 'Found'
    result = request201.POST('/openCategory.Ochan',
      ( NVPair('identifier', self.token_identifier),
        NVPair('categoryIdentifier', self.token_categoryIdentifier),
        NVPair('email', self.token_email),
        NVPair('url', self.token_url),
        NVPair('author', 'Anonymous'),
        NVPair('subject', ''),
        NVPair('comment', 'testing without blobs'), ),
      ( NVPair('Content-Type', 'application/x-www-form-urlencoded'), ))

    return result

  def __call__(self):
    """This method is called for every run performed by the worker thread."""
    self.page1()      # GET / (requests 101-102)

    grinder.sleep(5298)
    self.page2()      # POST openCategory.Ochan (request 201)


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
