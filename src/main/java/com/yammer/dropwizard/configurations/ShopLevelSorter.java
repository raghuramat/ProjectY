package com.yammer.dropwizard.configurations;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 4/12/13
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ShopLevelSorter  implements Comparator<ShopDetails>
{
    @Override
    public int compare(ShopDetails rc1, ShopDetails rc2) {
        if(rc1.getLevel() > rc2.getLevel()){
            return 1;
        } else {
            return -1;
        }
    }

}
