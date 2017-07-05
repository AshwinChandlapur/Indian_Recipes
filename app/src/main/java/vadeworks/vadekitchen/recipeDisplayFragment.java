package vadeworks.vadekitchen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import vadeworks.vadekitchen.adapter.DatabaseHelper;


@SuppressLint("ValidFragment")
public class recipeDisplayFragment extends Fragment {
    int img_id;
    SliderLayout mDemoSlider;
    DatabaseHelper myDBHelper;
    ListView list;
    Context context;



    public recipeDisplayFragment(int img_id) {
        this.img_id = img_id; // Required empty public constructor
    }
    //private List<nearby_places_adapter> nearby_adapterList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_display, container, false);
       // View header = getActivity().getLayoutInflater().inflate(R.layout.header, null);
      //  list.addHeaderView(header);



        final TextView recipe_textView = (TextView) view.findViewById(R.id.recipe_textView);
        TextView time_textView = (TextView) view.findViewById(R.id.time_textView);
        TextView ingredients_textView = (TextView) view.findViewById(R.id.ingredients_textView);
        TextView directions_textView = (TextView) view.findViewById(R.id.directions_textView);

        context = getActivity().getApplicationContext();
        myDBHelper = new DatabaseHelper(getContext());

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


        mDemoSlider = (SliderLayout) view.findViewById(R.id.layout_images);
        TextSliderView textSliderView;
        String[] imagesArray = new String[25];
        Cursor imageURLCursor = myDBHelper.getAllImagesArrayByID(img_id);
        for (int i=0;imageURLCursor.moveToNext();i++){
            imagesArray[i] = imageURLCursor.getString(0);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(7000);

        //displayList();
        return view;
    }





}
