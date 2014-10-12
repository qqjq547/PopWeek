package com.hjq.adapter;

import java.util.ArrayList;

import com.hjq.model.Comment;
import com.hjq.model.Deal;
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

public class CommentAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
    private ArrayList<Comment> mItems;
    private Context mcontext;
    private boolean islist;
	public CommentAdapter(Context context,ArrayList<Comment> Items,boolean islist){
		mItems=Items;
		mcontext=context;
		mInflater=LayoutInflater.from(mcontext);
		this.islist=islist;
	}
	@Override
	public int getCount() { 
		return mItems.size();
	}

	@Override
	public Comment getItem(int position) {
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
		Content content = null;
		 if (convertView == null) {
			 if (islist) {
				 convertView = mInflater.inflate(R.layout.comment_item1, null);
			}else {
				convertView = mInflater.inflate(R.layout.comment_item2, null);
			}
             
			 content = new Content();
             content.textname = (TextView) convertView
                     .findViewById(R.id.name);
             content.textauthor = (TextView) convertView
                     .findViewById(R.id.author);
             content.textaverage = (TextView) convertView
                     .findViewById(R.id.average);
             content.imageView = (ImageView) convertView
                     .findViewById(R.id.image);
             convertView.setTag(content);
         }else {
        	 content = (Content)convertView.getTag();
		}
         content.textname.setText(mItems.get(position).getName());
 		 content.textauthor.setText(mItems.get(position).getAuthor());
 		 content.textaverage.setText(mItems.get(position).getAverage());
         ImageLoaderUtil.displayImage(mItems.get(position).getImage(), content.imageView, mcontext);
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
class Content {
    TextView textname;
    TextView textauthor;
    TextView textaverage;
    ImageView imageView;
}
}

