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