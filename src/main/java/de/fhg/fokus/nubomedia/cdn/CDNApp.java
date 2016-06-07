//package de.fhg.fokus.nubomedia.cdn;
//
//import de.fhg.fokus.nubomedia.cdn.servlet.EventServlet;
//import de.fhg.fokus.nubomedia.cdn.servlet.YoutubeServlet;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.servlet.ServletHolder;
//import org.eclipse.jetty.webapp.WebAppContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Created by Robert Ende on 04.04.16.
// */
//public class CDNApp {
//    private static final Logger log = LoggerFactory.getLogger(CDNApp.class);
//
//
//    public static void main(String[] args) {
//        log.debug("starting CDNApp");
//        Server s = new Server(9090);
//        WebAppContext context = new WebAppContext();
//
//        context.setContextPath("/");
//        context.setResourceBase(CDNApp.class.getClassLoader().getResource("webapp").toExternalForm());
//        EventServlet eventServlet = new EventServlet();
//        ServletHolder eventHolder = new ServletHolder(eventServlet);
//        context.addServlet(eventHolder, "/event/*");
//
//        ServletHolder servletHolder = new ServletHolder(new YoutubeServlet(eventServlet));
//        context.addServlet(servletHolder, "/youtube/*");
//
//
//        s.setHandler(context);
//        log.debug("starting server");
//        try {
//            s.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}