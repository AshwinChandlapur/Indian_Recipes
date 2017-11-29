package vadeworks.vadekitchen;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
    private RecyclerView recyclerView_WeeklySpecials,recyclerView_DietSpecials,recyclerView_SpicySpecials,recyclerView_QuickSpecials,recyclerView_NonVegSpecials;
    private ArrayList<AndroidVersion> data;
    private DataAdapter adapter;
    OkHttpClient httpClient;
    int cacheSize;
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



        cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getContext().getCacheDir(), cacheSize);



        httpClient = new OkHttpClient.Builder()
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



        new initSpecials().execute();
        initDietViews();
        initWeeklySpecialsViews();
        initSpicySpecialsViews();
        initQuickSpecialsViews();
        initNonVegSpecialsViews();


//        HorizontalScrollView h4 = (HorizontalScrollView)view.findViewById(R.id.h4);
//        h4.setBackground(getResources().getDrawable(R.drawable.h4));
//
//        HorizontalScrollView h5 = (HorizontalScrollView)view.findViewById(R.id.h5);
//        h5.setBackground(getResources().getDrawable(R.drawable.h5));



        return  view;
    }


    private class initSpecials extends AsyncTask<Void, Void, Void>
    {

        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            loadWeeklySpecials();
            loadDietSpecials();
            loadSpicySpecials();
            loadQuickSpecials();
            loadNonVegSpecials();
            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            if (pdLoading!=null && pdLoading.isShowing()){
                pdLoading.dismiss();
            }

        }

    }





    private void initWeeklySpecialsViews(){
        recyclerView_WeeklySpecials = (RecyclerView)view.findViewById(R.id.card_recycler_view_WeeklySpecials);
        recyclerView_WeeklySpecials.setHasFixedSize(true);
        recyclerView_WeeklySpecials.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView_WeeklySpecials.setLayoutManager(layoutManager);
//        loadWeeklySpecials();//WeeklySpecials
    }


    private void initDietViews(){
        recyclerView_DietSpecials = (RecyclerView)view.findViewById(R.id.card_recycler_view_DietSpecials);
        recyclerView_DietSpecials.setHasFixedSize(true);
        recyclerView_DietSpecials.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView_DietSpecials.setLayoutManager(layoutManager);
//        loadDietSpecials();//Diet Food
    }

    private void initSpicySpecialsViews(){
        recyclerView_SpicySpecials = (RecyclerView)view.findViewById(R.id.card_recycler_view_SpicySpecials);
        recyclerView_SpicySpecials.setHasFixedSize(true);
        recyclerView_SpicySpecials.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView_SpicySpecials.setLayoutManager(layoutManager);
//        loadSpicySpecials();
    }

    private void initQuickSpecialsViews(){
        recyclerView_QuickSpecials = (RecyclerView)view.findViewById(R.id.card_recycler_view_QuickSpecials);
        recyclerView_QuickSpecials.setHasFixedSize(true);
        recyclerView_QuickSpecials.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView_QuickSpecials.setLayoutManager(layoutManager);
//        loadQuickSpecials();
    }

    private void initNonVegSpecialsViews(){
        recyclerView_NonVegSpecials = (RecyclerView)view.findViewById(R.id.card_recycler_view_NonVegSpecials);
        recyclerView_NonVegSpecials.setHasFixedSize(true);
        recyclerView_NonVegSpecials.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView_NonVegSpecials.setLayoutManager(layoutManager);
//        loadNonVegSpecials();
    }

    private void loadWeeklySpecials(){




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                //    https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/example.json
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getWeeklySpecials();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {


                try{
                    JSONResponse jsonResponse = response.body();
                    Log.d("Json Response", "onResponse: "+response.body() );
                    data = new ArrayList<AndroidVersion>(Arrays.asList(jsonResponse.getAndroid()));
                    adapter = new DataAdapter(data);
                    recyclerView_WeeklySpecials.setAdapter(adapter);
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


    private void loadDietSpecials(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                //    https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/example.json
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getDietSpecials();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                try{
                    JSONResponse jsonResponse = response.body();
                    Log.d("Json Response", "onResponse: "+response.body() );
                    data = new ArrayList<AndroidVersion>(Arrays.asList(jsonResponse.getAndroid()));
                    adapter = new DataAdapter(data);
                    recyclerView_DietSpecials.setAdapter(adapter);
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


    private void loadSpicySpecials(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                //    https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/example.json
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getSpicySpecials();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                try{
                    JSONResponse jsonResponse = response.body();
                    Log.d("Json Response", "onResponse: "+response.body() );
                    data = new ArrayList<AndroidVersion>(Arrays.asList(jsonResponse.getAndroid()));
                    adapter = new DataAdapter(data);
                    recyclerView_SpicySpecials.setAdapter(adapter);
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

    private void loadQuickSpecials(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                //    https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/example.json
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getQuickSpecials();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                try{
                    JSONResponse jsonResponse = response.body();
                    Log.d("Json Response", "onResponse: "+response.body() );
                    data = new ArrayList<AndroidVersion>(Arrays.asList(jsonResponse.getAndroid()));
                    adapter = new DataAdapter(data);
                    recyclerView_QuickSpecials.setAdapter(adapter);
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


    private void loadNonVegSpecials(){



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                //    https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/example.json
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getNonVegSpecials();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                try{
                    JSONResponse jsonResponse = response.body();
                    Log.d("Json Response", "onResponse: "+response.body() );
                    data = new ArrayList<AndroidVersion>(Arrays.asList(jsonResponse.getAndroid()));
                    adapter = new DataAdapter(data);
                    recyclerView_NonVegSpecials.setAdapter(adapter);
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
