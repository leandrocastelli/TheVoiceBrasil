package com.lcsmobileapps.tvb.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.lcsmobileapps.tvb.R;

public class VideosListAdapter extends BaseAdapter implements YouTubeThumbnailView.OnInitializedListener{

	private Context ctx;
	
	private class ViewHolder {
		YouTubeThumbnailView imageView;
		TextView txtName;
		
	}
	public VideosListAdapter (Context ctx) {
		super();
		this.ctx = ctx;
		
		
	}
	@Override
	public int getCount() {
//		return ninja.getNumSounds();
		return 5;
	}

	@Override
	public Object getItem(int position) {
//		return ninja.getSound()+position;
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.txtName = (TextView)convertView.findViewById(R.id.video_title);
			
			holder.imageView = (YouTubeThumbnailView) convertView.findViewById(R.id.thumbnail_frame);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
	//	ImageHelper.loadImage(holder.imageView, ninja.getIcon(), ctx);
		holder.imageView.initialize("AIzaSyCwKikt42UWCwSLAjiXqTFCo_DXhHVmaic", this);
		
		holder.txtName.setText("Testando "+position);
		
		return convertView;
	}
	@Override
	public void onInitializationFailure(YouTubeThumbnailView arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onInitializationSuccess(YouTubeThumbnailView thumbnailView,
		      YouTubeThumbnailLoader thumbnailLoader) {
		thumbnailLoader.setVideo("hYcPcEM8FyY");
		
	}
	
	

}
