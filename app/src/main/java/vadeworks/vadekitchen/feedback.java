package vadeworks.vadekitchen;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class feedback extends AppCompatActivity {

    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        if(!(android.os.Build.VERSION.SDK_INT <21))
        {
            android.support.v7.app.ActionBar AB = getSupportActionBar();
            AB.hide();
        }


//        input = new EditText(feedback.this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        input.setLayoutParams(lp);

        SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        //smileRating.setSelectedSmile(BaseRating.GREAT, true);

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {

                if(level<=3)
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(feedback.this, R.style.MyAlertDialogStyle);
                    } else {
                        builder = new AlertDialog.Builder(getApplicationContext());
                    }
                    builder.setTitle("We're really Sorry!")
                            .setMessage("Could you tell us what problems you faced. This will help us serve better.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "ashwinchandlapur@gmail.com"));
                                     intent1.putExtra(Intent.EXTRA_SUBJECT,"Kitchen Feedback");
                                     startActivity(intent1);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(R.mipmap.ic_launcher)
//                            .setView(input)
                            .show();
                }
                else{
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(feedback.this, R.style.MyAlertDialogStyle);
                    } else {
                        builder = new AlertDialog.Builder(getApplicationContext());
                    }
                    builder.setTitle("Thank You")
                            .setMessage("Glad You Liked It. Would you like to post your review on Play Store. This will help and motivate us a lot")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    final String appPackageName = getPackageName();
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(R.mipmap.ic_launcher)
                            .show();

                }
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
            }
        });








    }
}
