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

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.Correlator;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

import vadeworks.vadekitchen.adapter.DatabaseHelper;



public class recipeDisplayActivity extends AppCompatActivity  {
    int img_id;
    DatabaseHelper myDBHelper;
    Context context;
    String img,name,directions,time,ingredients;
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
                if(i>47)
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
                                .make(findViewById(R.id.activity_recipe_display), "Favorites Added", Snackbar.LENGTH_LONG)
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
                                       // Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
                                       // snackbar1.show();
                                    }
                                });

                        snackbar.show();







                        //Snackbar.make(findViewById(R.id.activity_recipe_display), name+" Added to Favourites list", Snackbar.LENGTH_SHORT)
                          //    .setAction("Action", null).show();


                        myDBHelper = new DatabaseHelper(context);
                       myDBHelper.insertIntoFavourites(img_id,name);
                    }
                });
        favorite.setOnFavoriteAnimationEndListener(
                new MaterialFavoriteButton.OnFavoriteAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(MaterialFavoriteButton buttonView, boolean favorite) {

                        //myDBHelper = new DatabaseHelper(context);
                        //myDBHelper.insertIntoFavourites(img_id,name);
                        //myDBHelper = new DatabaseHelper(context);
                        //myDBHelper.deleteFromFavourites(img_id);

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


        /*ImageButton share = (ImageButton) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Recipe Name: "+name+"\n\n"+"Time Taken: "+time+"\n\n"+"Ingredients: "+ingredients+"\n\n"+"Directions: "+directions);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });*/








       /* ImageView favourite_icon = (ImageView) findViewById(R.id.fav_icon);
        favourite_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view," Added to Favourites list", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                myDBHelper = new DatabaseHelper(context);
                myDBHelper.insertIntoFavourites(img_id);

            }

        });*/



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            img_id = extras.getInt("img_id");
            name = extras.getString("name");
            sr=extras.getString("sr");
            time = extras.getString("time");
            directions = extras.getString("directions");
            ingredients = extras.getString("ingredients");
            img=extras.getString("img");

           // Toast.makeText(recipeDisplayActivity.this, uri, Toast.LENGTH_LONG).show();
            //The key argument here must match that used in the other activity
        }
        Picasso.with(this)
                .load(sr)
                .fit()
                .centerCrop()
                .noFade()
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
            //  additionalInformation.setText(cursor.getString(5));
            //nearPlaces = cursor.getString(6);
            //  latitude = cursor.getDouble(7);
            // longitude = cursor.getDouble(8);
        }


        /*SliderLayout sliderShow = (SliderLayout) findViewById(R.id.layout_images);
        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView
                .description(name)
                .image(uri)
                .setScaleType(BaseSliderView.ScaleType.Fit);
         //.image("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        sliderShow.addSlider(textSliderView);*/


       // mDemoSlider = (SliderLayout) findViewById(R.id.layout_images);
       // TextSliderView textSliderView;
        //String[] imagesArray = new String[25];
        //Cursor imageURLCursor = myDBHelper.getAllImagesArrayByID(img_id);
        //for (int i=0;imageURLCursor.moveToNext();i++){
         //   imagesArray[i] = imageURLCursor.getString(0);
        //}

        //mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        //mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        //mDemoSlider.setDuration(7000);

        //displayList();


        return;



    }




    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
}
