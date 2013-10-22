package com.drago.jerseyexample.rules;

import org.junit.rules.ExternalResource;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import javax.ws.rs.core.UriBuilder;


public class ServerRule extends ExternalResource {

    private static final String WAR_LOCATION = "src/main/webapp";
    private static final String SCHEME = "http";
    static final String HOST = "localhost";
    private static final String SERVICES = "/services";
    static final int PORT = 9999;

    private Server server;

    @Override
    protected void before() throws Throwable {
        startAppServer();
    }

    @Override
    protected void after() {
        stopAppServer();
    }

    public UriBuilder baseUri() {
        return UriBuilder.fromPath(SERVICES)
                .host(HOST)
                .port(PORT)
                .scheme(SCHEME);
    }

    private void stopAppServer() {
        try {
            server.stop();
        } catch (Exception e) {
            new RuntimeException(e);
        }
    }

    private void startAppServer() throws Exception {
        server = new Server(PORT);
        server.setHandler(webApp());

        server.start();
    }

    private WebAppContext webApp() {
        final WebAppContext webApp = new WebAppContext();
        webApp.setWar(WAR_LOCATION);
        webApp.setContextPath(SERVICES);
        return webApp;
    }
}
