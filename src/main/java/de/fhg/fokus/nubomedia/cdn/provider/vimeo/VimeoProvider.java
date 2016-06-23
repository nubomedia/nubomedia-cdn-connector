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
package de.fhg.fokus.nubomedia.cdn.provider.vimeo;

import com.google.api.client.auth.oauth2.Credential;

import de.fhg.fokus.nubomedia.cdn.CdnException;
import de.fhg.fokus.nubomedia.cdn.CdnProvider;
import de.fhg.fokus.nubomedia.cdn.CdnProviderListener;
import de.fhg.fokus.nubomedia.cdn.VideoMetaData;

public class VimeoProvider implements CdnProvider {

	@Override
	public void uploadVideo(String sessionId, VideoMetaData metaData) throws CdnException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteVideo(String videoId) throws CdnException {
		// TODO Auto-generated method stub

	}

	@Override
	public void getChannelList() throws CdnException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addProviderListener(CdnProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeProviderListener(CdnProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeCredentials(Credential credential) {
		// TODO Auto-generated method stub
		
	}

}
