package com.uitox.jackob;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.net.InetAddress;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    //public static final String BASE_URI = "http://"+args[0].toString()+":"+args[1].toString()+"/"+args[2].toString()+"/";
    public static String BASE_URI =  "";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in $package package
        //final ResourceConfig rc = new ResourceConfig().packages("$package");
        final ResourceConfig rc = new ResourceConfig().packages("com.uitox.jackob");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        
        System.out.println(BASE_URI);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException,Exception {
    	
    	if (args.length >2){
    		BASE_URI = "http://"+args[0].toString()+":"+args[1].toString()+"/"+args[2].toString()+"/";
    	}else {
    		
    		if (InetAddress.getLocalHost().toString().split("/").length > 1){
    			BASE_URI = "http://"+InetAddress.getLocalHost().toString().split("/")[1].toString().trim()+":8080/api/";
    		}else {
    			BASE_URI = "http://localhost:8080/api/";
			}
    		
		}
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

