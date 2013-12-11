package com.yammer.dropwizard.DL;

import com.yammer.dropwizard.configurations.*;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 26/11/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MySqlDAO
{
    @SqlUpdate("insert into tb_accesspoints(bssid, ssid, capabilities) values (:bssid, :ssid, :capabilities)")
    void insertAP(@Bind("bssid") String bssid, @Bind("ssid") String ssid, @Bind("capabilities") String capabilities);

    @SqlQuery("select id from tb_accesspoints where bssid = :bssid")
    int findNameById(@Bind("bssid") String bssid);

    @SqlUpdate("insert into tbrouter_shop_mapping(shopno, strongestrouter, strongestlevel, otherrouters) values(:shopno, :strongestrouter, :strongestlevel, :otherrouters)")
    void insertRouterShopMapping(@Bind("shopno") int shopNo, @Bind("strongestrouter") int strongestrouter, @Bind("strongestlevel") double strongestlevel, @Bind("otherrouters") String otherrouters );

    @SqlUpdate("insert into tb_user_trail(username,usertrail) values (:username, :usertrail)")
    void insertUserTrail(@Bind("username") String username, @Bind("usertrail") String usertrail);

    @Mapper(LocationDetailsMapper.class)
    @SqlQuery("select * from tb_location")
    List<LocationDetails> fetchLocationDetails();

    @Mapper(ShopDetailsMapper.class)
    @SqlQuery("select * from tb_shop where locationid = :locationid")
    List<ShopDetails> fetchshopDetails(@Bind("locationid") int locationid);

    @Mapper(UserTrailMapper.class)
    @SqlQuery("select * from tb_user_trail where username = :username order by presencetime desc limit :size")
    List<UserTrail> fetchUserTrail(@Bind("username") String username, @Bind("size") int size);

    @Mapper(ShopAPMapper.class)
    @SqlQuery("select tbs.shopname as shopname, tba.ssid as routername, tbs.shopno as shopno, '' as otherrouters from tb_shop tbs, tb_accesspoints tba, tbrouter_shop_mapping tbrsm where tba.bssid = :strongestrouter and tbrsm.strongestrouter = tba.Id and tbs.shopno = tbrsm.shopno ")
    ShopAP findShop(@Bind("strongestrouter") String strongestrouter);

    @SqlUpdate("insert into tb_exceptions(exceptions_msg) values (:exceptionStr)")
    void insertException(@Bind("exceptionStr") String exceptionStr);

    @Mapper(LocationExceptionMapper.class)
    @SqlQuery("select * from tb_exceptions order by presencetime desc limit :size")
    List<LocationException> fetchExceptions(@Bind("size") int size);

    @Mapper(ShopAPMapper.class)
    @SqlQuery("select tbs.shopname as shopname, tba.ssid as routername, tbrsm.shopno as shopno, tbrsm.otherrouters as otherrouters from tbrouter_shop_mapping tbrsm, tb_shop tbs, tb_accesspoints tba where ((strongestrouter = (select ID from tb_accesspoints where bssid=:bssid)) OR (otherrouters LIKE :likebssid)) and tbs.shopno = tbrsm.shopno and tba.bssid = :bssid")
    List<ShopAP> findShop1(@Bind("bssid") String bssId, @Bind("likebssid") String likebssId );

    void close();
}

