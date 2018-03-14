package com.group3.swengandroidapp;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.group3.swengandroidapp.XMLRenderer.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marco on 14/03/2018.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
    private ItemClickListener mClickListener;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ItemDescriptor> items;

    HomeRecyclerViewAdapter(Context context, ArrayList<ItemDescriptor> items){
        this.mLayoutInflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mLayoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String temp = items.get(position).text;
        android.graphics.drawable.Drawable image = items.get(position).image;
        holder.tv.setText(temp);
        holder.iv.setImageDrawable(image);
    }


    @Override
    public int getItemCount(){
        return items.size();
    }

    public ItemDescriptor getItem(int position){
        return items.get(position);
    }

    public void setClickListener (ItemClickListener listener){
        this.mClickListener = listener;
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

    public static class ItemDescriptor{
        public String text;
        public android.graphics.drawable.Drawable image;

        ItemDescriptor(String text, android.graphics.drawable.Drawable image){
            this.text = text;
            this.image = image;
        }
    }

    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
