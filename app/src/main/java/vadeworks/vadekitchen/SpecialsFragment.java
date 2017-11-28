package vadeworks.vadekitchen;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vadeworks.vadekitchen.mainfeed.AndroidVersion;
import vadeworks.vadekitchen.mainfeed.DataAdapter;
import vadeworks.vadekitchen.mainfeed.JSONResponse;
import vadeworks.vadekitchen.mainfeed.RequestInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialsFragment extends Fragment {

View view;
Context context;
    private RecyclerView recyclerView,recyclerViews;
    private ArrayList<AndroidVersion> data;
    private DataAdapter adapter;
    public SpecialsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_specials, container, false);//TODO: Should CHange this accordinly
        context = getActivity().getApplicationContext();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Specials");




        //Recycler View Horizontal-Retrofit Code
        initWeeklySpecialsViews();
        initDietViews();


        HorizontalScrollView h4 = (HorizontalScrollView)view.findViewById(R.id.h4);
        h4.setBackground(getResources().getDrawable(R.drawable.h4));

        HorizontalScrollView h5 = (HorizontalScrollView)view.findViewById(R.id.h5);
        h5.setBackground(getResources().getDrawable(R.drawable.h5));



        return  view;
    }


    private void initWeeklySpecialsViews(){
        recyclerView = (RecyclerView)view.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();//WeeklySpecials
    }

    private void initDietViews(){
        recyclerViews = (RecyclerView)view.findViewById(R.id.card_recycler_views);
        recyclerViews.setHasFixedSize(true);
        recyclerViews.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerViews.setLayoutManager(layoutManager);
        loadDiet();//Diet Food
    }

    private void loadJSON(){

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getContext().getCacheDir(), cacheSize);



        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(chain -> {
                    try {
                        return chain.proceed(chain.request());
                    } catch (Exception e) {
                        Request offlineRequest = chain.request().newBuilder()
                                .header("Cache-Control", "public, only-if-cached," +
                                        "max-stale=" + 60 * 60 * 24)
                                .build();
                        return chain.proceed(offlineRequest);
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                //    https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/example.json
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {


                try{
                    JSONResponse jsonResponse = response.body();
                    Log.d("Json Response", "onResponse: "+response.body() );
                    data = new ArrayList<AndroidVersion>(Arrays.asList(jsonResponse.getAndroid()));
                    adapter = new DataAdapter(data);
                    recyclerView.setAdapter(adapter);
                }catch (Exception e)
                {
                    Log.e("Error","Error ");
                }



            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }


    private void loadDiet(){

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getContext().getCacheDir(), cacheSize);



        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(chain -> {
                    try {
                        return chain.proceed(chain.request());
                    } catch (Exception e) {
                        Request offlineRequest = chain.request().newBuilder()
                                .header("Cache-Control", "public, only-if-cached," +
                                        "max-stale=" + 60 * 60 * 24)
                                .build();
                        return chain.proceed(offlineRequest);
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                //    https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/example.json
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getDiet();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                try{
                    JSONResponse jsonResponse = response.body();
                    Log.d("Json Response", "onResponse: "+response.body() );
                    data = new ArrayList<AndroidVersion>(Arrays.asList(jsonResponse.getAndroid()));
                    adapter = new DataAdapter(data);
                    recyclerViews.setAdapter(adapter);
                }catch(Exception e)
                {

                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }



}
