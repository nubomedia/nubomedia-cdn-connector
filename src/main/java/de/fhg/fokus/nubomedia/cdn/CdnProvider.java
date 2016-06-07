package de.fhg.fokus.nubomedia.cdn;

import com.google.api.client.auth.oauth2.Credential;

public interface CdnProvider {
		
	/**
	 * Uploads a new video file to the CDN. SessionId is the
	 * @param sessionId -  identifier of a stored video on the NUBOMEDIA cloud repository
	 * @param metaData - Meta data such as title, description and tags
	 * @return 
	 * @throws CdnException - Exception 
	 */
	public void uploadVideo(String sessionId, VideoMetaData metaData) throws CdnException;
	
	/**
	 * Deletes the video with the given identifier from the CDN
	 * @param videoId
	 * @throws CdnException
	 */
	public void deleteVideo(String videoId) throws CdnException;
	
	/**
	 * Returns a JSON object with the list of all uploaded videos on the registered user�s channel
	 * @throws CdnException
	 */
	public void getChannelList() throws CdnException;
	
	/**
	 * add a cdn provider listener
	 * @param listener
	 */
	public void addProviderListener(CdnProviderListener listener);
	
	/**
	 * 
	 * @param listener
	 */
	public void removeProviderListener(CdnProviderListener listener);
	
	public void storeCredentials(Credential credential);
}
