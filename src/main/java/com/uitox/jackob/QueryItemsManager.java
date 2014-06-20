package com.uitox.jackob;

/**
 * Created by root on 3/3/14.
 */
public class QueryItemsManager {
//   private String clustername = "elastic";
//   private String Queryaddress = "elastic06.idc1.fn";
//    private String[] select_indexString = new String[]{"access_log-www-2014-03-03"};
//    private String select_doctypeString = "access_log";
//    private  String select_pageview = "market";
//    private String select_facetfeildString = "request_url_4";
//    private String select_termsqueryString = "request_url_3";
//    private int count_size = 500;

    
    private IQueryItems iquerys;
    private IQueryItem iquery;

  

	public QueryItemsManager() {



    }
	public void setIquery(IQueryItem iquery) {
		this.iquery = iquery;
	}
	
	public Item qetitem(String clustername,String ipaddress,String[] indices,String doctype,String select_querystring,String select_regex,String select_facetfeild,String select_termsquery){
    	
    	return iquery.QueryItemcount(clustername, ipaddress, indices, doctype, select_querystring, select_regex, select_facetfeild, select_termsquery);
    }

    public void setIquerys(IQueryItems iquerys) {
        this.iquerys = iquerys;
    }


    public Item[] queryitems(){

       return iquerys.QueryItemscount();

    }
    
    public Item[] queryitems(String clustername,String ipaddress,String[] indices,String doctype,String select_querystring,String select_facetfeild,String select_termsquery,int faces_count){
    	
    	
    	return iquerys.QueryItemscount(clustername, ipaddress, indices, doctype, select_querystring, select_facetfeild, select_termsquery, faces_count);
    }
  

   
}
