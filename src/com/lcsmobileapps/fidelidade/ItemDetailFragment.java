package com.lcsmobileapps.fidelidade;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static com.lcsmobileapps.fidelidade.adapter.VideosListAdapter.KEY;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {


			position = getArguments().getInt(ARG_ITEM_ID);
			YouTubePlayerSupportFragment youTubePlayerFragment =
					(YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
			youTubePlayerFragment.initialize(KEY, this);
		}
	}
	private int position;
	
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
	    if (!wasRestored) {
	      player.cueVideo(getActivity().getResources().getStringArray(R.array.youtube_list)[position]);
//	      player.setFullscreen(true);
	    }
	   
	  }
	  
}
