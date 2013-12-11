package com.yammer.dropwizard.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 4/12/13
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationException
{
    @JsonProperty
    private String excString;

    public LocationException() {}

    public LocationException(String excString)
    {
        this.excString = excString;
    }

    public String getExcString() {return this.excString;}
    public void setExcString(String excString) {this.excString = excString;}

}
