package de.fhg.fokus.nubomedia.cdn.provider.youtube;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Shared class used by every sample. Contains methods for authorizing a user and caching credentials.
 */
public class Auth {
    private static final Logger log = LoggerFactory.getLogger(Auth.class);

    /**
     * Define a global instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /**
     * This is the directory that will be used under the user's home directory where OAuth tokens will be stored.
     */
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";

    private static boolean isInitialized = false;
    private static GoogleClientSecrets clientSecrets = null;

    private static List<String> uploadScope = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");


    static {
        try {
            // Load client secrets.
            Reader clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/client_secrets.json"));
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static GoogleAuthorizationCodeFlow generateFlow() {
        return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, uploadScope).build();
    }
}
