package de.fhg.fokus.nubomedia.cdn;

import org.kurento.repository.RepositoryClient;
import org.kurento.repository.RepositoryClientProvider;

public class SessionManager {

	static final String DEFAULT_REPOSITORY_SERVER_URI = "http://localhost:7676";
	static final String REPOSITORY_SERVER_URI = System.getProperty("repository.uri",
			DEFAULT_REPOSITORY_SERVER_URI);

	private RepositoryClient repositoryClient;

	public SessionManager() {
		repositoryClient = repositoryServiceProvider();
		
		//TODO: find out how to retrieve the download URL of an Repository Item
	}

	public RepositoryClient repositoryServiceProvider() {
		if (REPOSITORY_SERVER_URI.startsWith("file://")) {
			return null;
		}
		return RepositoryClientProvider.create(REPOSITORY_SERVER_URI);//REPOSITORY_SERVER_URI
	}

}
