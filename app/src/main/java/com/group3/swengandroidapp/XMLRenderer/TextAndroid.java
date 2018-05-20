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
import android.widget.TextView;

/**
 * Created by Jack on 23/02/2018.
 */

public class TextAndroid extends Text {

    //BIU
//    private StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
//    private StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
//    private StyleSpan plainSpan = new StyleSpan(Typeface.NORMAL);
//    private UnderlineSpan underlineSpan = new UnderlineSpan();

    //Fonts
    private Handler mHandler = null;
    //private TypefaceSpan timeNewRoman = new TypefaceSpan("Times New Roman");



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
//    private Typeface getTimeNewRomanSpan(Activity activity) { return requestDownload("Times New Roman", activity);}
    private TypefaceSpan getTimeNewRomanSpan() { return new TypefaceSpan("monospace");}



    //Text size method spans
    //private RelativeSizeSpan getTextSizeSpan() { return new RelativeSizeSpan(); }

    @Override
    public void draw(Activity activity) {

        if (parent instanceof Slide) {
            LinearLayout layout = ((Slide) parent).getLayout();
            TextView textView = new TextView(activity);

            SpannableStringBuilder builder = new SpannableStringBuilder();
            buildString(builder, activity);

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

                // Remove white spaces
                for (int i = 1; i < builder.length(); i++) {
                    if (Character.isWhitespace(builder.charAt(i-1)) && Character.isWhitespace(builder.charAt(i))) {
                        builder.delete(i-1, i);
                    }
                }


            } else if (e instanceof Format) {
                ((Format)e).buildString(builder, activity);
            }

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



    //Requests a download from Google Fonts
    /*private Typeface requestDownload (String familyName, Activity activity) {
        int certs = 0;

        QueryBuilder queryBuilder = new QueryBuilder(familyName)
                .withWidth(0)
                .withWeight(0)
                .withItalic(0)
                .withBestEffort(false);
        String query = queryBuilder.build();

        Log.d("Requesting a font. Query: ", query);
        FontRequest request = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                query,
                certs);

        FontsContractCompat.FontRequestCallback callback = new FontsContractCompat
                .FontRequestCallback() {
            @Override
            public void onTypefaceRetrieved(Typeface typeface) {
                //return typeface;
            }

            @Override
            public void onTypefaceRequestFailed(int reason) {
                Log.d("TextAndroid: requestDownload: onTypeFaceRequestFailed: ", String.valueOf(reason));
            }
        };
        FontsContractCompat
                .requestFont(activity, request, callback,
                        getHandlerThreadHandler());

        return null;
    }

    private Handler getHandlerThreadHandler() {
        if (mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("fonts");
            handlerThread.start();
            mHandler = new Handler(handlerThread.getLooper());
        }
        return mHandler;
    }

    */
}
