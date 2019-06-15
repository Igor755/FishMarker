package com.company.imetlin.fishmarker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Shader;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.pojo.ModelClass;


import java.util.List;

public class AdapterGrid extends BaseAdapter {

    private List<ModelClass> listData;
    private LayoutInflater layoutInflater;
    private Context context;


    public AdapterGrid(Context aContext,  List<ModelClass> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.one_item_grid, null);
            holder = new ViewHolder();
            holder.PhotoWater = (ImageView) convertView.findViewById(R.id.imageViewWater);
            holder.NameWater = (TextView) convertView.findViewById(R.id.textViewWaterName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ModelClass ModelClass = this.listData.get(position);
        holder.NameWater.setText(ModelClass.getName());

        int imageId = this.getMipmapResIdByName(ModelClass.getPhoto());
        holder.PhotoWater.setImageResource(imageId);

        return convertView;
    }

    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "drawable", pkgName);
        Log.i("CustomGridView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }



    static class ViewHolder implements View.OnClickListener {
        ImageView PhotoWater;
        TextView NameWater;

       public RelativeLayout relativeLayout;


        @Override
        public void onClick(View v) {

            relativeLayout = (RelativeLayout) v.findViewById(R.id.relative);
            relativeLayout.setBackgroundColor(Color.green(R.color.colorRed));



        }
    }

}
