package com.wb.citylife.adapter;

import com.android.volley.toolbox.NetworkImageView;
import com.wb.citylife.R;
import com.wb.citylife.app.CityLifeApp;
import com.wb.citylife.bean.MyCollect;
import com.wb.citylife.bean.MyCollect.CollectItem;
import com.wb.citylife.bean.NewsList;
import com.wb.citylife.bean.NewsList.NewsItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectListAdapter extends BaseAdapter {
	
	private Context mContext;
	private MyCollect mCollect;
	
	public CollectListAdapter(Context context, MyCollect mCollect) {
		mContext = context;
		this.mCollect = mCollect;
	}

	@Override
	public int getCount() {
		return mCollect.datas.size();
	}

	@Override
	public Object getItem(int position) {
		return mCollect.datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder holder;
		if(convertView == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.text_item_layout, null);
			holder = new ViewHolder();
			holder.titleTv = (TextView) view.findViewById(R.id.title);
			holder.descTv = (TextView) view.findViewById(R.id.desc);
			holder.tagTv = (TextView) view.findViewById(R.id.tag);
			holder.tag2Tv = (TextView) view.findViewById(R.id.tag2);
			view.setTag(holder);
			
			holder.tagTv.setVisibility(View.GONE);
			holder.tag2Tv.setVisibility(View.GONE);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		CollectItem cItem = mCollect.datas.get(position);	
		holder.titleTv.setText(cItem.title);
		holder.descTv.setText(cItem.desc);
				
		if(position % 2 == 0) {
			view.setBackgroundColor(0xfffafafa);
		} else {
			view.setBackgroundColor(0xffffffff);
		}
		
		return view;
	}
		
	public class ViewHolder {
		NetworkImageView thumbIv;
		ImageView videoIv;
		TextView titleTv;
		TextView descTv;
		TextView tagTv;
		TextView tag2Tv;
	}	
}
