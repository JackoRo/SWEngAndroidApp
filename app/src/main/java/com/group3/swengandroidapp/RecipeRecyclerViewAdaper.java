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
    private ItemClickListener clickListener;
    private LayoutInflater layoutInflater;

    private ArrayList<Recipe.Icon> items;

    RecipeRecyclerViewAdaper(Context context){
        this.layoutInflater = LayoutInflater.from(context);
        this.items = new ArrayList<Recipe.Icon>(0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.recipe_icon, parent, false);
        return new ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String temp = items.get(position).getTitle();
        android.graphics.drawable.Drawable image = items.get(position).getDrawable();
        holder.title.setText(temp);
        holder.image.setImageDrawable(image);
    }


    @Override
    public int getItemCount(){
        return items.size();
    }

    public Recipe.Icon getItem(int position){
        return items.get(position);
    }

    public void setClickListener (ItemClickListener listener){
        this.clickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView title;
        TextView numFavourites;
        TextView time;

        ViewHolder(View itemView){
            super(itemView);
            image =(ImageView) itemView.findViewById(R.id.recipe_icon_image);
            title =(TextView) itemView.findViewById(R.id.recipe_icon_title);
            numFavourites =(TextView) itemView.findViewById(R.id.recipe_icon_numfavourites);
            time =(TextView) itemView.findViewById(R.id.recipe_icon_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
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
