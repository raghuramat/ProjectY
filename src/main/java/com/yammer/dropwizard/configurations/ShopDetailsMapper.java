package com.yammer.dropwizard.configurations;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 28/11/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShopDetailsMapper  implements ResultSetMapper<ShopDetails>
{
    public ShopDetails map(int index, ResultSet r, StatementContext ctx)  throws SQLException
    {
        return new ShopDetails(r.getInt("shopno"),r.getString("shopname"), r.getInt("level"), r.getInt("locationid"));
    }
}
