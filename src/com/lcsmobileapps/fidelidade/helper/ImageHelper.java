package com.lcsmobileapps.fidelidade.helper;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageHelper {
	
	
	
	public static LruCache<String, Bitmap> mMemoryCache;
		
	public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	    	
	        mMemoryCache.put(key, bitmap);
	    }
	}

	public static Bitmap getBitmapFromMemCache(String key) {
		
	    return mMemoryCache.get(key);
	}
	
}

