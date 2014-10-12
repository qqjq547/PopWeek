package com.hjq.adapter;

import java.util.ArrayList;

import com.hjq.adapter.CommentAdapter.Content;
import com.hjq.model.Deal;
import com.hjq.model.MainChild;
import com.hjq.model.PoiItem;
import com.hjq.ui.MainActivity;
import com.hjq.util.ImageLoaderUtil;
import com.hjq.week.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DealAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
    private ArrayList<Deal> mItems;
    private Context mcontext;
	public DealAdapter(Context context,ArrayList<Deal> Items){
		mItems=Items;
		mcontext=context;
		mInflater=LayoutInflater.from(mcontext);
	}
	@Override
	public int getCount() { 
		return mItems.size();
	}

	@Override
	public Deal getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub	
		Content content=null;
		 if (convertView == null) {
             convertView = mInflater.inflate(R.layout.child, null);
             content = new Content();
             content.textTitle = (TextView) convertView
                     .findViewById(R.id.title);
             content.textDes = (TextView) convertView
                     .findViewById(R.id.des);
             content.textCount = (TextView) convertView
                     .findViewById(R.id.count);
             content.imageView = (ImageView) convertView
                     .findViewById(R.id.image);
             convertView.setTag(content);
            
         }else {
			content=(Content)convertView.getTag();
		}
		 content.textTitle.setText(mItems.get(position).getTitle());
		 content.textDes.setText(String.valueOf(mItems.get(position).getDescription()));
		 content.textCount.setText("￥"+String.valueOf(mItems.get(position).getCurrent_price()));
         ImageLoaderUtil.displayImage(mItems.get(position).getImage_url(), content.imageView, mcontext);
         return convertView;
	}
class Content {
    TextView textTitle;
    TextView textDes;
    TextView textCount;
    ImageView imageView;
}
}

