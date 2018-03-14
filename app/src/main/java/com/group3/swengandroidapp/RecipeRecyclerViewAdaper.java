package com.group3.swengandroidapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.group3.swengandroidapp.XMLRenderer.Recipe;

import java.util.ArrayList;

/**
 * Created by Marco on 14/03/2018.
 */

public class RecipeRecyclerViewAdaper extends RecyclerView.Adapter<RecipeRecyclerViewAdaper.ViewHolder> {
    private ItemClickListener mClickListener;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Recipe.Icon> items;

    RecipeRecyclerViewAdaper(Context context){
        this.mLayoutInflater = LayoutInflater.from(context);
        this.items = new ArrayList<Recipe.Icon>(0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mLayoutInflater.inflate(R.layout.recipe_icon, parent, false);
        return new ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String temp = items.get(position).getTitle();
        android.graphics.drawable.Drawable image = items.get(position).getDrawable();
        holder.tv.setText(temp);
        holder.iv.setImageDrawable(image);
    }


    @Override
    public int getItemCount(){
        return items.size();
    }

    public Recipe.Icon getItem(int position){
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
            iv=(ImageView) itemView.findViewById(R.id.recipe_icon_image);
            tv=(TextView) itemView.findViewById(R.id.recipe_icon_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // RECIPE DISPLAY MANAGEMENT
    // RECIPE DRAWING STUFF
    public void addRecipe(Context context, Recipe r){
        items.add(Recipe.produceDescriptor(context, r));
        this.notifyItemChanged(items.indexOf(r));
    }

    public void clearView(){
        items.clear();
        this.notifyDataSetChanged();
    }

    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
