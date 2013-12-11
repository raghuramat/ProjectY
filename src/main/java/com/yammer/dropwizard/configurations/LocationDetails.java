package com.yammer.dropwizard.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 28/11/13
 * Time: 6:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationDetails
{
    @JsonProperty
    private int locationid;

    @JsonProperty
    private String locationname;

    @JsonProperty
    private String locationcity;

    public LocationDetails() {}

    public LocationDetails(int locationid, String locationname, String locationcity)
    {
        this.locationname = locationname;
        this.locationcity = locationcity;
        this.locationid = locationid;
    }

    public String getLocationname() {return this.locationname;}
    public void setLocationname(String locationname){this.locationname = locationname;}

    public String getLocationcity() {return this.locationcity;}
     public void  setLocationcity(String locationcity){this.locationcity= locationcity;}

    public int getLocationid() {return this.locationid;}
    public void setLocationid(int locationid){this.locationid = locationid;}
}


