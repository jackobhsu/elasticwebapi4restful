package com.uitox.jackob;

import java.lang.*;
public interface IQueryItem  {
	
   public Item QueryItemcount( String clustername,String ipaddress,String[] indices,String doctype,String select_querystring,String select_regex,String select_facetfeild,String select_termsquery);
 
}
