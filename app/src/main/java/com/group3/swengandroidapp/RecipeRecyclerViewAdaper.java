package com.group3.swengandroidapp;

import android.content.Context;
import android.content.Intent;
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
 *
 * Created by Marco on 14/03/2018.
 */

// TODO: Rename - "Adapter"
public class RecipeRecyclerViewAdaper extends RecyclerView.Adapter<RecipeRecyclerViewAdaper.ViewHolder> {
    private ItemClickListener clickListener;
    private LayoutInflater layoutInflater;

    private ArrayList<Recipe.Icon> items;
    private Context context;

    RecipeRecyclerViewAdaper(Context context){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.items = new ArrayList<>(0);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.recipe_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        final String title = items.get(position).getTitle();
        android.graphics.drawable.Drawable image = items.get(position).getDrawable();
        final String time = items.get(position).getTime();
        final String numFavourites = items.get(position).getNumFavourites();
        final String id = items.get(position).getId();

        holder.title.setText(title);
        holder.favouritesButton.setOnClickListener((View view) -> {
            if(id != null){
                FavouritesHandler.getInstance().toggleFavourite(context, id);
                if (FavouritesHandler.getInstance().contains(id)) {
                    holder.favouritesButton.setImageResource(R.drawable.favfull);
                    // Extract number of favourites, and add 1, and reconvert to string
                    holder.numFavourites.setText(Integer.toString(Integer.parseInt(numFavourites) + 1));
                } else {
                    holder.favouritesButton.setImageResource(R.drawable.favempty);
                    holder.numFavourites.setText(numFavourites);
                }
            }

            // Send out a broadcast notifying that icon has changed
            Intent intent = new Intent(Recipe.Icon.ICON_CHANGED);
            intent.putExtra(Recipe.ID, id);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        });

        if (FavouritesHandler.getInstance().contains(id)) {
            holder.favouritesButton.setImageResource(R.drawable.favfull);
            // Extract number of favourites, and add 1, and reconvert to string
            int number;
            try{
                number = Integer.parseInt(numFavourites) + 1;
            }catch(NumberFormatException e){
                // Unable to parse integer number from numFavourites
                number = 1;
            }

            holder.numFavourites.setText(Integer.toString(number));

        } else {
            holder.favouritesButton.setImageResource(R.drawable.favempty);
            if(numFavourites!=null){
                holder.numFavourites.setText(numFavourites);
            }else{
                holder.numFavourites.setText("0");
            }
        }
        if(image!=null) holder.image.setImageDrawable(image);
        Log.d("MARCO", "Time : "+ time);
        if(time!=null) holder.time.setText(time);
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


    //******** ADDING ICONS *********//

    /**
     * The new and cool way to handle icons. This way, one Icon object can be drawn in multiple
     * viewholders. <br>
     * Adds icon the the recyclerview, unless an icon describing the corresponding recipe already
     * exists, in which case the icon is replaced by the new icon.
     * @param icon the icon the be added.
     */
    public void addIcon(Recipe.Icon icon){
        try{
            // Search for existing icons with same id, and replace
            int index = indexOf(icon.getId());
            items.set(index, icon);
        }catch(IconNotFoundException e){
            // Icon doesnt exist. Add new.
            items.add(icon);
        }
    }

    public void clear() {
        items.clear();
    }

    /**
     * @deprecated
     * @param r recipe to be added
     */
    public void addRecipe(Recipe r){
        try{
            int index = indexOf(r.getID());
            items.set(index, Recipe.produceDescriptor(this.context, r));
            notifyItemChanged(index);
            // If there is no bitmap, request the loading of the bitmap
        }catch(Exception e){
            items.add(Recipe.produceDescriptor(this.context, r));
            this.notifyDataSetChanged();
        }
    }

    /**
     * @deprecated
     * @param id id of recipe to be added
     */
    public void addRecipe(String id){
        Recipe r = RemoteFileManager.getInstance().getRecipe(id);
        if(r != null){ // If recipe exists in file manager
            addRecipe(r);
        }
    }

    /**
     * @deprecated
     * @param ids ids of recipes to be added
     */
    public void addRecipe(String[] ids){
        for(String id : ids){
            addRecipe(id);
        }
    }

    /**
     * @deprecated
     * @param recipes recipes to be added
     */
    public void addRecipe(Recipe[] recipes){
        for(Recipe r : recipes){
            addRecipe(r);
        }
    }

    /**
     * @deprecated
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
     * @deprecated
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





    //******** UPDATING ICONS ********//

    /**
     * <p>
     *     Notify the adapter that of a recipe change.
     * </p>
     * <p>
     *     This will update any instances of an icon describing the recipe described by the id
     * </p>
     * <p>
     *     If no instances of the recipe exists, nothing happens.
     * </p>
     *
     * @param id id of the recipe(s) to be updated
     */
    public void notifyIconChanged(String id){
        for(Recipe.Icon i : items) {
            if (i.getId().matches(id)) {
                notifyItemChanged(items.indexOf(i));
                break;
            }
        }
    }

    /**
     * @deprecated
     * <p>
     *     Re-produce and redraw any instances of the recipe icon(s)
     * </p>
     * <p>
     *     If no instances of the recipe exist, nothing happens.
     * </p>
     * @param id id of recipe to re-draw
     */
    public void reloadIcon(String id){
        for(Recipe.Icon i : items){
            if(i.getId().matches(id)){
                int index = items.indexOf(i);
                items.set(index, Recipe.produceDescriptor(this.context, RemoteFileManager.getInstance().getRecipe(id)));
                notifyItemChanged(index);
                break;
            }
        }
    }

    
    //******** UTILITIES ********//

    private int indexOf(String recipeId) throws IconNotFoundException{
        for(Recipe.Icon i : items){
            if(i.getId().matches(recipeId)){
                return items.indexOf(i);
            }
        }
        throw new IconNotFoundException(recipeId);
    }




    //******** OTHER ********//

    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener{
        void onItemClick(String recipeId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView title;
        TextView numFavourites;
        TextView time;
        ImageButton favouritesButton;


        ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.recipe_icon_image);
            title = itemView.findViewById(R.id.recipe_icon_title);
            numFavourites = itemView.findViewById(R.id.recipe_icon_numfavourites);
            time = itemView.findViewById(R.id.recipe_time_text);
            favouritesButton = itemView.findViewById(R.id.recipe_icon_numfavourites_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if (clickListener != null) clickListener.onItemClick(items.get(getAdapterPosition()).getId());
        }
    }

    public class IconNotFoundException extends Exception{
        IconNotFoundException(String iconId){
            super("Icon with id: " + iconId + " not found.");
        }
    }
}
