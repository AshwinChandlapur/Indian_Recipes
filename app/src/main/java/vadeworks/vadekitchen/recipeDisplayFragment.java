package vadeworks.vadekitchen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import vadeworks.vadekitchen.adapter.DatabaseHelper;


@SuppressLint("ValidFragment")
public class recipeDisplayFragment extends Fragment {
    int img_id;
    private Button gmapButton;
    private double latitude, longitude;
    SliderLayout mDemoSlider;
    private InterstitialAd interstitial;
    DatabaseHelper myDBHelper;
    TextView t;
    ListView list;
    Context context;
    String places[];


    public recipeDisplayFragment(int img_id) {
        this.img_id = img_id; // Required empty public constructor
    }
   // private List<nearby_places_adapter> nearby_adapterList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_display, container, false);
        View header = getActivity().getLayoutInflater().inflate(R.layout.header, null);
       // View footer = getActivity().getLayoutInflater().inflate(R.layout.footer, null);

        list.addHeaderView(header);
       // list.addFooterView(footer);


        final TextView place_textView = (TextView) view.findViewById(R.id.place_textView);
        TextView description_textView = (TextView) view.findViewById(R.id.description_textView);
        TextView location_textView = (TextView) view.findViewById(R.id.location_textView);
        TextView season_textView = (TextView) view.findViewById(R.id.season_textView);
        TextView additionalInformation = (TextView) view.findViewById(R.id.additionalInformation);
        context = getActivity().getApplicationContext();
        mDemoSlider = (SliderLayout) view.findViewById(R.id.layout_images);

        myDBHelper = new DatabaseHelper(getContext());
        Cursor cursor = myDBHelper.getPlaceById(img_id);

        while (cursor.moveToNext()){
            place_textView.setText(cursor.getString(1));
            description_textView.setText(cursor.getString(2));
            location_textView.setText(cursor.getString(3));
            season_textView.setText(cursor.getString(4));
            additionalInformation.setText(cursor.getString(5));
            //nearPlaces = cursor.getString(6);
            latitude = cursor.getDouble(7);
            longitude = cursor.getDouble(8);
        }



        return view;
    }





}
