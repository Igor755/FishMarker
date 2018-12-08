package com.company.imetlin.fishmarker.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.imetlin.fishmarker.MapActivity;
import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.WaterActivity;
import com.company.imetlin.fishmarker.myinterfaces.OnItemClickListener;
import com.company.imetlin.fishmarker.pojo.ModelClass;

import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {

    //private String[] mDataset;
    private List<ModelClass> listData;
    private OnItemClickListener itemClickListener;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public class ViewHolder extends RecyclerView.ViewHolder{
        // наш пункт состоит только из одного TextView
        public TextView mTextView;
        //OnItemClickListener mItemClickListener;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_recycler_item);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = ViewHolder.super.getAdapterPosition();
                    //ModelClass cliced = listData.get(pos);

                    itemClickListener.onItemClick(v,pos);
                    //Toast.makeText(v.getContext(),"click" + mTextView.getText(),Toast.LENGTH_SHORT).show();

                }
            });
        }



    }


    // Конструктор
    public AdapterRecycler(List<ModelClass> listData,OnItemClickListener itemClickListener) {
        //mDataset = dataset;
        this.itemClickListener =itemClickListener;
        this.listData = listData;
    }


    // Создает новые views (вызывается layout manager-ом)
    @Override
    public AdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_item_recycler, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(listData.get(position).getName());



    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        //return listData.length;
        return listData.size();
    }
}
