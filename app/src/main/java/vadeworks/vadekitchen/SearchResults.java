package vadeworks.vadekitchen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import vadeworks.vadekitchen.adapter.DatabaseHelper;
import vadeworks.vadekitchen.adapter.generic_adapter;

import static vadeworks.vadekitchen.appetizersFragment.ViewHolder.videoUrl;


public class SearchResults extends Fragment {

    static class ViewHolder {
        int img_id;
        String name,ingredients,directions,time,videoUrl;
    }

    Cursor PlaceCursor;
    private List<generic_adapter> search_adapterList = new ArrayList<>();
    private static final String TAG = "MyAppTag";
    static SimpleDraweeView draweeView;
    View view;
    Context context;
    ListView list;
    DatabaseHelper myDBHelper;


    public SearchResults() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SearchResults(Cursor PlaceCursor) {
        this.PlaceCursor = PlaceCursor;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        context = getActivity().getApplicationContext();

        list = (ListView) view.findViewById(R.id.searchList);
        list.setSmoothScrollbarEnabled(true);

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        Fresco.initialize(getActivity());
        myDBHelper = new DatabaseHelper(context);
        while(PlaceCursor.moveToNext()){

            String [] imagesArray = new String[1];
            Cursor imageURLCursor = myDBHelper.getAllImagesArrayByID(PlaceCursor.getInt(0));
            for (int i=0;imageURLCursor.moveToNext();i++){
                imagesArray[i] = imageURLCursor.getString(1);
            }

            search_adapterList.add(
                    new generic_adapter(
                            imagesArray,        //id
                            PlaceCursor.getString(1),//title
                            PlaceCursor.getString(2),//time
                            PlaceCursor.getString(3),//ingredients
                            PlaceCursor.getString(4),
                            PlaceCursor.getString(5),
                            PlaceCursor.getString(6)//directions
                    ));
        }

        displayList();

        return view;

    }


    private void displayList() {
        ArrayAdapter<generic_adapter> adapter = new mySearchListAdapterClass();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                generic_adapter current = search_adapterList.get(position);
                PlaceCursor.moveToPosition(position);
                int img_id = PlaceCursor.getInt(0);
                Uri uri = Uri.parse(current.getImage()[0]);
                String sr = String.valueOf(uri);

                String img[] = search_adapterList.get(position).getImage();
                String name = search_adapterList.get(position).getTitle();
                String ingredients =search_adapterList.get(position).getIngredients();
                String directions = search_adapterList.get(position).getDirections();
                String time = search_adapterList.get(position).getTime();
                videoUrl = search_adapterList.get(position).getVideoUrl();



                Intent intent = new Intent(getActivity(), recipeDisplayActivity.class);
                intent.putExtra("img_id",img_id);
                intent.putExtra("name",name);
                intent.putExtra("time",time);
                intent.putExtra("ingredients",ingredients);
                intent.putExtra("directions",directions);
                intent.putExtra("img",img);
                intent.putExtra("sr",sr);
                intent.putExtra("videoUrl",videoUrl);
                Log.i("Value of VideoUrl is",videoUrl);
                startActivity(intent);


            }
        });
    }

    public class mySearchListAdapterClass extends ArrayAdapter<generic_adapter> {

        mySearchListAdapterClass() {
            super(context, R.layout.item, search_adapterList);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                itemView = inflater.inflate(R.layout.item, parent, false);
            }
            generic_adapter current = search_adapterList.get(position);

            //Code to download image from url and paste.
           Uri uri = Uri.parse(current.getImage()[0]);
           draweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_Image);
           draweeView.setImageURI(uri);
            //Code ends here.
            TextView t_name = (TextView) itemView.findViewById(R.id.item_Title);
            t_name.setText(current.getTitle());

            TextView t_dist = (TextView) itemView.findViewById(R.id.item_Dist);
            t_dist.setText(current.getTime());

            return itemView;
        }


    }

}
