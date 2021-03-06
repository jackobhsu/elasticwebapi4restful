package com.uitox.jackob;

import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilderString;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryFilterBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.strings.InternalStringTermsFacet.TermEntry;
import org.glassfish.grizzly.http.io.InputBuffer;

/**
 * Created by root on 3/3/14.
 */
public class ItemQueryItems implements IQueryItems,IQueryItem {

	
	@Override
	public Item[] QueryItemscount(String clustername,String ipaddress,String[] indices,String doctype,String select_querystring,String select_facetfeild,String select_termsquery,int faces_count) {

        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clustername).put("client.transport.sniff",true).build();
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(ipaddress, 9300));

        String tts = client.prepareSearch(indices)
        		.setPreference("_primary_first")
                .setTypes(doctype)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .addFields("request_url_2","request_url_3","request_url_4")
                .setSize(0)
                .setQuery(QueryBuilders.filteredQuery(QueryBuilders.termQuery(select_termsquery, select_querystring), FilterBuilders.rangeFilter("@timestamp").from("2014-01-01").to("now")))
                .addFacet(FacetBuilders.termsFacet("pageview").field(select_facetfeild).size(faces_count)).toString();
       
        SearchResponse response = client.prepareSearch(indices)
        		.setPreference("_primary_first")
                .setTypes(doctype)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .addFields("request_url_2","request_url_3","request_url_4")
                .setSize(0)
                .setQuery(QueryBuilders.filteredQuery(QueryBuilders.termQuery(select_termsquery, select_querystring), FilterBuilders.rangeFilter("@timestamp").from("2014-01-01").to("now")))
                .addFacet(FacetBuilders.termsFacet("pageview").field(select_facetfeild).size(faces_count)).execute().actionGet();

        TermsFacet f =(TermsFacet) response.getFacets().facetsAsMap().get("pageview");
        ItemsManager im = new ItemsManager();
        int i = 0;
        for (TermsFacet.Entry entry : f.getEntries()) {
            Item inputitem = new Item(entry.getTerm().toString(), entry.getCount(), select_querystring);
            im.add(inputitem);
            i++;
        }
        client.close();
        return im.get_Items();

    }
	
	
    @Override
    public Item[] QueryItemscount() {

        String clustername = "elastic";
        String Queryaddress = "elastic06.idc1.fn";
        String[] select_indexString = new String[]{"access_log-www-2014-03-03"};
        String select_doctypeString = "access_log";
        String select_pageview = "item";
        String select_facetfeildString = "request_url_4";
        String select_termsqueryString = "request_url_3";
        int count_size = 10;

        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clustername).put("client.transport.sniff",true).build();
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(Queryaddress, 9300));

        SearchResponse response = client.prepareSearch(select_indexString)
        		.setPreference("_primary_first")
                .setTypes(select_doctypeString)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .addFields("request_url_2","request_url_3","request_url_4")
                .setSize(0)
                .setFilter(FilterBuilders.rangeFilter("@timestamp").from("2014-01-01"))
                .setQuery(QueryBuilders.termsQuery(select_termsqueryString, select_pageview))
                .addFacet(FacetBuilders.termsFacet("pageview").field(select_facetfeildString).size(count_size)).execute().actionGet();

        TermsFacet f =(TermsFacet) response.getFacets().facetsAsMap().get("pageview");
        ItemsManager im = new ItemsManager();
        int i = 0;
        for (TermsFacet.Entry entry : f.getEntries()) {
            Item inputitem = new Item(entry.getTerm().toString(), entry.getCount(), select_pageview);
            im.add(inputitem);
            i++;
        }
        client.close();
        return im.get_Items();

    }
    
    /**
     * 
     * curl -XGET "http://elastic05.idc1.fn:9200/access_log-www-2014-03-17/access_log/_search?pretty" -d '
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
            Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clustername).put("client.transport.sniff",true).build();
            Client client = new TransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(ipaddress, 9300));
            client.admin().indices().prepareRefresh(indices).execute().actionGet();

            String tt =client.prepareSearch(indices)
            		.setPreference("_primary_first")
                    .setTypes(doctype)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .addFields("request_url_2","request_url_3","request_url_4")
                    .setSize(0)
                    .setQuery(QueryBuilders.filteredQuery(QueryBuilders.termQuery("request_url_3", select_querystring), FilterBuilders.boolFilter().must(FilterBuilders.regexpFilter("request_url_4", select_regex)).should(FilterBuilders.rangeFilter("@timestamp").from("2014-01-01").to("now"))))
                    .addFacet(FacetBuilders.termsFacet("pageview").field(select_facetfeild).size(10)).toString();
           System.out.println(tt);
            SearchResponse response = client.prepareSearch(indices)
            		.setPreference("_primary_first")
                    .setTypes(doctype)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .addFields("request_url_2","request_url_3","request_url_4")
                    .setSize(0)
                    .setQuery(QueryBuilders.filteredQuery(QueryBuilders.termQuery("request_url_3", select_querystring), FilterBuilders.boolFilter().must(FilterBuilders.regexpFilter("request_url_4", select_regex)).should(FilterBuilders.rangeFilter("@timestamp").from("2014-01-01").to("now"))))
                    .addFacet(FacetBuilders.termsFacet("pageview").field(select_facetfeild).size(10)).execute().actionGet();
            TermsFacet f =(TermsFacet) response.getFacets().facetsAsMap().get("pageview");
            Item inputitem = null;
            if (!f.getEntries().isEmpty()){
            	 if (select_regex.toString().equals(f.getEntries().get(0).getTerm().toString())){
                       inputitem = new Item(f.getEntries().get(0).getTerm().toString(), f.getEntries().get(0).getCount(), select_querystring);
                       inputitem.set_itemcategory(select_querystring);
                       inputitem.set_itemcount(f.getEntries().get(0).getCount());
                       inputitem.set_itemid(f.getEntries().get(0).getTerm().toString());
            		 
            	 }
            }
            else {
            	inputitem = new Item(select_regex, 0, select_querystring);
				
			}
            
            client.close();
            return inputitem;

    }


}
