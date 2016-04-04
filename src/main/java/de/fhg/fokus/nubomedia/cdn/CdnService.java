/**
 * 
 * @author Alice Cheambe <alice.cheambe[at]fokus.fraunhofer.de>
 *
 */
package de.fhg.fokus.nubomedia.cdn;

import com.google.gson.JsonObject;

public interface CdnService {

	/**
	 * Register a new CDN Provider. Input parameter is the scheme  and a 
	 * Json object with required credentials for authenticating to the registered user’s data center
	 * 
	 * @param scheme - e.g. //youtube for a YouTubeProvider
	 * @param provider - the provider implementation of the CDN
	 * @param auth - JsonObject containing authentification parameters to authenticate on the CDN 
	 */
	public void registerCdnProvider(String scheme, CdnProvider provider, JsonObject auth);

	/**
	 * Unregisters a CDN Provider from the collection of providers
	 * @param scheme - the CDN provider scheme to unregister
	 */
	public void unregisterCdnProvider(String scheme);

	/**
	 * Returns the specified CDN Provider from the collection
	 * @return - Instance of the CDN provider 
	 */
	public CdnProvider getCdnProvider();
	
	/**
	 * Returns an instance of the Session Manager object
	 * @return SessionManager
	 */
	public SessionManager getSessionManager();
	
	/**
	 * Uploads a new video file to the CDN. SessionId is the
	 * @param sessionId -  identifier of a stored video on the NUBOMEDIA cloud repository
	 * @return 
	 * @throws CdnException - Exception 
	 */
	public void uploadVideo(String sessionId) throws CdnException;
	
	/**
	 * Deletes the video with the given identifier from the CDN
	 * @param videoId
	 * @throws CdnException
	 */
	public void deleteVideo(String videoId) throws CdnException;
	
	/**
	 * Returns a JSON object with the list of all uploaded videos on the registered user’s channel
	 * @throws CdnException
	 */
	public void getChannelList() throws CdnException;
}
