package com.example.tencountries;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.List;

public class CountryCustomAdapter extends ArrayAdapter  implements Serializable {
    private List<Country> countryList;
    private Context context;

    public CountryCustomAdapter(Context context, List<Country> countryList) {
        super(context, R.layout.fragment_single_country,R.id.countryName, countryList);
        this.countryList = countryList;
        this.context=context;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup ListView) {
        viewHolder Holder;
        View row=currentView;
        if(row == null) {
            LayoutInflater Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = Inflater.inflate(R.layout.fragment_single_country, ListView, false);
            Holder=new viewHolder(row);
            //counter++;
            row.setTag(Holder);
            //System.out.println("NewRowCreated");
        }else{
            Holder= (viewHolder) row.getTag();
            //System.out.println("RowRecycled");
        }
        Holder.getImage().setImageBitmap(StringToBitMap(countryList.get(position).getImage()));
        Holder.getName().setText(countryList.get(position).getName());
        return row;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}

