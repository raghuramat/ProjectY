package com.yammer.dropwizard.resources;

import com.yammer.dropwizard.DL.MySqlDAO;
import com.yammer.dropwizard.configurations.*;
import com.yammer.dropwizard.jdbi.logging.LogbackLog;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Path("/")
public class IndoorMapping
{
    private MySqlDAO mappingDAO;

    public IndoorMapping(MySqlDAO dao)
    {
        mappingDAO = dao;
    }

    //region  This method reads the input router config along with the level of the router and stores it
    //
    @POST
    @Path("indoor")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public String postScannedData(List<RouterConfig> lstRC)
    {
        int shopNo = 0;
        try
        {
            int index = 0;
            double strongestLevel = 0;
            String otherRouters = "";
            String strongestBssId = "";
            Collections.sort(lstRC,new RCSorter() {
                @Override
                public int compare(RouterConfig rc1, RouterConfig rc2) {
                    return super.compare(rc1, rc2);    //To change body of overridden methods use File | Settings | File Templates.
                }
            });

            int size = lstRC.size();

            for(RouterConfig rc : lstRC)
            {
                try
                {
                   mappingDAO.insertAP(rc.getBssId(), rc.getSsId(), rc.getCaps());
                }
                catch(Exception ex)
                {
                    mappingDAO.insertException(ex.toString());
                }

                if(index == 0)
                {
                    index++;
                    strongestBssId = rc.getBssId();
                    strongestLevel = rc.getLevel();
                    shopNo = rc.getShopNo();
                }
                else
                {
                    if(index < size - 1)
                    {
                       otherRouters += rc.getBssId()+ ":" + rc.getLevel() + ";";
                    }
                    else
                    {
                        otherRouters += rc.getBssId()+ ":" + rc.getLevel();
                    }

                    index++;
                }
            }

            int strongestRouterId = mappingDAO.findNameById(strongestBssId); //dbLayer.FindStrongestRouter(strongestBssId);
            mappingDAO.insertRouterShopMapping(shopNo, strongestRouterId, strongestLevel, otherRouters);
            mappingDAO.close();
        }
        catch (Exception exc)
        {
            mappingDAO.insertException(exc.toString());
            return "500";
        }
        finally {

            mappingDAO.close();;
        }

        return "200";
    }
    //endregion

    //region This method returns a sample output, its a get
    @GET
    @Path("get")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getRouterList()
    {
       List<RouterConfig> cfgList = new ArrayList<RouterConfig>();

        RouterConfig cfg1 = new RouterConfig(1,"fc:da:be:8g","R1","WPA2",-25.00);
        RouterConfig cfg2 = new RouterConfig(1,"ab:cd:ef:gh:ij","R2","WPA2",-30.00);
        RouterConfig cfg3 = new RouterConfig(1,"kl:mn:op:qr:st","R3","WPA2",-20.00);
        RouterConfig cfg4 = new RouterConfig(1,"uv:wx:yz:ab:cd","R4","WPA2",-45.00);
        RouterConfig cfg5 = new RouterConfig(1,"ap:an:am:qa:er","R5","WPA2",-15.00);

        cfgList.add(cfg1);
        cfgList.add(cfg2);

        cfgList.add(cfg3);
        cfgList.add(cfg4);
        cfgList.add(cfg5);

        return Response.ok(cfgList).header("Access-Control-Allow-Origin","*").build();


    }
     //endregion

    //region This method stores usertrail
    @POST
    @Path("manual")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public String manScan(UserTrail userTrail)   //
    {
        try
        {
            mappingDAO.insertUserTrail(userTrail.getUsername(), userTrail.getUsertrail());
        }
        catch (Exception exc)
        {
            mappingDAO.insertException(exc.toString());
              return "500";
        }  finally {
            mappingDAO.close();
        }

        return "200";
    }
    //endregion

