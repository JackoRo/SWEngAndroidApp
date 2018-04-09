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
                    setSpicy(Boolean.valueOf(xpp.getAttributeValue(null, "spicy")));
                    setLactose(Boolean.valueOf(xpp.getAttributeValue(null, "lactose")));
                    setNuts(Boolean.valueOf(xpp.getAttributeValue(null, "nuts")));
                    setVegetarian(Boolean.valueOf(xpp.getAttributeValue(null, "vegetarian")));
                    setVegan(Boolean.valueOf(xpp.getAttributeValue(null, "vegan")));
                    setGluten(Boolean.valueOf(xpp.getAttributeValue(null, "gluten")));
                }
                else if (xpp.getName().equals("Ingredient")) {
                    String name = "";
                    String quantity = "";

                    quantity += xpp.getAttributeValue(null, "amount");
                    quantity += xpp.getAttributeValue(null, "units");

                    xpp.next();

                    name = xpp.getText();

                    appendIngredient(new Ingredient(name, quantity));
                }
                else if (xpp.getName().equals("id")) {
                    xpp.next();
                    setID(xpp.getText());
                }
                else if (xpp.getName().equals("title")) {
                    xpp.next();
                    setTitle(xpp.getText());
                }
                else if (xpp.getName().equals("author")) {
                    xpp.next();
                    setAuthor(xpp.getText());
                }
                else if (xpp.getName().equals("description")) {
                    xpp.next();
                    setDescription(xpp.getText());
                }
                else if (xpp.getName().equals("thumbnailURL")) {
                    xpp.next();
                    setThumbnail(xpp.getText());
                }

            }
            //else if
            eventType = xpp.next();
        }

    }
}
