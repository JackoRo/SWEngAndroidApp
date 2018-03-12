package com.group3.swengandroidapp;
//import javafx.scene.control.ListView;

import android.util.Log;

import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

/**
 * Created by St. John on 06/03/2018.
 */

public class Filter {
    private static final Filter ourInstance = new Filter();

    private Filter.Info criteria;


    private Filter(){
        criteria = new Filter.Info();
    }

    public static Filter getInstance(){
        return Filter.ourInstance;
    }


    public String[] process(String[] inputIds){
        String[] output = new String[inputIds.length];
        int counter = 0;
        for(String s : inputIds){
            try{
                Info info = RemoteFileManager.getInstance().getRecipe(s).getFilterInfo();
                System.out.println("TESTING: "+info.toString());
                if(infoMatchesCriteria(info)){
                    output[counter] = s;
                    counter++;
                }
            }catch (Exception e) {
                e.printStackTrace();
                //Log.d("Filter", "Error getting info from recipe: "+s);
            }
        }
        return output;
    }

    private Boolean infoMatchesCriteria(Info info){
        if (criteria.getSpicy()){
            if (!info.getSpicy()) {
                System.out.println("Failed at spicy");
                return false;
            }
        }
        System.out.println("Passed spicy");

        if (criteria.getLactose()){
            if (!info.getLactose()){
                System.out.println("Failed at lactose");
                return false;
            }
        }
        System.out.println("Passed lactose");

        if (criteria.getNuts()){
            if (!info.getNuts()){
                System.out.println("Failed at nuts");
                return false;
            }
        }
        System.out.println("Passed nuts");

        if (criteria.getVegetarian()){
            if (!info.getVegetarian()){
                System.out.println("Failed at vegetarian");
                return false;
            }
        }
        System.out.println("Passed vegetarian");

        if (criteria.getVegan()){
            if (!info.getVegan()){
                System.out.println("Failed at vegan");
                return false;
            }
        }
        System.out.println("Passed vegan");

        if (criteria.getGluten()){
            if (!info.getGluten()){
                System.out.println("Failed at gluten");
                return false;
            }
        }
        System.out.println("Passed gluten");

        return true;

    }

    public void setCriteria(Info criteria) {
        this.criteria = criteria;
    }

    public static class Info {

        private Boolean spicy;
        private Boolean lactose;
        private Boolean nuts;
        private Boolean vegetarian;
        private Boolean vegan;
        private Boolean gluten;


        public Info(Boolean spicy, Boolean lactose, Boolean nuts, Boolean vegetarian, Boolean vegan, Boolean gluten){

            this.setGluten(gluten);
            this.setLactose(lactose);
            this.setNuts(nuts);
            this.setSpicy(spicy);
            this.setVegan(vegan);
            this.setVegetarian(vegetarian);
        }

        public Info(){
            this.setGluten(false);
            this.setLactose(false);
            this.setNuts(false);
            this.setSpicy(false);
            this.setVegan(false);
            this.setVegetarian(false);
        }


        public Boolean getSpicy(){
            return spicy;
        }
        public void setSpicy(Boolean spicy){
            this.spicy = spicy;
        }

        public Boolean getLactose(){
            return lactose;
        }
        public void setLactose(Boolean lactose){
            this.lactose = lactose;
        }

        public Boolean getNuts(){
            return nuts;
        }
        public void setNuts(Boolean nuts){
            this.nuts = nuts;
        }

        public Boolean getVegetarian(){
            return vegetarian;
        }
        public void setVegetarian(Boolean vegetarian){
            this.vegetarian = vegetarian;
        }

        public Boolean getVegan(){
            return vegan;
        }
        public void setVegan(Boolean vegan){
            this.vegan = vegan;
        }

        public Boolean getGluten(){
            return gluten;
        }
        public void setGluten(Boolean gluten){
            this.gluten = gluten;
        }

        public String toString(){
            char[] temp = {'0', '0', '0', '0', '0', '0', '\0'};
            if(this.spicy){
                temp[0] = '1';
            }

            if(this.gluten){
                temp[1] = '1';
            }

            if(this.vegan){
                temp[2] = '1';
            }

            if(this.vegetarian){
                temp[3] = '1';
            }

            if(this.nuts){
                temp[4] = '1';
            }

            if(this.lactose){
                temp[5] = '1';
            }

            return new String(temp);
        }

    }


}
