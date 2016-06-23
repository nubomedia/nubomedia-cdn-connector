/*******************************************************************************
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * 
 *******************************************************************************/
package de.fhg.fokus.nubomedia.cdn.provider.youtube;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.common.collect.Lists;

import de.fhg.fokus.nubomedia.cdn.CdnException;
import de.fhg.fokus.nubomedia.cdn.CdnProvider;
import de.fhg.fokus.nubomedia.cdn.CdnProviderListener;
import de.fhg.fokus.nubomedia.cdn.VideoMetaData;

public class YouTubeProvider implements CdnProvider {

    private final String VIDEO_FILE_FORMAT = "video/*";
    private static final Logger log = LoggerFactory.getLogger(YouTubeProvider.class);
    private Credential credential;
    
    public void uploadVideo(URL url, VideoMetaData metaData, String accessToken, MediaHttpUploaderProgressListener listerner, Credential credential2) {
        this.credential = credential2;
    	uploadVideo(url, metaData, accessToken, listerner);
    }

    public void uploadVideo(URL url, VideoMetaData metaData, String accessToken, MediaHttpUploaderProgressListener progressListener) {
        try {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            InputStream videoStream = Channels.newInputStream(rbc);
            uploadVideo(videoStream, metaData, accessToken, progressListener);
        } catch (IOException | CdnException e) {
            e.printStackTrace();
        }
    }

    public Object uploadVideo(InputStream videoStream, VideoMetaData metaData, String accessToken, MediaHttpUploaderProgressListener progressListener) throws CdnException {
        try 
        {
        	if(credential == null)
        		credential = Auth.authorize(Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload"), "uploadvideo");
        	       	
        	//Credential credential = new GoogleCredential().setAccessToken(accessToken);
            YouTube youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential).setApplicationName(
                    "youtube-cmdline-uploadvideo-sample").build();

            // Add extra information to the video before uploading.
            Video videoObjectDefiningMetadata = new Video();

            VideoStatus status = new VideoStatus();
            status.setPrivacyStatus("public");
            videoObjectDefiningMetadata.setStatus(status);

            VideoSnippet snippet = new VideoSnippet();
            snippet.setTitle(metaData.getTitle());
            snippet.setDescription(metaData.getDescription());
            snippet.setTags(metaData.getTags());
            videoObjectDefiningMetadata.setSnippet(snippet);

            InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT, videoStream);

            YouTube.Videos.Insert videoInsert = youtube.videos()
                    .insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent);

            // Set the upload type and add an event listener.
            MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();
            uploader.setDirectUploadEnabled(false);

            if (progressListener == null)
                progressListener = new MediaHttpUploaderProgressListener() {
                    public void progressChanged(MediaHttpUploader uploader) throws IOException {
                        switch (uploader.getUploadState()) {
                            case INITIATION_STARTED:
                                log.debug("Initiation Started");
                                //if (event != null)
                                //    event.sendNotification("Initiation Started");
                                break;
                            case INITIATION_COMPLETE:
                                log.debug("Initiation Completed");
                                //if (event != null)
                                //    event.sendNotification("Initiation Completed");
                                break;
                            case MEDIA_IN_PROGRESS:
                                log.debug("Upload in progress, " + uploader.getProgress() + "%");
                                //if (event != null)
                                //    event.sendNotification("Upload in progress, " + uploader.getProgress() + "%");
                                break;
                            case MEDIA_COMPLETE:
                                log.debug("Upload Completed!");
                                //if (event != null)
                                //    event.sendNotification("Upload Completed!");
                                break;
                            case NOT_STARTED:
                                log.debug("Upload Not Started!");
                                //if (event != null)
                                //    event.sendNotification("Upload Not Started!");
                                break;
                        }
                    }
                };
            uploader.setProgressListener(progressListener);
            
            // Call the API and upload the video.
            Video returnedVideo = videoInsert.execute();

            // Print data about the newly inserted video from the API response.
            System.out.println("\n================== Returned Video ==================\n");
            System.out.println("  - Id: " + returnedVideo.getId());
            System.out.println("  - Title: " + returnedVideo.getSnippet().getTitle());
            System.out.println("  - Tags: " + returnedVideo.getSnippet().getTags());
            System.out.println("  - Privacy Status: " + returnedVideo.getStatus().getPrivacyStatus());
            System.out.println("  - Video view count: " + returnedVideo.getStatistics().getViewCount());
            return returnedVideo;
        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
		return null;

    }

    @Override
    public void uploadVideo(String sessionId, VideoMetaData metaData) throws CdnException {
    	// TODO Auto-generated method stub
    }

    public void deleteVideo(String videoId) throws CdnException {
        // TODO Auto-generated method stub

    }

    public void getChannelList() throws CdnException {
        // TODO Auto-generated method stub

    }

    public void addProviderListener(CdnProviderListener listener) {
        // TODO Auto-generated method stub

    }

    public void removeProviderListener(CdnProviderListener listener) {
        // TODO Auto-generated method stub

    }

	@Override
	public void storeCredentials(Credential credential) {
		this.credential = credential;
	}

}
