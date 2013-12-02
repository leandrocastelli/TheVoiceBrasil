package com.lcsmobileapps.fidelidade.helper;

import java.io.IOException;
import java.lang.ref.WeakReference;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

public class ImageHelper extends AsyncTask<Integer,Void,Bitmap>{
	private final WeakReference<View> listViewReference;
	private int data = 0;
	private Context ctx;
	public static LruCache<String, Bitmap> mMemoryCache;
	public ImageHelper(View listView, Context ctx) {
		// Use a WeakReference to ensure the ImageView can be garbage collected
		listViewReference = new WeakReference<View>(listView);
		this.ctx = ctx;
	}
	

	// Decode image in background.
	@Override
	protected Bitmap doInBackground(Integer... params) {
		data = params[0];
		

		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int width;
		int heigth;
		if (android.os.Build.VERSION.SDK_INT >=13) {
			Point size =new Point();
			display.getSize(size);
			width = size.x;
			heigth = size.y;
		}
		else{
			width = display.getWidth();
			heigth = display.getHeight();
		}
		if (params.length > 1)
			width = width <<1;
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(ctx.getResources(), data, options);

		options.inSampleSize = calculateInSampleSize(options, width, heigth);

		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), data,options);
		if(params.length ==1)
			addBitmapToMemoryCache(String.valueOf(data), bitmap);
		return bitmap;
	}

	// Once complete, see if ImageView is still around and set bitmap.
	@SuppressWarnings("deprecation")
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		
		if (listViewReference != null && bitmap != null) {
			final View view = listViewReference.get();
			if (view instanceof ListView) {
				
				if (android.os.Build.VERSION.SDK_INT >=16) {
					view.setBackground(new BitmapDrawable(ctx.getResources(), bitmap));
				}
				else {
					view.setBackgroundDrawable(new BitmapDrawable(ctx.getResources(), bitmap));
				}
			}
			else {
				ImageView imageView = (ImageView)view;
				imageView.setImageBitmap(bitmap);
			}

		}
		
		
	}

	public static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
	
		
	public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	    	
	        mMemoryCache.put(key, bitmap);
	    }
	}

	public static Bitmap getBitmapFromMemCache(String key) {
		
	    return mMemoryCache.get(key);
	}
	
	@SuppressWarnings("deprecation")
	public static void loadImage(View view, int id, Context ctx) {
		final String imageKey = String.valueOf(id);
		
		
		ImageHelper imageHelper = new ImageHelper(view, ctx);
		final Bitmap bitmap = imageHelper.getBitmapFromMemCache(imageKey);
		
		if (bitmap != null) {
			if (view instanceof ListView) {
				
				if (android.os.Build.VERSION.SDK_INT >=16) {
					view.setBackground(new BitmapDrawable(ctx.getResources(), bitmap));
				}
				else {
					view.setBackgroundDrawable(new BitmapDrawable(ctx.getResources(), bitmap));
				}
			}
			else {
				ImageView imageView = (ImageView)view;
				imageView.setImageBitmap(bitmap);
			}
	    }
		else {
			imageHelper.execute(id);
		}
		
	}
	
}

