package com.yammer.dropwizard.configurations;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDetailsMapper implements ResultSetMapper<LocationDetails>
{
        public LocationDetails map(int index, ResultSet r, StatementContext ctx)  throws SQLException
        {
            return new LocationDetails(r.getInt("id"),r.getString("locationname"), r.getString("locationcity"));
        }
}

