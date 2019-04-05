package com.example.tencountries;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class viewHolder {
    private View currentView;
    private ImageView countryFlag;
    private TextView countryName;

    public viewHolder(View currentView) {
        this.currentView = currentView;
    }

    public ImageView getImage() {
        if (countryFlag==null)
            countryFlag=currentView.findViewById(R.id.countryFlag);
        return countryFlag;
    }

    public TextView getName() {
        if(countryName==null)
            countryName=currentView.findViewById(R.id.countryName);
        return countryName;
    }
}
