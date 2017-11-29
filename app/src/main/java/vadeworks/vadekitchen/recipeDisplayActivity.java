package vadeworks.vadekitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Path;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.Correlator;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.zip.Inflater;

import vadeworks.vadekitchen.adapter.DatabaseHelper;



public class recipeDisplayActivity extends AppCompatActivity  {
    int img_id;
    DatabaseHelper myDBHelper;
    Context context;
    String img,name,directions,time,ingredients,youtubeLink;
    String sr;
    ImageView recipeImage;
    private InterstitialAd interstitial;
    int i = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);

        CoordinatorLayout  co= (CoordinatorLayout)findViewById(R.id.activity_recipe_display);
        co.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-ge`enter code here`nerated method stub
                Log.d("Touching","Touching");
                i++;
                if(i>1)
                {
                    i=0;
                    displayInterstitial();
                }
                return false;
            }
        });


        android.support.v7.app.ActionBar AB = getSupportActionBar();
            AB.hide();


        NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
        .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37")
               .build();
 adView.loadAd(request);



        //Interstitial Ad Space
        AdRequest adRequests = new AdRequest.Builder()
                .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37")
                .build();
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(recipeDisplayActivity.this);
// Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.recipeDisplay_interstitial_id));
        interstitial.loadAd(adRequests);
// Prepare an Interstitial Ad Listener
//        interstitial.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                // Call displayInterstitial() function
//                displayInterstitial();
//            }
//        });
// Interstetial ad Finished


        final TextView recipe_textView = (TextView) findViewById(R.id.recipe_textView);
        TextView time_textView = (TextView) findViewById(R.id.time_textView);
        TextView ingredients_textView = (TextView) findViewById(R.id.ingredients_textView);
        TextView directions_textView = (TextView) findViewById(R.id.directions_textView);
        recipeImage =(ImageView)findViewById(R.id.recipe_image);
        TextView onPicText= (TextView)findViewById(R.id.onPicText);


        Typeface regular_font =Typeface.createFromAsset(this.getAssets(),"fonts/Aller_Rg.ttf");
        Typeface heading_font = Typeface.createFromAsset(this.getAssets(),"fonts/AllerDisplay.ttf");



        TextView time_heading=(TextView)findViewById(R.id.time);
        TextView ingredients_heading=(TextView)findViewById(R.id.ingredients);
        TextView directions_heading=(TextView)findViewById(R.id.directions);
        time_heading.setTypeface(heading_font);
        ingredients_heading.setTypeface(heading_font);
        directions_heading.setTypeface(heading_font);
        recipe_textView.setTypeface(heading_font);
        onPicText.setTypeface(heading_font);
        time_textView.setTypeface(regular_font);
        ingredients_textView.setTypeface(regular_font);
        directions_textView.setTypeface(regular_font);




        Toolbar toolbar = (Toolbar)findViewById(R.id.tool);
       // setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //ImageView recipe_image= (ImageView) findViewById(R.id.recipe_image);


        MaterialFavoriteButton favorite = (MaterialFavoriteButton)findViewById(R.id.favs);
        favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.activity_recipe_display), "Added to Favorites ", Snackbar.LENGTH_LONG)
                                .setAction("View", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Fragment fragment;
                                        FragmentTransaction ft;
                                        fragment = new favoritesFragment();
                                        ft = getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.activity_recipe_display, fragment);
                                        ft.addToBackStack(null);
                                        ft.commit();

                                    }
                                });

                        snackbar.show();

                        myDBHelper = new DatabaseHelper(context);
                       myDBHelper.insertIntoFavourites(img_id,name);
                    }
                });
        favorite.setOnFavoriteAnimationEndListener(
                new MaterialFavoriteButton.OnFavoriteAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(MaterialFavoriteButton buttonView, boolean favorite) {


                    }
                });

        MaterialFavoriteButton share = (MaterialFavoriteButton)findViewById(R.id.share);
        share.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        Intent sendIntent = new Intent();
                        String str = "https://play.google.com/store/apps/details?id=" + getPackageName();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Recipe Name: "+name+"\n\n"+"Time Taken: "+time+"\n\n"+"Ingredients: "+ingredients+"\n\n"+"Directions: "+directions+"\n\n"
                                        +"One Stop for your Indian Cusine Dishes.  Download Now:\n"+str);

                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                });


        MaterialFavoriteButton play = (MaterialFavoriteButton)findViewById(R.id.play);
        play.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        Intent intent = new Intent(getApplicationContext(), YoutubePlayerActivity.class);
                        intent.putExtra("youtubeLink",youtubeLink);
                        startActivity(intent);
                    }
                });




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            img_id = extras.getInt("img_id");
            name = extras.getString("name");
            sr=extras.getString("sr");
            time = extras.getString("time");
            directions = extras.getString("directions");
            ingredients = extras.getString("ingredients");
            img=extras.getString("img");
            youtubeLink = extras.getString("videoUrl");
//            Toast.makeText(recipeDisplayActivity.this, youtubeLink, Toast.LENGTH_LONG).show();
            //The key argument here must match that used in the other activity
        }


        RequestOptions options = new RequestOptions();
        options.centerCrop();
//        options.placeholder(R.drawable.burger);
        options.error(R.drawable.background);

        Glide
                .with(this) // replace with 'this' if it's in activity
                .load(sr)
                .apply(options)
                .into(recipeImage);


        recipe_textView.setText(extras.getString("name"));
        time_textView.setText(extras.getString("time"));
        ingredients_textView.setText(extras.getString("ingredients"));
        directions_textView.setText(extras.getString("directions"));

        onPicText.setText(extras.getString("name"));


        context = getApplicationContext();
        myDBHelper = new DatabaseHelper(getApplicationContext());

        Cursor cursor = myDBHelper.getRecipeById(img_id);


        while (cursor.moveToNext()){
            recipe_textView.setText(cursor.getString(1));
            time_textView.setText(cursor.getString(2));
            ingredients_textView.setText(cursor.getString(3));
            directions_textView.setText(cursor.getString(4));
        }




        return;



    }




    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
}
