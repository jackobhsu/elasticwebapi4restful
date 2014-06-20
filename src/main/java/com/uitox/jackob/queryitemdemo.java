package com.uitox.jackob;


import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.mvel2.util.Varargs;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacet;
//import com.uitox.jackob.ItemQueryItems;

public class queryitemdemo {

    public static Item[] QueryItemsCount(){

        String clustername = "elastic";
        String Queryaddress = "elastic06.idc1.fn";
        String[] select_indexString = new String[]{"access_log-www-2014-03-03"};
        String select_doctypeString = "access_log";
        String select_pageview = "category";
        String select_facetfeildString = "request_url_4";
        String select_termsqueryString = "request_url_3";
        int count_size = 5;

        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clustername).put("client.transport.sniff",true).build();
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(Queryaddress, 9300));

        SearchResponse response = client.prepareSearch(select_indexString)
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
    public static Item QueryItemCount(String itemid){


        ItemsManager im=null;
        Item newitem =null;
        String clustername = "elastic";
        String Queryaddress = "elastic06.idc1.fn";
        String[] select_indexString = new String[]{"access_log-www-2014-03-03"};
        String select_doctypeString = "access_log";
        String select_pageview = "item";
        String select_facetfeildString = "request_url_4";
        String select_termsqueryString = "request_url_3";
        //String select_termsqueryString = itemid;
        int count_size = 5;

        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clustername).put("client.transport.sniff",true).build();
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(Queryaddress, 9300));

        SearchResponse response = client.prepareSearch(select_indexString)
                .setTypes(select_doctypeString)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .addFields("request_url_2","request_url_3","request_url_4")
                .setSize(0)
                .setFilter(FilterBuilders.rangeFilter("@timestamp").from("2014-01-01"))
                .setQuery(QueryBuilders.termsQuery(select_termsqueryString, select_pageview))
                .addFacet(FacetBuilders.termsFacet("pageview").field(select_facetfeildString).size(count_size)).execute().actionGet();

        TermsFacet f =(TermsFacet) response.getFacets().facetsAsMap().get("pageview");
        im = new ItemsManager();
        int i = 0;
        for (TermsFacet.Entry entry : f.getEntries()) {
            Item inputitem = new Item(entry.getTerm().toString(), entry.getCount(), select_pageview);
            im.add(inputitem);
            i++;
        }










