package com.group3.swengandroidapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.group3.swengandroidapp.XMLRenderer.InstructionalVideo;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

import java.util.ArrayList;

/**
 *
 * Created by Marco on 14/03/2018.
 */

// TODO: Rename - "Adapter"
public class InstructionalVideoRecyclerViewAdapter extends RecyclerView.Adapter<InstructionalVideoRecyclerViewAdapter.ViewHolder> {
    private ItemClickListener clickListener;
    private LayoutInflater layoutInflater;

    private ArrayList<InstructionalVideo.Icon> items;
    private Context context;

    InstructionalVideoRecyclerViewAdapter(Context context){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.items = new ArrayList<>(0);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.instructional_video_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        final String title = items.get(position).getTitle();
        android.graphics.drawable.Drawable image = items.get(position).getDrawable();
        final String id = items.get(position).getId();

        holder.title.setText(title);
        holder.image.setImageDrawable(items.get(position).getDrawable());
        if(image!=null) holder.image.setImageDrawable(image);
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public InstructionalVideo.Icon getItem(int position){
        return items.get(position);
    }

    public void setClickListener (ItemClickListener listener){
        this.clickListener = listener;
    }


    //******** ADDING ICONS *********//

    /**
     * The new and cool way to handle icons. This way, one Icon object can be drawn in multiple
     * viewholders. <br>
     * Adds icon the the recyclerview, unless an icon describing the corresponding Instructional Video already
     * exists, in which case the icon is replaced by the new icon.
     * @param icon the icon the be added.
     */
    public void addIcon(InstructionalVideo.Icon icon){
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
     * @param r InstructionalVideo to be added
     */
    public void addInstructionalVideo(InstructionalVideo r){
        try{
            int index = indexOf(r.getID());
            items.set(index, InstructionalVideo.produceDescriptor(this.context, r));
            notifyItemChanged(index);
            // If there is no bitmap, request the loading of the bitmap
        }catch(Exception e){
            items.add(InstructionalVideo.produceDescriptor(this.context, r));
            this.notifyDataSetChanged();
        }
    }

    /**
     * @deprecated
     * @param id id of InstructionalVideo to be added
     */
    public void addInstructionalVideo(String id){
        InstructionalVideo r = RemoteFileManager.getInstance().getInstructionalVideo(id);
        if(r != null){ // If recipe exists in file manager
            addInstructionalVideo(r);
        }
    }

    /**
     * @deprecated
     * @param ids ids of InstructionalVideos to be added
     */
    public void addInstructionalVideo(String[] ids){
        for(String id : ids){
            addInstructionalVideo(id);
        }
    }

    /**
     * @deprecated
     * @param InstructionalVideos instructionalVideos to be added
     */
    public void addInstructionalVideo(InstructionalVideo[] InstructionalVideos){
        for(InstructionalVideo r : InstructionalVideos){
            addInstructionalVideo(r);
        }
    }

    /**
     * @deprecated
     * Clears container and fills with given recipes (Recipe type or String!!)
     * @param items ArrayList of Recipe or String (id string) to be processed!
     */
    public void setInstructionalVideos(ArrayList<?> items){
        this.items.clear();
        for(Object o : items){
            if(o instanceof InstructionalVideo){
                this.addInstructionalVideo((InstructionalVideo)o);
            }else if(o instanceof String){
                InstructionalVideo r = RemoteFileManager.getInstance().getInstructionalVideo((String)o);
                if(r != null) this.addInstructionalVideo(r);
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * @deprecated
     * Clears container and fills with given recipes
     * @param ids ids of recipes to be fetched from server
     */
    public void setInstructionalVideos(String[] ids){
        this.items.clear();
        for(String id : ids){
            InstructionalVideo r = RemoteFileManager.getInstance().getInstructionalVideo(id);
            if(r != null){
                this.addInstructionalVideo(r);
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
        for(InstructionalVideo.Icon i : items) {
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
        for(InstructionalVideo.Icon i : items){
            if(i.getId().matches(id)){
                int index = items.indexOf(i);
                items.set(index, InstructionalVideo.produceDescriptor(this.context, RemoteFileManager.getInstance().getInstructionalVideo(id)));
                notifyItemChanged(index);
                break;
            }
        }
    }


    //******** UTILITIES ********//

    private int indexOf(String instructionalVideoId) throws IconNotFoundException{
        for(InstructionalVideo.Icon i : items){
            if(i.getId().matches(instructionalVideoId)){
                return items.indexOf(i);
            }
        }
        throw new IconNotFoundException(instructionalVideoId);
    }




    //******** OTHER ********//

    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener{
        void onItemClick(String instructionalVideoId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView title;


        ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.instructional_video_icon_image);
            title = itemView.findViewById(R.id.instructional_video_icon_title);
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
