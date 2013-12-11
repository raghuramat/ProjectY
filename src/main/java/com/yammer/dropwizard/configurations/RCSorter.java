package com.yammer.dropwizard.configurations;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 26/11/13
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class RCSorter implements Comparator<RouterConfig>
{
     @Override
        public int compare(RouterConfig rc1, RouterConfig rc2) {
            if(rc1.getLevel() < rc2.getLevel()){
                return 1;
            } else {
                return -1;
            }
        }

}
