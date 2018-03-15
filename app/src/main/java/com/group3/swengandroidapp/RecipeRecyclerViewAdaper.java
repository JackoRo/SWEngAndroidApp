package com.group3.swengandroidapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        String temp = items.get(position).getTitle();
        android.graphics.drawable.Drawable image = items.get(position).getDrawable();
        String time = items.get(position).getTime();
        String numFavourites = items.get(position).getNumFavourites();

        holder.title.setText(temp);
        if(image!=null) holder.image.setImageDrawable(image);
        if(time!=null) holder.time.setText(time);
        if(numFavourites!=null) holder.numFavourites.setText(numFavourites);
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

    public void addRecipe(Recipe r){
        items.add(Recipe.produceDescriptor(context, r));
        this.notifyItemChanged(items.indexOf(r));
    }

    public void addRecipe(String id){
        Recipe r = RemoteFileManager.getInstance().getRecipe(id);
        if(r != null){
            items.add(Recipe.produceDescriptor(context, r));
            this.notifyItemChanged(items.indexOf(r));
        }else{
            Log.d("RecyclerViewAdapter", "90: Unable to fetch recipe with id " + id);
            r = new Recipe(id, "temp", "Replacement Recipe!", id);
            items.add(Recipe.produceDescriptor(context, r));
            this.notifyItemChanged(items.indexOf(r));
        }
    }

    public void addRecipe(String[] ids){
        for(String id : ids){
            addRecipe(id);
        }
    }


    public void addRecipe(ArrayList<String> ids){
        for(int i=0; i<ids.size(); i++){
            addRecipe(ids.get(i));
        }
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
                this.items.add(Recipe.produceDescriptor(context, (Recipe)o));
            }else if(o instanceof String){
                Recipe r = RemoteFileManager.getInstance().getRecipe((String)o);
                if(r == null){
                    // If server fails to return a recipe, report and make a fake one
                    Log.d("RecyclerViewAdapter", "90: Unable to fetch recipe with id " + (String)o);
                    //TEMPORARY CODE:
                    r = new Recipe((String)o, "temp", "Replacement Recipe!", (String)o);
                }

                this.items.add(Recipe.produceDescriptor(context, r));
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
                items.add(Recipe.produceDescriptor(context, r));
            }else{
                Log.d("RecyclerViewAdapter", "90: Unable to fetch recipe with id " + id);
                //TEMPORARY CODE:
                r = new Recipe(id, "temp", "Replacement Recipe!", id);
                items.add(Recipe.produceDescriptor(context, r));
            }
        }
        this.notifyDataSetChanged();
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
