package com.yammer.dropwizard.configurations;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 29/11/13
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserTrailMapper implements ResultSetMapper<UserTrail>
{
   public UserTrail map(int index, ResultSet r, StatementContext ctx)  throws SQLException
        {
            Date newDate =  r.getTimestamp("presencetime");
           return new UserTrail(r.getString("username"), r.getString("usertrail"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newDate) );
        }
}
