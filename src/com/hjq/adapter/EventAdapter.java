package com.hjq.adapter;

import java.util.ArrayList;

import com.hjq.model.Comment;
import com.hjq.model.Deal;
import com.hjq.model.Event;
import com.hjq.model.MainChild;
import com.hjq.model.PoiItem;
import com.hjq.ui.MainActivity;
import com.hjq.ui.WebActivity;
import com.hjq.util.ImageLoaderUtil;
import com.hjq.week.R;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
    private ArrayList<Event> mItems;
    private Context mcontext;
	public EventAdapter(Context context,ArrayList<Event> Items){
		mItems=Items;
		mcontext=context;
		mInflater=LayoutInflater.from(mcontext);
	}
	@Override
	public int getCount() { 
		return mItems.size();
	}

	@Override
	public Event getItem(int position) {
		// TODO Auto-generated method stub
		Log.i("hjq", "getItem:"+position+" : "+mItems.get(position).getName() );
		return mItems.get(position);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		 if (convertView == null) {
		     convertView = mInflater.inflate(R.layout.event_item, null);
		     holder = new ViewHolder();
		     holder.textname = (TextView) convertView
                     .findViewById(R.id.name);
		     holder.textauthor = (TextView) convertView
                     .findViewById(R.id.author);
		     holder.textcount = (TextView) convertView
                     .findViewById(R.id.average);
		     holder.textCategory = (TextView) convertView
                     .findViewById(R.id.category);
		     holder.textTime = (TextView) convertView
                     .findViewById(R.id.begin_time);
		     holder.textloction = (TextView) convertView
                     .findViewById(R.id.location);
		     holder.imageView = (ImageView) convertView
                     .findViewById(R.id.image);
             convertView.setTag(holder);
         }else {
        	 holder = (ViewHolder)convertView.getTag();
		}
		 holder.textname.setText(mItems.get(position).getName());
		 holder.textauthor.setText(mItems.get(position).getAuthor());
		 holder.textcount.setText(mItems.get(position).getCount());
		 holder.textCategory.setText(mItems.get(position).getCategory_name());
		 holder.textTime.setText(mItems.get(position).getBegin_time());
		 holder.textloction.setText(mItems.get(position).getLoc_name());
         ImageLoaderUtil.displayImage(mItems.get(position).getImage(), holder.imageView, mcontext);
		 convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		    	String title= mItems.get(position).getName();
				String url=mItems.get(position).getLink();
				Intent mIntent=new Intent(mcontext, WebActivity.class);
		        mIntent.putExtra("title", title);
		        mIntent.putExtra("url", url);
		        mcontext.startActivity(mIntent);
			}
		});
		 
         return convertView;
	}
class ViewHolder {
    TextView textname;
    TextView textauthor;
    TextView textcount;
    TextView textCategory;
    TextView textTime;
    TextView textloction;
    ImageView imageView;
}
}

