package com.example.tencountries;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class countryDetailsFragment extends Fragment {
    TextView TextViewRank;
    TextView TextViewPopulation;
    TextView TextViewCountryName;
    ImageView Flag;
    Button BtnNext;
    Button BtnPrevious;
    private ArrayList<Country> countryList;

    public countryDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_country_details, container, false);
        TextViewPopulation=view.findViewById(R.id.population);
        TextViewCountryName=view.findViewById(R.id.Country);
        TextViewRank=view.findViewById(R.id.rank);
       // BtnNext=view.findViewById(R.id.Next);
       // BtnPrevious=view.findViewById(R.id.previous);
        Flag=view.findViewById(R.id.countryFlag);
        Intent intent=getActivity().getIntent();
        Country country = (Country)intent.getSerializableExtra("Country");
        if(country!=null){
            TextViewPopulation.setText(country.getPopulation());
            TextViewRank.setText(country.getRank());
            TextViewCountryName.setText(country.getName());
            Flag.setImageBitmap(StringToBitMap(country.getImage()));
        }
        return view;
    }
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }

    }

    public  void displayCountry(Country country){
        TextViewPopulation.setText(country.getPopulation());
        TextViewRank.setText(country.getRank());
        TextViewCountryName.setText(country.getName());
        Flag.setImageBitmap(StringToBitMap(country.getImage()));
    }

}
