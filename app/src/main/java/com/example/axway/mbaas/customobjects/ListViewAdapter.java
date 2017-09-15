/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.customobjects;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.axway.mbaas.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
	private Activity context;
	private ArrayList<String> title;

	public ListViewAdapter(Activity context, ArrayList<String> title) {
		super();
		this.context = context;
		this.title = title;
		
	}

	@Override
	public int getCount() {
		return title.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder {
		TextView textViewTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.customobjects_query_listitem_row, parent, false);
			holder = new ViewHolder();
			holder.textViewTitle = (TextView) convertView.findViewById(R.id.textView1);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textViewTitle.setText(title.get(position));
		
		return convertView;
	}

}