package com.yammer.dropwizard.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Date;


public class UserTrail
{
    @JsonProperty
    private String username;

    @JsonProperty
    private String usertrail;

    @JsonProperty
    private String presencetime;

    public UserTrail(){}

    public UserTrail(String username, String usertrail, String presencetime)
    {
        this.username = username;
        this.usertrail = usertrail;
        this.presencetime = presencetime;
    }

    public UserTrail(String username, String usertrail)
    {
        this.username = username;
        this.usertrail = usertrail;
    }

    public String getUsername() {return this.username;}
    public void setUsername(String username){this.username = username;}

    public String getUsertrail() {return this.usertrail;}
    public void setUsertrail(String usertrail){this.usertrail = usertrail;}

   public String getPresencetime() {return this.presencetime;}
   public void setPresencetime(String presencetime) {this.presencetime = presencetime;}
}
