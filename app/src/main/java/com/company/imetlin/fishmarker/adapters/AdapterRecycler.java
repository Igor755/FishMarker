package com.company.imetlin.fishmarker.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.pojo.ItemTwoActivity;

import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder>{

    //private String[] mDataset;
    private List<ItemTwoActivity> listData;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит только из одного TextView
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_recycler_item);
        }
    }


    // Конструктор
    public AdapterRecycler( List<ItemTwoActivity> listData) {
        //mDataset = dataset;
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
