package com.group3.Filter;

/**
 * Created by St. John on 06/03/2018.
 */

public class FilterTest {


    public static void  main(String[] args){
        Filter myFilter = new Filter();
        Filter.Info criteria = new Filter.Info( true, false, false, false, true, false);
        myFilter.setCriteria(criteria);

        Filter.Info[] infos = new Filter.Info[4];
        Filter.Info[] newInfos;


        infos[0] = new Filter.Info(true, true, false, true, true, false);
        infos[1] = new Filter.Info(false, false, true, true, true, false);
        infos[2] = new Filter.Info(true, true, false, true, true, false);
        infos[3] = new Filter.Info(false, false, false, true, false, false);

        newInfos = myFilter.manyTest(infos);

        System.out.println("CRITERIA: " + criteria.toString());

        System.out.println("INPUTS:");
        for(Filter.Info i : infos){
            System.out.println(i.toString());
        }
        System.out.println("OUTPUT:");
        for(Filter.Info i : newInfos){
            System.out.println(i.toString());
        }
    }

}
