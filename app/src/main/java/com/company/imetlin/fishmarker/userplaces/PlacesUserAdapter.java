package com.company.imetlin.fishmarker.userplaces;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.myinterfaces.OnItemClickListener;
import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.company.imetlin.fishmarker.pojo.Places;

import java.util.List;

public class PlacesUserAdapter extends RecyclerView.Adapter<PlacesUserAdapter.ViewHolder> {
    private List<Places> listItems;
    private Context mContext;
    private OnItemClickListener itemClickListener;


    public PlacesUserAdapter(List<Places> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    public PlacesUserAdapter(List<Places> listItems, OnItemClickListener itemClickListener) {
        this.itemClickListener =itemClickListener;
        this.listItems = listItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_places_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final Places itemList = listItems.get(position);
        holder.txtNamePlaces.setText(itemList.getNameplace());
        holder.txtLatitude.setText(Double.toString(itemList.getLatitude()));
        holder.txtLongitude.setText(Double.toString(itemList.getLongitude()));
        holder.txtZoom.setText(Double.toString(itemList.getZoom()));
        holder.txt_menu_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display option menu

                PopupMenu popupMenu = new PopupMenu(mContext, holder.txt_menu_places);
                popupMenu.inflate(R.menu.menu_places);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.update:
                                Toast.makeText(mContext, "update", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.delete:
                                //Delete item
                                listItems.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "delete", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{

         TextView txtNamePlaces;
         TextView txtLatitude;
         TextView txtLongitude;
         TextView txtZoom;
         TextView txt_menu_places;

         ViewHolder(View itemView) {
            super(itemView);

            txtNamePlaces = (TextView) itemView.findViewById(R.id.txtnameplace);
            txtLatitude = (TextView) itemView.findViewById(R.id.txtlatitude);
            txtLongitude = (TextView) itemView.findViewById(R.id.txtlongitude);
            txtZoom = (TextView) itemView.findViewById(R.id.txtzoom);
            txt_menu_places = (TextView) itemView.findViewById(R.id.txt_menu_places);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int pos = ViewHolder.super.getAdapterPosition();
                     itemClickListener.onItemClick(v,pos);

                 }
             });
        }
    }
}
