package com.uitox.jackob;
//package $package;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("mymains")
public class MyMains {

	
	 String pre_indice = "access_log-www-";
	 String mycategoryString = "/"; 
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{ mydate: ([0-9]{4}-[0-9]{2}-[0-9]{2}(?<dd>,[0-9]{4}-[0-9]{2}-[0-9]{2})+)|(?<d>[0-9]{4}-[0-9]{2}-[0-9]{2})}")
    public String getIt(@PathParam("mydate") String mydate,@QueryParam("count") @DefaultValue("10") int count) {

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
         
         qim.setIquerys(new MainQueryItems());
         
   
         Item[] qitems = qim.queryitems("elastic","elastic06.idc1.fn", indices, "access_log", mycategoryString,"request_url_2" , "request_url_3", count);
   
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
}
