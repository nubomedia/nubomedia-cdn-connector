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
 * @author Alice Cheambe <alice.cheambe[at]fokus.fraunhofer.de>
 *******************************************************************************/
package de.fhg.fokus.nubomedia.cdn;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import de.fhg.fokus.nubomedia.cdn.provider.youtube.YouTubeProvider;

public class CdnManager {
	
	private final Logger log = LoggerFactory.getLogger(CdnManager.class);
	private Map<String, CdnProvider> providers = new HashMap<>();
	
	public CdnManager(){
		regiserCdnProvider(Schemes.YOUTUBE, new YouTubeProvider());		
	}
	
	public void regiserCdnProvider(String scheme, CdnProvider provider)
	{
		providers.put(scheme, provider);
	}
	
	
	public void unregisterCdnProvider(String scheme, CdnProvider provider)
	{
		providers.remove(scheme);
	}
	
	public void uploadVideo(String scheme, String videoURL, JsonObject jsonMessage, Credential credential, MediaHttpUploaderProgressListener listerner)
	{
		if(!providers.isEmpty())
		{
			YouTubeProvider provider = null;
			if(scheme == Schemes.YOUTUBE)
			{				
				try {
					provider = (YouTubeProvider) providers.get(scheme);	
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					VideoMetaData metaData = gson.fromJson(jsonMessage.get("metaData"), VideoMetaData.class);
					provider.uploadVideo(new URL(videoURL), metaData, null, listerner, credential);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					log.error("failed to upload video", e.getMessage());
				}				
			}						
		}
	}

	public void storeCredentials(String scheme, Credential credential) {
		CdnProvider provider = providers.get(scheme);
		if(provider != null)
			provider.storeCredentials(credential);
		
	}

	
}
