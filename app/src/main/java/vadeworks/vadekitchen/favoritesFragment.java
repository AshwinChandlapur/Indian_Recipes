package vadeworks.vadekitchen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import vadeworks.vadekitchen.adapter.DatabaseHelper;
import vadeworks.vadekitchen.adapter.generic_adapter;




public class favoritesFragment extends Fragment {
    static SimpleDraweeView draweeView;
    View view;
    Context context;
    ListView list;
    DatabaseHelper myDBHelper;
    Cursor cursor, PlaceCursor;
    int id;

    public favoritesFragment() {

    }

    private List<generic_adapter> favourites_adapterList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_favorites, container, false);

        context = getActivity().getApplicationContext();
        //((MainActivity) getActivity()).getSupportActionBar().setTitle("Favourites");

        Toast.makeText(getContext(),"Long Click on a Recipe, to delete from Favourites",Toast.LENGTH_SHORT).show();

        list = (ListView) view.findViewById(R.id.favouritesList);
        list.setSmoothScrollbarEnabled(true);
        //((MainActivity) getActivity()).getSupportActionBar().setTitle("Favourites");

        favourites_adapterList.clear();

        Fresco.initialize(getActivity());
        myDBHelper = new DatabaseHelper(context);
        PlaceCursor = myDBHelper.getAllFavourites();


        while(PlaceCursor.moveToNext()) {
            id = PlaceCursor.getInt(0);
            String[] imagesArray = new String[1];
            Cursor imageURLCursor = myDBHelper.getAllImagesArrayByID(PlaceCursor.getInt(0));
            for (int i = 0; imageURLCursor.moveToNext(); i++) {
                imagesArray[i] = imageURLCursor.getString(1);
            }
            cursor = myDBHelper.getRecipeById(id);

           // while (cursor.moveToNext()) {

                favourites_adapterList.add(
                        new generic_adapter(
                                imagesArray,
                                PlaceCursor.getString(1),
                                PlaceCursor.getString(2),
                                PlaceCursor.getString(3),
                                PlaceCursor.getString(4)
                        ));
           // }
       }

        displayList();


        return view;
    }


    private void displayList() {
        final ArrayAdapter<generic_adapter> adapter = new myFavouritesListAdapterClass();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 generic_adapter current = favourites_adapterList.get(position);
                PlaceCursor.moveToPosition(position);
                int img_id = PlaceCursor.getInt(0);
               Uri uri = Uri.parse(current.getImage()[0]);
                String sr = String.valueOf(uri);

                String img[] = favourites_adapterList.get(position).getImage();
                String name = favourites_adapterList.get(position).getTitle();
                String ingredients =favourites_adapterList.get(position).getIngredients();
                String directions = favourites_adapterList.get(position).getDirections();
                String time = favourites_adapterList.get(position).getTime();
                //Toast.makeText(view.getContext(), String.valueOf(img), Toast.LENGTH_LONG).show();
               // Log.i(TAG, String.valueOf(img));


                Intent intent = new Intent(getActivity(), recipeDisplayActivity.class);
                intent.putExtra("img_id",img_id);
                intent.putExtra("name",name);
                intent.putExtra("time",time);
                intent.putExtra("ingredients",ingredients);
                intent.putExtra("directions",directions);
                intent.putExtra("img",img);
                intent.putExtra("sr",sr);
                startActivity(intent);


               // Fragment fragment = new recipeDisplayFragment(img_id);
                //FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
               // ft.replace(R.id.content_main, fragment);
               // ft.addToBackStack(null);
                //ft.commit();



            }
        });



        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {

                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Delete from favourites ?\n");
                adb.setCancelable(false);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PlaceCursor.moveToPosition(pos);
                        int img_id = PlaceCursor.getInt(0);

                        myDBHelper = new DatabaseHelper(context);
                        myDBHelper.deleteFromFavourites(img_id);

                        adapter.remove(adapter.getItem(pos));
                        adapter.notifyDataSetChanged();


                    } });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    } });
                adb.show();

                return true;
            }
        });
    }




    public class myFavouritesListAdapterClass extends ArrayAdapter<generic_adapter> {
        myFavouritesListAdapterClass() {
            super(context, R.layout.item, favourites_adapterList);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                itemView = inflater.inflate(R.layout.item, parent, false);

            }
            generic_adapter current = favourites_adapterList.get(position);

           Uri uri = Uri.parse(current.getImage()[0]);
           draweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_Image);
           // ImageView img =(ImageView) itemView.findViewById(R.id.item_Image);
            //draweeView.getHierarchy().setProgressBarImage(new CircleProgressBarDrawable(1));
           draweeView.setImageURI(uri);
            //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(img);

            TextView t_name = (TextView) itemView.findViewById(R.id.item_Title);
            t_name.setText(current.getTitle());

            TextView t_dist = (TextView) itemView.findViewById(R.id.item_Dist);
            t_dist.setText(current.getTime());

            return itemView;
        }
    }






}