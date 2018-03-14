package com.group3.swengandroidapp;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.group3.swengandroidapp.XMLRenderer.Drawable;

/**
 * Created by Marco on 14/03/2018.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViwHolder> {
    public
    //private ItemClickListener mClickListener;
    private LayoutInflater mLayoutInflater;
    private ItemDescriptor[] mData = new ItemDescriptor[0];

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mLayoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String temp = mData[position].text;
        android.graphics.drawable.Drawable image = mData[position].image;
        holder.tv.setText(temp);
        holder.iv.setImageDrawable(image);
    }

    @Override
    public int getItemCount(){
        return mData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iv;
        TextView tv;

        ViewHolder(View itemView){
            super(itemView);
            iv=(ImageView) itemView.findViewById(R.id.recycler_item_image);
            tv=(TextView) itemView.findViewById(R.id.recycler_item_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public class ItemDescriptor{
        public String text;
        public android.graphics.drawable.Drawable image;
    }
}
