package com.digitalrupay.customviews.fonts;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalrupay.R;

import java.util.HashMap;


public class AppCustomFontLoader {

    public static void loadFont(View view, AttributeSet attrs) {
        String fontFamily = null;
        String fontFamilyType = null;
        int fontStyle = 0;
        if (view instanceof TextView) {
            TextView textview = (TextView) view;
            if (textview.getTypeface() != null) {
                fontStyle = textview.getTypeface().getStyle();
            }
            TypedArray a = textview.getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;

            if (editText.getTypeface() != null) {
                fontStyle = editText.getTypeface().getStyle();
            }
        }
        TypedArray a = view.getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTextView_fontFamily:
                    fontFamily = a.getString(attr);
                    break;
                case R.styleable.CustomTextView_fontFamilyType:
                    fontFamilyType = a.getString(attr);
                    break;
            }
        }
        a.recycle();
        setTypeFace(view, fontFamily, fontFamilyType);
    }

    public static HashMap<String, OverwatchTypeface> types = new HashMap<String, OverwatchTypeface>();

    private static final class OverwatchTypeface {
        public OverwatchTypeface(Typeface face) {
            this.typeface = face;
        }

        public Typeface typeface;
        public int style;
    }

    private static String getTypefaceHashName(String name) {
        return name;
    }

    public static void setTypeFace(View view, String fontFamily, String fontFamilyType) {
        String key = getTypefaceHashName(fontFamily);
        if (types.containsKey(key)) {
            OverwatchTypeface face = types.get(key);
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(face.typeface);
            } else if (view instanceof EditText) {
                ((EditText) view).setTypeface(face.typeface);
            }

            return;
        }
        if (fontFamilyType == null || fontFamilyType.trim().length() == 0) {
            fontFamilyType = "otf";
        }
        Typeface type = Typeface.create((String) null, Typeface.NORMAL);
        type = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/" + fontFamily + "." + fontFamilyType);
        types.put(key, new OverwatchTypeface(type));
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(type);
        } else if (view instanceof EditText) {
            ((EditText) view).setTypeface(type);
        }
    }
}
