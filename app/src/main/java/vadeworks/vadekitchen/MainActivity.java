package vadeworks.vadekitchen;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import vadeworks.vadekitchen.adapter.DatabaseHelper;
import vadeworks.vadekitchen.adapter.generic_adapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView t;
    static int serverVersion, localVersion;
    ProgressDialog pd;
    DatabaseHelper myDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pd = new ProgressDialog(this);



        //Code To ask for User Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //Code to ask for User Permissions Ends





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(isNetworkConnected()) {
            SharedPreferences preferences = getSharedPreferences("only_once", Context.MODE_PRIVATE);
           if(preferences.getInt("first", 0) == 0) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("first", 1);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Please Wait!", Toast.LENGTH_SHORT).show();
                localVersion = preferences.getInt("version", 0);
                new baseNewsVersion().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/base_version.json");
            }
        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public class baseNewsVersion extends AsyncTask<String, String, String> {
        HttpURLConnection connection;
        BufferedReader reader;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject parent = new JSONObject(s);
                JSONObject base_version = parent.getJSONObject("base_version");
                serverVersion = base_version.getInt("version");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Compare Server Version of JSON and local version of JSON if not same download the new JSON content
           if (localVersion != serverVersion) {
                pd.setMessage("Fethcing for Cooking");
                pd.setIcon(R.mipmap.ic_launcher);
                pd.setCancelable(false);
                pd.show();
               // new baseFile().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/base.json");
                new baseFile().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/new_version.json");
                //new baseFile().execute("http://nammakarnataka.net23.net/general/base.json");
            }
            else {
                Toast.makeText(getApplicationContext(), "All Recipes are up to date!", Toast.LENGTH_SHORT).show();
           }
        }
    }

    public class baseFile extends AsyncTask<String, String, String> {

        HttpURLConnection connection;
        BufferedReader reader;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                String str = builder.toString();
                saveJsonFile(str);
                return str;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            pd.setMessage("Updating Database...");


            try {
                JSONObject parent = new JSONObject(s);
                JSONArray items = parent.getJSONArray("list");

                if (items != null){
                    if (pd.isShowing())
                        pd.dismiss();

                    myDBHelper = new DatabaseHelper(getApplicationContext());
                    myDBHelper.deleteTables();

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject child = items.getJSONObject(i);
                        JSONArray images = child.getJSONArray("image");

                        for (int j = 0; j < images.length(); j++) {
                            myDBHelper.insertIntoImages(child.getInt("id"),images.getString(j));
                        }
                       // myDBHelper.insertIntoPlace(child.getInt("id"), child.getString("name"), child.getString("description"), child.getString("district"), child.getString("bestSeason"), child.getString("additionalInformation"), child.getString("nearByPlaces"), child.getDouble("latitude"), child.getDouble("longitude"), child.getString("category"));
                        myDBHelper.insertIntoRecipe(child.getInt("id"), child.getString("name"), child.getString("time"), child.getString("ingredients"), child.getString("directions"), child.getString("category"));
                    }

                    SharedPreferences preferences = getSharedPreferences("base_version", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("version", serverVersion);
                    editor.commit();


                   // if(pd.isShowing())
                     //   pd.dismiss();

                    Toast.makeText(getApplicationContext(),"Update Successful",Toast.LENGTH_SHORT).show();

                }else {
                    SharedPreferences preferences = getSharedPreferences("base_version", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("version", localVersion);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Reatining The Same List",Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void saveJsonFile(String data) {
        FileOutputStream stream = null;
        try {
            //File path = new File("/data/data/smartAmigos.com.nammakarnataka/general.json");
            File path = new File("/data/data"+getApplicationContext().getPackageName()+"/"+"general.json");
            stream = new FileOutputStream(path);
            stream.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
                        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
                        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                        searchView.setOnQueryTextListener(
                                new SearchView.OnQueryTextListener(){

                                    @Override
                                    public boolean onQueryTextSubmit(String query) {
                                        myDBHelper = new DatabaseHelper(getApplicationContext());
                                        Cursor cursor = myDBHelper.getRecipeByString(query);
                                        Fragment fragment = new SearchResults(cursor);
                                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                        //ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                                        ft.replace(R.id.content_main, fragment);
                                        ft.commit();
                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String newText) {
                                        myDBHelper = new DatabaseHelper(getApplicationContext());
                                        Cursor cursor = myDBHelper.getRecipeByString(newText);
                                        Fragment fragment = new SearchResults(cursor);
                                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                        // ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                                        ft.replace(R.id.content_main, fragment);
                                        ft.commit();
                                        return false;
                                    }

                                }
                        );
                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    //Press Search and then If u press back, then the below mentioned fargment is loaded
                        Fragment fragment = new breakfastFragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        //ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        ft.replace(R.id.content_main, fragment);
                        //ft.hide(fragment);
                        //ft.detach(fragment);
                        ft.commit();

                        // Do whatever you need
                        return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
                        // When the action view is collapsed, reset the query
                       // townList.setVisibility(View.INVISIBLE);
                        // Return true to allow the action view to collapse

                    }
                });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_dev:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);//TODO: Create Dev Here
                startActivity(intent);
                break;


            case R.id.action_share:
                String str = "https://play.google.com/store/apps/details?id=" + getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "All you need to know about Karnataka\n\nDownload:\n" + str);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;


            case R.id.action_refresh:
                if (isNetworkConnected()) {
                    Toast.makeText(getApplicationContext(), "Please wait..", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("base_version", Context.MODE_PRIVATE);
                    localVersion = preferences.getInt("version", 0);
                    //new baseNewsVersion().execute("http://nammakarnataka.net23.net/general/base_version.json");
                    new baseFile().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/new_version.json");
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
            Fragment fragment;
            FragmentTransaction ft;
            Intent intent;
            int id = item.getItemId();

        switch (id) {
            case R.id.nav_appetizers:
                fragment = new appetizersFragment();
                //fragment = new riceitemsFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_breakfast:
                fragment = new breakfastFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_desserts:
                fragment = new breakfastFragment();
                //fragment = new dessertsFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_lunch:
                fragment = new breakfastFragment();
               // fragment = new lunchFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_curry:
                fragment = new breakfastFragment();
               // fragment = new curryFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_snacks:
                fragment = new breakfastFragment();
                //fragment = new snacksFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;



            case R.id.feedback:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;


            case R.id.nav_chicken:
                fragment = new breakfastFragment();
               // fragment = new chickenFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();

                break;


            case R.id.nav_home:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;


            case R.id.nav_cuisines:
                fragment = new breakfastFragment();
                //fragment = new cuisinesFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;


            case R.id.nav_fish:
                fragment = new breakfastFragment();
                //fragment = new fishFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_favourites:
                fragment = new favoritesFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
