[![][NUBOMEDIA Logo]][NUBOMEDIA]

Copyright © 2016 [NUBOMEDIA]. Licensed under [Apache 2.0 License].

NUBOMEDIA CDN Connector
=======================

NUBOMEDIA Connectors enable interoperability of the NUBOMEDIA platfrom with external media service providers with the main objective to enrich the capabilitiy set of the NUBOMEDIA services. Ths CDN connector is an SDK which offers a framework that aggregates different CDN Data APIs towards NUBOMEDIA applications. Developers can incorporate this SDK to upload media sessions to CDN networks. 

What is NUBOMEDIA
-----------------

This project is part of [NUBOMEDIA], which is an open source cloud Platform as a
Service (PaaS) which makes possible to integrate Real Time Communications (RTC)
and multimedia through advanced media processing capabilities. The aim of
NUBOMEDIA is to democratize multimedia technologies helping all developers to
include advanced multimedia capabilities into their Web and smartphone
applications in a simple, direct and fast manner. To accomplish that objective,
NUBOMEDIA provides a set of APIs that try to abstract all the low level details
of service deployment, management, and exploitation allowing applications to
transparently scale and adapt to the required load while preserving QoS
guarantees.

Getting Started with NUBOMEDIA CDN Connector
--------------------------------------------

To obtain the CDN connector, it can be done by means of [Maven] in a Java project. The CDN Connector library can be found on [Maven Central Repository]. Simply include it on your project's *pom.xml* file as describe below. 

```xml
<dependencies>
   <!-- Nubomedia cdn client dependency -->
   <dependency>
      <groupId>de.fhg.fokus.nubomedia</groupId>
      <artifactId>nubomedia-cdn-client</artifactId>
      <version>0.0.5</version>
   </dependency>
</dependencies>
```

Usage for Application Developers
--------------------------------

### Youtube Provider API
To use the Nubomedia CDN Connector to upload a video that is stored on the kurento repository server, you have to connect a browser to the following destination:
```
http://<cdn-connector-ip>:9090/youtube?videoUrl=<repository-url>

<cdn-connector-ip>: IP of this server
<repository-url>: kurento repository url that would let you play the video directly,
e.g. http://localhost:7676/repository_servlet/k9uaue12345o4t20cd9pd80vl0
```

To get the correct repository url for the item you want uploaded to youtube, the following code snipped should help you:
```
RepositoryItemRecorder repoItem;
...
RepositoryItemPlayer itemPlayer = repositoryClient.getReadEndpoint(repoItem.getId());
String urlString = itemPlayer.getUrl();
```

Please Note: `repoItem.getUrl()` is not the correct URL!

To get notifications of the upload status, you can set up an EventSource at:

`http://<cdn-connector-ip>:9090/event?videoUrl=<repository-url>`

You can provide meta data for the video by adding the "metaData" query in the initial request, its value should be a json encoded as base64.

Here is a code snippet showing you how your javascript solution could look like:
```
var cdnurl = "http://localhost:9090";
var cdn_youtube = cdnurl + "/youtube";
var cdn_event = cdnurl + "/event";

var metaData = {
    "title": "someTitle",
    "description": "someDescription",
    "tags": [
        "firstTag",
        "secondTag",
        "thirdTag"
    ]
};

// set up EventSource
var eventUrl = cdn_event + "?videoUrl=" + videoUrl;
console.log("trying to connect to eventSource: " + eventUrl);
var eventSource = new EventSource(eventUrl);
var notificationCallback = function (msg) {
    var content = JSON.parse(msg.data);
    console.log("NOTIFICATION for %s: %s", content["videoUrl"], content);
};
eventSource.addEventListener('NOTIFICATION', notificationCallback, false);

// open browser window for URL
var url = cdn_youtube + "?videoUrl=" + videoUrl + "&metaData=" + window.btoa(JSON.stringify(metaData));
console.log("opening new window for: " + url);
window.open(url);
```

Documentation
-------------

The [NUBOMEDIA] project provides detailed documentation including tutorials,
installation and [Development Guide].

Source
------

Source code for other NUBOMEDIA projects can be found in the [GitHub NUBOMEDIA
Group].

News
----

Follow us on Twitter @[NUBOMEDIA Twitter].

Issue tracker
-------------

Issues and bug reports should be posted to [GitHub Issues].

Licensing and distribution
--------------------------

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.

Contribution policy
-------------------

You can contribute to the NUBOMEDIA community through bug-reports, bug-fixes,
new code or new documentation. For contributing to the NUBOMEDIA community,
drop a post to the [NUBOMEDIA Public Mailing List] providing full information
about your contribution and its value. In your contributions, you must comply
with the following guidelines

* You must specify the specific contents of your contribution either through a
  detailed bug description, through a pull-request or through a patch.
* You must specify the licensing restrictions of the code you contribute.
* For newly created code to be incorporated in the NUBOMEDIA code-base, you
  must accept NUBOMEDIA to own the code copyright, so that its open source
  nature is guaranteed.
* You must justify appropriately the need and value of your contribution. The
  NUBOMEDIA project has no obligations in relation to accepting contributions
  from third parties.
* The NUBOMEDIA project leaders have the right of asking for further
  explanations, tests or validations of any code contributed to the community
  before it being incorporated into the NUBOMEDIA code-base. You must be ready
  to addressing all these kind of concerns before having your code approved.

Support
-------

The NUBOMEDIA community provides support through the [NUBOMEDIA Public Mailing List].

[Apache 2.0 License]: https://www.apache.org/licenses/LICENSE-2.0.txt
[Development Guide]: http://nubomedia.readthedocs.org/
[GitHub Issues]: https://github.com/nubomedia/nubomedia-cdn-connector/issues
[GitHub NUBOMEDIA Group]: https://github.com/nubomedia
[NUBOMEDIA Logo]: http://www.nubomedia.eu/sites/default/files/nubomedia_logo-small.png
[NUBOMEDIA Twitter]: https://twitter.com/nubomedia
[NUBOMEDIA Public Mailing list]: https://groups.google.com/forum/#!forum/nubomedia-dev
[NUBOMEDIA]: http://www.nubomedia.eu
[Maven]: https://maven.apache.org/
[Maven Central Repository]: http://search.maven.org/#search%7Cga%7C1%7Cde.fhg.fokus.nubomedia
