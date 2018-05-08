/*This class temporarily acts as the adapter for the favourites page.*/
package com.group3.swengandroidapp.XMLRenderer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.group3.swengandroidapp.R;

import java.util.ArrayList;

/**
 * Created by mlowt on 14/03/2018.
 */

public class FavouritesAdapter extends ArrayAdapter<Recipe> {

    private int layoutid;

    public FavouritesAdapter(Context context, ArrayList<Recipe> items){
        super(context, R.layout.activity_favourites, items);

    }

    public View getView(int position, View convertView, ViewGroup parent){

        Recipe recipeShown = getItem(position);
        ViewHolder viewHolder;
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.activity_favourites, parent, false);
            //viewHolder.txtTitle = (TextView)view.findViewById(R.id.favourite_recipe);
            view.setTag(viewHolder);
        }

        else{
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.txtTitle.setText(recipeShown.getTitle());

        return view;
    }

    private static class ViewHolder{
        TextView txtTitle;
    }

}


