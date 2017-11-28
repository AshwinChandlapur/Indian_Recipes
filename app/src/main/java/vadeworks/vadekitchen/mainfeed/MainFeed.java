package vadeworks.vadekitchen.mainfeed;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import vadeworks.vadekitchen.MainActivity;
import vadeworks.vadekitchen.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFeed extends Fragment {

    String name,url,content,videoUrl;
    TextView newsname,newscontent;
    ImageView newsimage,youtube;
    private InterstitialAd interstitial;
    int i = 0;

    public MainFeed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.content_main, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
             name = bundle.getString("name");
             url = bundle.getString("url");
             content = bundle.getString("con");
             videoUrl = bundle.getString("videoUrl");
        }




//        newsname = (TextView)rootView.findViewById(R.id.newsname);
//        newscontent = (TextView)rootView.findViewById(R.id.newscontent);
//        newsimage = (ImageView)rootView.findViewById(R.id.newsimage);
//        youtube = (ImageView)rootView.findViewById(R.id.youtube);


        newsname.setText(name);
        newscontent.setText(content);


        return rootView;
    }


    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if ((interstitial.isLoaded()) && (interstitial!=null)) {
            interstitial.show();
        }
    }




    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    Intent i = new Intent(getActivity(),MainActivity.class);
                    startActivity(i);

                    return true;

                }

                return false;
            }
        });
    }

}
