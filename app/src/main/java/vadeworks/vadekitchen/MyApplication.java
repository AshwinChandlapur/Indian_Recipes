package vadeworks.vadekitchen;

/**
 * Created by ashwinchandlapur on 17/10/17.
 */

import android.app.Application;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONObject;

import static vadeworks.vadekitchen.R.layout.item;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //OneSignal.startInit(this).init();
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .init();

        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);
    }



    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String id="";
            String recipeName="";
            String time="",ingredients="",directions="";



            String targetUrl;
            String imgUrl;
            int i =0;


            if (data != null) {
                 imgUrl=data.optString("imgUrl",null);
                 targetUrl = data.optString("targetUrl",null);
                String value = data.optString("recipe",null);
                try{
                    JSONObject item = new JSONObject(value);

                    id = item.getString("id");
                    recipeName = item.getString("name");
                    time = item.getString("time");
                    ingredients = item.getString("ingredients");
                    directions = item.getString("directions");

                }catch(Exception e){

                }

                if((recipeName!=null && time!=null && ingredients!=null && directions!=null && id!=null) && (targetUrl==null) ) {
                    Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                    Log.i("OneSignal","Executed");
                    intent.putExtra("name", recipeName);
                    intent.putExtra("time", time);
                    intent.putExtra("ingredients", ingredients);
                    intent.putExtra("directions", directions);
                    intent.putExtra("sr",imgUrl);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                if(!(recipeName!="" && time!="" && ingredients!="" && directions!="" && id!="") && targetUrl!=null){
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(targetUrl));
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                data.remove(value);//This is mandatory, because the Old JSON data will still be stored that causes error while opening newest notification

            }

        }
    }
}