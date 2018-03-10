package com.group3.swengandroidapp.XMLRenderer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Jack on 10/03/2018.
 */

public class XmlRecipe extends Recipe {

    private XmlPullParser xpp;

    public XmlRecipe(String xmlFile) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        xpp = factory.newPullParser();

        xpp.setInput(new StringReader(xmlFile));

        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("Filters")) {
                    setSpicy(Boolean.getBoolean(xpp.getAttributeValue(null, "spicy")));
                    setLactose(Boolean.getBoolean(xpp.getAttributeValue(null, "lactose")));
                    setNuts(Boolean.getBoolean(xpp.getAttributeValue(null, "nuts")));
                    setVegetarian(Boolean.getBoolean(xpp.getAttributeValue(null, "vegetarian")));
                    setVegan(Boolean.getBoolean(xpp.getAttributeValue(null, "vegan")));
                    setGluten(Boolean.getBoolean(xpp.getAttributeValue(null, "gluten")));
                }
                else if (xpp.getName().equals("Ingredients")) {
                    String name = "";
                    String quantity = "";

                    quantity += xpp.getAttributeValue(null, "amount");
                    quantity += xpp.getAttributeValue(null, "units");

                    xpp.next();

                    name = xpp.getText();

                    new Ingredient(name, quantity);
                }

            }
            else if (eventType == XmlPullParser.TEXT) {
                System.out.println("Text " + xpp.getText());
            }
            eventType = xpp.next();
        }
    }
}
