package com.group3.Filter;

import android.content.Intent;

import com.group3.swengandroidapp.Filter;
import com.group3.swengandroidapp.MainActivity;
import com.group3.swengandroidapp.PythonClient;

/**
 * @deprecated
 * Created by St. John on 06/03/2018.
 */

public class FilterTest {


    public static void  main(String[] args) throws Exception{

        PythonClient client = new PythonClient();

        Thread.sleep(1000);
        String[] inputIds = new String[1];

        inputIds[0] = "0000";

        Filter.Info criteria = new Filter.Info(true, true, false, false, false, false);
        Filter.getInstance().setCriteria(criteria);
        System.out.println("CRITERIA: " + criteria.toString());

        String[] filterOut = Filter.getInstance().process(inputIds);

        if(filterOut[0] != null){
            System.out.println("Test Outcome: "+filterOut[0].toString());
        }else {
            System.out.println("Test Outcome: YE OLDE FAILE");
        }

        criteria = new Filter.Info(true, true, false, true, false, true);
        Filter.getInstance().setCriteria(criteria);
        System.out.println("CRITERIA: " + criteria.toString());

        if(filterOut[0] != null){
            System.out.println("Test Outcome: "+filterOut[0].toString());
        }else {
            System.out.println("Test Outcome: YE OLDE FAILE");
        }

        System.out.println("Test Finished");
    }

}
