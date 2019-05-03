package com.company.imetlin.fishmarker.userplaces;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.database.DatabaseLoad;
import com.company.imetlin.fishmarker.myinterfaces.OnItemClickListener;
import com.company.imetlin.fishmarker.pojo.MarkerInformation;
import com.company.imetlin.fishmarker.pojo.ModelClass;
import com.company.imetlin.fishmarker.pojo.Places;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import static android.support.constraint.Constraints.TAG;

public class PlacesUserAdapter extends RecyclerView.Adapter<PlacesUserAdapter.ViewHolder> {
    private List<Places> listItems;
    private Context mContext;
    private OnItemClickListener itemClickListener;


    /*public PlacesUserAdapter(List<Places> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }*/

    public PlacesUserAdapter(List<Places> listItems,Context mContext, OnItemClickListener itemClickListener) {
        this.itemClickListener =itemClickListener;
        this.listItems = listItems;
        this.mContext = mContext;
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
        holder.menu_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display option menu

                PopupMenu popupMenu = new PopupMenu(mContext, holder.menu_places);
                popupMenu.inflate(R.menu.menu_places);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {


///////////////////////////////////////////////////////////UPDATE PLACES
                            case R.id.update:

                                float f3 = Float.parseFloat(itemList.getZoom().toString());
                                PlacesUserMapActivity placesUserMapActivity = new PlacesUserMapActivity();
                                placesUserMapActivity.UpdatePlace(itemList.getLatitude(),itemList.getLongitude(),f3);



                                /*String id_place = itemList.getPlace_id();
                                FirebaseDatabase.getInstance().getReference("Places").child(id_place).setValue(itemList);

                                ListIterator<Places> iterator_update = PlacesUserActivity.alldataplaces.listIterator();
                                while (iterator_update.hasNext()) {
                                    Places next = iterator_update.next();
                                    if (next.getPlace_id().equals(itemList.getPlace_id())) {

                                        iterator_update.set(itemList);
                                        break;
                                    }
                                }
                                notifyDataSetChanged();*/


                                Toast.makeText(mContext, "update", Toast.LENGTH_LONG).show();
                                break;

///////////////////////////////////////////////////////////DELETE PLACES
                            case R.id.delete:


                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query delmark =  ref.child("Places").
                                        orderByChild("place_id").
                                        equalTo(itemList.getPlace_id());

                                ListIterator<Places> iterator = PlacesUserActivity.alldataplaces.listIterator();
                                System.out.println("sdsds");

                                while (iterator.hasNext()) {
                                    Places next = iterator.next();
                                    System.out.println("sdsds");
                                    if (next.getPlace_id().equals(itemList.getPlace_id())) {
                                        System.out.println("sdsds");
                                        iterator.remove();
                                        break;
                                    }
                                }


                                delmark.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e(TAG, "onCancelled", databaseError.toException());
                                    }
                                });


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
         TextView menu_places;

         ViewHolder(View itemView) {
            super(itemView);

            txtNamePlaces = (TextView) itemView.findViewById(R.id.txtnameplace);
            txtLatitude = (TextView) itemView.findViewById(R.id.txtlatitude);
            txtLongitude = (TextView) itemView.findViewById(R.id.txtlongitude);
            txtZoom = (TextView) itemView.findViewById(R.id.txtzoom);
             menu_places = (TextView) itemView.findViewById(R.id.txt_menu_places);

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
