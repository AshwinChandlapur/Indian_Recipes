package vadeworks.vadekitchen;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import vadeworks.vadekitchen.adapter.DatabaseHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static int serverVersion, localVersion;
    ProgressDialog pd;
    DatabaseHelper myDBHelper;


    String [] d1= {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/aloo-gobi-10-min.jpg","Bhapaa Aloo", "50 Minutes","•200 gm small potatoes \n•2 tsp mustard oil \n•1/2 tsp Bengali five spice mixture (panchphoron) (whole jeera, saunf seeds, fenugreek seeds, black mustrad seeds and kalaunji) \n•2 dry red chillies \n•1/2 tsp mustard paste \n•1 tsp hung curd \n•3/4 tsp desiccated coconut paste \n•Pinch of green chilli paste \n•Pinch of turmeric powder \n•Salt - to taste \n•Dash of lime juice \n•2 banana leaves","•Peel the potatoes and par boil them in salted water, drain and keep aside. \n\n•Heat oil in a non-stick pan, add the five spice mixture, break the red chillies in half and add them next. \n\n•Stir the spices around till they splutter.Pour this over the potatoes and put aside \n\n•In a mixing bowl prepare a marinade with the mustard paste, curd, coconut paste, green chilli paste and turmeric powder, whip it well. \n\n•Gently mix the potatoes into this marinade. \n\n•Add the salt and lime juice, mix again. \n\n•Put the potatoes on a steel plate, cover with the banana leaves and steam them for 6-8 minutes. Serve hot."};

    String [] d2 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/hyderabadi%20biryani%20new-min.jpg",
            "Hyderabadi Biryani","1 Hour 10 Minutes",
            "•1 kg meat \n•1 Tbsp salt \n•1 Tbsp ginger garlic paste \n•1 Tbsp red chilli paste \n•1 Tbsp green chilli paste sauteed brown onions to taste \n•1/2 Tbsp cardamom powder \n•3-4 sticks cinnamon \n•1 Tbsp cumin seeds \n•4 cloves pinch of mace mint leaves to taste \n•2 Tbsp lemon juice \n•250 gm curd \n•4 Tbsp clarified butter \n•750 gm semi cooked rice \n•1 tsp saffron \n•1/2 cup water \n•1/2 cup of oil \n•boiled eggs \n•carrots,sliced \n•cucumbers",
            "•Clean the meat. Then in a pan add meat, salt, ginger garlic paste, red chilli powder, green chilli paste, sauteed brown onions, cardamom powder, cinnamon, cumin seeds, cloves, mace, mint leaves and lemon juice. Mix it well. \n\n• Add curd, clarified butter, semi cooked rice, saffron, water and oil and mix it well. \n\n• Now apply sticky dough on the sides of the pan. Cover with lid to seal it and cook for about 25 minutes. \n\n• Hyderabadi Biryani is ready to eat. Garnish it with boiled eggs, sliced carrots, cucumbers and serve hot."};


    String [] d3 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/bisi%20bele%20bhaat-min.jpg",
            "Bisibelebath","45 Minutes",
            "•1/2 cup rice \n•1/4 cup toor dal \n•1 1/2 chopped vegetables \n•4 Tbsp chana dal \n•2 Tbsp urad dal \n•2 tsp jeera \n•1/4 tsp fenugreek \n•1 tsp pepper \n•3 red chillies \n•1 tsp couscous \n•2 Tbsp grated coconut \n•1 sprig of curry leaves \n•1 tsp turmeric powder \n•1 Tbsp jaggery \n•1 tamarind \n•2 Tbsp oil \n•Salt to taste",
            "•Cook the rice and toor dal and keep it aside. \n\n•Soak the tamarind in hot water and extract the tamarind water. \n\n•Boil the vegetables. \n\n•In a pan heat oil and add chana dal, urad dal, jeera, fenugreek, pepper, red chilli. Fry until dal turns light brown. \n\n•Fry couscous and curry leaves.  Grind the fried ingredients with coconut. \n\n•Boil the tamarind water for 1 to 2 minutes. Add turmeric powder, salt and jaggery to it. \n\n•Add vegetables and allow it to boil for 5 to 6 minutes. \n\n•Add the grounded mixture. Let it boil for 2 to 3 minutes. Add toor dal followed by rice and cook for 2 minutes. \n\n•Heat oil in a pan and fry groundnut, channa dal,urad dal and mustard. \n\n•Add the fired ingredients to the bisi belebath. \n\n•Garnish with boondhi and coriander leaves. Serve hot."};

    String [] d4 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/falhari-pakode-new-min.jpg", "Falhari Pakore","20 Minutes", "•3 to 4 medium potatoes \n•5 Tbsp buckwheat flour \n•1 green chili, chopped \n•1 tsp anardana \n•1/2 tsp cumin powder \n•3/4 to 1 cup water, as required \n•Oil for frying \n•Rock salt, as required", "•Wash, peel and cut the potatoes into small cubes. Mix in the buckwheat flour. \n\n•Add the rest of the ingredients to the potatoes and mix well. \n\n•Add little water in intervals and keep on mixing till you get a thick batter. It should be thicker than idli batter. \n\n•Heat oil in a pan for deep frying. With the help of a tablespoon, shape the batter with your hands and drop the shaped batter in hot oil. \n\n•Fry till the pakoras are golden and crisp. Drain the excessive oil with the help of a tissue. \n\n•Serve them hot with freshly made mint yoghurt dip."};

    String [] d5 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/malai-kofta-curry-03-700x525-min.jpg", "Malai Kofta","1 Hour 40 Minutes", "•4 big potatoes, boiled \n•250 gm paneer  (cottage cheese) \n•50 gm maida \n•1 Tbsp coriander leaves, chopped \n•3 onions \n•1 Tbsp ginger garlic paste \n•2 tomatoes \n•200 ml malai or cream \n•2 Tbsp raisins and cashew nuts \n•50 gms cashew nuts paste \n•1/2 tsp haldi (turmeric) \n•1/2 tsp red chilli powder \n•1/2 tsp kitchen king masala \n•1 Tbsp kasturi methi \n•Salt to Taste \n•1Tbsp sugar", "For the koftas: \n\n•Refrigerate the boiled potatoes for 4 to 6 hours as this makes it easy to cook koftas. \n\n•Mash the boiled potatoes, paneer, maida. The mix should not be too hard or too soft. Add salt, chopped coriander leaves and mix well. \n\n•Cut the raisins and cashew nuts into very small pieces and add 1/2 tsp of sugar to the mix. \n\n•Heat up the oil for deep frying. Roll out the balls from the dough you prepared and stuff the dry fruit mix in the centre. \n•Fry the koftas and if they break in hot oil then dust them with dry maida before putting them in. \n\nFor the gravy: \n\n•Fry some onion, ginger garlic paste and tomato paste. \n\n•Mix the cashew nut paste with 2 Tbsp of warm milk and pour it into the paste. \n\n•Except kasturi methi, add all the dry masala into the paste and saute till the oil separates itself. \n\n•Add  and a half cup of water and simmer the gravy till it's done. \n\n•Add cream/malai, 1 Tbsp of sugar and kasturi methi. \n\n•Simmer the gravy till the oil starts separating and once it's done, put the fried koftas into the gravy and serve hot with chapatis. "};

    String [] d6 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/Gaajar-Halwa_600-min.jpg", "Gaajar Halwa","40 Minites", "   •1 1/2 kg carrots \n•10 green cardamom, whole \n•2 cinnamon sticks \n•500 g sugar \n•250 g desi ghee(clarified butter) \n•400 g condensed milk (option: mawa / khoya) \n•4 pieces gold vark \n•50 g almonds \n•50 g pistachios", "   •Grate the carrot and put it in a pot on heat, stir fry continuously till all the water is evaporated. \n\n•Then put some green cardamom, cinnamon and sugar and cook for a while. Then add desi ghee and cook 5-7 minutes further.\n\n•At last, finish with condensed milk (or mawa/khoya). \n•Place gold vark on top for decoration. \n\n•Present on a plate and garnish with pistachios and almonds."};

    String [] d7 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/Aam-Shrikhand-with-Mango-Salad_med-min.jpg", "Aam Shrikhand with Mango Salad","40 Minutes", "•2 cups hung yogurt(hung curd) \n•1/2  tsp saffron \n•2 tins condensed milk \n•300 gm fresh mangoes \n•1/2 cup sugar \n•2 tsp green cardamom powder \n•1/4 cup fresh cream \n•Juice of 1 lemon \n•20 gm mint \n•2 tsp chaat masala \n•4 sheets gold varq \n•80 gm pistachio, peeled and not salted", "   •Mix hung curd with saffron, condensed milk, chopped mangoes, sugar, cardamom powder and cream. Leave to chill. \n\n•Prepare mango salad by mixing diced mango with lemon juice, mint and chaat masala. \n\n•Serve shrikhand with mango salad. Garnish with gold varq and pistachios. "};

    String [] d8 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/l8-min.jpg", "Kohlapuri Chicken", "2 Hours","  For the marination: \n•1 kg chicken \n•2/3 cup yoghurt \n•1 tsp turmeric powder \n•2 tsp red chilli \n•1 tsp garlic paste \n•Salt to taste \n•1 tsp lime juice \n\nFor the Kohlapur masala: \n•2 tsp corn or peanut oil \n•1 bay leaf \n•2 cinnamon sticks \n•6  cloves \n•1/2 tsp crushed black pepper \n•2 medium onions, chopped \n•2 tsp grated coconut \n•1 large tomato, chopped \n\nFor the main preparation: \n•2 tsp oil \n•1 tsp coriander leaves", "For the marination: \n•Mix turmeric powder, red chillies, garlic paste, salt, and lime juice with curd. The marinade is ready. \n•Add the cut chicken pieces to the marinade and leave aside for half an hour. \n\nFor the Kohlapur masala: \n•Heat oil in pan. Add bay leaf, cinnamon, cloves, crushed black pepper and chopped onions. Saute till onions turn translucent. \n•Add grated coconut. Saute till coconut color changes. \n•Add tomatoes. Cook for 10 minutes. Cool and puree the mix in a blender. \n\nFor the main preparation: \n•Heat oil and add the marinated chicken to the pan. Cook for 25 minutes on low fire. Stir regularly. \n•Add paste and simmer on low flame for five minutes. \n•Kolhapur chicken is ready. Garnish with coriander leaves. \n•Serve hot with rice."};

    String [] d9  = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/51154870_chicken-tikka-masala_1x1-min.jpg", "Chicken Tikka Masala","1 Hour", "       •6 garlic cloves, finely grated \n•4 teaspoons finely grated peeled ginger \n•4 teaspoons ground turmeric \n•2 teaspoons garam masala \n•2 teaspoons ground coriander \n•2 teaspoons ground cumin \n•1 1/2 cups whole-milk yogurt (not Greek) \n•1 tablespoon kosher salt \n•2 pounds skinless, boneless chicken breasts, halved lengthwise \n•3 tablespoons ghee (clarified butter) or vegetable oil \n•1 small onion, thinly sliced \n•1/4 cup tomato paste \n•6 cardamom pods, crushed \n•2 dried chiles de árbol or 1/2 teaspoon crushed red pepper flakes \n•1 28-ounce can whole peeled tomatoes \n•2 cups heavy cream \n•3/4 cup chopped fresh cilantro plus sprigs for garnish \n•Steamed basmati rice (for serving)", " •Combine garlic, ginger, turmeric, garam masala, coriander, and cumin in a small bowl. Whisk yogurt, salt, and half of spice mixture in a medium bowl; add chicken and turn to coat. Cover and chill 4-6 hours. Cover and chill remaining spice mixture. \n\n•Heat ghee in a large heavy pot over medium heat. Add onion, tomato paste, cardamom, and chiles and cook, stirring often, until tomato paste has darkened and onion is soft, about 5 minutes. Add remaining half of spice mixture and cook, stirring often, until bottom of pot begins to brown, about 4 minutes. \n\n•Add tomatoes with juices, crushing them with your hands as you add them. Bring to a boil, reduce heat, and simmer, stirring often and scraping up browned bits from bottom of pot, until sauce thickens, 8-10 minutes. \n\n•Add cream and chopped cilantro. Simmer, stirring occasionally, until sauce thickens, 30-40 minutes. \n\n•Meanwhile, preheat broiler. Line a rimmed baking sheet with foil and set a wire rack inside sheet. Arrange chicken on rack in a single layer. Broil until chicken starts to blacken in spots (it will not be cooked through), about 10 minutes. \n\n•Cut chicken into bite-size pieces, add to sauce, and simmer, stirring occasionally, until chicken is cooked through, 8-10 minutes. Serve with rice and cilantro sprigs. "};

    String [] d10 ={"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/mango-lassi-aam-lassi-2-min.jpg", "Mango Lassi","10 Minutes", "•1 cup plain yogurt \n•1/2 cup milk \n•1 cup chopped very ripe mango ,frozen chopped mango, or a cup of canned mango pulp \n•4 teaspoons honey or sugar, more or less to taste \n•A dash of ground cardamom (optional) \n•Ice (optional)", "•Put mango, yogurt, milk, sugar and cardamom into a blender and blend for 2 minutes. \n\n•If you want a more milkshake consistency and it's a hot day, either blend in some ice as well or serve over ice cubes. \n\n•Sprinkle with a tiny pinch of ground cardamom to serve."};

    String [] d11 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/Tadka-Dhal-min.jpg", "Tadka-Dhal","1 Hour", "       •1 1/2 cups dried toor dhal (yellow lentils/yellow split peas), rinsed in several changes of water \n•1 teaspoon ground turmeric \n•2 black cardamom pods (optional) \n•3 tablespoons vegetable oil \n•2 cinnamon sticks \n•4 green cardamom pods \n•6 cloves \n•2 teaspoons black mustard seeds \n•1 teaspoon cumin seeds \n•2 scallions, finely sliced \n•2–3 chiles, any color, seeded if you don’t like it fiery, some chopped and the rest left whole \n•2 fat cloves garlic, finely chopped \n•1 tablespoon peeled and finely chopped fresh ginger \n•6 cherry tomatoes, cut in half \n•Good pinch of sea salt, or to taste \n•1 teaspoon sugar, or to taste \n•Juice of 1/2 lemon, or to taste", "\n•Gently boil the lentils in a large saucepan of cold water (around 4 cups will do) and stir in the turmeric and black cardamom pods (if using)—this will add a subtle smoky flavor. Allow to cook for around 45 minutes, or until the lentils have softened and started to break down. Skim off any foam that sits on the top and give the lentils a stir every now and again in case they begin to stick on the bottom. If they boil dry, add more water. \n\n•Once the lentils have softened, turn down the heat and make the tadka. Gently heat the oil in a skillet and add the cinnamon sticks, green cardamom pods and cloves. When the cardamoms have turned white and the heads of the cloves have swollen, you are ready to stir in the mustard and cumin seeds. When they are sizzling, stir in the scallions, chiles, garlic and ginger. \n\n•After a minute, stir through the tomatoes and turn off the heat. Pour the tadka into the dhal so that it floats on top. Season with salt, sugar and lemon juice. Finally, stir through plenty of chopped cilantro and serve with some rice or fresh bread for the ultimate comfort food. "};

    String [] d12 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/pav.bhaji%20(1)-min.jpg", "Pav Bhaji", "40 Minutes", "   •2 tbsp oil \n•1 large onion chopped finely \n•1 tsp ginger, minced \n•1 tsp minced garlic \n•2 green chillies slit lengthwise \n•1 bell pepper, finely chopped \n•3 tomatoes, finely chopped \n•1 cup each of each of these vegetables, finely diced and boiled: green beans, carrots, cauliflower \n•1 cup boiled and peeled potatoes \n•1/2 cup boiled green peas \n•3 tsp pav bhaji masala \n•1 tsp red chili powder \n•1/4 tsp turmeric powder \n•Salt to taste \n•1/2 tsp lemon juice \n•2 buns (called pavs)", "•Heat oil in a pan. Add onions, ginger, garlic, and green chillies and sauté till golden brown. Add boiled vegetables and stir. \n\n•Stir in the pav bhaji masala, turmeric, tomatoes, capsicum and salt. Add slit green chillies and water and mix it well. Cook for 4-5 minutes.\n\n•Mash the vegetables till they are little pulpy. \n\n•Add lemon juice and serve with toasted buns or other bread."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code To ask for User Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //Code to ask for User Permissions Ends


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(!(android.os.Build.VERSION.SDK_INT <21))
            setSupportActionBar(toolbar);

                if(isNetworkConnected()) {
                    SharedPreferences preferences = getSharedPreferences("only_once", Context.MODE_PRIVATE);
                    if(preferences.getInt("first", 0) == 0) // First Open of App.
                    {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("first", 1);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Please Wait!", Toast.LENGTH_SHORT).show();
                        localVersion = preferences.getInt("version", 0);
                        new baseNewsVersion().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/base_version.json");
                        new baseFile().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/new_version.json");
                    }
                }
                else
                {
                    SharedPreferences preferences = getSharedPreferences("only_once", Context.MODE_PRIVATE);
                    if (preferences.getBoolean("firstrun", true)) {
                        Toast.makeText(this, "Please Connect to Internet!", Toast.LENGTH_LONG).show();
                        preferences.edit().putBoolean("firstrun", false).commit();
                    }

                }
        pd = new ProgressDialog(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (R.id.fab){
                    case R.id.fab:
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.openDrawer(GravityCompat.START);
                        break;
                    default:
                        DrawerLayout drawers = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawers.closeDrawer(Gravity.START);
                        break;
                }
            }
        });

        Typeface regular_font =Typeface.createFromAsset(this.getAssets(),"fonts/Aller_Rg.ttf");

        HorizontalScrollView h1 = (HorizontalScrollView)findViewById(R.id.h1);
        h1.setBackground(getResources().getDrawable(R.drawable.h1));

        HorizontalScrollView h2 = (HorizontalScrollView)findViewById(R.id.h2);
        h2.setBackground(getResources().getDrawable(R.drawable.h2));

        HorizontalScrollView h3 = (HorizontalScrollView)findViewById(R.id.h3);
        h3.setBackground(getResources().getDrawable(R.drawable.h3));
        //setSupportActionBar(toolbar);
        // Picasso.with(this).load("https://images6.alphacoders.com/336/336514.jpg").apply(options).centerCrop().into(h1);


        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.background);
        options.error(R.drawable.background);

        CardView c1 =(CardView)findViewById(R.id.c1);
        ImageView i1 = (ImageView)findViewById(R.id.i1);
        TextView t1 = (TextView)findViewById(R.id.t1);
        t1.setTypeface(regular_font);
        t1.setText(d1[1]);
        Glide.with(this).load(d1[0]).apply(options).into(i1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d1[1]);
                intent.putExtra("time",d1[2]);
                intent.putExtra("ingredients",d1[3]);
                intent.putExtra("directions",d1[4]);
                intent.putExtra("img",d1[0]);
                intent.putExtra("sr",d1[0]);
                startActivity(intent);
            }
        });


        CardView c2 =(CardView)findViewById(R.id.c2);
        ImageView i2 = (ImageView)findViewById(R.id.i2);
        TextView t2 =(TextView)findViewById(R.id.t2);
        t2.setTypeface(regular_font);
        t2.setText(d2[1]);
        Glide.with(this).load(d2[0]).apply(options).into(i2);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d2[1]);
                intent.putExtra("time",d2[2]);
                intent.putExtra("ingredients",d2[3]);
                intent.putExtra("directions",d2[4]);
                intent.putExtra("img",d2[0]);
                intent.putExtra("sr",d2[0]);
                startActivity(intent);

            }
        });

        CardView c3 =(CardView)findViewById(R.id.c3);
        ImageView i3 = (ImageView)findViewById(R.id.i3);
        TextView t3 = (TextView)findViewById(R.id.t3);
        t3.setTypeface(regular_font);
        t3.setText(d3[1]);
        Glide.with(this).load(d3[0]).apply(options).into(i3);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d3[1]);
                intent.putExtra("time",d3[2]);
                intent.putExtra("ingredients",d3[3]);
                intent.putExtra("directions",d3[4]);
                intent.putExtra("img",d3[0]);
                intent.putExtra("sr",d3[0]);
                startActivity(intent);

            }
        });

        CardView c4 =(CardView)findViewById(R.id.c4);
        ImageView i4 = (ImageView)findViewById(R.id.i4);
        TextView t4 = (TextView)findViewById(R.id.t4);
        t4.setTypeface(regular_font);
        t4.setText(d4[1]);
        Glide.with(this).load(d4[0]).apply(options).into(i4);
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d4[1]);
                intent.putExtra("time",d4[2]);
                intent.putExtra("ingredients",d4[3]);
                intent.putExtra("directions",d4[4]);
                intent.putExtra("img",d4[0]);
                intent.putExtra("sr",d4[0]);
                startActivity(intent);
            }
        });


        CardView c5 =(CardView)findViewById(R.id.c5);
        ImageView i5 = (ImageView)findViewById(R.id.i5);
        TextView t5 = (TextView)findViewById(R.id.t5);
        t5.setTypeface(regular_font);
        t5.setText(d5[1]);
        Glide.with(this).load(d5[0]).apply(options).into(i5);
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d5[1]);
                intent.putExtra("time",d5[2]);
                intent.putExtra("ingredients",d5[3]);
                intent.putExtra("directions",d5[4]);
                intent.putExtra("img",d5[0]);
                intent.putExtra("sr",d5[0]);
                startActivity(intent);
            }
        });


        CardView c6 =(CardView)findViewById(R.id.c6);
        ImageView i6 = (ImageView)findViewById(R.id.i6);
        TextView t6 = (TextView)findViewById(R.id.t6);
        t6.setTypeface(regular_font);
        t6.setText(d6[1]);
        Glide.with(this).load(d6[0]).apply(options).into(i6);
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d6[1]);
                intent.putExtra("time",d6[2]);
                intent.putExtra("ingredients",d6[3]);
                intent.putExtra("directions",d6[4]);
                intent.putExtra("img",d6[0]);
                intent.putExtra("sr",d6[0]);
                startActivity(intent);
            }
        });


        CardView c7 =(CardView)findViewById(R.id.c7);
        ImageView i7 = (ImageView)findViewById(R.id.i7);
        TextView t7 = (TextView)findViewById(R.id.t7);
        t7.setTypeface(regular_font);
        t7.setText(d7[1]);
        Glide.with(this).load(d7[0]).apply(options).into(i7);
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d7[1]);
                intent.putExtra("time",d7[2]);
                intent.putExtra("ingredients",d7[3]);
                intent.putExtra("directions",d7[4]);
                intent.putExtra("img",d7[0]);
                intent.putExtra("sr",d7[0]);
                startActivity(intent);
            }
        });


        CardView c8 =(CardView)findViewById(R.id.c8);
        ImageView i8 = (ImageView)findViewById(R.id.i8);
        TextView t8 = (TextView)findViewById(R.id.t8);
        t8.setTypeface(regular_font);
        t8.setText(d8[1]);
        Glide.with(this).load(d8[0]).apply(options).into(i8);
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d8[1]);
                intent.putExtra("time",d8[2]);
                intent.putExtra("ingredients",d8[3]);
                intent.putExtra("directions",d8[4]);
                intent.putExtra("img",d8[0]);
                intent.putExtra("sr",d8[0]);
                startActivity(intent);
            }
        });


        CardView c9 =(CardView)findViewById(R.id.c9);
        ImageView i9 = (ImageView)findViewById(R.id.i9);
        TextView t9 = (TextView)findViewById(R.id.t9);
        t9.setTypeface(regular_font);
        t9.setText(d9[1]);
        Glide.with(this).load(d9[0]).apply(options).into(i9);
        c9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d9[1]);
                intent.putExtra("time",d9[2]);
                intent.putExtra("ingredients",d9[3]);
                intent.putExtra("directions",d9[4]);
                intent.putExtra("img",d9[0]);
                intent.putExtra("sr",d9[0]);
                startActivity(intent);
            }
        });

        CardView c10 =(CardView)findViewById(R.id.c10);
        ImageView i10 = (ImageView)findViewById(R.id.i10);
        TextView t10 = (TextView)findViewById(R.id.t10);
        t10.setTypeface(regular_font);
        t10.setText(d10[1]);
        Glide.with(this).load(d10[0]).apply(options).into(i10);
        c10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d10[1]);
                intent.putExtra("time",d10[2]);
                intent.putExtra("ingredients",d10[3]);
                intent.putExtra("directions",d10[4]);
                intent.putExtra("img",d10[0]);
                intent.putExtra("sr",d10 [0]);
                startActivity(intent);
            }
        });

        CardView c11 =(CardView)findViewById(R.id.c11);
        ImageView i11 = (ImageView)findViewById(R.id.i11);
        TextView t11 = (TextView)findViewById(R.id.t11);
        t11.setTypeface(regular_font);
        t11.setText(d11[1]);
        Glide.with(this).load(d11[0]).apply(options).into(i11);
        c11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d11[1]);
                intent.putExtra("time",d11[2]);
                intent.putExtra("ingredients",d11[3]);
                intent.putExtra("directions",d11[4]);
                intent.putExtra("img",d11[0]);
                intent.putExtra("sr",d11[0]);
                startActivity(intent);
            }
        });

        CardView c12 =(CardView)findViewById(R.id.c12);
        ImageView i12 = (ImageView)findViewById(R.id.i12);
        TextView t12 = (TextView)findViewById(R.id.t12);
        t12.setTypeface(regular_font);
        t12.setText(d12[1]);
        Glide.with(this).load(d12[0]).apply(options).into(i12);
        c12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d12[1]);
                intent.putExtra("time",d12[2]);
                intent.putExtra("ingredients",d12[3]);
                intent.putExtra("directions",d12[4]);
                intent.putExtra("img",d12[0]);
                intent.putExtra("sr",d12[0]);
                startActivity(intent);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


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
        protected void onPostExecute(final String  s) {
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
                        pd.setMessage("Stirring Things Up. Please Wait.....");
                        pd.setIcon(R.mipmap.ic_launcher);
                        pd.setCancelable(false);
                        pd.show();
                        // new baseFile().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/base.json");
                        new baseFile().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/new_version.json");
                        //new baseFile().execute("http://nammakarnataka.net23.net/general/base.json");
                    }
                    else {
                            Log.d("OKOK","OKOK");
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
        protected void onPostExecute(final String s) {
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





                        }else {
                            SharedPreferences preferences = getSharedPreferences("base_version", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("version", localVersion);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"Reatining The Same List",Toast.LENGTH_SHORT).show();
                        }

                        if(pd.isShowing())
                            pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Update Successful",Toast.LENGTH_SHORT).show();

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
                                        ft.replace(R.id.app_bar, fragment);
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
                                        ft.replace(R.id.app_bar, fragment);
                                        ft.commit();

                                        return false;
                                    }

                                }
                        );
                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        //((Activity) getApplicationContext()).overridePendingTransition(0,0);
                    //Press Search and then If u press back, then the below mentioned fargment is loaded
                      //  Fragment fragment = new breakfastFragment();
                       // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        //ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                       // ft.replace(R.id.app_bar, fragment);
                       // ft.hide(fragment);
                       // ft.detach(fragment);
                       // ft.commit();

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


            case android.R.id.home:
                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;


            case R.id.action_share:
                String str = "https://play.google.com/store/apps/details?id=" + getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "One Stop for your Indian Cusine Dishes\nDownload Now:\n" + str);
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
            case R.id.nav_home:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_breakfast:
                fragment = new breakfastFragment();
                ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.app_bar, fragment);
                //ft.disallowAddToBackStack()
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_appetizers:
                fragment = new appetizersFragment();
                //fragment = new riceitemsFragment();
                ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_main_course:
                fragment = new maincourseFragment();
                // fragment = new lunchFragment();
                ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;



            case R.id.nav_dessert:
                fragment = new dessertFragment();
                //fragment = new dessertsFragment();
                ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_snacks:
                fragment = new snacksFragment();
                //fragment = new snacksFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_festive:
                fragment = new festiveFragment() ;
               // fragment = new curryFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_favourites:
                fragment = new favoritesFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;



            case R.id.feedback:
                Intent intent3 = new Intent(getApplicationContext(), feedback.class);
                startActivity(intent3);
                //Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "ashwinchandlapur@gmail.com"));
              //  intent1.putExtra(Intent.EXTRA_SUBJECT,"Kitchen Feedback");
              //  startActivity(intent1);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
