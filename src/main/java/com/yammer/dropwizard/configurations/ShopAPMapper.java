package com.yammer.dropwizard.configurations;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 2/12/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShopAPMapper implements ResultSetMapper<ShopAP>
{
    public ShopAP map(int index, ResultSet r, StatementContext ctx)  throws SQLException
        {
            return new ShopAP(r.getString("shopname"),r.getString("routername"), r.getInt("shopno"), r.getString("otherrouters"));
        }
}