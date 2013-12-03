package com.lcsmobileapps.fidelidade;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.GoogleAuthUtil;
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
	private static boolean mTwoPane;

	public static boolean isTwoPane() {
		
		return mTwoPane;
	}
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
		ListFragment listFragment = (ListFragment)getSupportFragmentManager().findFragmentById(
				R.id.item_list);
		ImageHelper.loadImage(listFragment.getListView(), R.drawable.background, this);
		listFragment.getListView().setBackgroundColor(Color.TRANSPARENT);
		
		
		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
		adRequestBuilder.addTestDevice("5A873CD5069A96C1FCBBEB66EB7CBC5A");
//			adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
			AdView adView = (AdView)findViewById(R.id.ad);
			
			
			if(!mTwoPane)
				adView.loadAd(adRequestBuilder.build());
	
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setNegativeButton(getString(android.R.string.cancel), new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
					
				}
			});
			String[] accounts = getAccountNames();
			builder.setAdapter(new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, android.R.id.text1,accounts) ,null);
			builder.show();
	}
	private String[] getAccountNames() {
		AccountManager accountManager = AccountManager.get(this);
	    Account[] accounts = accountManager.getAccountsByType(
	            GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
	    String[] names = new String[accounts.length];
	    for (int i = 0; i < names.length; i++) {
	        names[i] = accounts[i].name;
	    }
	    return names;
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
			Fragment oldFragment = getSupportFragmentManager().findFragmentByTag("DetailFragment");
			
			if (oldFragment!=null) {

				((ItemDetailFragment)oldFragment).changeVideo(id);
			}
			else {
				ItemDetailFragment fragment = new ItemDetailFragment();
				
				fragment.setArguments(arguments);
				getSupportFragmentManager().beginTransaction()
						.add(R.id.item_detail_container, fragment,"DetailFragment").commit();
			}

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
