package vadeworks.vadekitchen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

import vadeworks.vadekitchen.adapter.DatabaseHelper;



public class recipeDisplayActivity extends AppCompatActivity {
    int img_id;
    DatabaseHelper myDBHelper;
    Context context;
    String img,name,directions,time,ingredients;
    String sr;
    ImageView recipeImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);

        //ImageView recipe_image= (ImageView) findViewById(R.id.recipe_image);
        final TextView recipe_textView = (TextView) findViewById(R.id.recipe_textView);
        TextView time_textView = (TextView) findViewById(R.id.time_textView);
        TextView ingredients_textView = (TextView) findViewById(R.id.ingredients_textView);
        TextView directions_textView = (TextView) findViewById(R.id.directions_textView);
        recipeImage =(ImageView)findViewById(R.id.recipe_image);

        MaterialFavoriteButton favorite = (MaterialFavoriteButton)findViewById(R.id.favs);
        favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                        Snackbar.make(findViewById(R.id.activity_recipe_display), name+" Added to Favourites list", Snackbar.LENGTH_SHORT)
                               .setAction("Action", null).show();


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
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Recipe Name: "+name+"\n\n"+"Time Taken: "+time+"\n\n"+"Ingredients: "+ingredients+"\n\n"+"Directions: "+directions);
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
}
