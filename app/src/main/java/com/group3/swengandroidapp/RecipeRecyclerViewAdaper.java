package com.group3.swengandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

import java.util.ArrayList;

/**
 * Created by Marco on 14/03/2018.
 */

public class RecipeRecyclerViewAdaper extends RecyclerView.Adapter<RecipeRecyclerViewAdaper.ViewHolder> {
    private ItemClickListener clickListener;
    private LayoutInflater layoutInflater;

    private ArrayList<Recipe.Icon> items;
    private Context context;

    RecipeRecyclerViewAdaper(Context context){
        this.context = context;
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
        String title = items.get(position).getTitle();
        android.graphics.drawable.Drawable image = items.get(position).getDrawable();
        String time = items.get(position).getTime();
        String numFavourites = items.get(position).getNumFavourites();
        String id = items.get(position).getId();

        if(FavouritesHandler.getInstance().contains(id)){
            int temp = Integer.parseInt(numFavourites);
            temp++;
            numFavourites = Integer.toString(temp);
        }

        holder.title.setText(title);
        holder.favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id != null){
                    FavouritesHandler.getInstance().toggleFavourite(context, id);
                    if (FavouritesHandler.getInstance().contains(id)) {
                        holder.favouritesButton.setImageResource(R.drawable.heart_on);
                    } else {
                        holder.favouritesButton.setImageResource(R.drawable.heart_off);
                    }
                }
                //notifyDataSetChanged();
                notifyItemChanged(position);
                notifyActivity(items.get(position).getId());
            }
        });

        if (FavouritesHandler.getInstance().contains(id)) {
            holder.favouritesButton.setImageResource(R.drawable.heart_on);
        } else {
            holder.favouritesButton.setImageResource(R.drawable.heart_off);
        }
        if(image!=null) holder.image.setImageDrawable(image);
        if(time!=null) holder.time.setText(time);
        if(numFavourites!=null) holder.numFavourites.setText(numFavourites);
    }

    /**
     * Override this method if needed.
     * This method is called each time
     * @param id
     */
    public void notifyActivity(String id){}


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
        ImageButton favouritesButton;


        ViewHolder(View itemView){
            super(itemView);
            image =(ImageView) itemView.findViewById(R.id.recipe_icon_image);
            title =(TextView) itemView.findViewById(R.id.recipe_icon_title);
            numFavourites =(TextView) itemView.findViewById(R.id.recipe_icon_numfavourites);
            time =(TextView) itemView.findViewById(R.id.recipe_icon_time);
            favouritesButton = (ImageButton) itemView.findViewById(R.id.recipe_icon_numfavourites_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // RECIPE DISPLAY MANAGEMENT
    // RECIPE DRAWING STUFF

    public void addRecipe(Recipe r){
        try{
            int index = indexOf(r.getID());
            items.set(index, Recipe.produceDescriptor(this.context, r));
            notifyItemChanged(index);
        }catch(Exception e){
            items.add(Recipe.produceDescriptor(this.context, r));
            this.notifyDataSetChanged();
        }
    }

    public void addRecipe(String id){
        Recipe r = RemoteFileManager.getInstance().getRecipe(id);
        if(r != null){ // If recipe exists in file manager
            addRecipe(r);
        }
    }

    public void addRecipe(String[] ids){
        for(String id : ids){
            addRecipe(id);
        }
    }
    public void addRecipe(Recipe[] recipes){
        for(Recipe r : recipes){
            addRecipe(r);
        }
    }

    public void notifyRecipeChanged(String id){
        for(Recipe.Icon i : items) {
            if (i.getId().matches(id)) {
                notifyItemChanged(items.indexOf(i));
                break;
            }
        }
    }

    public void reloadRecipe(String id){
        for(Recipe.Icon i : items){
            if(i.getId().matches(id)){
                int index = items.indexOf(i);
                items.set(index, Recipe.produceDescriptor(this.context, RemoteFileManager.getInstance().getRecipe(id)));
                notifyItemChanged(index);
                break;
            }
        }
    }

    public int indexOf(String recipeId) throws Resources.NotFoundException{
        for(Recipe.Icon i : items){
            if(i.getId()==recipeId){
                return items.indexOf(i);
            }
        }
        throw new Resources.NotFoundException();
    }

    public void removeRecipe(int index){
        items.remove(index);
        this.notifyDataSetChanged();
    }

    /**
     * Searches through recipes currently on screen and removes
     * recipe whos id matches the input string
     * @param id
     */
    public void removeRecipe(String id){
        for(Recipe.Icon i : items){
            if(i.getId()==id){
                items.remove(i);
                this.notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * Clears container and fills with given recipes (Recipe type or String!!)
     * @param items ArrayList of Recipe or String (id string) to be processed!
     */
    public void setRecipes(ArrayList<?> items){
        this.items.clear();
        for(Object o : items){
            if(o instanceof Recipe){
                this.addRecipe((Recipe)o);
            }else if(o instanceof String){
                Recipe r = RemoteFileManager.getInstance().getRecipe((String)o);
                if(r != null) this.addRecipe(r);
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * Clears container and fills with given recipes
     * @param ids ids of recipes to be fetched from server
     */
    public void setRecipes(String[] ids){
        this.items.clear();
        for(String id : ids){
            Recipe r = RemoteFileManager.getInstance().getRecipe(id);
            if(r != null){
                this.addRecipe(r);
            }
        }


    }

    public void clearView(){
        items.clear();
        this.notifyDataSetChanged();
    }

    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String id = intent.getStringExtra(ImageDownloaderService.ID);
            reloadRecipe(id);
            //notifyRecipeChanged(id);
        }
    };

    public void onPause(){
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.mMessageReceiver);
    }

    public void onResume(){
        LocalBroadcastManager.getInstance(this.context).registerReceiver(mMessageReceiver, new IntentFilter(ImageDownloaderService.DOWNLOAD_COMPLETE));

    }
}
