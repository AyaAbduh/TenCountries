package com.example.tencountries;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class countryListFragment extends Fragment{

    ListView countryListView;   //listView to display each country
    ArrayList<Country> countryList;
    private Handler handler;
    DownloadImage imageThread;
    private Bitmap image;
    private ArrayList<Bitmap> imageList;
    private int i=0;
    private Communication communication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_country_list, container, false);
        countryListView=view.findViewById(R.id.countryList);

        if(savedInstanceState==null){
            countryList = new ArrayList<>();
            String url="https://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
            imageList=new ArrayList<>();
            handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    image =msg.getData().getParcelable("ImageBitmap");
                    imageList.add(image);
                    countryList.get(i).setImage(BitMapToString(image));
                    i++;
                }
            };
            new JsonTask().execute(url);
        }else {
            imageList=savedInstanceState.getParcelableArrayList("imageList");
            countryList=savedInstanceState.getParcelableArrayList("countryList");
            CountryCustomAdapter Adapter=new CountryCustomAdapter(getActivity(),countryList);
            countryListView.setAdapter(Adapter);
            countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Country country= (Country) parent.getItemAtPosition(position);
                    int orientation = getResources().getConfiguration().orientation;
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        // In landscape
                        Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        communication.SetCountry(country);
                    }else{
                        Intent intent=new Intent(getActivity(),CountryDetails.class);
                        intent.putExtra("Country", (Parcelable) country);
                        startActivity(intent);
                    }
                }
            });
        }
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communication= (Communication) getActivity();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("countryList",countryList);
        outState.putParcelableArrayList("imageList",imageList);

    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

   private void  Download(String url) throws IOException {
      //  HashMap<String,String> country=null;
        Country countryObj;
        URL urlobj=null;
        HttpsURLConnection httpsURLConnection;
        InputStream inputStream=null;
        String JsonString=null;
        try {
            urlobj=new URL(url);
            httpsURLConnection= (HttpsURLConnection) urlobj.openConnection();
            httpsURLConnection.connect();
            inputStream=httpsURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JsonString=stringBuilder.toString();
            if (JsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(JsonString);
                    JSONArray countries = jsonObj.getJSONArray("worldpopulation");
                    for (int i = 0; i < countries.length(); i++) {
                        JSONObject countryObject = countries.getJSONObject(i);
                        String rank=countryObject.getString("rank");
                        String countryName=countryObject.getString("country");
                        String population=countryObject.getString("population");
                        String flag=countryObject.getString("flag");
                        //convert from http to https
                        flag=flag.replace("http","https");
                        synchronized (handler) {
                            //call handler
                            imageThread = new DownloadImage(handler, flag);
                            Thread thread=new Thread(imageThread);
                            thread.start();
                        }
                        // hash map for single country
                       // country = new HashMap<String,String>();
                        countryObj=new Country();
                        // adding each child node to HashMap key => value
                        countryObj.setName(countryName);
                        countryObj.setRank(rank);
                        countryObj.setPopulation(population);
//                        country.put("rank", rank);
//                        country.put("countryName", countryName);
//                        country.put("population", population);
////                        if(image!=null){
//                            country.put("flag", BitMapToString(image));
//                        }
                        countryList.add(countryObj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // return countryList;
    }
    private class JsonTask extends AsyncTask<String, Void, Void> {

        @Override
        protected synchronized Void doInBackground(String... urls) {
            try {
                Download(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute( Void result) {
            CountryCustomAdapter Adapter=new CountryCustomAdapter(getActivity(),countryList);
            countryListView.setAdapter(Adapter);
            countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Country country= (Country) parent.getItemAtPosition(position);
                    int orientation = getResources().getConfiguration().orientation;
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        // In landscape
                        Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        communication.SetCountry(country);
                    }else{
                        Intent intent=new Intent(getActivity(),CountryDetails.class);
                        intent.putExtra("Country", (Parcelable) country);
                        startActivity(intent);
                    }
                }
            });
            //get values from arrayList of Bitmap into cuntryList
           // System.out.println(countryList.get(i).getName());
        }

    }


}
