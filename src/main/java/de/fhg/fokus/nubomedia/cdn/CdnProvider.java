/*
 * (C) Copyright 2016 NUBOMEDIA (http://www.nubomedia.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.fhg.fokus.nubomedia.cdn;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.model.Video;

public interface CdnProvider {
		
	/**
	 * Uploads a new video file to the CDN. SessionId is the
	 * @param sessionId -  identifier of a stored video on the NUBOMEDIA cloud repository
	 * @param metaData - Meta data such as title, description and tags	 
	 * @throws CdnException - Exception 
	 */
	public Video uploadVideo(String sessionId, VideoMetaData metaData) throws CdnException;
	
	/**
	 * Deletes the video with the given identifier from the CDN
	 * @param videoId - the identifier of the video file to be deleted
	 * @throws CdnException - CDN exception thrown in case of failure 
	 */
	public void deleteVideo(String videoId) throws CdnException;
	
	/**
	 * Returns a JSON object with the list of all uploaded videos on the registered user�s channel
	 * @throws CdnException - CDN exception thrown in case of failure
	 */
	public void getChannelList() throws CdnException;
	
	/**
	 * add a CDN provider listener
	 * @param listener - a new listener for CDN triggered events
	 */
	public void addProviderListener(CdnProviderListener listener);
	
	/**
	 * Remove the given CDN Provider Listener
	 * @param listener - the CDN provider listener
	 */
	public void removeProviderListener(CdnProviderListener listener);
	
	/**
	 * Store user's credentials to access the CDN network
	 * @param credential - the credential to be stored 
	 */
	public void storeCredentials(Credential credential);
}
