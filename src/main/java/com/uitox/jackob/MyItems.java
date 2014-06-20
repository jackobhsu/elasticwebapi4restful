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

@Path("myitems")
public class MyItems {
	 String pre_indice = "access_log-www-";
	 String mycategoryString = "item"; 
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET

    @Path("{ mydate: ([0-9]{4}-[0-9]{2}-[0-9]{2}(?<dd>,[0-9]{4}-[0-9]{2}-[0-9]{2})+)|(?<d>[0-9]{4}-[0-9]{2}-[0-9]{2})}")
    
    @Produces(MediaType.TEXT_PLAIN)
    public String getItemscount(@PathParam("mydate") String mydate,@QueryParam("count") @DefaultValue("10") int count) {
        
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
         qim.setIquerys(new ItemQueryItems());
    
         Item[] qitems = qim.queryitems("elastic","elastic06.idc1.fn", indices, "access_log", mycategoryString,"request_url_4" , "request_url_3", count);
    
         int icount = 1;
         res.append("{");
         for (Item item : qitems) {
    
        	 if (qitems.length == 1){
        		 
                 res.append("\""+icount+"\":{\""+mycategoryString+"\":{\""+item.get_itemid()+"\":"+item.get_itemcount()+"}}");
        		 
        	 }else{
        		 
        		 if (icount < qitems.length ){
                 res.append("\""+icount+"\":{\""+mycategoryString+"\":{\""+item.get_itemid()+"\":"+item.get_itemcount()+"}},");
        		 }
        		 else {
        			 res.append("\""+icount+"\":{\""+mycategoryString+"\":{\""+item.get_itemid()+"\":"+item.get_itemcount()+"}}");
				}
        		 
        	 }
        	 
        	 icount++;

         }
         res.append("}");

           String result = res.toString();
         
        return result;
        
        
    }
    	 
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    
    //2014-03-12,2014-03-13,2014-03-14,2014-03-13
    //(?<1>2014-03-12),<1>+
    
    public String[] clean(final String[] v) {
    	  int r, w, n = r = w = v.length;
    	  while (r > 0) {
    	    final String s = v[--r];
    	    if (!s.equals("null")) {
    	      v[--w] = s;
    	    }
    	  }
    	  final String[] c = new String[n -= w];
    	  System.arraycopy(v, w, c, 0, n);
    	  return c;
    	}
    
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
           
           qim.setIquery(new ItemQueryItems());
           Item item = qim.qetitem("elastic", "elastic06.idc1.fn", indices, "access_log", mycategoryString, myregex , "request_url_4", "request_url_3");
 
           String result = "{\"1\":{\""+item.get_itemcategory()+"\":{\""+item.get_itemid()+"\":"+item.get_itemcount().toString()+"}}}";
 
          return result;
        
    }
    
    
    
}
