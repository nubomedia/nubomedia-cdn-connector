# Nubomedia CDN Connector

## Usage
### Youtube
To use the Nubomedia CDN Connector to upload a video that is stored on the kurento repository server, you have to connect a browser to the following destination:

http://<cdn-connector-ip>:9090/youtube?videoUrl=<repository-url>

cdn-connector-ip: IP of this server
repository-url: kurento repository url that would let you play the video directly,
e.g. http://localhost:7676/repository_servlet/k9uaue12345o4t20cd9pd80vl0

To get the correct repository url for the item you want uploaded to youtube, the following code snipped should help you:

```
RepositoryItemRecorder repoItem;
...
RepositoryItemPlayer itemPlayer = repositoryClient.getReadEndpoint(repoItemId);
String urlString = itemPlayer.getUrl();
```

Please Note: repoItem.getUrl() will not work!

To get notifications of the upload status, you can set up an EventSource at: http://<cdn-connector-ip>:9090/event?videoUrl=<repository-url>

You can provide meta data for the video by adding the "metaData" query in the initial request, its value should be a json encoded as base64.

Here is a code snippet showing you, how your javascript solution could look like:

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