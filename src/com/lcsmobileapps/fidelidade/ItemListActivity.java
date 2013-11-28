package com.lcsmobileapps.fidelidade;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBarActivity;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.lcsmobileapps.fidelidade.helper.ImageHelper;

/**
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ItemDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details (if present) is a
 * {@link ItemDetailFragment}.
 * <p>
 * This activity also implements the required {@link ItemListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class ItemListActivity extends ActionBarActivity implements
		ItemListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
		final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
		
		int cacheSize;
		
			cacheSize = maxMemory / 8;
		
		ImageHelper.mMemoryCache = new LruCache<String, Bitmap>(cacheSize);
		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ItemListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);
		}
		((ItemListFragment) getSupportFragmentManager().findFragmentById(
				R.id.item_list)).getView().setBackgroundResource(R.drawable.background);
		AdRequest adRequest = new AdRequest();
			adRequest.addTestDevice("5A873CD5069A96C1FCBBEB66EB7CBC5A");
//			adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
			AdView adView = (AdView)findViewById(R.id.ad);
			
			String locationProvider = LocationManager.NETWORK_PROVIDER;
			LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
			Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
			adRequest.setLocation(lastKnownLocation);
			adView.loadAd(adRequest);
		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link ItemListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(int id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putInt(ItemDetailFragment.ARG_ITEM_ID, id);
			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ItemDetailActivity.class);
			detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	  @Override
	  public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	  }
}
