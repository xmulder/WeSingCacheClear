package com.jc.wesingcacheclear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WscAdapter extends ArrayAdapter {
     private int resourceId;
     private Context context;

     public WscAdapter(Context context, int textViewResourceId, List<Wsc> objects){
         super(context,textViewResourceId,objects);
         resourceId=textViewResourceId;
         this.context=context;
     }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         Wsc wscInstance= (Wsc) getItem(position);
         View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
         ImageView wscImage=(ImageView)view.findViewById(R.id.wsc_image_view);
         TextView wscTextView=(TextView)view.findViewById(R.id.wsc_text_view);
         //wscImage.setImageResource(wscInstance.getWscImageId());
         Glide.with(context).load(wscInstance.getWscImageId()).into(wscImage);
         wscTextView.setText(wscInstance.getWscTextView());
         return view;

    }
}
