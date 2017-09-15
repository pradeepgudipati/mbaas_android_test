/**
 * Axway Platform SDK
 * Copyright (c) 2017 by Axway, Inc. All Rights Reserved.
 * Proprietary and Confidential - This source code is not for redistribution
 */

package com.example.axway.mbaas.chats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.axway.mbaas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatsRowAdapter extends ArrayAdapter<JSONObject> {

    private final Context context;
    private final ArrayList<JSONObject> itemsArrayList;
    private ChatsOnDeleteClickListener deleteClickListener;

    public interface ChatsOnDeleteClickListener {
        void onClick(int position, View v);
    }

    static class ViewHolderItem {
        TextView titleView;
        TextView descriptionView;
        Button deleteButton;
    }

    public ChatsRowAdapter(Context context, ArrayList<JSONObject> itemsArrayList) {

        super(context, R.layout.chats_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    public void setOnDeleteClickListener(ChatsOnDeleteClickListener listener) {
        deleteClickListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chats_row, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.titleView = (TextView) convertView.findViewById(R.id.chats_row_title);
            viewHolder.descriptionView = (TextView) convertView
                    .findViewById(R.id.chats_row_description);
            viewHolder.deleteButton = (Button) convertView
                    .findViewById(R.id.chats_row_deleteButton1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (deleteClickListener != null) {
                    deleteClickListener.onClick(position, v);
                }
            }

        });

        JSONObject item = itemsArrayList.get(position);
        try {
            JSONObject from = item.getJSONObject("from");
            viewHolder.titleView.setText(item.getString("message"));
            StringBuilder sb = new StringBuilder();
            sb.append(from.getString("first_name"));
            sb.append(" ");
            sb.append(from.getString("last_name"));
            viewHolder.descriptionView.setText(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
