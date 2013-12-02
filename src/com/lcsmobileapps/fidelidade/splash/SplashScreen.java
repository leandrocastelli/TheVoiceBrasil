package com.lcsmobileapps.fidelidade.splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lcsmobileapps.fidelidade.ItemListActivity;
import com.lcsmobileapps.fidelidade.R;
import com.lcsmobileapps.fidelidade.helper.ImageHelper;



/**
 * SplashScreen give sometime to Zeus load his resources, connect wifi and
 * receive data from PRI
 */
public class SplashScreen extends Activity implements Runnable {
	private final int DELAY = 2000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
		
		int cacheSize;
		
			cacheSize = maxMemory / 8;
		
		ImageHelper.mMemoryCache = new LruCache<String, Bitmap>(cacheSize);
		setContentView(R.layout.splash_screen);
		ImageView imageView = (ImageView)findViewById(R.id.splash);
		ImageHelper.loadImage(imageView, R.drawable.background, this);
		Handler h = new Handler();
		h.postDelayed(this, DELAY);
		
	}

	@Override
	public void run() {
		startActivity(new Intent(this, ItemListActivity.class));
		this.finish();
	}

}
