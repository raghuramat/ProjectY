package com.yammer.dropwizard.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShopDetails
{
    @JsonProperty
    private int shopid;

    @JsonProperty
    private String shopname;

    @JsonProperty
    private int level;

    @JsonProperty
    private int locationid;

    public ShopDetails(){}

    public ShopDetails(int shopid, String shopname, int level, int locationid)
    {
        this.shopid = shopid;
        this.shopname = shopname;
        this.level = level;
        this.locationid = locationid;
    }

    public String getShopname(){return this.shopname;}
    public void setShopname(String shopname) {this.shopname = shopname;}

    public int getLevel() {return this.level;}
    public void setLevel(int level) {this.level = level;}

    public int getLocationid() {return this.locationid;}
    public void setLocationid(int locationid) {this.locationid = locationid;}

    public int getShopid() {return this.shopid;}
    public void setShopid(int shopid) {this.shopid = shopid;}
}
