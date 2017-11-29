package vadeworks.vadekitchen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class YoutubePlayerActivity extends YouTubeBaseActivity {


    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    String youtubeLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        Button play = (Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize("AIzaSyDv0pauJboPppeokddIMC7OCTIRq7fBRIg",onInitializedListener);
            }
        });



        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubePlayer);
        onInitializedListener =   new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youtubeLink);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("Error","Error");
            }
        };


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            youtubeLink = extras.getString("youtubeLink");
            Toast.makeText(this,youtubeLink,Toast.LENGTH_LONG);
//            Log.i("videoUrl is is",youtubeLink);
            // Toast.makeText(recipeDisplayActivity.this, uri, Toast.LENGTH_LONG).show();
            //The key argument here must match that used in the other activity
        }

    }
}
