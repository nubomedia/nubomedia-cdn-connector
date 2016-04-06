package de.fhg.fokus.nubomedia.cdn.servlet;

import org.eclipse.jetty.servlets.EventSource;
import org.eclipse.jetty.servlets.EventSourceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Robert Ende on 04.04.16.
 */
public class EventServlet extends EventSourceServlet {
    private static final Logger log = LoggerFactory.getLogger(EventServlet.class);
    private Map<String, Event> events = new ConcurrentHashMap<>();

    @Override
    protected EventSource newEventSource(HttpServletRequest req) {
        log.debug("new event source: " + req);
        String videoUrl = req.getParameter("videoUrl");
        log.debug("subscription on status of videoUrl " + videoUrl);
        return new Event(videoUrl);
    }

    public Event getEvent(String videoUrl) {
        return events.get(videoUrl);
    }

    public class Event implements EventSource {
        public static final String EVENT_NOTIFICATION = "NOTIFICATION";

        private String videoUrl;
        private Emitter emitter;

        public Event(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        @Override
        public void onOpen(Emitter emitter) throws IOException {
            this.emitter = emitter;
            events.put(videoUrl, this);
            log.debug("onOpen. videoUrl: " + videoUrl);
        }

        @Override
        public void onClose() {
            events.remove(videoUrl);
            log.debug("onClose. videoUrl: " + videoUrl);
        }

        public void sendEvent(String event, String data) {
            try {
                emitter.event(event, data);
            } catch (IOException e) {
                //e.printStackTrace();
                onClose();
            }
        }

        public void sendNotification(String data) {
            sendEvent(EVENT_NOTIFICATION, data);
        }
    }
}
