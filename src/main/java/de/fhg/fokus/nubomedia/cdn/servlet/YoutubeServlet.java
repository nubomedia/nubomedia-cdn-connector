package de.fhg.fokus.nubomedia.cdn.servlet;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.util.Base64;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.fhg.fokus.nubomedia.cdn.VideoMetaData;
import de.fhg.fokus.nubomedia.cdn.provider.youtube.Auth;
import de.fhg.fokus.nubomedia.cdn.provider.youtube.YouTubeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Robert Ende on 04.04.16.
 */
public class YoutubeServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(YoutubeServlet.class);
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private YouTubeProvider youTubeProvider = new YouTubeProvider();

    private Map<String, JsonObject> userMap = new HashMap<>();

    GoogleAuthorizationCodeFlow flow = Auth.generateFlow();

    EventServlet eventServlet = null;

    public YoutubeServlet(EventServlet eventServlet) {
        this.eventServlet = eventServlet;
    }

    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        log.debug("got GET2: {}", req);

        if (req.getPathInfo() == null) {
            log.debug("no pathInfo -> initial authorization");
            log.debug("kurento repository url: {}", req.getParameter("videoUrl"));
            String videoUrl = req.getParameter("videoUrl");
            String metaData = req.getParameter("metaData");

            log.debug("videoUrl: " + videoUrl);


            JsonObject videoMetaData = null;
            if (metaData != null) {
                log.debug("encoded metaData: " + metaData);
                metaData = new String(Base64.decodeBase64(metaData));
                log.debug("decoded metaData: " + metaData);
            }
            videoMetaData = gson.fromJson(metaData, JsonObject.class);

            JsonObject jState = new JsonObject();
            jState.addProperty("videoUrl", videoUrl);
            jState.add("metaData", videoMetaData);


            // redirect to /youtube/rd
            String redirectUri = req.getRequestURL() + "/rd";
            log.debug("redirectUri: " + redirectUri);

            log.debug("state: " + jState);

            String base64State = Base64.encodeBase64URLSafeString(jState.toString().getBytes());
            log.debug("base64 state: " + base64State);

            String authUrl = flow.newAuthorizationUrl().setState(base64State).setRedirectUri(redirectUri).build();
            log.debug("generated authUrl: " + authUrl);
            log.debug("sending redirect...");
            resp.sendRedirect(authUrl);
        } else if (req.getPathInfo().equals("/rd")) {
            String code = req.getParameter("code");
            log.debug("got auth code: " + code);
            String redirUri = req.getRequestURL().toString();
            log.debug("redirectURI: " + redirUri);

            GoogleTokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirUri).execute();

            log.debug("tokenResponse: {}", tokenResponse);

            String state = req.getParameter("state");
            log.debug("base64 state string: " + state);
            state = new String(Base64.decodeBase64(state));
            log.debug("parsed state string: " + state);

            JsonObject jState = gson.fromJson(state, JsonObject.class);
            final String videoUrl = jState.get("videoUrl").getAsString();
            log.debug("recovered videoUrl from state: " + videoUrl);
            VideoMetaData metaData = null;
            if (jState.has("metaData")) {
                metaData = gson.fromJson(jState.get("metaData"), VideoMetaData.class);
            } else {
                List<String> tags = new ArrayList<>(3);
                tags.add("kurento");
                tags.add("hello");
                tags.add("world");
                metaData = new VideoMetaData("kurentoTest", "does it work?", tags);
            }
            log.debug("metaData: " + metaData);

            MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    EventServlet.Event event = eventServlet.getEvent(videoUrl);
                    switch (uploader.getUploadState()) {
                        case INITIATION_STARTED:
                            log.debug("Initiation Started");
                            if (event != null)
                                event.sendNotification("Initiation Started");
                            break;
                        case INITIATION_COMPLETE:
                            log.debug("Initiation Completed");
                            if (event != null)
                                event.sendNotification("Initiation Completed");
                            break;
                        case MEDIA_IN_PROGRESS:
                            log.debug("Upload in progress, " + uploader.getProgress() + "%");
                            if (event != null)
                                event.sendNotification("Upload in progress, " + uploader.getProgress() + "%");
                            break;
                        case MEDIA_COMPLETE:
                            log.debug("Upload Completed!");
                            if (event != null)
                                event.sendNotification("Upload Completed!");
                            resp.getWriter().write("<h1>Upload complete!</h1>");
                            break;
                        case NOT_STARTED:
                            log.debug("Upload Not Started!");
                            if (event != null)
                                event.sendNotification("Upload Not Started!");
                            break;
                    }
                }
            };

            youTubeProvider.uploadVideo(new URL(videoUrl), metaData, tokenResponse.getAccessToken(), progressListener);

        } else if (req.getPathInfo().equals("/token")) {
            log.debug("got token... queries: ", req.getQueryString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("got POST");
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("got PUT");
        super.doPut(req, resp);
    }
}
