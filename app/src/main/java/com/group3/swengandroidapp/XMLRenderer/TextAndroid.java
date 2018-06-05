package com.group3.swengandroidapp.XMLRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * Text class for drawing in the presentation for Android
 */

public class TextAndroid extends Text {

    private TextView textView;

    //Fonts
    private Handler mHandler = null;

    //Constuctor
    public TextAndroid(XmlElement parent) {
        super(parent);
    }

    //BIU method spans
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

    //Font method spans
    private TypefaceSpan getTimeNewRomanSpan() { return new TypefaceSpan("monospace");}


    public TextView getTextView() {
        return textView;
    }

    @Override
    public void draw(Activity activity) {

        if (parent instanceof Slide) {
            LinearLayout layout = ((Slide) parent).getLayout();
            textView = new TextView(activity);

            SpannableStringBuilder builder = new SpannableStringBuilder();
            buildString(builder, activity);


            if (getX1() != null && getX2() != null && getY1() != null && getY2() != null) {
                int x1 = Integer.valueOf(getX1());
                int x2 = Integer.valueOf(getX2());
                int y1 = Integer.valueOf(getY1());
                int y2 = Integer.valueOf(getY2());


                LayoutParams layoutParams=new LayoutParams(x2, y2);
                layoutParams.setMargins(x1, y1, x2, y2);
                textView.setLayoutParams(layoutParams);
            }
            else {
                Log.d("Draw", "No parameters set. Loading default text position");
            }

            textView.setText(builder);
            layout.addView(textView);
        }

    }

    @Override
    public void buildString(SpannableStringBuilder builder, Activity activity) {

        for (XmlElement e : children) {

            if (e instanceof RawText) {
                int start = builder.length();

                RawText t = (RawText) e;

                Log.d("TextAndroid: buildString: Text", t.getText());

                Log.d("TextAndroid: buildString: Bold", String.valueOf(getBold().equals("true")));
                Log.d("TextAndroid: buildString: Italic", String.valueOf(getItalic().equals("true")));
                Log.d("TextAndroid: buildString: Underline", String.valueOf(getUnderline().equals("true")));

                builder.append(t.getText());

                builderBUI(builder, start);
                builderFont(builder, start);
                builderSize(builder, start);
                builderColor(builder, start);
            } else if (e instanceof Br) {
                builder.append("\n");

            } else if (e instanceof Format) {
                ((Format)e).buildString(builder, activity);
            }

        }

        // Remove leading and trailing white spaces
        while (builder.length() > 0 && Character.isWhitespace(builder.charAt(0))) {
            builder.delete(0, 1);
        }
        while (builder.length() > 0 && Character.isWhitespace(builder.charAt(builder.length()-1))) {
            builder.delete(builder.length()-1, builder.length());
        }
    }

    //Sets the Bold, Underline, Italic settings
    private void builderBUI (SpannableStringBuilder builder, int start) {

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

    //Sets font
    private void builderFont (SpannableStringBuilder builder, int start) {

        if (getFont().equals("Times New Roman")) {
            builder.setSpan(getTimeNewRomanSpan(),start,builder.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }

    //Sets text size
    private void builderSize (SpannableStringBuilder builder, int start) {

        Log.d("TextAndroid: builderSize", getTextSize());
        builder.setSpan(new RelativeSizeSpan(Float.valueOf(getTextSize())/10),start,builder.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);


    }

    //Sets text colour
    private void builderColor (SpannableStringBuilder builder, int start) {

        Log.d("TextAndroid: builderColor", getColor());
        builder.setSpan(new ForegroundColorSpan(Color.parseColor(getColor())),start,builder.length(),SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

}
