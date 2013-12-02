package com.lcsmobileapps.fidelidade.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.lcsmobileapps.fidelidade.R;
import com.lcsmobileapps.fidelidade.helper.ImageHelper;

public class VideosListAdapter extends BaseAdapter implements YouTubeThumbnailView.OnInitializedListener,YouTubeThumbnailLoader.OnThumbnailLoadedListener{

	private Context ctx;
	public final static String KEY = "AIzaSyCwKikt42UWCwSLAjiXqTFCo_DXhHVmaic";
	private class ViewHolder {
		YouTubeThumbnailView imageView;
		TextView txtName;
		
	}
	List<YouTubeThumbnailView> listThumbnail = new ArrayList<YouTubeThumbnailView>();
	public VideosListAdapter (Context ctx) {
		super();
		this.ctx = ctx;
		
		
		
	}
	@Override
	public int getCount() {

		return ((Activity)ctx).getResources().getStringArray(R.array.youtube_list).length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getItemPosition (YouTubeThumbnailView currentThumbnail) {
		
		return listThumbnail.indexOf(currentThumbnail);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
//		if(convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
//			holder.txtName = (TextView)convertView.findViewById(R.id.video_title);
			
			holder.imageView = (YouTubeThumbnailView) convertView.findViewById(R.id.thumbnail_frame);
			holder.imageView.setDrawingCacheEnabled(true);
		
			convertView.setTag(holder);
//		}
//		else {
//			
//			holder = (ViewHolder) convertView.getTag();
//			
//		}
		
		Bitmap bitmap = ImageHelper.getBitmapFromMemCache(((Activity)ctx).getResources().getStringArray(R.array.youtube_list)[position]);
		
		if (bitmap != null) {
			
			holder.imageView.setImageBitmap(bitmap);
			
		}
		else {
			holder.imageView.initialize("AIzaSyCwKikt42UWCwSLAjiXqTFCo_DXhHVmaic", this);
			holder.imageView.setId(position+1);
		}

		
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
//		int currentPosition = getItemPosition(thumbnailView);
		thumbnailLoader.setOnThumbnailLoadedListener(this);
		thumbnailLoader.setVideo(((Activity)ctx).getResources().getStringArray(R.array.youtube_list)[thumbnailView.getId()-1]);
		
		
	}
	@Override
	public void onThumbnailError(YouTubeThumbnailView thumbnailView, ErrorReason arg1) {
		
		thumbnailView.initialize(KEY, this);
		
	}
	@Override
	public void onThumbnailLoaded(YouTubeThumbnailView thumbnailView, String videoId) {
		Bitmap bitmap = thumbnailView.getDrawingCache();
		
		
		ImageHelper.addBitmapToMemoryCache(videoId, Bitmap.createBitmap(bitmap));
		
	}
	
	

}