//        for (Item item : im.get_Items())
//        {
//            if (item.get_itemid().toString() == itemid.toString())
//            {
//                newitem = item;
//
//            }
//
//        }
//
//        if (newitem ==null ){
//
//            newitem = new Item(itemid,0,"null");
//
//        }

        client.close();

        //int im_length = im.count;

        newitem = im.get_ItemById(itemid);

        return newitem;




    }


    public static void main(String[] args) {

//       System.out.println("hello world!");
//        Item[] its = QueryItemsCount();
////
//        for (Item item : its) {
//            System.out.println(item.get_info());
//        }



//        Item it = QueryItemCount("201310CM310000842");
//        System.out.println(it.get_info());


        //Item item =QueryItemCount("C7394");



//        ItemQueryItems itsq = new ItemQueryItems();
//        for (Item item : itsq.QueryItemscount()) {
//            System.out.println(item.get_info());
//        }
        /*
        * OOP 寫法
        *
        *
        QueryItemsManager qim = new QueryItemsManager();

        qim.setIquery(new MainQueryItems());
        System.out.println(qim.queryitems()[0].get_info());
        System.out.println(" ============ ");

        qim.setIquery(new ItemQueryItems());
        System.out.println(qim.queryitems()[0].get_info());
//        for (Item item : qim.queryitems()) {
//            System.out.println(item.get_info());
//        }

        System.out.println(" ============ ");

        qim.setIquery(new CategoryQueryItems());
        System.out.println(qim.queryitems()[0].get_info());
//        for (Item item : qim.queryitems()) {
//            System.out.println(item.get_info());
//        }

        System.out.println(" ============ ");
        qim.setIquery(new MarketQueryItems());
        System.out.println(qim.queryitems()[0].get_info());
//        for (Item item : qim.queryitems()) {
//            System.out.println(item.get_info());
//        }



* */

    	String[] indicesstringStrings = null;
    	int show_size = Integer.parseInt(args[1].toString());
    	
    	  Item[] qitems = null;
    	  
    	  if (args.length != 0){
    		  
    		if (args[0].toString().indexOf(',')>0){
    			
    			indicesstringStrings = args[0].toString().split(",");
    			
    		} 
    		else {
    			indicesstringStrings = new String[]{args[0].toString()};
			}
    		  
    	  }
    	  
    	  
    	 QueryItemsManager qim = new QueryItemsManager();
    	 qim.setIquerys(new MainQueryItems());
    	System.out.println(qim.queryitems("elastic","elastic06.idc1.fn",new String[]{"access_log-www-2014-03-11"} , "access_log", "/","request_url_2" , "request_url_3", 5)[0].get_info());
    	//  System.out.println(qim.queryitems("elastic","elastic06.idc1.fn",indicesstringStrings, "access_log", "/","request_url_2" , "request_url_3", show_size)[0].get_info());
    	 
    	  /*
    	   *     String clustername = "elastic";
        String Queryaddress = "elastic06.idc1.fn";
        String[] select_indexString = new String[]{"access_log-www-2014-03-03"};
        String select_doctypeString = "access_log";
        String select_pageview = "category";
        String select_facetfeildString = "request_url_4";
        String select_termsqueryString = "request_url_3";
        int count_size = 5;
    	   * 
    	   * */
    	 
    	  
    	 qim.setIquerys(new ItemQueryItems());
    	 //qitems = qim.queryitems("elastic","elastic06.idc1.fn",new String[]{"access_log-www-2014-03-11"} , "access_log", "item","request_url_4" , "request_url_3", 5);
    	 qitems = qim.queryitems("elastic","elastic06.idc1.fn",indicesstringStrings , "access_log", "item","request_url_4" , "request_url_3", show_size);
    	 //System.out.println(qim.queryitems()[0].get_info());
	     for (Item item : qitems) {
	        System.out.println(item.get_info());
	     }
	     
	     System.out.println(" ############### ");
	     
	     qim.setIquerys(new CategoryQueryItems());
	     //qitems = qim.queryitems("elastic","elastic06.idc1.fn",new String[]{"access_log-www-2014-03-11"} , "access_log", "category","request_url_4" , "request_url_3", 5);
	     qitems = qim.queryitems("elastic","elastic06.idc1.fn",indicesstringStrings , "access_log", "category","request_url_4" , "request_url_3", show_size);
    	 //System.out.println(qim.queryitems()[0].get_info());
	     for (Item item : qitems) {
	        System.out.println(item.get_info());
	     } 
	     
	     System.out.println(" ############### ");
	     
	     qim.setIquerys(new MarketQueryItems());
	     //qitems = qim.queryitems("elastic","elastic06.idc1.fn",new String[]{"access_log-www-2014-03-09","access_log-www-2014-03-10","access_log-www-2014-03-11"} , "access_log", "market","request_url_4" , "request_url_3", 5);
	     qitems = qim.queryitems("elastic","elastic06.idc1.fn",indicesstringStrings, "access_log", "market","request_url_4" , "request_url_3", show_size);
    	 //System.out.println(qim.queryitems()[0].get_info());
	     for (Item item : qitems) {
	        System.out.println(item.get_info());
	     } 
	     
	     System.out.println(" ############### ");
	     
	     
	     qim.setIquerys(new MarketQueryItems());
	     //qitems = qim.queryitems("elastic","elastic06.idc1.fn",new String[]{"access_log-www-2014-03-09","access_log-www-2014-03-10","access_log-www-2014-03-11"} , "access_log", "market","request_url_4" , "request_url_3", 5);
	     qitems = qim.queryitems("elastic","elastic06.idc1.fn",indicesstringStrings, "access_log", "edm","request_url_4" , "request_url_3", show_size);
    	 //System.out.println(qim.queryitems()[0].get_info());
	     for (Item item : qitems) {
	        System.out.println(item.get_info());
	     } 
	     
	     System.out.println(" ############### ");
	     
    	
     }
}
