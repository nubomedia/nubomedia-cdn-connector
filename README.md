# NUBOMEDIA CDN Connector
This project is part of the [NUBOMEDIA](http://www.nubomedia.eu/) research initiative.

### Connectors Overview
NUBOMEDIA Connectors enable interoperability of the NUBOMEDIA platfrom with external media service providers with the main objective to enrich the capabilitiy set of the NUBOMEDIA services. Ths CDN connector is an SDK which offers a framework that aggregates different CDN Data APIs towards NUBOMEDIA applications. Developers can incorporate this SDK to upload media sessions to CDN networks. 

## Getting Started
To obtain the CDN connector, it can be done by means of [Maven](https://maven.apache.org/) in a Java project. The CDN Connector library can be found on [Maven central repository](http://search.maven.org/#search%7Cga%7C1%7Cde.fhg). Simply include it on your project's *pom.xml* file as describe below. 

```xml
<dependencies>
   <!-- Kurento client dependency -->
   <dependency>
      <groupId>org.kurento</groupId>
	  <artifactId>kurento-client</artifactId>
   </dependency>

   <!-- Nubomedia client dependency -->
   <dependency>
      <groupId>de.fhg.fokus.nubomedia</groupId>
      <artifactId>nubomedia-media-client</artifactId>
      <version>1.0.2</version>
   </dependency>
</dependencies>
```




```
1. mvn install
2. java -jar target/nubomedia-cdn-client-0.0.2-jar-with-dependencies.jar
```

## Usage for Application Developers
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
Issue tracker
-------------

Issues and bug reports should be posted to the [GitHub Issue List](https://github.com/fhg-fokus-nubomedia/nubomedia-cdn-connector/issues)

Licensing and distribution
--------------------------

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Support and Contribution
-------------------------

Need some support, wish to contribute? Then get in contact with us via our [mailinglist](mailto:nubomedia@fokus.fraunhofer.de)!
