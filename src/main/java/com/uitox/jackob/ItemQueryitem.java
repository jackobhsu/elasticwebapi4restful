package com.uitox.jackob;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacet;

/**
 * Created by root on 3/3/14.
 */
public class ItemQueryitem implements IQueryItem {

    /**
     * 
     * curl -XGET "http://elastic05.idc1.fn:9200/access_log-www-2014-03-13/access_log/_search?pretty" -d '
 {
   "facets": {
     "/pageview/": {
       "terms": {
         "field": "request_url_4",
                 // must be 1 //
         "size": "1" 
       }   
     }   
   },  
   "query": {
     "filtered": {
       "filter": {
         "bool": {
           "must": {
             "regexp": {            // select_regex //
               "request_url_4": "201311CM060000224"
             }
           },
           "should": {
             "range": {
               "@timestamp": {
                 "from": "1391817600000",
                 "to": "now"
               }
             }
           }
         }
       },  
       "query": {
         "term": {
           "request_url_3": "item"
         }
       }   
     },  
     "size": "0",
     "fields": [
       "request_url",
       "request_url_2",
       "request_url_3",
       "request_url_4"
     ]   
   }
 }
 ' 
     * 
     * 
     * 
     */
    
    
  @Override
  public Item QueryItemcount( String clustername,String ipaddress,String[] indices,String doctype,String select_querystring,String select_regex,String select_facetfeild,String select_termsquery){

          Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clustername).build();
          Client client = new TransportClient(settings)
                  .addTransportAddress(new InetSocketTransportAddress(ipaddress, 9300));

          SearchResponse response = client.prepareSearch(indices)
                  .setTypes(doctype)
                  .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                  .addFields("request_url_2","request_url_3","request_url_4")
                  .setSize(0)
                  .setFilter(FilterBuilders.boolFilter().must(FilterBuilders.regexpFilter("request_url_4", select_regex)).should(FilterBuilders.rangeFilter("@timestamp").from("2014-03-14")))      
                  .setQuery(QueryBuilders.termsQuery(select_termsquery, select_querystring))
                  .addFacet(FacetBuilders.termsFacet("pageview").field(select_facetfeild).size(1)).execute().actionGet();

          TermsFacet f =(TermsFacet) response.getFacets().facetsAsMap().get("pageview");
          ItemsManager im = new ItemsManager();
          int i = 0;
          for (TermsFacet.Entry entry : f.getEntries()) {
              Item inputitem = new Item(entry.getTerm().toString(), entry.getCount(), select_querystring);
              im.add(inputitem);
              i++;
          }
          client.close();

          return im.get_ItemById(select_regex.toUpperCase());


  }


}
