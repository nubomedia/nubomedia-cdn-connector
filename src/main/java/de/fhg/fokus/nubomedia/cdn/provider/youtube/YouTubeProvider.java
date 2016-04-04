package de.fhg.fokus.nubomedia.cdn.provider.youtube;

import java.io.IOException;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
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

public class YouTubeProvider implements CdnProvider{
		
		
    private final String VIDEO_FILE_FORMAT = "video/*";
    private final String SAMPLE_VIDEO_FILENAME = "sample-video.mp4";
    private YouTube youtube;
    
	//constructor
	public YouTubeProvider(){		 
		

	}
	
	public void uploadVideo(String sessionId, VideoMetaData metaData) throws CdnException {
		try {
			System.out.println("Uploading: " + SAMPLE_VIDEO_FILENAME);

            
			List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");
			Credential credential = Auth.authorize(scopes, "uploadvideo");
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential).setApplicationName(
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

            InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT,
                    YouTubeProvider.class.getResourceAsStream("/sample-video.mp4"));
          
            YouTube.Videos.Insert videoInsert = youtube.videos()
                    .insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent);

            // Set the upload type and add an event listener.
            MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();
            uploader.setDirectUploadEnabled(false);

            MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    switch (uploader.getUploadState()) {
                        case INITIATION_STARTED:
                            System.out.println("Initiation Started");
                            break;
                        case INITIATION_COMPLETE:
                            System.out.println("Initiation Completed");
                            break;
                        case MEDIA_IN_PROGRESS:
                            System.out.println("Upload in progress");
                            System.out.println("Upload percentage: " + uploader.getProgress());
                            break;
                        case MEDIA_COMPLETE:
                            System.out.println("Upload Completed!");
                            break;
                        case NOT_STARTED:
                            System.out.println("Upload Not Started!");
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
            System.out.println("  - Video Count: " + returnedVideo.getStatistics().getViewCount());

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

}
