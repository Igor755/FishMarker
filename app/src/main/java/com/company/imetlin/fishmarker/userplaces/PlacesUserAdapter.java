package com.company.imetlin.fishmarker.userplaces;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import static android.view.View.VISIBLE;

public class PlacesUserAdapter extends RecyclerView.Adapter<PlacesUserAdapter.ViewHolder> {
    private List<Places> listItems;
    public Context mContext;
    private OnItemClickListener itemClickListener;
    private AlertDialog builder;
    private PlacesUserActivity placesUserActivity;


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
        holder.txtLatitude.setText(mContext.getResources().getString(R.string.latitude_holder) + ": " + Double.toString(itemList.getLatitude()));
        holder.txtLongitude.setText(mContext.getResources().getString(R.string.longitude_holder) + ": " + Double.toString(itemList.getLongitude()));
        holder.txtZoom.setText(mContext.getResources().getString(R.string.zoom_holder) + ": " + Double.toString(itemList.getZoom()));
        holder.menu_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                builder = new AlertDialog.Builder(v.getRootView().getContext()).create();
                builder.setTitle(R.string.delete_place_title);
                builder.setMessage(mContext.getResources().getString(R.string.delete_place_message));
                builder.setButton(Dialog.BUTTON_POSITIVE, mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query delmark =  ref.child("Places").
                                orderByChild("place_id").
                                equalTo(itemList.getPlace_id());

                        ListIterator<Places> iterator = PlacesUserActivity.alldataplaces.listIterator();

                        while (iterator.hasNext()) {
                            Places next = iterator.next();
                            if (next.getPlace_id().equals(itemList.getPlace_id())) {
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
                                Log.e(TAG, String.valueOf(R.string.cancel), databaseError.toException());
                            }
                        });


                        listItems.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, R.string.delete, Toast.LENGTH_LONG).show();

                        if(listItems.size() == 0){
                            PlacesUserActivity.txtnameplace.setVisibility(VISIBLE);

                        }


                    }
                });
                builder.setButton(Dialog.BUTTON_NEGATIVE, mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.cancel), Toast.LENGTH_LONG).show();


                    }
                });

                builder.show();


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
         ImageButton menu_places;

         ViewHolder(View itemView) {
            super(itemView);

            txtNamePlaces = (TextView) itemView.findViewById(R.id.txtnameplace);
            txtLatitude = (TextView) itemView.findViewById(R.id.txtlatitude);
            txtLongitude = (TextView) itemView.findViewById(R.id.txtlongitude);
            txtZoom = (TextView) itemView.findViewById(R.id.txtzoom);
             menu_places = (ImageButton) itemView.findViewById(R.id.txt_menu_places);

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
