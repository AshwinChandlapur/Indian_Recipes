package vadeworks.vadekitchen.mainfeed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import vadeworks.vadekitchen.R;
import vadeworks.vadekitchen.offlineRecipeDisplayActivity;

/**
 * Created by ashwinchandlapur on 04/10/17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;
    FragmentTransaction ft;

    String name,image,time,ingredients,directions,youtubeLink;

    public DataAdapter(ArrayList<AndroidVersion> android) {
        this.android = android;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {




        viewHolder.tv_name.setText(android.get(i).getName());
        Glide.with(viewHolder.feedimage.getContext())
                .load(android.get(i).getImage())
                .into(viewHolder.feedimage);




        viewHolder.parent_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = android.get(i).getName();
                image = android.get(i).getImage();
                time= android.get(i).getTime();
                ingredients = android.get(i).getIngredients();
                directions = android.get(i).getDirections();
                youtubeLink = android.get(i).getVideoUrl();


                Intent intent = new Intent((AppCompatActivity)viewHolder.parent_feed.getContext(),offlineRecipeDisplayActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("sr",image);
                intent.putExtra("time",time);
                intent.putExtra("ingredients",ingredients);
                intent.putExtra("directions",directions);
                intent.putExtra("youtubeLink",youtubeLink);
                viewHolder.parent_feed.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_name,tv_url,tv_content;
        public ImageView feedimage;
        public CardView parent_feed;
        public ViewHolder(View view) {
            super(view);
            parent_feed = (CardView)view.findViewById(R.id.card_row);
            tv_name = (TextView)view.findViewById(R.id.recipeName);
            feedimage = (ImageView)view.findViewById(R.id.recipeImage);

        }
    }

}
