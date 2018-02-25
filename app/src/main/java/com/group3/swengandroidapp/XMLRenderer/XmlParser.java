package com.group3.swengandroidapp.XMLRenderer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Jack on 15/02/2018.
 */

public class XmlParser {
    private XmlPullParser xpp;


    public XmlParser(String xmlFile) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        xpp = factory.newPullParser();

        xpp.setInput(new StringReader(xmlFile));

        xpp.next();
    }

    public Presentation parse() throws XmlPullParserException, IOException {
        return (Presentation)parse(null);
    }

    private XmlElement parse(XmlElement parent) throws XmlPullParserException, IOException {
        xpp.require(XmlPullParser.START_TAG, null, null);

        String name = xpp.getName();
        int depth = xpp.getDepth();

        XmlElement element = createFromName(name, parent);
        if (element == null) {
            consumeUnknownTag();
            return null;
        }
        parseAttributesInto(element);

        while (!(xpp.next() == XmlPullParser.END_TAG && xpp.getName() == name && xpp.getDepth() == depth)) {
            switch (xpp.getEventType()) {
                case XmlPullParser.TEXT:
                    element.addChild(new RawText(element, xpp.getText()));
                    break;
                case XmlPullParser.START_TAG:
                    element.addChild(parse(element));
                    break;
                case XmlPullParser.END_DOCUMENT:
                    return element;
                    //break;
            }
        }

        return element;
    }

    private XmlElement createFromName(String name, XmlElement parent) {
        switch (name.toLowerCase()) {
            case "presentation":
                return new Presentation(/* should be top level, no parent */);
            case "meta":
                return new Meta(parent);
            case "slide":
                return new Slide(parent);
            case "image":
                return new Image(parent);
            case "text":
                return new TextAndroid(parent);
            case "format":
                return new Format(parent);
            case "shape":
                return new Shape(parent);
            case "video":
                return new Video(parent);
            case "audio":
                return new Audio(parent);
        }
        return null;
    }

    private void parseAttributesInto(XmlElement element) {
        int count = xpp.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String name = xpp.getAttributeName(i);
            String value = xpp.getAttributeValue(i);
            element.setProperty(name, value);
        }
    }

    public void consumeUnknownTag() throws XmlPullParserException, IOException {
        xpp.require(XmlPullParser.START_TAG, null, null);
        String name = xpp.getName();
        int depth = xpp.getDepth();
        while (!(xpp.next() == XmlPullParser.END_TAG && xpp.getName() == name && xpp.getDepth() == depth));
    }
}
