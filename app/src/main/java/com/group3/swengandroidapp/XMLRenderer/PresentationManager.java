package com.group3.swengandroidapp.XMLRenderer;

/**
 * Created by Jack on 11/02/2018.
 */

public class PresentationManager {
    private static final PresentationManager ourInstance = new PresentationManager();
    public static PresentationManager getInstance() {
        return ourInstance;
    }

    private static String xml;
    private static Presentation presentation;
    //private static List<Entry> xml = null;


    private PresentationManager() {

    }

    public void setXML(String xml){
        this.xml = xml;
    }

    public String getXML(){
        return xml;
    }

    public void setPresentation(Presentation presentation) { this.presentation = presentation; }

    public Presentation getPresentation() { return presentation; }

//    public void setXML(List<Entry> xml){
//        this.xml = xml;
//    }
//
//    public List<Entry> getXML(){
//        return xml;
//    }



}
