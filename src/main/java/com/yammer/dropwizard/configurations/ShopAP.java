package com.yammer.dropwizard.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 2/12/13
 * Time: 5:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShopAP
{
    @JsonProperty
    private String shopname;

    @JsonProperty
    private String routername;

    @JsonProperty
    private int shopno;

    @JsonProperty
    private String otherrouters;

    public ShopAP() {}

    public ShopAP(String shopname, String routername, int shopno, String otherrouters)
    {
        this.shopname = shopname;
        this.routername = routername;
        this.shopno = shopno;
        this.otherrouters = otherrouters;
    }

    public String getShopname() {return this.shopname;}
    public void setShopname(String shopname) {this.shopname = shopname;}

    public String getRoutername() {return this.routername;}
    public void setRoutername(String routername) {this.routername = routername;}

    public int getShopno() {return this.shopno;}
    public void setShopno(int shopno) {this.shopno = shopno;}

    public String getOtherrouters() {return this.otherrouters;}
    public void setOtherrouters(String otherrouters){this.otherrouters = otherrouters;}
}
