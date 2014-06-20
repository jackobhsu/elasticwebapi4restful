package com.uitox.jackob;


public class Item {

    private String _itemid;
    private Integer _itemcount;
    private String _itemcategory;


    public Item(String itemid, Integer itemcount, String itemcategory) {

        this._itemid = itemid;
        this._itemcount = itemcount;
        this._itemcategory = itemcategory;

    }


    public String get_itemid() {
        return _itemid.toUpperCase();
    }


    public void set_itemid(String _itemid) {
        this._itemid = _itemid.toUpperCase();
    }


    public Integer get_itemcount() {
        return _itemcount;
    }


    public void set_itemcount(Integer _itemcount) {
        this._itemcount = _itemcount;
    }


    public String get_itemcategory() {
        return _itemcategory.toLowerCase();
    }


    public void set_itemcategory(String _itemcategory) {
        this._itemcategory = _itemcategory.toLowerCase();
    }


    public String get_info() {

       // String reString = _itemcategory + " : " + Integer.toOctalString(_itemcount) + " : " + _itemid;
        String reString = _itemcategory + " : " + _itemcount.toString() + " : " + _itemid;


        return reString;

    }


}
