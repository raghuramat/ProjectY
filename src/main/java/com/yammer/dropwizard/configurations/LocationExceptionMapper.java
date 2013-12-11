package com.yammer.dropwizard.configurations;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 4/12/13
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationExceptionMapper implements ResultSetMapper<LocationException>
{
    public LocationException map(int index, ResultSet r, StatementContext ctx)  throws SQLException
    {
        return new LocationException(r.getString("exceptions_msg"));
    }
}