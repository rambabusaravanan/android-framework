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

    private final static String KEY = "FONT_AWESOME";
    private final static String PATH = "font/fontawesome_webfont.ttf";
    private static LruCache<String, Typeface> cache = new LruCache<>(12);

    public TextAwesome(Context context) {
        super(context);
        init();
    }

    public TextAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        Typeface typeface = cache.get(KEY);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(getContext().getAssets(), PATH);
            cache.put(KEY, typeface);
        }
        setTypeface(typeface);
    }

}
