package com.rambabusaravanan.android.framework.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Andro Babu on Oct 03, 2015.
 */
public class TextAwesome extends TextView {

    private final static String NAME = "FONT_AWESOME";
    private static LruCache<String, Typeface> typefaceCache = new LruCache<String, Typeface>(12);

    public TextAwesome(Context context) {
        super(context);
        init();
    }

    public TextAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        Typeface typeface = typefaceCache.get(NAME);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(getContext().getAssets(), "font/fontawesome_webfont.ttf");
            typefaceCache.put(NAME, typeface);
        }
        setTypeface(typeface);
    }

}
