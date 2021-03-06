package vadeworks.vadekitchen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;

import java.util.zip.Inflater;

import vadeworks.vadekitchen.adapter.DatabaseHelper;



public class offlineRecipeDisplayActivity extends AppCompatActivity {
    int img_id;
    DatabaseHelper myDBHelper;
    Context context;
    String img,name,directions,time,ingredients,youtubeLink;
    String sr;
    ImageView recipeImage;
    private InterstitialAd interstitial;
    int i=0;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_recipe_display);
        android.support.v7.app.ActionBar AB = getSupportActionBar();
        AB.hide();

        final TextView recipe_textView = (TextView) findViewById(R.id.recipe_textView);
        TextView time_textView = (TextView) findViewById(R.id.time_textView);
        TextView ingredients_textView = (TextView) findViewById(R.id.ingredients_textView);
        TextView directions_textView = (TextView) findViewById(R.id.directions_textView);
        recipeImage =(ImageView)findViewById(R.id.recipe_image);
        TextView onPicText= (TextView)findViewById(R.id.onPicText);


        CoordinatorLayout co= (CoordinatorLayout)findViewById(R.id.activity_recipe_display);
        co.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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


        //Interstitial Ad Space
        AdRequest adRequests = new AdRequest.Builder()
                .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37")
                .build();
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(offlineRecipeDisplayActivity.this);
// Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.recipeDisplay_interstitial_id));
        interstitial.loadAd(adRequests);


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
            youtubeLink = extras.getString("youtubeLink");


            // Toast.makeText(recipeDisplayActivity.this, uri, Toast.LENGTH_LONG).show();
            //The key argument here must match that used in the other activity
        }


        RequestOptions options = new RequestOptions();
        options.centerCrop();
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


