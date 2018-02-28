package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jack on 23/02/2018.
 */

public class TextAndroid extends Text {

    private StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
    private StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
    private StyleSpan plainSpan = new StyleSpan(Typeface.NORMAL);
    private UnderlineSpan underlineSpan = new UnderlineSpan();

    public TextAndroid(XmlElement parent) {
        super(parent);

    }

    private UnderlineSpan getUnderlineSpan() {
        return new UnderlineSpan();
    }

    private StyleSpan getBoldSpan() {
        return new StyleSpan(Typeface.BOLD);
    }

    private StyleSpan getItalicSpan() {
        return new StyleSpan(Typeface.ITALIC);
    }

    private StyleSpan getPlainSpan() {
        return new StyleSpan(Typeface.NORMAL);
    }

    @Override
    public void draw(Activity activity) {

        if (parent instanceof Slide) {
            LinearLayout layout = ((Slide) parent).getLayout();
            TextView textView = new TextView(activity);

            SpannableStringBuilder builder = new SpannableStringBuilder();
            buildString(builder);

            textView.setText(builder);

            layout.addView(textView);
        }

    }

    @Override
    public void buildString(SpannableStringBuilder builder) {

        for (XmlElement e : children) {

            if (e instanceof RawText) {
                int start = builder.length();

                RawText t = (RawText) e;

                Log.d("TextAndroid: buildString: Text", t.getText());

                Log.d("TextAndroid: buildString: Bold", String.valueOf(getBold().equals("true")));
                Log.d("TextAndroid: buildString: Italic", String.valueOf(getItalic().equals("true")));
                Log.d("TextAndroid: buildString: Underline", String.valueOf(getUnderline().equals("true")));

                builder.append(t.getText());

                buildBUI(builder, start);
                buildFont(builder);

            } else if (e instanceof Format) {
                ((Format)e).buildString(builder);
            }

        }
    }

    // sets the Bold, Underline, Italic settings
    private void buildBUI (SpannableStringBuilder builder, int start) {

        if (getBold().equals("true") && getItalic().equals("true") && getUnderline().equals("true")) {
            builder.setSpan(getBoldSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(getItalicSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(getUnderlineSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        else if (getUnderline().equals("true") && getBold().equals("true")) {
            builder.setSpan(getBoldSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(getUnderlineSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else if (getUnderline().equals("true") && getItalic().equals("true")) {
            builder.setSpan(getItalicSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(getUnderlineSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else if (getBold().equals("true") && getItalic().equals("true")) {
            builder.setSpan(getBoldSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(getItalicSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else if (getBold().equals("true")) {
            builder.setSpan(getBoldSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else if (getItalic().equals("true")) {
            builder.setSpan(getItalicSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else if (getUnderline().equals("true")) {
            builder.setSpan(getUnderlineSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else {
            builder.setSpan(getPlainSpan(),start,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }

    //set Fonts
    private void buildFont (SpannableStringBuilder builder) {



    }

}
