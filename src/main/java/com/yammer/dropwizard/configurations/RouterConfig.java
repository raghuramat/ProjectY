package com.yammer.dropwizard.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import java.util.Comparator;


public class RouterConfig
{
    @JsonProperty
    private int shopNo;

    @JsonProperty
    private String bssId;

    @JsonProperty
    private String ssId;

    @JsonProperty
    private String caps;

    @JsonProperty
    private double level;

    public RouterConfig() {}

    public RouterConfig(int shopNo, String bssId, String ssId, String caps, double level)
    {
        this.shopNo = shopNo;
        this.bssId = bssId;
        this.ssId = ssId;
        this.caps = caps;
        this.level = level;
    }

    public int getShopNo() {return shopNo;}
    public void setShopNo(int shopNo) {this.shopNo = shopNo;}

    public String getBssId(){return bssId;}
    public void setBssId(String bssId) {this.bssId = bssId;}

    public String getSsId() {return ssId;}
    public void setSsId(String ssId) {this.ssId = ssId;}

    public String getCaps() {return caps;}
    public void setCaps(String caps) {this.caps = caps;}

    public double getLevel() {return level;}
    public void setLevel(double level) {this.level = level;}
}
