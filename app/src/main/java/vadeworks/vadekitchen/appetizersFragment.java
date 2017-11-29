package vadeworks.vadekitchen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

import vadeworks.vadekitchen.adapter.DatabaseHelper;
import vadeworks.vadekitchen.adapter.generic_adapter;

import static vadeworks.vadekitchen.appetizersFragment.ViewHolder.uri;
import static vadeworks.vadekitchen.appetizersFragment.ViewHolder.videoUrl;


public class appetizersFragment extends Fragment {

    static class ViewHolder {
        static int img_id;
        static String name,ingredients,directions,time,videoUrl;
        static SimpleDraweeView draweeView;
        static TextView t_name,t_dist;
        static Uri uri;
    }


    private List<generic_adapter> appetizers_adapterList = new ArrayList<>();//TODO: Should CHange this accordinly
    View view;
    Context context;
    ListView list;
    DatabaseHelper myDBHelper;
    Cursor PlaceCursor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_appetizers, container, false);//TODO: Should CHange this accordinly
        context = getActivity().getApplicationContext();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Appetizers");

        list = (ListView) view.findViewById(R.id.appetizersList);//TODO: Should Change this accordinly
        list.setSmoothScrollbarEnabled(true);





        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        new AsyncCaller().execute();

        return view;
    }





    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            Fresco.initialize(getActivity());
            appetizers_adapterList.clear();
            myDBHelper = new DatabaseHelper(context);
            PlaceCursor = myDBHelper.getAllAppetizers();//TODO: Should CHange this accordinly
            while(PlaceCursor.moveToNext()){
                String [] imagesArray = new String[1];
                Cursor imageURLCursor = myDBHelper.getAllImagesArrayByID(PlaceCursor.getInt(0));
                for (int i=0;imageURLCursor.moveToNext();i++){
                    imagesArray[i] = imageURLCursor.getString(1);
                }

                appetizers_adapterList.add(
                        new generic_adapter(
                                imagesArray,        //id
                                PlaceCursor.getString(1),//name
                                PlaceCursor.getString(2),//Time Taken
                                PlaceCursor.getString(3),//Ingredients
                                PlaceCursor.getString(4),//Directions
                                PlaceCursor.getString(5),//category
                                PlaceCursor.getString(6)//videoUrl
                        ));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            displayList();
        }

    }

    private void displayList() {
        ArrayAdapter<generic_adapter> adapter = new mybreakfastListAdapterClass();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                generic_adapter current = appetizers_adapterList.get(position);
                PlaceCursor.moveToPosition(position);

                Uri uri = Uri.parse(current.getImage()[0]);
                String sr = String.valueOf(uri);

                ViewHolder holder = new ViewHolder();
                holder.img_id = PlaceCursor.getInt(0);
                String img[] = appetizers_adapterList.get(position).getImage();
                holder.name = appetizers_adapterList.get(position).getTitle();
                holder.ingredients =appetizers_adapterList.get(position).getIngredients();
                holder.directions = appetizers_adapterList.get(position).getDirections();
                holder.time = appetizers_adapterList.get(position).getTime();
                holder.videoUrl = appetizers_adapterList.get(position).getVideoUrl();
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
                intent.putExtra("videoUrl",holder.videoUrl);
                Log.i("Value of VideoUrl is",holder.videoUrl);
                startActivity(intent);


            }
        });
    }

    public class mybreakfastListAdapterClass extends ArrayAdapter<generic_adapter> {

        mybreakfastListAdapterClass() {
            super(context, R.layout.item, appetizers_adapterList);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                itemView = inflater.inflate(R.layout.item, parent, false);

            }
            generic_adapter current = appetizers_adapterList.get(position);

            ViewHolder holder =  new ViewHolder();

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
