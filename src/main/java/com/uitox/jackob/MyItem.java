package com.uitox.jackob;
//package $package;

import java.lang.StringBuffer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;

import com.sun.corba.se.spi.orb.StringPair;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * Root resource (exposed at "myresource" path)
 */
//@Path("myitems/{ mydate: [0-9]{4}-[0-9]{2}-[0-9]{2},[0-9]{4}-[0-9]{2}-[0-9]{2}}")

@Path("myitem")
public class MyItem {
	 String pre_indice = "access_log-www-";
	 String mycategoryString = "item"; 
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    //@Path("{ mydate: [0-9]{4}-[0-9]{2}-[0-9]{2}}")
    @Path("{ mydate: ([0-9]{4}-[0-9]{2}-[0-9]{2}(?<dd>,[0-9]{4}-[0-9]{2}-[0-9]{2})+)|(?<d>[0-9]{4}-[0-9]{2}-[0-9]{2})}/{ myregex: .*}")
    
    @Produces(MediaType.TEXT_PLAIN)
    public String getItemcount(@PathParam("mydate") String mydate,@PathParam("myregex") String myregex,@QueryParam("count") @DefaultValue("1") int count) {
         StringBuffer res = new StringBuffer(); 
         
         String indice= pre_indice+ mydate;
         String[] indices = null;
         int i = 0;
         if (mydate.indexOf(",")> 0){

        	 indices = new String[mydate.split(",").length];
        	 for (String post_indices :mydate.split(",") ) {

        		 indices[i] = pre_indice+ mydate.split(",")[i];
        		 i++;

			}
        	 
         }else {
			indices = new String[1];
			indices[0] = pre_indice+mydate;
		}
         QueryItemsManager qim = new QueryItemsManager();
         
         qim.setIquery(new ItemQueryitem());
         Item item = qim.qetitem("elastic", "elastic06.idc1.fn", indices, "access_log", mycategoryString, myregex , "request_url_4", "request_url_3");
         
         String result = "{" +item.get_info() + "}";
         
        return result;
        
    }
    
}
