package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jack on 23/02/2018.
 */

public class TextAndroid extends Text {

    public TextAndroid(XmlElement parent) {
        super(parent);

    }

    @Override
    public void draw(Activity activity) {
        TextView textView = new TextView(activity);

        // Probably make a TextView and add it to the parent view or whatever
        LinearLayout linearLayout = new LinearLayout(activity);
        activity.setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        buildString(builder);

        String str = builder.toString();


        //for (Map.Entry<String, String> e : properties.entrySet()) {
           // Log.d("TextAndroid", String.format("%s : %s", e.getKey(), e.getValue()));
        //}

        textView.setText(str);
//
//        SpannableStringBuilder ssb = new SpannableStringBuilder();
//        ssb.append(getProperties());
//        StyleSpan boldSpan = new StyleSpan(Typeface.ITALIC);
//        StyleSpan plainSpan = new StyleSpan(Typeface.NORMAL);
//        ssb.setSpan(plainSpan, 1, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ssb.setSpan(boldSpan, 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        textView.setText(ssb);
        linearLayout.addView(textView);

    }

    @Override
    public void buildString(SpannableStringBuilder builder) {
        for (XmlElement e : children) {
            if (e instanceof RawText) {
                RawText t = (RawText)e;
                Log.d("TextAndroid",t.getText());
                builder.append(t.getText());
            } else if (e instanceof Format) {
                ((Format)e).buildString(builder);
            }
        }
    }
}
