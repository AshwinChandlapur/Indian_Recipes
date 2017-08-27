package vadeworks.vadekitchen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import vadeworks.vadekitchen.adapter.DatabaseHelper;
import vadeworks.vadekitchen.adapter.generic_adapter;

import static vadeworks.vadekitchen.snacksFragment.ViewHolder.uri;


public class snacksFragment extends Fragment {

    static class ViewHolder {
        static int img_id;
        static String name,ingredients,directions,time;
        static SimpleDraweeView draweeView;
        static TextView t_name,t_dist;
        static Uri uri;
    }

    private List<generic_adapter> snacks_adapterList = new ArrayList<>();//TODO: Should CHange this accordinly
    static SimpleDraweeView draweeView;
    View view;
    Context context;
    ListView list;
    DatabaseHelper myDBHelper;
    Cursor PlaceCursor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_snacks, container, false);//TODO: Should CHange this accordinly
        context = getActivity().getApplicationContext();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Snacks");


        list = (ListView) view.findViewById(R.id.snacksList);//TODO: Should CHange this accordinly
        list.setSmoothScrollbarEnabled(true);


        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        Fresco.initialize(getActivity());

        snacks_adapterList.clear();
        myDBHelper = new DatabaseHelper(context);
        PlaceCursor = myDBHelper.getAllSnacks();//TODO: Should CHange this accordinly
        while(PlaceCursor.moveToNext()){
            String [] imagesArray = new String[1];
            Cursor imageURLCursor = myDBHelper.getAllImagesArrayByID(PlaceCursor.getInt(0));
            for (int i=0;imageURLCursor.moveToNext();i++){
                imagesArray[i] = imageURLCursor.getString(1);
            }

            snacks_adapterList.add(
                    new generic_adapter(
                            imagesArray,        //id
                            PlaceCursor.getString(1),//name
                            PlaceCursor.getString(2),//description
                            PlaceCursor.getString(3),//district
                            PlaceCursor.getString(4),
                            PlaceCursor.getString(5)//best season
                    ));
        }

        displayList();

        return view;
    }


    private void displayList() {
        ArrayAdapter<generic_adapter> adapter = new mybreakfastListAdapterClass();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                generic_adapter current = snacks_adapterList.get(position);
                PlaceCursor.moveToPosition(position);
                Uri uri = Uri.parse(current.getImage()[0]);
                String sr = String.valueOf(uri);

                ViewHolder holder = new ViewHolder();

                holder.img_id = PlaceCursor.getInt(0);
                String img[] = snacks_adapterList.get(position).getImage();
                holder.name = snacks_adapterList.get(position).getTitle();
                holder.ingredients = snacks_adapterList.get(position).getIngredients();
                holder.directions = snacks_adapterList.get(position).getDirections();
                holder.time = snacks_adapterList.get(position).getTime();
                //Toast.makeText(view.getContext(), String.valueOf(sr), Toast.LENGTH_LONG).show();
                //Log.i(TAG, String.valueOf(img));


                Intent intent = new Intent(getActivity(), recipeDisplayActivity.class);
                intent.putExtra("img_id",holder.img_id);
                intent.putExtra("name",holder.name);
                intent.putExtra("time",holder.time);
                intent.putExtra("ingredients",holder.ingredients);
                intent.putExtra("directions",holder.directions);
                intent.putExtra("img",img);
                intent.putExtra("sr",sr);
                startActivity(intent);



                //Fragment fragment = new placeDisplayFragment(img_id);
                //FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                //ft.replace(R.id.content_main, fragment);
                // ft.addToBackStack(null);
                // ft.commit();

            }
        });
    }

    public class mybreakfastListAdapterClass extends ArrayAdapter<generic_adapter> {

        mybreakfastListAdapterClass() {
            super(context, R.layout.item, snacks_adapterList);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                itemView = inflater.inflate(R.layout.item, parent, false);

            }
            generic_adapter current = snacks_adapterList.get(position);

            ViewHolder holder = new ViewHolder();

            //Code to download image from url and paste.
            holder.uri = Uri.parse(current.getImage()[0]);
            holder.draweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_Image);
            holder.draweeView.setImageURI(uri);
            //Code ends here.

            holder.t_name = (TextView) itemView.findViewById(R.id.item_Title);
            holder.t_name.setText(current.getTitle());

            holder.t_dist = (TextView) itemView.findViewById(R.id.item_Dist);
            holder.t_dist.setText(current.getTime());

            return itemView;
        }
    }


}
