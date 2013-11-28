package com.lcsmobileapps.fidelidade;

import static com.lcsmobileapps.fidelidade.adapter.VideosListAdapter.KEY;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;



/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment implements OnInitializedListener  {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemDetailFragment() {
	}
	private YouTubePlayer instancePlayer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {


			position = getArguments().getInt(ARG_ITEM_ID);
			YouTubePlayerSupportFragment youTubePlayerFragment =
					(YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
			youTubePlayerFragment.initialize(KEY, this);
		      AdRequest adRequest = new AdRequest();
				adRequest.addTestDevice("5A873CD5069A96C1FCBBEB66EB7CBC5A");
//				adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
				AdView adView = (AdView)getActivity().findViewById(R.id.ad_video);
				
				String locationProvider = LocationManager.NETWORK_PROVIDER;
				LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
				Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
				adRequest.setLocation(lastKnownLocation);
				adView.loadAd(adRequest);
			
		}
	}
	private int position;
	
	@Override
	public void onResume() {
		super.onResume();
		if (instancePlayer!=null && !instancePlayer.isPlaying()) {
		
			
//			instancePlayer.seekToMillis(instancePlayer.getCurrentTimeMillis());
		}
	}
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		
//		
//		
//		
//		View view = (View)inflater.inflate(R.layout.fragment_item_detail, container,false);
//		
//		    return view;
//	}	
	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		
	}

	  @Override
	  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
	      boolean wasRestored) {
		  instancePlayer = player;
	    if (!wasRestored) {
	      player.cueVideo(getActivity().getResources().getStringArray(R.array.youtube_list)[position]);
	      

//	      player.setFullscreen(true);
	    }
	   
	    
	   
	  }
	  
}
