package com.example.tencountries;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements Communication{

    public  countryListFragment ListFragment;
    public  countryDetailsFragment DetailsFragment;
    public FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=getSupportFragmentManager();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            if(savedInstanceState!=null){
                ListFragment= (countryListFragment) manager.findFragmentById(R.id.countryList);
                if(DetailsFragment!=null)
                    DetailsFragment= (countryDetailsFragment) manager.findFragmentById(R.id.countryDetails);
                else{
                    DetailsFragment=new countryDetailsFragment();
                    FragmentTransaction Transaction=manager.beginTransaction();
                    Transaction.add(R.id.countryDetails,DetailsFragment);
                    Transaction.commit();
                }
            }else{
                ListFragment=new countryListFragment();
                DetailsFragment=new countryDetailsFragment();
                FragmentTransaction Transaction=manager.beginTransaction();
                Transaction.add(R.id.countryList,ListFragment);
                Transaction.add(R.id.countryDetails,DetailsFragment);
                Transaction.commit();
            }
        } else {
            if(savedInstanceState!=null){
                ListFragment= (countryListFragment) manager.findFragmentById(R.id.countryList);
            }else{
                ListFragment=new countryListFragment();
                FragmentTransaction Transaction=manager.beginTransaction();
                Transaction.add(R.id.countryList,ListFragment);
                Transaction.commit();
            }
        }


    }

    @Override
    public void SetCountry(Country country) {
        DetailsFragment.displayCountry(country);
    }
}
