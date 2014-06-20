package com.uitox.jackob;

public interface IQueryItems  {

	/*
	 *   String clustername = "elastic";
        String ipaddress = "elastic06.idc1.fn";
        String[] indices = new String[]{"access_log-www-2014-03-03"};
        String doctype = "access_log";
        String select_querystring= "category";
        String select_facetfeild = "request_url_4";
        String select_termsquery = "request_url_3";
        int count_size = 25;
	 * 
	 * 
	 * */
	
	 public Item[] QueryItemscount();
	 public Item[] QueryItemscount(String clustername,String ipaddress,String[] indices,String doctype,String select_querystring,String select_facetfeild,String select_termsquery,int faces_count);
	 	
}