    //region this API will help fetch shop details
    @GET
    @Path("fetchshops/{locationid}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getShops(@PathParam("locationid") int locationid)
    {
        try
        {
            List<ShopDetails> lstShopDetails = mappingDAO.fetchshopDetails(locationid);

            Collections.sort(lstShopDetails,new ShopLevelSorter() {
                @Override
                public int compare(ShopDetails sd1, ShopDetails sd2) {
                    return super.compare(sd1, sd2);    //To change body of overridden methods use File | Settings | File Templates.
                }
            });

            return Response.ok(lstShopDetails).header("Access-Control-Allow-Origin","*").build();
        }
        catch (Exception exc)
        {
            mappingDAO.insertException(exc.toString());
            return null;
        }
        finally {
            mappingDAO.close();
        }


    }
    //endregion

    //region this API will help fetch location details
    @GET
    @Path("fetchlocations")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getLocations()
    {
        try
        {
            return Response.ok(mappingDAO.fetchLocationDetails()).header("Access-Control-Allow-Origin","*").build();

        }
        catch (Exception exc)
        {
            mappingDAO.insertException(exc.toString());
            return null;
        }
        finally {
            mappingDAO.close();
        }


    }
    //endregion

    //region this API will fetch user trail, in a sorted order with timestamp
    @GET
    @Path("/userTrail/{username}/{size}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response fetchUserTrail(@PathParam("username") String username, @PathParam("size") int size)
    {
        try
        {
                return Response.ok(mappingDAO.fetchUserTrail(username.trim(), size)).header("Access-Control-Allow-Origin","*").build();
        }
        catch(Exception exc)
        {
            mappingDAO.insertException(exc.toString());
               return null;
        }
        finally
        {
               mappingDAO.close();
        }
    }
    //endregion

    //region this API will fetch last user trail, in a sorted order with timestamp
    @GET
    @Path("/fetchUserLocation/{username}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response fetchStrongestRouter(@PathParam("username") String username)
    {
        try
        {
            ShopAP strongestRouterId = new ShopAP();
            List<UserTrail> tList = mappingDAO.fetchUserTrail(username.trim(), 1);
            for(UserTrail t : tList)
            {
                String s = t.getUsertrail();
                String[] sList = s.split(";");
                String sToSplit = sList[0];

                String[] sNewList = sToSplit .split("\\|");
                String routerSSId = sNewList[0];
                strongestRouterId = mappingDAO.findShop(routerSSId);
            }

            return Response.ok(strongestRouterId).header("Access-Control-Allow-Origin","*").build();

        }
        catch(Exception exc)
        {
            mappingDAO.insertException(exc.toString());
            return null;
        }
        finally
        {
            mappingDAO.close();
        }
    }
    //endregion

    //region fetch exceptions
    @GET
    @Path("/fetchExceptions/{size}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response fetchExceptions(@PathParam("size") int size)
    {
        try
        {
            return Response.ok(mappingDAO.fetchExceptions(size)).header("Access-Control-Allow-Origin","*").build();

        }
        catch(Exception exc)
        {
            mappingDAO.insertException(exc.toString());
            return null;
        }
        finally
        {
            mappingDAO.close();
        }
    }
    //endregion

    //region this API will fetch last user trail using a different manner, in a sorted order with timestamp
    @GET
    @Path("/fetchUserLocApprox/{username}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response fetchStrongestRouter1(@PathParam("username") String username)
    {
        try
        {
            List<ShopAP> arList = new ArrayList<ShopAP>();

            List<UserTrail> tList = mappingDAO.fetchUserTrail(username.trim(), 1);

            for(UserTrail t : tList)
            {
                String s = t.getUsertrail();
                String[] sList = s.split(";");
                String sBssId = sList[0];

                String[] sNewList = sBssId .split("\\|");
                String routerSSId = sNewList[0];
                ShopAP shopAP = mappingDAO.findShop(routerSSId);

                if(shopAP == null)
                {
                    for(ShopAP ap :  mappingDAO.findShop1(routerSSId,"%" + routerSSId + "%"))
                    {
                        String otherRoutersStr = ap.getOtherrouters();
                        for(String sbssId : otherRoutersStr.split(";"))
                        {
                            String[] bssIdLevelArr = sbssId.split(":");
                            int sizeOfArr = bssIdLevelArr.length;
                            String compareRouterId = "";
                            // typical other routers string looks like d8:9d:67:b5:23:a9:-63.0;c0:8a:de:38:21:f8:-69.0;b0:b2:dc:dc:0b:f4:-91.0;00:18:f8:f9:08:96:-93.0;fc:f5:28:7b:37:cd:-97.0;1c:7e:e5:aa:3e:e0:-99.0

                            for(int i = 0; i <= sizeOfArr - 2; i++)
                            {
                                 if(i < sizeOfArr - 2)
                                   compareRouterId += bssIdLevelArr[i] + ":";
                                else
                                   compareRouterId += bssIdLevelArr[i];
                            }

                            if(compareRouterId.contentEquals(routerSSId))
                            {
                                if(Double.parseDouble(bssIdLevelArr[sizeOfArr -1]) > -100.0)
                                {
                                    arList.add(ap);
                                }
                            }

                        }

                    }
                }
                else
                {
                    arList.add(shopAP);
                }
            }

            return Response.ok(arList).header("Access-Control-Allow-Origin","*").build();

        }
        catch(Exception exc)
        {
            mappingDAO.insertException(exc.toString());
            return null;
        }
        finally
        {
            mappingDAO.close();
        }
    }
    //endregion

}
