package com.example.tencountries;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CountryDetails extends AppCompatActivity  implements Communication{

    public  countryDetailsFragment DetailsFragment;
    public FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        manager=getSupportFragmentManager();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            if(savedInstanceState!=null){
                DetailsFragment= (countryDetailsFragment) manager.findFragmentById(R.id.countryDetails);
            }else{
                DetailsFragment=new countryDetailsFragment();
                FragmentTransaction Transaction=manager.beginTransaction();
                Transaction.add(R.id.countryDetails,DetailsFragment);
                Transaction.commit();
            }
        } else {
                //in portria
            if(savedInstanceState!=null){
                DetailsFragment= (countryDetailsFragment) manager.findFragmentById(R.id.countryDetails);
            }else{
                DetailsFragment=new countryDetailsFragment();
                FragmentTransaction Transaction=manager.beginTransaction();
                Transaction.add(R.id.countryDetails,DetailsFragment);
                Transaction.commit();
            }
        }
    }

    @Override
    public void SetCountry(Country country) {
        DetailsFragment.displayCountry(country);
    }
}
