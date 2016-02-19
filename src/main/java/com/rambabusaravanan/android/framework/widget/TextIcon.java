package com.rambabusaravanan.android.framework.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Andro Babu on Dec 12, 2015.
 */
public class TextIcon extends TextView {

    private static LruCache<String, Typeface> cache = new LruCache<>(12);
    private static String key = "FONT_MATERIAL";
    private static String path = "font/MaterialIcons-Regular.ttf";

    public TextIcon(Context context) {
        super(context);
        this.init();
    }

    public TextIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public void init() {
        Typeface typeface = cache.get(key);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(this.getContext().getAssets(), path);
            cache.put(key, typeface);
        }

        this.setTypeface(typeface);
    }
}
