package com.uitox.jackob;

/**
 * Created by root on 3/3/14.
 */


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

public class MainQueryItems implements IQueryItems {

	@Override
    public Item[] QueryItemscount(String clustername,String ipaddress,String[] indices,String doctype,String select_querystring,String select_facetfeild,String select_termsquery,int faces_count) {

//        String clustername = "elastic";
//        String ipaddress = "elastic06.idc1.fn";
//        String[] indices = new String[]{"access_log-www-2014-03-03"};
//        String doctype = "access_log";
//        String select_querystring = "/";
//        String select_facetfeild = "request_url_2";
//        String select_termsquery = "request_url_3";
//        int faces_count = 10;

        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clustername).put("client.transport.sniff",true).build();
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(ipaddress, 9300));

        SearchResponse response = client.prepareSearch(indices)
        		.setPreference("_primary_first")
                .setTypes(doctype)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .addFields("request_url_2","request_url_3","request_url_4")
                .setSize(0)
                .setFilter(FilterBuilders.rangeFilter("@timestamp").from("2014-01-01"))
                .setQuery(QueryBuilders.termsQuery(select_termsquery, select_querystring))
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
        String select_pageview = "/";
        String select_facetfeildString = "request_url_2";
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

//    @Override
//    public Item QueryItemcount(String itemid) {
//        if (itemid == "/"){
//
//            String clustername = "elastic";
//            String Queryaddress = "elastic06.idc1.fn";
//            String[] select_indexString = new String[]{"access_log-www-2014-03-03"};
//            String select_doctypeString = "access_log";
//            String select_pageview = "/";
//            String select_facetfeildString = "request_url_2";
//            String select_termsqueryString = "request_url_3";
//            int count_size = 500;
//
//            Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clustername).put("client.transport.sniff",true).build();
//            Client client = new TransportClient(settings)
//                    .addTransportAddress(new InetSocketTransportAddress(Queryaddress, 9300));
//
//            SearchResponse response = client.prepareSearch(select_indexString)
//                    .setTypes(select_doctypeString)
//                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                    .addFields("request_url_2","request_url_3","request_url_4")
//                    .setSize(0)
//                    .setFilter(FilterBuilders.rangeFilter("@timestamp").from("2014-01-01"))
//                    .setQuery(QueryBuilders.termsQuery(select_termsqueryString, select_pageview))
//                    .addFacet(FacetBuilders.termsFacet("pageview").field(select_facetfeildString).size(count_size)).execute().actionGet();
//
//            TermsFacet f =(TermsFacet) response.getFacets().facetsAsMap().get("pageview");
//            ItemsManager im = new ItemsManager();
//            int i = 0;
//            for (TermsFacet.Entry entry : f.getEntries()) {
//                Item inputitem = new Item(entry.getTerm().toString(), entry.getCount(), select_pageview);
//                im.add(inputitem);
//                i++;
//            }
//            client.close();
//            return im.get_Items()[0];
//        }
//        else
//        {
//            return new Item(itemid,0,itemid);
//        }
//
//    }
}
