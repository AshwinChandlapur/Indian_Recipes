package vadeworks.vadekitchen;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import vadeworks.vadekitchen.adapter.DatabaseHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static int serverVersion, localVersion;
    ProgressDialog pd;
    DatabaseHelper myDBHelper;



    String [] d1= {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/aloo-gobi-10-min.jpg",
            "ಭಾಪಾ ಆಲೂ",
            "50 ನಿಮಿಷಗಳು",
            "• 200 ಗ್ರಾಂ ಸಣ್ಣ ಆಲೂಗಡ್ಡೆ \n • 2 ಟೀಸ್ಪೂನ್ ಸಾಸಿವೆ ಎಣ್ಣೆ \n • 1/2 ಟೀಸ್ಪೇನ್ ಬಂಗಾಳಿ ಐದು ಮಸಾಲೆ ಮಿಶ್ರಣ (ಪಾಂಚ್ಫೋರನ್) (ಸಂಪೂರ್ಣ ಜೀರಾ, ಸಾನ್ಫ್ ಬೀಜಗಳು, ಮೆಂತ್ಯೆ ಬೀಜಗಳು, ಕಪ್ಪು ಮಸ್ರಾಡ್ ಬೀಜಗಳು ಮತ್ತು ಕಲಾಂಜೀ) \n • 2 ಒಣಮೆಣಸು \n • 1/2 ಟೀಸ್ಪೂನ್ ಸಾಸಿವೆ ಪೇಸ್ಟ್ \n • 1 ಟೀಸ್ಪೂನ್ ಹುರಿದ ಮೊಸರು \n • 3/4 ಟೀಸ್ಪೂನ್ ಡಸರ್ಕೈಡ್ ತೆಂಗಿನ ಪೇಸ್ಟ್ \n • ಹಸಿರು ಮೆಣಸಿನಕಾಯಿ ಪೇಸ್ಟ್ \n • ಪಿಂಚ್ ಆಫ್ ಅರಿಶಿನ ಪುಡಿ \n • ಸಾಲ್ಟ್ - ರುಚಿಗೆ \n • ನಿಂಬೆ ರಸದ ಡ್ಯಾಶ್ \n • 2 ಬಾಳೆ ಎಲೆಗಳು ",
            "ಆಲೂಗಡ್ಡೆ ಉಪ್ಪುಸಹಿತ ನೀರಿನಲ್ಲಿ ಕುದಿಸಿ, ಬರಿದಾಗಿಸಿ ಮತ್ತು ಬದಿಗಿರಿಸಿ. \n\n • ಅಂಟಿಸದ ಪಾನ್ ನಲ್ಲಿ ಎಣ್ಣೆ ಹೀಟ್ ಮಾಡಿ, ಐದು ಮಸಾಲೆ ಮಿಶ್ರಣವನ್ನು ಸೇರಿಸಿ, ಕೆಂಪು ಮೆಣಸಿನಕಾಯಿಗಳನ್ನು ಅರ್ಧದಷ್ಟು ಮುರಿದು ಮತ್ತು ಮುಂದಿನದನ್ನು ಸೇರಿಸಿ. \n\n • ಆಲೂಗಡ್ಡೆಗೆ ತಕ್ಕಷ್ಟು ಮಸಾಲೆಗಳನ್ನು ಸುರಿಯಿರಿ.ಇದನ್ನು ಆಲೂಗಡ್ಡೆಯ ಮೇಲೆ ಹಾಕಿ ಮತ್ತು ಪಕ್ಕಕ್ಕೆ ಇರಿಸಿ \n\n • ಒಂದು ಮಿಶ್ರಣ ಬಟ್ಟಲಿನಲ್ಲಿ ಸಾಸಿವೆ ಪೇಸ್ಟ್, ಮೊಸರು, ತೆಂಗಿನಕಾಯಿ ಪೇಸ್ಟ್, ಹಸಿರು ಮೆಣಸಿನಕಾಯಿ ಪೇಸ್ಟ್ ಮತ್ತು ಅರಿಶಿನ ಪುಡಿ, ಚಾವಟಿ \n\n • ಮೃದುವಾಗಿ ಈ ಮ್ಯಾರಿನೇಡ್ನಲ್ಲಿ ಆಲೂಗಡ್ಡೆ ಮಿಶ್ರಣ ಮಾಡಿ. \n\n • ಉಪ್ಪು ಮತ್ತು ನಿಂಬೆ ರಸವನ್ನು ಸೇರಿಸಿ ಮತ್ತೆ ಬೆರೆಸಿ. \n\n • ಉಕ್ಕಿನ ತಟ್ಟೆಯಲ್ಲಿ ಆಲೂಗಡ್ಡೆ ಹಾಕಿ, ಬಾಳೆಹಣ್ಣುಗಳು ಮತ್ತು ಅವುಗಳನ್ನು 6-8 ನಿಮಿಷಗಳ ಕಾಲ ಉಜ್ಜುವುದು."};


    String [] d2 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/hyderabadi%20biryani%20new-min.jpg",
            "ಹೈದರಾಬಾದಿ ಬಿರಿಯಾನಿ",
            "1 ಗಂಟೆ 10 ನಿಮಿಷಗಳು",
            "1 ಕೆಜಿ ಮಾಂಸ \n • 1 ಟೀಸ್ಪೂನ್ ಉಪ್ಪು \n • 1 ಟೀಸ್ಪೂನ್ ಶುಂಠಿ ಬೆಳ್ಳುಳ್ಳಿ ಪೇಸ್ಟ್ \n • 1 ಟೀಸ್ಪೂನ್ ಕೆಂಪು ಮೆಣಸು ಪೇಸ್ಟ್ \n • 1 ಟೀಸ್ಪೂನ್ ಹಸಿರು ಮೆಣಸು ಪೇಸ್ಟ್ ಸೌತೆಡ್ ಕಂದು ಈರುಳ್ಳಿ ರುಚಿಗೆ \n • 1/2 ಟೀಸ್ಪೂನ್ ಏಲಕ್ಕಿ ಪುಡಿ \n • 3-4 ತುಂಡುಗಳು ದಾಲ್ಚಿನ್ನಿ \n • 1 ಟೀಸ್ಪೂನ್ ಜೀರಿಗೆ ಬೀಜಗಳು \n • 4 ಲವಂಗ ಪಿಂಚ್ ಆಫ್ ಮೆಸ್ ಮಿಂಟ್ ಎಲೆಗಳು ರುಚಿಗೆ \n • 2 ಟೀಸ್ಪೂನ್ ನಿಂಬೆ ರಸ \n • 250 ಗ್ರಾಂ ಮೊಸರು \n • 4 ಟೀಸ್ಪೂನ್ ಸ್ಪಷ್ಟಪಡಿಸಿದ ಬೆಣ್ಣೆ \n • 750 Gm ಅರೆ ಬೇಯಿಸಿದ ಅಕ್ಕಿ \n • 1 ಟೀಸ್ಪೂನ್ ಕೇಸರಿ \n • 1/2 ಕಪ್ ನೀರು \n • 1/2 ಕಪ್ ತೈಲ \n • ಬೇಯಿಸಿದ ಮೊಟ್ಟೆಗಳು \n • ಕ್ಯಾರೆಟ್, ಹಲ್ಲೆ \n • ಸೌತೆಕಾಯಿಗಳು",
            "ಮಾಂಸವನ್ನು ಸ್ವಚ್ಛಗೊಳಿಸಿ ನಂತರ ಒಂದು ಪ್ಯಾನ್ ನಲ್ಲಿ ಮಾಂಸ, ಉಪ್ಪು, ಶುಂಠಿ ಬೆಳ್ಳುಳ್ಳಿ ಪೇಸ್ಟ್, ಕೆಂಪು ಮೆಣಸಿನ ಪುಡಿ, ಹಸಿರು ಮೆಣಸಿನಕಾಯಿ ಪೇಸ್ಟ್, ಸೌತೆಡ್ ಕಂದು ಈರುಳ್ಳಿ, ಏಲಕ್ಕಿ ಪುಡಿ, ದಾಲ್ಚಿನ್ನಿ, ಜೀರಿಗೆ ಬೀಜಗಳು, ಲವಂಗ, ಮೆಸ್, ಪುದೀನ ಎಲೆಗಳು ಮತ್ತು ನಿಂಬೆ ರಸ ಸೇರಿಸಿ. \n\n • ಮೊಸರು, ಸ್ಪಷ್ಟವಾದ ಬೆಣ್ಣೆ, ಅರೆ ಬೇಯಿಸಿದ ಅಕ್ಕಿ, ಕೇಸರಿ, ನೀರು ಮತ್ತು ಎಣ್ಣೆಯನ್ನು ಸೇರಿಸಿ ಚೆನ್ನಾಗಿ ಮಿಶ್ರಣ ಮಾಡಿ. \n\n • ಈಗ ಪ್ಯಾನ್ನ ಬದಿಗಳಲ್ಲಿ ಜಿಗುಟಾದ ಹಿಟ್ಟನ್ನು ಅನ್ವಯಿಸಿ. ಮತ್ತು 25 ನಿಮಿಷಗಳ ಕಾಲ ಬೇಯಿಸಿ. \n\n • ಹೈದರಾಬಾದಿ ಬಿರಿಯಾನಿ ತಿನ್ನಲು ಸಿದ್ಧವಾಗಿದೆ.ಇದನ್ನು ಬೇಯಿಸಿದ ಮೊಟ್ಟೆಗಳು, ಹಲ್ಲೆ ಕ್ಯಾರೆಟ್ಗಳು, ಸೌತೆಕಾಯಿಗಳು ಮತ್ತು ಬಿಸಿಯಾಗಿ ಸೇವಿಸುತ್ತಾರೆ."};


    String [] d3 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/bisi%20bele%20bhaat-min.jpg",
            "ಬಿಸಿಬೆಲೆಬಾತ್","45 ನಿಮಿಷಗಳು",
            "1/2 ಕಪ್ ಅಕ್ಕಿ \n • 1/4 ಕಪ್ ಟೋಲ್ ಡಾಲ್ \n • 1 1/2 ಕತ್ತರಿಸಿದ ತರಕಾರಿಗಳು \n • 4 ಟೀಸ್ಪೂನ್ ಚಾನಾ ದಳ \n • 2 ಟೀಸ್ಪೂನ್ ಉರಾಸ್ ಡಾಲ್ \n • 2 ಟೀಸ್ಪೂನ್ ಜೀರಾ \n • 1 / 4 ಟೀಸ್ಪೂನ್ ಮೆಂತ್ಯೆ \n • 1 ಟೀಸ್ಪೂನ್ ಮೆಣಸು \n • 1 ಕೆಂಪು ಮೆಣಸಿನಕಾಯಿಗಳು \n • 1 ಟೀಸ್ಪೂನ್ ಕೂಸ್ ಕೂಸ್ \n • 2 ಟೀಸ್ಪೂನ್ ತುರಿದ ತೆಂಗಿನಕಾಯಿ \n • ಮೇಲೋಗರದ ಎಲೆಗಳ 1 ಚಿಗುರು \n • 1 ಟೀಸ್ಪೂನ್ ಅರಿಶಿನ ಪುಡಿ \n • 1 ಟೀಸ್ಪೂನ್ ಬೆಲ್ಲ \n • 1 ಹುಣಿಸೇಹಣ್ಣು \n • 2 ಚಮಚ ತೈಲ \n • ಉಪ್ಪು ರುಚಿ ",
            "ಅಕ್ಕಿ ಮತ್ತು ಟೋಲ್ ಬೇಯಿಸಿ ಮತ್ತು ಅದನ್ನು ಪಕ್ಕಕ್ಕೆ ಇಟ್ಟುಕೊಳ್ಳಿ. \n\n • ಹುಣಿಸೇಹಣ್ಣಿನ ಬಿಸಿ ನೀರಿನಲ್ಲಿ ನೆನೆಸಿ ಮತ್ತು ಹುಣಿಸೇಹಣ್ಣಿನ ನೀರನ್ನು ಹೊರತೆಗೆಯಿರಿ. \n\n • ತರಕಾರಿಗಳನ್ನು ಕುದಿಸಿ. \n\n • ಪ್ಯಾನ್ ಶಾಖ ತೈಲ ಮತ್ತು ಮಸಾಲೆ, ಮೆಣಸಿನಕಾಯಿಗಳು, ಕೆಂಪು ಮೆಣಸಿನಕಾಯಿಯನ್ನು ಬೆರೆಸಿ ಫ್ರೈ ಲಘು ಕಂದು ಬಣ್ಣವನ್ನು ತನಕ ಫ್ರೈ ಸೇರಿಸಿ. \n\n • ಫ್ರೈ ಕೂಸ್ ಕೂಸ್ ಮತ್ತು ಮೇಲೋಗರದ ಎಲೆಗಳು ತೆಂಗಿನಕಾಯಿಯೊಂದಿಗೆ ಹುರಿದ ಪದಾರ್ಥಗಳನ್ನು ರುಬ್ಬಿಸಿ. \n\n • ಹುಣಿಸೇಹಣ್ಣು 1 ರಿಂದ 2 ನಿಮಿಷಗಳ ಕಾಲ ಸೇರಿಸಿ, ಅರಿಶಿನ ಪುಡಿ, ಉಪ್ಪು ಮತ್ತು ಬೆಲ್ಲ ಸೇರಿಸಿ. \n\n • ತರಕಾರಿಗಳನ್ನು ಸೇರಿಸಿ ಮತ್ತು 5 ರಿಂದ 6 ನಿಮಿಷಗಳ ಕಾಲ ಕುದಿಸಿಕೊಳ್ಳಿ. \n\n • ನೆಲದ ಮಿಶ್ರಣವನ್ನು ಸೇರಿಸಿ. 3 ನಿಮಿಷ ಬೇಯಿಸಿ, ಅಕ್ಕಿ ಹಿಟ್ಟನ್ನು ಸೇರಿಸಿ 2 ನಿಮಿಷ ಬೇಯಿಸಿ. \n\n • ಒಂದು ಪ್ಯಾನ್ ಮತ್ತು ಫ್ರೈ ಕಡಲೆಕಾಯಿ, ಚಾನಾ ದಳ, ಉರಾಡ್ ಡಾಲ್ ಮತ್ತು ಸಾಸಿವೆಗಳಲ್ಲಿ ಒಣಗಿಸಿ. \n\n • ಬಿಸಿ ಬೀಲೆಬಾತ್ಗೆ ತೆಗೆದ ಪದಾರ್ಥಗಳನ್ನು ಸೇರಿಸಿ . \n\n • ಬೊಂಧಿ ಮತ್ತು ಕೊತ್ತುಂಬರಿ ಎಲೆಗಳೊಂದಿಗೆ ಅಲಂಕರಿಸಲು ಬಿಸಿಯಾಗಿ ಸರ್ವ್ ಮಾಡಿ. "};


    String [] d4 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/falhari-pakode-new-min.jpg",
            "ಫಲಹರಿ ಪಾಕೊರಾ","20 ",

            "3 ರಿಂದ 4 ಮಧ್ಯಮ ಆಲೂಗಡ್ಡೆ \n • 5 ಟೀಸ್ಪೂನ್ ಹುರುಳಿ ಹಿಟ್ಟು \n • 1 ಹಸಿರು ಮೆಣಸಿನಕಾಯಿ, ಕತ್ತರಿಸಿದ \n • 1 ಟೀಸ್ಪೂನ್ ಅನಾರ್ನಾನಾ \n • 1/2 ಟೀಸ್ಪೂನ್ ಜೀರಿಗೆ ಪುಡಿ \n • 3/4 ರಿಂದ 1 ಕಪ್ ನೀರು, ಅಗತ್ಯವಿರುವಂತೆ \n • ಹುರಿಯಲು ತೈಲ \n • ಅಗತ್ಯವಿರುವಂತೆ ರಾಕ್ ಉಪ್ಪು",

            "ತೊಳೆಯಿರಿ, ಸಿಪ್ಪೆ ಮಾಡಿ ಆಲೂಗಡ್ಡೆಯನ್ನು ಸಣ್ಣ ತುಂಡುಗಳಾಗಿ ಕತ್ತರಿಸಿ ಬೆಕ್ಹ್ಯಾಟ್ ಹಿಟ್ಟಿನಲ್ಲಿ ಮಿಶ್ರಣ ಮಾಡಿ. \n\n • ಆಲೂಗಡ್ಡೆಗೆ ಉಳಿದ ಪದಾರ್ಥಗಳನ್ನು ಸೇರಿಸಿ ಮತ್ತು ಚೆನ್ನಾಗಿ ಮಿಶ್ರಣ ಮಾಡಿ. \n\n • ಮಧ್ಯಂತರಗಳಲ್ಲಿ ಸ್ವಲ್ಪ ನೀರು ಸೇರಿಸಿ ಮತ್ತು ನೀವು ದಪ್ಪ ಬ್ಯಾಟರ್ ಪಡೆಯುವ ತನಕ ಮಿಶ್ರಣ ಮಾಡಿ ಅದು ಇಡ್ಲಿ ಬ್ಯಾಟರ್ಗಿಂತ ದಪ್ಪವಾಗಿರಬೇಕು. \n\n • ಆಳವಾದ ಹುರಿಯಲು ಪ್ಯಾನ್ ನಲ್ಲಿ ಹೀಟ್ ಎಣ್ಣೆ.ಒಂದು ಟೇಬಲ್ಸ್ಪೂನ್ ಸಹಾಯದಿಂದ, ಬ್ಯಾಟರ್ ಅನ್ನು ನಿಮ್ಮ ಕೈಗಳಿಂದ ಆಕಾರಗೊಳಿಸಿ ಬಿಸಿಯಾಗಿ ಆಕಾರದ ಬ್ಯಾಟರ್ ಅನ್ನು ಬಿಡಿ ತೈಲ. \n\n • ಪ್ಯಾಕೋರಾಗಳು ತನಕ ಫ್ರೈ ಗೋಲ್ಡನ್ ಮತ್ತು ಗರಿಗರಿಯಾದವು.ಒಂದು ಅಂಗಾಂಶದ ಸಹಾಯದಿಂದ ಅತಿಯಾದ ಎಣ್ಣೆಯನ್ನು ಹರಿಸುತ್ತವೆ. \n\n • ಹೊಸದಾಗಿ ಮಾಡಿದ ಪುದೀನಾ ಮೊಸರು ಅದ್ದುದಿಂದ ಬಿಸಿಯಾಗಿ ಅವರನ್ನು ಸರ್ವ್ ಮಾಡಿ."};


    String [] d5 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/malai-kofta-curry-03-700x525-min.jpg",
            "ಮಲೈ ಕೋಫ್ಟಾ","1 ಗಂಟೆ 40 ನಿಮಿಷಗಳು",

            "•4 ದೊಡ್ಡ ಆಲೂಗಡ್ಡೆ, ಬೇಯಿಸಿದ \n • 250 ಗ್ರಾಂ ಪನೀರ್ (ಕಾಟೇಜ್ ಚೀಸ್) \n • 50 ಗ್ರಾಂ ಮೈದಾ \n • 1 ಟೀಸ್ಪೂನ್ ಕೊತ್ತಂಬರಿ ಎಲೆಗಳು, ಕತ್ತರಿಸಿದ \n • 3 ಈರುಳ್ಳಿ \n • 1 ಟೀಸ್ಪೂನ್ ಶುಂಠಿ ಬೆಳ್ಳುಳ್ಳಿ ಪೇಸ್ಟ್ \n • 2 ಟೊಮ್ಯಾಟೊ \n • 200 ಮಿಲಿ ಮಲಯ ಅಥವಾ ಕೆನೆ \n • 2 ಟೀಸ್ಪೂನ್ ಒಣದ್ರಾಕ್ಷಿ ಮತ್ತು ಗೋಡಂಬಿ ಬೀಜಗಳು \n • 50 ಗ್ರಾಂ ಗೋಡಂಬಿ ಬೀಜಗಳು ಅಂಟಿಸಿ \n • 1/2 ಟೀಸ್ಪೂನ್ ಹಲ್ಡಿ (ಅರಿಶಿನ) \n • 1/2 ಟೀಸ್ಪೂನ್ ಕೆಂಪು ಮೆಣಸಿನ ಪುಡಿ \n • 1 / 2 ಟೀಸ್ಪೂನ್ ಕಿಚನ್ ಕಿಂಗ್ ಮಸಾಲಾ \n • 1 ಟೀಸ್ಪೂನ್ ಕಸ್ತೂರಿ ಮೆಥಿ \n • ಸಾಲ್ಟ್ ಟೇಸ್ಟ್ ಟು \n • 1 ಟೀಸ್ಪೂನ್ ಸಕ್ಕರೆ ",

            "ಕೋಫ್ಟಾಗಳಿಗೆ: \n\n • 4 ರಿಂದ 6 ಗಂಟೆಗಳ ಕಾಲ ಬೇಯಿಸಿದ ಆಲೂಗಡ್ಡೆ ಅನ್ನು ಶೈತ್ಯೀಕರಣಗೊಳಿಸಿ, ಇದು ಕೂಫ್ಟಾಗಳನ್ನು ಬೇಯಿಸುವುದು ಸುಲಭವಾಗಿರುತ್ತದೆ. \n\n • ಮ್ಯಾಶ್ ಬೇಯಿಸಿದ ಆಲೂಗಡ್ಡೆ, ಪನೆರ್, ಮೈದಾ. ಉಪ್ಪು, ಕತ್ತರಿಸಿದ ಕೊತ್ತಂಬರಿ ಸೊಪ್ಪು ಸೇರಿಸಿ ಚೆನ್ನಾಗಿ ಮಿಶ್ರಣ ಮಾಡಿ. \n\n • ಒಣದ್ರಾಕ್ಷಿ ಮತ್ತು ಗೋಡಂಬಿ ಬೀಜಗಳನ್ನು ಸಣ್ಣ ತುಂಡುಗಳಾಗಿ ಕತ್ತರಿಸಿ ಮಿಶ್ರಣಕ್ಕೆ 1/2 ಟೀಸ್ಪೂನ್ ಸಕ್ಕರೆ ಸೇರಿಸಿ. \n\n • ಆಳವಾದ ಹುರಿಯಲು ನೀವು ತಯಾರಿಸಿದ ಹಿಟ್ಟಿನಿಂದ ಚೆಂಡುಗಳನ್ನು ರೋಲ್ ಮಾಡಿ ಮತ್ತು ಕೇಂದ್ರದಲ್ಲಿ ಶುಷ್ಕ ಹಣ್ಣಿನ ಮಿಶ್ರಣವನ್ನು ಸುರುಳಿ ಮಾಡಿ. \n • ಕಾಫ್ಟಾಗಳನ್ನು ಫ್ರೈ ಮಾಡಿ ಮತ್ತು ಬಿಸಿ ಎಣ್ಣೆಯಲ್ಲಿ ಮುರಿದರೆ ಅವುಗಳನ್ನು ಒಣಗಿಸುವ ಮೊದಲು ಒಣ ಮೈದಾದಲ್ಲಿ ಅವುಗಳನ್ನು ಧೂಳು ಹಾಕಿ. \n\n ಫಾರ್ ಮಾಂಸರಸ: \n\n • ಕೆಲವು ಈರುಳ್ಳಿ, ಶುಂಠಿ ಬೆಳ್ಳುಳ್ಳಿ ಪೇಸ್ಟ್ ಮತ್ತು ಟೊಮ್ಯಾಟೊ ಪೇಸ್ಟ್ ಅನ್ನು ಹಾಕಿ. \n\n • ಗೋಧಿ ಬೀಜದ ಪೇಸ್ಟ್ ಅನ್ನು 2 ಟೀಸ್ಪೂನ್ ಬೆಚ್ಚಗಿನ ಹಾಲಿನೊಂದಿಗೆ ಮಿಶ್ರ ಮಾಡಿ ಮತ್ತು ಅದನ್ನು ಪೇಸ್ಟ್ ಆಗಿ ಸುರಿಯಿರಿ. \n\n • ಕಸ್ತೂರಿ ಮೆಥಿ ಹೊರತುಪಡಿಸಿ , ಒಣ ಮಸಾಲಾವನ್ನು ಪೇಸ್ಟ್ ಆಗಿ ಸೇರಿಸಿ ಮತ್ತು ಎಣ್ಣೆ ಬೇರ್ಪಡಿಸುವ ತನಕ ಸಾಟ್ ಮಾಡಿ. \n\n • ಸೇರಿಸಿ ಮತ್ತು ಅರ್ಧ ಕಪ್ ನೀರು ಮತ್ತು ತನಕ ಮಾಂಸವನ್ನು ತೊಳೆಯಿರಿ. \n\n • ಕೆನೆ / ಮಲೈ ಸೇರಿಸಿ, 1 ಟೀಸ್ಪೂನ್ ಸಕ್ಕರೆ ಮತ್ತು ಕಸ್ತೂರಿ ಮೆಥಿ. \n\n • ಸಿಮ್ ಎಣ್ಣೆ ಬೇರ್ಪಡಿಸುವ ತನಕ ಎಣ್ಣೆ ಬೇರ್ಪಡಿಸುವವರೆಗೂ ಮತ್ತು ಒಮ್ಮೆ ಮುಗಿದ ನಂತರ, ಹುರಿದ ಕೊಫ್ಟಾಗಳನ್ನು ಮಾಂಸರಹಿತವಾಗಿ ಹಾಕಿ, ಚಪಾಟಿಗಳೊಂದಿಗೆ ಬಿಸಿ ಮಾಡಿ. "};


    String [] d6 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/Gaajar-Halwa_600-min.jpg",
            "ಕ್ಯಾರೆಟ್ ಹಲ್ವಾ","40 ನಿಮಿಷಗಳು",

            "1 1/2 ಕೆ.ಜಿ ಕ್ಯಾರೆಟ್ಗಳು \n • 10 ಹಸಿರು ಏಲಕ್ಕಿ, ಸಂಪೂರ್ಣ \n • 2 ದಾಲ್ಚಿನ್ನಿ ಸ್ಟಿಕ್ಸ್ \n • 500 ಗ್ರಾಂ ಸಕ್ಕರೆ \n • 250 ಗ್ರಾಂ ದೇಸಿ ತುಪ್ಪ (ಸ್ಪಷ್ಟಪಡಿಸಿದ ಬೆಣ್ಣೆ) \n • 400 ಗ್ರಾಂ ಮಂದಗೊಳಿಸಿದ ಹಾಲು (ಆಯ್ಕೆ: ಮಾವಾ / ಖೋಯಾ) \n • 4 ತುಂಡುಗಳು ಚಿನ್ನದ ತೊಗಟೆ \n • 50 ಗ್ರಾಂ ಬಾದಾಮಿ \n • 50 ಗ್ರಾಂ ಪಿಸ್ತಾಗಳು ",

            "• ಕ್ಯಾರೆಟ್ ಅನ್ನು ತುರಿ ಹಾಕಿ, ಶಾಖದ ಮೇಲೆ ಮಡಕೆ ಹಾಕಿ, ಎಲ್ಲಾ ನೀರು ಆವಿಯಾಗುವ ತನಕ ನಿರಂತರವಾಗಿ ಫ್ರೈ ಬೆರೆಸಿ. \n\n • ನಂತರ ಸ್ವಲ್ಪ ಹಸಿರು ಏಲಕ್ಕಿ, ದಾಲ್ಚಿನ್ನಿ ಮತ್ತು ಸಕ್ಕರೆ ಹಾಕಿ ಸ್ವಲ್ಪ ಕಾಲ ಬೇಯಿಸಿ ನಂತರ ಬೇಯಿಸಿ ಬೇಯಿಸಿ 5-7 ನಿಮಿಷಗಳ ನಂತರ. \n\n • ಕೊನೆಯದಾಗಿ, ಮಂದಗೊಳಿಸಿದ ಹಾಲು (ಅಥವಾ ಮಾವಾ / ಖೊಯಾ) ನೊಂದಿಗೆ ಮುಕ್ತಾಯಗೊಳಿಸಿ. \n • ಅಲಂಕಾರಿಕಕ್ಕಾಗಿ ಚಿನ್ನದ ಬಣ್ಣವನ್ನು ಇರಿಸಿ. \n\n • ಪಿಸ್ತಾ ಜೊತೆ ಪ್ಲೇಟ್ ಮತ್ತು ಅಲಂಕರಣವನ್ನು ಪ್ರಸ್ತುತಪಡಿಸಿ ಬಾದಾಮಿ. "};

    String [] d7 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/Aam-Shrikhand-with-Mango-Salad_med-min.jpg",
            "ಆಮ್ ಶ್ರೀಖಂಡ್","40 ನಿಮಿಷಗಳು",

            "•2 ಕಪ್ಗಳು ಮೊಸರು ಮೊಸರು (ತೂಗು ಮೊಸರು) \n • 1/2 ಟೀಸ್ಪೂನ್ ಕೇಸರಿ \n • 2 ಟಿನ್ಸ್ ಮಂದಗೊಳಿಸಿದ ಹಾಲು \n • 300 ಗ್ರಾಂ ತಾಜಾ ಮಾವಿನಕಾಯಿಗಳು \n • 1/2 ಕಪ್ ಸಕ್ಕರೆ \n • 2 ಟೀಸ್ಪೂನ್ ಹಸಿರು ಏಲಕ್ಕಿ ಪುಡಿ \n • 1/4 ಕಪ್ ತಾಜಾ ಕ್ರೀಮ್ \n • 1 ನಿಂಬೆ ರಸ \n • 20 ಗ್ರಾಂ ಪುದೀನ \n • 2 ಟೀಸ್ಪೂನ್ ಚಟ್ ಮಸಾಲಾ \n • 4 ಹಾಳೆಗಳು ಚಿನ್ನದ ವರ್ಕ್ \n • 80 ಗ್ರಾಂ ಪಿಸ್ತಾ, ಸಿಪ್ಪೆ ಸುಲಿದ ಮತ್ತು ಉಪ್ಪು ಇಲ್ಲ ",

            "• ಕೇಸರಿ, ಮಂದಗೊಳಿಸಿದ ಹಾಲು, ಕತ್ತರಿಸಿದ ಮಾವಿನಕಾಯಿ, ಸಕ್ಕರೆ, ಏಲಕ್ಕಿ ಪುಡಿ ಮತ್ತು ಕೆನೆ ಜೊತೆ ಹಾಲಿನ ಮೊಸರು ಮಿಶ್ರಣವನ್ನು ಮಿಶ್ರಣ ಮಾಡಿ. \n\n • ಚೌಕವಾಗಿ ಮಾವಿನಕಾಯಿಯನ್ನು ನಿಂಬೆ ರಸ, ಪುದೀನ ಮತ್ತು ಚಟ್ ಮಸಾಲಾ ಮಿಶ್ರಣ ಮಾಡುವ ಮೂಲಕ ಮಾವಿನ ಸಲಾಡ್ ತಯಾರಿಸಿ. \n\n • ಮಾವಿನ ಸಲಾಡ್ನೊಂದಿಗೆ ಶ್ರೀಖಂಡ್ ಅನ್ನು ಸರ್ವ್ ಮಾಡಿ ಚಿನ್ನದ ವರ್ಕ್ ಮತ್ತು ಪಿಸ್ತಾ ಜೊತೆ ಅಲಂಕಾರಿಕ. "};

    String [] d8 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/l8-min.jpg",
            "ಕೊಹ್ಲಾಪುರಿ ಚಿಕನ್", "2 ಗಂಟೆಗಳು",

            "ಮ್ಯಾರಿನೇಶನ್ಗಾಗಿ: \n • 1 ಕೆಜಿ ಕೋಳಿ \n • 2/3 ಕಪ್ ಮೊಸರು \n • 1 ಟೀಸ್ಪೂನ್ ಅರಿಶಿನ ಪುಡಿ \n • 2 ಟೀಸ್ಪೂನ್ ಕೆಂಪು ಮೆಣಸಿನಕಾಯಿ \n • 1 ಟೀಸ್ಪೂನ್ ಬೆಳ್ಳುಳ್ಳಿ ಪೇಸ್ಟ್ \n • ಉಪ್ಪು ರುಚಿ \n • 1 ಕೊಹ್ಲಾಪುರ್ ಮಸಾಲೆಗಾಗಿ: \n • 2 ಟೀಸ್ಪೂನ್ ಕಾರ್ನ್ ಅಥವಾ ಕಡಲೆಕಾಯಿ ಎಣ್ಣೆ \n • 1 ಬೇ ಎಲೆಯು \n • 2 ದಾಲ್ಚಿನ್ನಿ ಸ್ಟಿಕ್ಸ್ \n • 6 ಲವಂಗಗಳು \n • 1/2 ಟೀಸ್ಪೂನ್ ಪುಡಿಮಾಡಿದ ಕಪ್ಪು ಮೆಣಸು \n • 2 ಮಧ್ಯಮ ಈರುಳ್ಳಿ, ಕತ್ತರಿಸಿದ \n • 2 ಟೀಸ್ಪೂನ್ ತುರಿದ ತೆಂಗಿನಕಾಯಿ \n • 1 ದೊಡ್ಡ ಟೊಮೆಟೊ, ಕತ್ತರಿಸಿ \n\n ಮುಖ್ಯ ತಯಾರಿಗಾಗಿ: \n • 2 ಟೀಸ್ಪೂನ್ ತೈಲ \n • 1 ಟೀಸ್ಪೂನ್ ಕೊತ್ತಂಬರಿ ಎಲೆಗಳು",

            "ಮ್ಯಾರಿನೇಶನ್ಗಾಗಿ: \n • ಅರಿಶಿನ ಪುಡಿ, ಕೆಂಪು ಮೆಣಸಿನಕಾಯಿಗಳು, ಬೆಳ್ಳುಳ್ಳಿ ಪೇಸ್ಟ್, ಉಪ್ಪು ಮತ್ತು ಸುಣ್ಣದ ರಸವನ್ನು ಮೊಸರು ಜೊತೆ ಮಿಶ್ರಣ ಮಾಡಿ, ಮ್ಯಾರಿನೇಡ್ ಸಿದ್ಧವಾಗಿದೆ. \n • ಕರಿಮೆಣಸುಗಳನ್ನು ಮ್ಯಾರಿನೇಡ್ನಲ್ಲಿ ಸೇರಿಸಿ ಮತ್ತು ಅರ್ಧ ಘಂಟೆಯ ಕಾಲ ಬಿಡಿ. \n\n ಕೊಹ್ಲಾಪುರ್ ಮಸಾಲೆಗಾಗಿ: \n • ಪ್ಯಾನ್ ನಲ್ಲಿ ಎಣ್ಣೆ ಹಾಕಿ, ಬೇ ಎಲೆ, ದಾಲ್ಚಿನ್ನಿ, ಲವಂಗ, ಪುಡಿಮಾಡಿದ ಕರಿಮೆಣಸು ಮತ್ತು ಕತ್ತರಿಸಿದ ಈರುಳ್ಳಿಯನ್ನು ಸೇರಿಸಿ ಈರುಳ್ಳಿಗಳು ಅರೆಪಾರದರ್ಶಕವಾಗುವವರೆಗೆ ತಿರುಗಿಸಿ. \n • ತುರಿದ ತೆಂಗಿನಕಾಯಿಯನ್ನು ಸೇರಿಸಿ ತೆಂಗಿನಕಾಯಿ ಬಣ್ಣವನ್ನು ತನಕ ಬೆರೆಸಿ. \n • ಟೊಮ್ಯಾಟೊ ಸೇರಿಸಿ 10 ನಿಮಿಷ ಬೇಯಿಸಿ ಕೂಲ್ ಮತ್ತು ಮಿಶ್ರಣವನ್ನು ಬ್ಲೆಂಡರ್ನಲ್ಲಿ ಸೇರಿಸಿ. \n\n ಮುಖ್ಯ ತಯಾರಿಕೆಯಲ್ಲಿ: \n • ತೈಲವನ್ನು ತೊಳೆಯಿರಿ ಮತ್ತು ಪ್ಯಾನ್ಗೆ ಮ್ಯಾರಿನೇಡ್ ಚಿಕನ್ ಸೇರಿಸಿ, ಕಡಿಮೆ ಬೆಂಕಿಯಲ್ಲಿ 25 ನಿಮಿಷ ಬೇಯಿಸಿ. ನಿಯಮಿತವಾಗಿ ಬೆರೆಸಿ. \n • ಐದು ನಿಮಿಷಗಳ ಕಾಲ ಕಡಿಮೆ ಜ್ವಾಲೆಯ ಮೇಲೆ ಅಂಟಿಸಿ ಮತ್ತು ತಳಮಳಿಸುತ್ತಿರು. \n • ಕೊಲ್ಹಾಪುರ್ ಚಿಕನ್ ತಯಾರಿಸಲಾಗುತ್ತದೆ ಕೊತ್ತಂಬರಿ ಎಲೆಗಳೊಂದಿಗೆ ಅಲಂಕರಿಸಲು. \n • ಅನ್ನದೊಂದಿಗೆ ಬಿಸಿಯಾಗಿ ಸರ್ವ್ ಮಾಡಿ."};

    String [] d9  = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/51154870_chicken-tikka-masala_1x1-min.jpg",

            "ಚಿಕನ್ ಟಿಕ್ಕಾ ಮಸಾಲಾ","1 ಗಂಟೆ",

            "• 6 ಬೆಳ್ಳುಳ್ಳಿ ಲವಂಗ, ನುಣ್ಣಗೆ ತುರಿದ \n • 4 ಟೀ ಚಮಚಗಳು ತುರಿದ ಸುಲಿದ ಶುಂಠಿ \n • 4 ಟೀಚಮಚಗಳು ನೆಲದ ಅರಿಶಿನ \n • 2 ಟೀ ಚಮಚಗಳು ಗರಂ ಮಸಾಲಾ \n • 2 ಟೀ ಚಮಚಗಳು ನೆಲದ ಕೊತ್ತಂಬರಿ \n • 2 ಟೀ ಚಮಚದ ಜೀರಿಗೆ \n • 1 1 / 2 ಕಪ್ಗಳು ಸಂಪೂರ್ಣ ಹಾಲು ಮೊಸರು (ಅಲ್ಲ ಗ್ರೀಕ್) \n • 1 ಟೇಬಲ್ಸ್ಪೂನ್ ಕೋಷರ್ ಉಪ್ಪು \n • 2 ಪೌಂಡ್ ಚರ್ಮರಹಿತ, ಮೂಳೆಗಳಿಲ್ಲದ ಕೋಳಿ ಸ್ತನಗಳನ್ನು, ಉದ್ದವಾಗಿ ಅರ್ಧಮಟ್ಟಕ್ಕಿಳಿಸಲಾಯಿತು \n • 3 ಟೇಬಲ್ಸ್ಪೂನ್ ತುಪ್ಪ (ಸ್ಪಷ್ಟವಾದ ಬೆಣ್ಣೆ) ಅಥವಾ ತರಕಾರಿ ತೈಲ \n • 1 ಸಣ್ಣ ಈರುಳ್ಳಿ , ತೆಳುವಾಗಿ ಕತ್ತರಿಸಿ \n • 1/4 ಕಪ್ ಟೊಮ್ಯಾಟೊ ಪೇಸ್ಟ್ \n • ಏಲಕ್ಕಿ ಬೀಜಗಳು, ಪುಡಿಮಾಡಿದ \n • 2 ಒಣಗಿದ ಚಿಲೆಸ್ ಡಿಅರ್ಬೋಲ್ ಅಥವಾ 1/2 ಟೀಚಮಚ ಪುಡಿ ಮಾಡಿದ ಕೆಂಪು ಮೆಣಸು ಪದರಗಳು \n • 1 28-ಔನ್ಸ್ ಸಂಪೂರ್ಣ ಸಿಪ್ಪೆ ಸುಲಿದ ಟೊಮೆಟೊಗಳು \n • 2 ಕಪ್ ಭಾರೀ ಕೆನೆ \n • ಅಲಂಕಾರಿಕಕ್ಕಾಗಿ 3/4 ಕಪ್ ಕತ್ತರಿಸಿದ ತಾಜಾ ಸಿಲಾಂಟ್ರೋ ಪ್ಲಸ್ sprigs \n • ಆವಿಯಿಂದ ಬೇಸ್ಮತಿ ಅಕ್ಕಿ (ಸೇವೆಗಾಗಿ) ",


            "• ಒಂದು ಸಣ್ಣ ಬಟ್ಟಲಿನಲ್ಲಿ ಬೆಳ್ಳುಳ್ಳಿ, ಶುಂಠಿ, ಅರಿಶಿನ, ಗರಂ ಮಸಾಲಾ, ಕೊತ್ತಂಬರಿ, ಮತ್ತು ಜೀರಿಗೆ ಸೇರಿಸಿ.ಒಂದು ಮಧ್ಯಮ ಬಟ್ಟಲಿನಲ್ಲಿ ಮಸಾಲೆ ಮೊಸರು, ಉಪ್ಪು ಮತ್ತು ಅರ್ಧ ಮಸಾಲೆ ಮಿಶ್ರಣವನ್ನು ಸೇರಿಸಿ ಕೋಳಿ ಸೇರಿಸಿ ಮತ್ತು ಕೋಟ್ಗೆ ತಿರುಗಿ 4-6 ಗಂಟೆಗಳ ಕವರ್ ಕವರ್ ಮತ್ತು ಚಿಲ್ ಮಸಾಲೆ ಮಿಶ್ರಣವನ್ನು ಉಳಿದಿರುತ್ತದೆ. \n\n • ಸಾಧಾರಣ ಶಾಖದ ಮೇಲೆ ಭಾರೀ ಹೆಜ್ಜೆಯಲ್ಲಿ ಉಪ್ಪು ಹಾಕಿ ಈರುಳ್ಳಿ, ಟೊಮೆಟೊ ಪೇಸ್ಟ್, ಏಲಕ್ಕಿ ಮತ್ತು ಚಿಲಿ ಮತ್ತು ಉಪ್ಪು ಸೇರಿಸಿ, ಟೊಮೆಟೊ ಪೇಸ್ಟ್ ಕತ್ತರಿಸಿ, ಈರುಳ್ಳಿ ಮೃದುವಾಗುವುದಕ್ಕಿಂತ ಹೆಚ್ಚಾಗಿ, ಸುಮಾರು 5 ನಿಮಿಷಗಳ ಕಾಲ, ಮಸಾಲೆ ಮಿಶ್ರಣವನ್ನು ಉಳಿದ ಅರ್ಧವನ್ನು ಸೇರಿಸಿ ಮತ್ತು ಬೇಯಿಸಿ, ಸಾಮಾನ್ಯವಾಗಿ 4 ನಿಮಿಷಗಳಷ್ಟು ಮಡಕೆ ಪ್ರಾರಂಭವಾಗುವವರೆಗೆ, ಆಗಾಗ್ಗೆ ಸ್ಫೂರ್ತಿದಾಯಕವಾಗಿದೆ. \n\n • ರಸವನ್ನು ನೀರಿನಲ್ಲಿ ಟೊಮೆಟೊಗಳನ್ನು ಸೇರಿಸಿ, ನಿಮ್ಮ ಕೈಗಳನ್ನು ಸೇರಿಸಿದಂತೆ ಅವುಗಳನ್ನು ಹಚ್ಚಿ. ಒಂದು ಕುದಿಯುತ್ತವೆ, ಶಾಖವನ್ನು ತಗ್ಗಿಸಿ ಮತ್ತು ತಳಮಳಿಸುತ್ತಿರು, ಆಗಾಗ್ಗೆ ಸ್ಫೂರ್ತಿದಾಯಕ ಮತ್ತು ಮಡಕೆ ಕೆಳಗಿನಿಂದ browned ಬಿಟ್ಗಳನ್ನು ಕೆರೆದು, 8-10 ನಿಮಿಷಗಳ ತನಕ ಸಾಸ್ ದಪ್ಪವಾಗಿರುತ್ತದೆ. \n\n • ಕ್ರೀಮ್ ಮತ್ತು ಕತ್ತರಿಸಿದ ಸಿಲಾಂಟ್ರೋ ಸೇರಿಸಿ ತಳಮಳಿಸುತ್ತಿರು, ಕೆಲವೊಮ್ಮೆ ಸಾಸ್ ದಪ್ಪವಾಗಿರುತ್ತದೆ, 30-40 ನಿಮಿಷಗಳು. \n\n • ಏತನ್ಮಧ್ಯೆ, ಪೂರ್ವಭಾವಿಯಾಗಿ ಕಾಯಿಸಲ್ಪಟ್ಟಿರುವ ಅಡುಗೆಯವನು ಹಾಳೆಯೊಂದಿಗೆ ಒಂದು ರಿಮ್ಡ್ ಬೇಕಿಂಗ್ ಶೀಟ್ ಅನ್ನು ಹಾಕಿ ಮತ್ತು ಶೀಟ್ ಒಳಗೆ ಒಂದು ತಂತಿ ರಾಕ್ ಅನ್ನು ಹೊಂದಿಸಿ. ಒಂದೇ ಪದರದಲ್ಲಿ ಚಿಕನ್ ಮೇಲೆ ಚಿಕನ್ ವ್ಯವಸ್ಥೆ. ಚಿಕನ್ ತನಕ ಬ್ರೋಲ್ ಸ್ಥಳಗಳಲ್ಲಿ (ಅದನ್ನು ಬೇಯಿಸಲಾಗುವುದಿಲ್ಲ) ಸುಮಾರು 10 ನಿಮಿಷಗಳವರೆಗೆ ಕಪ್ಪಾಗಲು ಆರಂಭಿಸುತ್ತದೆ. \n\n • ಚಿಕನ್ ಅನ್ನು 8-10 ನಿಮಿಷಗಳ ತನಕ ಬೇಯಿಸಿದ ತನಕ ಸಾಂದರ್ಭಿಕವಾಗಿ ಸ್ಫೂರ್ತಿದಾಯಕವಾಗಿಸಿ ಚಿಕನ್ ಅನ್ನು ಬೈಟ್-ಗಾತ್ರದ ತುಂಡುಗಳಾಗಿ ಕತ್ತರಿಸಿ ಸಾಸ್ಗೆ ಸೇರಿಸಿ ಮತ್ತು ತಳಮಳಿಸುತ್ತಿರು. ಅಕ್ಕಿ ಮತ್ತು ಸಿಲಾಂಟ್ರೋ ಬುಗ್ಗೆಗಳೊಂದಿಗೆ ಸೇವೆ. "};


    String [] d10 ={"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/mango-lassi-aam-lassi-2-min.jpg",
            "ಮ್ಯಾಂಗೋ ಲಾಸ್ಸಿ","10 ನಿಮಿಷಗಳು",
            "•1 ಕಪ್ ಸರಳ ಮೊಸರು \n • 1/2 ಕಪ್ ಹಾಲು \n • 1 ಕಪ್ ತುಂಬಾ ಮಾಗಿದ ಮಾವಿನಕಾಯಿ, ಹೆಪ್ಪುಗಟ್ಟಿದ ಕತ್ತರಿಸಿದ ಮಾವಿನಕಾಯಿ, ಅಥವಾ ಒಂದು ಕಪ್ ಕ್ಯಾನ್ಡ್ ಮಾವಿನ ತಿರುಳನ್ನು ಕತ್ತರಿಸಿ \n • 4 ಟೀಚಮಚ ಜೇನುತುಪ್ಪ ಅಥವಾ ಸಕ್ಕರೆ, ರುಚಿಗೆ ಹೆಚ್ಚು ಅಥವಾ ಕಡಿಮೆ \n • ನೆಲ ಏಲಕ್ಕಿ ಡ್ಯಾಶ್ (ಐಚ್ಛಿಕ) \n • ಐಸ್ (ಐಚ್ಛಿಕ) ",

            "•ಮಾವಿನಕಾಯಿ, ಮೊಸರು, ಹಾಲು, ಸಕ್ಕರೆ ಮತ್ತು ಏಲಕ್ಕಿಗೆ 2 ನಿಮಿಷಗಳ ಕಾಲ ಬ್ಲೆಂಡರ್ ಮತ್ತು ಮಿಶ್ರಣವಾಗಿ ಇರಿಸಿ. \n\n • ನೀವು ಹೆಚ್ಚು ಮಿಲ್ಕ್ಶೇಕ್ ಸ್ಥಿರತೆಯನ್ನು ಬಯಸಿದರೆ ಮತ್ತು ಅದು ಬಿಸಿ ದಿನವಾಗಿದ್ದರೆ, ಕೆಲವು ಐಸ್ನಲ್ಲಿ ಮಿಶ್ರಣ ಮಾಡುವುದು ಅಥವಾ ಐಸ್ ಘನಗಳು. \n\n • ಪೂರೈಸಲು ನೆಲದ ಏಲಕ್ಕಿ ಸಣ್ಣ ಪಿಂಚ್ನೊಂದಿಗೆ ಸಿಂಪಡಿಸಿ. "};

    String [] d11 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/Tadka-Dhal-min.jpg",
            "ತಡ್ಕ-ದಲ್","1 ಗಂಟೆ",

            "1 1/2 ಕಪ್ಗಳು ಒಣಗಿದ ಟೋರ್ ಢಲ್ (ಹಳದಿ ಮಸೂರ / ಹಳದಿ ಒಡೆದ ಅವರೆಕಾಳು), ಹಲವಾರು ಬದಲಾವಣೆಗಳ ನೀರಿನಲ್ಲಿ ತೊಳೆಯಲಾಗುತ್ತದೆ \n • 1 ಟೀಚಮಚ ನೆಲದ ಅರಿಶಿನ \n • 2 ಕಪ್ಪು ಏಲಕ್ಕಿ ಬೀಜಕೋಶಗಳು (ಐಚ್ಛಿಕ) \n • 3 ಟೇಬಲ್ಸ್ಪೂನ್ ಸಸ್ಯಜನ್ಯ ಎಣ್ಣೆ \n • 2 ದಾಲ್ಚಿನ್ನಿ ಸ್ಟಿಕ್ಗಳು ​​\n • 4 ಹಸಿರು ಏಲಕ್ಕಿ ಬೀಜಗಳು \n • 6 ಲವಂಗಗಳು \n • 2 ಟೀ ಚಮಚಗಳು ಕಪ್ಪು ಸಾಸಿವೆ ಬೀಜಗಳು \n • 1 ಟೀಚಮಚ ಜೀರಿಗೆ ಬೀಜಗಳು \n • 2 ಸ್ಕಲ್ಲಿಯನ್ಸ್, ಸಣ್ಣದಾಗಿ ಕತ್ತರಿಸಿ \n • 2-3 ಚಿಲಿಗಳು, ಯಾವುದೇ ಬಣ್ಣ , ಬೀಜಗಳನ್ನು ನೀವು ಉಪ್ಪು ಇಷ್ಟವಿಲ್ಲದಿದ್ದರೆ, ಕೆಲವು ಕತ್ತರಿಸಿದ ಮತ್ತು ಉಳಿದವು ಸಂಪೂರ್ಣ ಉಳಿದಿವೆ \n • 2 ಕೊಬ್ಬಿನ ಲವಂಗ ಬೆಳ್ಳುಳ್ಳಿ, ನುಣ್ಣಗೆ ಕತ್ತರಿಸಿದ \n • 1 ಚಮಚ ಸಿಪ್ಪೆ ಸುಲಿದ ಮತ್ತು ನುಣ್ಣಗೆ ಕತ್ತರಿಸಿದ ತಾಜಾ ಶುಂಠಿ \n • 6 ಚೆರ್ರಿ ಟೊಮೆಟೊಗಳು ಅರ್ಧದಷ್ಟು ಕತ್ತರಿಸಿ \n • ಸಮುದ್ರ ಉಪ್ಪಿನ ಉತ್ತಮ ಪಿಂಚ್ ಅಥವಾ ರುಚಿಗೆ \n • 1 ಟೀಚಮಚ ಸಕ್ಕರೆ, ಅಥವಾ ರುಚಿಗೆ \n • 1/2 ನಿಂಬೆ ರಸ, ಅಥವಾ ರುಚಿಗೆ ತಕ್ಕಂತೆ",

            "\n • ತೆಂಗಿನ ಮಣ್ಣನ್ನು ತಣ್ಣಗಿನ ನೀರಿನ ದೊಡ್ಡ ಲೋಹದ ಬೋಗುಣಿ (ಸುಮಾರು 4 ಕಪ್ಗಳು ಮಾಡುತ್ತಾರೆ) ಮತ್ತು ಅರಿಶಿನ ಮತ್ತು ಕಪ್ಪು ಏಲಕ್ಕಿ ಬೀಜಗಳಲ್ಲಿ ಬೆರೆಸಿ (ಬಳಸುತ್ತಿದ್ದರೆ) - ಇವು ಸೂಕ್ಷ್ಮವಾದ ಹೊಗೆಯುಳ್ಳ ಪರಿಮಳವನ್ನು ಸೇರಿಸುತ್ತವೆ. ನಿಮಿಷಗಳವರೆಗೆ ಅಥವಾ ಮಸೂರವು ಮೃದುಗೊಳಿಸಿದಾಗ ಮತ್ತು ಮುರಿಯಲು ಪ್ರಾರಂಭಿಸಿತು.ಮೇಲಿನ ಮೇಲೆ ಇರುವ ಯಾವುದೇ ಫೋಮ್ ಅನ್ನು ಸ್ಕಿಮ್ ಮಾಡಿ ಮತ್ತು ಮಸೂರವನ್ನು ಪ್ರತಿ ಬಾರಿಯೂ ಮತ್ತೆ ಬೆರೆಸಲು ಪ್ರಾರಂಭಿಸಿದರೆ ಅವುಗಳು ಒಣಗಿದಲ್ಲಿ, ಒಣಗಿದಲ್ಲಿ ಮತ್ತಷ್ಟು ಸೇರಿಸಿ ನೀರು. \n\n • ಮಸೂರವು ಮೃದುವಾದ ನಂತರ, ಶಾಖವನ್ನು ತಿರಸ್ಕರಿಸಿ ತಡ್ಕ ಮಾಡಿ, ತೈಲವನ್ನು ಬಾಣಲೆಗೆ ಬಿಸಿ ಮತ್ತು ದಾಲ್ಚಿನ್ನಿ ಸ್ಟಿಕ್ಗಳು, ಹಸಿರು ಏಲಕ್ಕಿ ಬೀಜಗಳು ಮತ್ತು ಲವಂಗ ಸೇರಿಸಿ. ಸಾಸಿವೆ ಮತ್ತು ಜೀರಿಗೆ ಬೀಜಗಳಲ್ಲಿ ಬೆರೆಸಲು ನೀವು ತಯಾರಾಗಿದ್ದೀರಿ ಅವರು ಸಿಜ್ಲಿಂಗ್ ಮಾಡಿದಾಗ, ಸ್ಕಲ್ಲಿಯನ್ಸ್, ಚಿಲೆಸ್, ಬೆಳ್ಳುಳ್ಳಿ ಮತ್ತು ಶುಂಠಿಯಲ್ಲಿ ಬೆರೆಸಿ. \n\n • ಒಂದು ನಿಮಿಷದ ನಂತರ, ಟೊಮ್ಯಾಟೊ ಮೂಲಕ ಬೆರೆಸಿ ಮತ್ತು ಆಫ್ ಮಾಡಿ ಉಷ್ಣ, ಉಪ್ಪು, ಸಕ್ಕರೆ ಮತ್ತು ನಿಂಬೆ ರಸದೊಂದಿಗೆ ಋತುವಿನ ಮೇಲೆ ತೇಲುತ್ತದೆ. ಮಿತ್ರರಾಷ್ಟ್ರ, ಕತ್ತರಿಸಿದ ಸಿಲಾಂಟ್ರೋ ಮೂಲಕ ಸಾಕಷ್ಟು ಬೆರೆಸಿ ಮತ್ತು ಅಂತಿಮ ಆರಾಮ ಆಹಾರಕ್ಕಾಗಿ ಕೆಲವು ಅಕ್ಕಿ ಅಥವಾ ತಾಜಾ ಬ್ರೆಡ್ನೊಂದಿಗೆ ಸೇವೆ ಮಾಡಿ. "};

    String [] d12 = {"https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/pav.bhaji%20(1)-min.jpg",


            "ಪಾವ್ ಬಾಜಿ", "40 ನಿಮಿಷಗಳು",
            " • 2 ಟೀಸ್ಪೂನ್ ಎಣ್ಣೆ \n • 1 ದೊಡ್ಡ ಈರುಳ್ಳಿ ಸಣ್ಣದಾಗಿ ಕತ್ತರಿಸಿ \n • 1 ಟೀಸ್ಪೂನ್ ಶುಂಠಿ, ಮೃದುಮಾಡಲಾಗುತ್ತದೆ \n • 1 ಟೀಸ್ಪೂನ್ ಮೃದುಮಾಡಿದ ಬೆಳ್ಳುಳ್ಳಿ \n • 2 ಹಸಿರು ಮೆಣಸಿನಕಾಯಿಗಳು ಉದ್ದವಾದ ಸ್ಲಿಟ್ \n • 1 ಗಂಟೆ ಮೆಣಸು, ಸಣ್ಣದಾಗಿ ಕೊಚ್ಚಿದ \n • 3 ಟೊಮ್ಯಾಟೊ , ಸಣ್ಣದಾಗಿ ಕೊಚ್ಚಿದ \n • ಈ ತರಕಾರಿಗಳಲ್ಲಿ ಪ್ರತಿಯೊಂದರಲ್ಲೂ 1 ಕಪ್ ತಕ್ಕಷ್ಟು ಚೌಕವಾಗಿ ಮತ್ತು ಬೇಯಿಸಿದ: ಹಸಿರು ಬೀನ್ಸ್, ಕ್ಯಾರೆಟ್, ಹೂಕೋಸು \n • 1 ಕಪ್ ಬೇಯಿಸಿದ ಮತ್ತು ಸಿಪ್ಪೆ ಸುಲಿದ ಆಲೂಗಡ್ಡೆ \n • 1/2 ಕಪ್ ಬೇಯಿಸಿದ ಹಸಿರು ಅವರೆಕಾಳು \n • 3 ಟೀಸ್ಪೂನ್ ಪಾವ್ ಬಾಜಿ ಮಸಾಲಾ \n • 1 ಟೀಸ್ಪೂನ್ ಕೆಂಪು ಮೆಣಸಿನ ಪುಡಿ \n • 1/4 ಟೀಸ್ಪೂನ್ ಅರಿಶಿನ ಪುಡಿ \n • ಉಪ್ಪು ರುಚಿಗೆ \n • 1/2 ಟೀಸ್ಪೂನ್ ನಿಂಬೆ ರಸ \n • 2 ಬನ್ಗಳು (ಪಾವ್ಸ್ ಎಂದು ಕರೆಯಲಾಗುತ್ತದೆ) ",
            "• ಒಂದು ಪ್ಯಾನ್ ನಲ್ಲಿ ಬಿಸಿ ಎಣ್ಣೆ ಸೇರಿಸಿ ಈರುಳ್ಳಿಗಳು, ಶುಂಠಿ, ಬೆಳ್ಳುಳ್ಳಿ ಮತ್ತು ಹಸಿರು ಮೆಣಸಿನಕಾಯಿ ಸೇರಿಸಿ ಮತ್ತು ಸುವರ್ಣ ಕಂದು ತನಕ ಬೇಯಿಸಿ ಬೇಯಿಸಿದ ತರಕಾರಿಗಳನ್ನು ಸೇರಿಸಿ ಮತ್ತು ಬೆರೆಸಿ. \n\n • ಪಾವ್ ಬಾಜಿ ಮಸಾಲಾ, ಅರಿಶಿನ, ಟೊಮ್ಯಾಟೊ, ಕ್ಯಾಪ್ಸಿಕಂ ಮತ್ತು ಉಪ್ಪಿನಲ್ಲಿ ಬೆರೆಸಿ. ಸ್ಲಿಟ್ ಹಸಿರು ಮೆಣಸಿನಕಾಯಿ ಮತ್ತು ನೀರು ಸೇರಿಸಿ ಮತ್ತು ಚೆನ್ನಾಗಿ ಮಿಶ್ರಣ 4-5 ನಿಮಿಷ ಬೇಯಿಸಿ. \n\n • ಮ್ಯಾಶ್ ತರಕಾರಿಗಳು ಸ್ವಲ್ಪ ಮೆಣಸಿನಕಾಯಿ ತನಕ. \n\n • ನಿಂಬೆ ರಸವನ್ನು ಸೇರಿಸಿ ಮತ್ತು ಸುಟ್ಟ ಬನ್ ಅಥವಾ ಇತರ ಬ್ರೆಡ್ನೊಂದಿಗೆ ಸೇವಿಸಿ." };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code To ask for User Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //Code to ask for User Permissions Ends


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(!(android.os.Build.VERSION.SDK_INT <21))
            setSupportActionBar(toolbar);

                if(isNetworkConnected()) {
                    SharedPreferences preferences = getSharedPreferences("only_once", Context.MODE_PRIVATE);
                    if(preferences.getInt("first", 0) == 0) // First Open of App.
                    {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("first", 1);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Please Wait!", Toast.LENGTH_SHORT).show();
                        localVersion = preferences.getInt("version", 0);
                        new baseNewsVersion().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/base_version.json");
                        new baseFile().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/kannada_recipes.json");
                    }
                }
                else
                {
                    SharedPreferences preferences = getSharedPreferences("only_once", Context.MODE_PRIVATE);
                    if (preferences.getBoolean("firstrun", true)) {
                        Toast.makeText(this, "Please Connect to Internet!", Toast.LENGTH_LONG).show();
                        preferences.edit().putBoolean("firstrun", false).commit();
                    }

                }
        pd = new ProgressDialog(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (R.id.fab){
                    case R.id.fab:
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.openDrawer(GravityCompat.START);
                        break;
                    default:
                        DrawerLayout drawers = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawers.closeDrawer(Gravity.START);
                        break;
                }
            }
        });

        Typeface regular_font =Typeface.createFromAsset(this.getAssets(),"fonts/Aller_Rg.ttf");

        HorizontalScrollView h1 = (HorizontalScrollView)findViewById(R.id.h1);
        h1.setBackground(getResources().getDrawable(R.drawable.h1));

        HorizontalScrollView h2 = (HorizontalScrollView)findViewById(R.id.h2);
        h2.setBackground(getResources().getDrawable(R.drawable.h2));

        HorizontalScrollView h3 = (HorizontalScrollView)findViewById(R.id.h3);
        h3.setBackground(getResources().getDrawable(R.drawable.h3));
        setSupportActionBar(toolbar);
        // Picasso.with(this).load("https://images6.alphacoders.com/336/336514.jpg").placeholder(R.drawable.background).centerCrop().into(h1);


        CardView c1 =(CardView)findViewById(R.id.c1);
        ImageView i1 = (ImageView)findViewById(R.id.i1);
        TextView t1 = (TextView)findViewById(R.id.t1);
        t1.setTypeface(regular_font);
        t1.setText(d1[1]);
        Picasso.with(this).load(d1[0]).placeholder(R.drawable.background).into(i1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d1[1]);
                intent.putExtra("time",d1[2]);
                intent.putExtra("ingredients",d1[3]);
                intent.putExtra("directions",d1[4]);
                intent.putExtra("img",d1[0]);
                intent.putExtra("sr",d1[0]);
                startActivity(intent);
            }
        });


        CardView c2 =(CardView)findViewById(R.id.c2);
        ImageView i2 = (ImageView)findViewById(R.id.i2);
        TextView t2 =(TextView)findViewById(R.id.t2);
        t2.setTypeface(regular_font);
        t2.setText(d2[1]);
        Picasso.with(this).load(d2[0]).placeholder(R.drawable.background).into(i2);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d2[1]);
                intent.putExtra("time",d2[2]);
                intent.putExtra("ingredients",d2[3]);
                intent.putExtra("directions",d2[4]);
                intent.putExtra("img",d2[0]);
                intent.putExtra("sr",d2[0]);
                startActivity(intent);

            }
        });

        CardView c3 =(CardView)findViewById(R.id.c3);
        ImageView i3 = (ImageView)findViewById(R.id.i3);
        TextView t3 = (TextView)findViewById(R.id.t3);
        t3.setTypeface(regular_font);
        t3.setText(d3[1]);
        Picasso.with(this).load(d3[0]).placeholder(R.drawable.background).into(i3);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d3[1]);
                intent.putExtra("time",d3[2]);
                intent.putExtra("ingredients",d3[3]);
                intent.putExtra("directions",d3[4]);
                intent.putExtra("img",d3[0]);
                intent.putExtra("sr",d3[0]);
                startActivity(intent);

            }
        });

        CardView c4 =(CardView)findViewById(R.id.c4);
        ImageView i4 = (ImageView)findViewById(R.id.i4);
        TextView t4 = (TextView)findViewById(R.id.t4);
        t4.setTypeface(regular_font);
        t4.setText(d4[1]);
        Picasso.with(this).load(d4[0]).placeholder(R.drawable.background).into(i4);
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d4[1]);
                intent.putExtra("time",d4[2]);
                intent.putExtra("ingredients",d4[3]);
                intent.putExtra("directions",d4[4]);
                intent.putExtra("img",d4[0]);
                intent.putExtra("sr",d4[0]);
                startActivity(intent);
            }
        });


        CardView c5 =(CardView)findViewById(R.id.c5);
        ImageView i5 = (ImageView)findViewById(R.id.i5);
        TextView t5 = (TextView)findViewById(R.id.t5);
        t5.setTypeface(regular_font);
        t5.setText(d5[1]);
        Picasso.with(this).load(d5[0]).placeholder(R.drawable.background).into(i5);
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d5[1]);
                intent.putExtra("time",d5[2]);
                intent.putExtra("ingredients",d5[3]);
                intent.putExtra("directions",d5[4]);
                intent.putExtra("img",d5[0]);
                intent.putExtra("sr",d5[0]);
                startActivity(intent);
            }
        });


        CardView c6 =(CardView)findViewById(R.id.c6);
        ImageView i6 = (ImageView)findViewById(R.id.i6);
        TextView t6 = (TextView)findViewById(R.id.t6);
        t6.setTypeface(regular_font);
        t6.setText(d6[1]);
        Picasso.with(this).load(d6[0]).placeholder(R.drawable.background).into(i6);
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d6[1]);
                intent.putExtra("time",d6[2]);
                intent.putExtra("ingredients",d6[3]);
                intent.putExtra("directions",d6[4]);
                intent.putExtra("img",d6[0]);
                intent.putExtra("sr",d6[0]);
                startActivity(intent);
            }
        });


        CardView c7 =(CardView)findViewById(R.id.c7);
        ImageView i7 = (ImageView)findViewById(R.id.i7);
        TextView t7 = (TextView)findViewById(R.id.t7);
        t7.setTypeface(regular_font);
        t7.setText(d7[1]);
        Picasso.with(this).load(d7[0]).placeholder(R.drawable.background).into(i7);
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d7[1]);
                intent.putExtra("time",d7[2]);
                intent.putExtra("ingredients",d7[3]);
                intent.putExtra("directions",d7[4]);
                intent.putExtra("img",d7[0]);
                intent.putExtra("sr",d7[0]);
                startActivity(intent);
            }
        });


        CardView c8 =(CardView)findViewById(R.id.c8);
        ImageView i8 = (ImageView)findViewById(R.id.i8);
        TextView t8 = (TextView)findViewById(R.id.t8);
        t8.setTypeface(regular_font);
        t8.setText(d8[1]);
        Picasso.with(this).load(d8[0]).placeholder(R.drawable.background).into(i8);
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d8[1]);
                intent.putExtra("time",d8[2]);
                intent.putExtra("ingredients",d8[3]);
                intent.putExtra("directions",d8[4]);
                intent.putExtra("img",d8[0]);
                intent.putExtra("sr",d8[0]);
                startActivity(intent);
            }
        });


        CardView c9 =(CardView)findViewById(R.id.c9);
        ImageView i9 = (ImageView)findViewById(R.id.i9);
        TextView t9 = (TextView)findViewById(R.id.t9);
        t9.setTypeface(regular_font);
        t9.setText(d9[1]);
        Picasso.with(this).load(d9[0]).placeholder(R.drawable.background).into(i9);
        c9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d9[1]);
                intent.putExtra("time",d9[2]);
                intent.putExtra("ingredients",d9[3]);
                intent.putExtra("directions",d9[4]);
                intent.putExtra("img",d9[0]);
                intent.putExtra("sr",d9[0]);
                startActivity(intent);
            }
        });

        CardView c10 =(CardView)findViewById(R.id.c10);
        ImageView i10 = (ImageView)findViewById(R.id.i10);
        TextView t10 = (TextView)findViewById(R.id.t10);
        t10.setTypeface(regular_font);
        t10.setText(d10[1]);
        Picasso.with(this).load(d10[0]).placeholder(R.drawable.background).into(i10);
        c10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d10[1]);
                intent.putExtra("time",d10[2]);
                intent.putExtra("ingredients",d10[3]);
                intent.putExtra("directions",d10[4]);
                intent.putExtra("img",d10[0]);
                intent.putExtra("sr",d10 [0]);
                startActivity(intent);
            }
        });

        CardView c11 =(CardView)findViewById(R.id.c11);
        ImageView i11 = (ImageView)findViewById(R.id.i11);
        TextView t11 = (TextView)findViewById(R.id.t11);
        t11.setTypeface(regular_font);
        t11.setText(d11[1]);
        Picasso.with(this).load(d11[0]).placeholder(R.drawable.background).into(i11);
        c11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d11[1]);
                intent.putExtra("time",d11[2]);
                intent.putExtra("ingredients",d11[3]);
                intent.putExtra("directions",d11[4]);
                intent.putExtra("img",d11[0]);
                intent.putExtra("sr",d11[0]);
                startActivity(intent);
            }
        });

        CardView c12 =(CardView)findViewById(R.id.c12);
        ImageView i12 = (ImageView)findViewById(R.id.i12);
        TextView t12 = (TextView)findViewById(R.id.t12);
        t12.setTypeface(regular_font);
        t12.setText(d12[1]);
        Picasso.with(this).load(d12[0]).placeholder(R.drawable.background).into(i12);
        c12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), offlineRecipeDisplayActivity.class);
                //intent.putExtra("img_id",1);
                intent.putExtra("name",d12[1]);
                intent.putExtra("time",d12[2]);
                intent.putExtra("ingredients",d12[3]);
                intent.putExtra("directions",d12[4]);
                intent.putExtra("img",d12[0]);
                intent.putExtra("sr",d12[0]);
                startActivity(intent);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


    }



    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public class baseNewsVersion extends AsyncTask<String, String, String> {
        HttpURLConnection connection;
        BufferedReader reader;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String  s) {
            super.onPostExecute(s);
                    try {
                        JSONObject parent = new JSONObject(s);
                        JSONObject base_version = parent.getJSONObject("base_version");
                        serverVersion = base_version.getInt("version");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Compare Server Version of JSON and local version of JSON if not same download the new JSON content
                    if (localVersion != serverVersion) {
                        pd.setMessage("Stirring Things Up. Please Wait.....");
                        pd.setIcon(R.mipmap.ic_launcher);
                        pd.setCancelable(false);
                        pd.show();
                        // new baseFile().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/base.json");
                        new baseFile().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/kannada_recipes.json");
                        //new baseFile().execute("http://nammakarnataka.net23.net/general/base.json");
                    }
                    else {
                            Log.d("OKOK","OKOK");
                        Toast.makeText(getApplicationContext(), "All Recipes are up to date!", Toast.LENGTH_SHORT).show();
                    }
        }
    }

    public class baseFile extends AsyncTask<String, String, String> {

        HttpURLConnection connection;
        BufferedReader reader;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                String str = builder.toString();
                saveJsonFile(str);
                return str;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);

                    pd.setMessage("Updating Database...");


                    try {
                        JSONObject parent = new JSONObject(s);
                        JSONArray items = parent.getJSONArray("list");

                        if (items != null){
                            if (pd.isShowing())
                                pd.dismiss();

                            myDBHelper = new DatabaseHelper(getApplicationContext());
                            myDBHelper.deleteTables();

                            for (int i = 0; i < items.length(); i++) {
                                JSONObject child = items.getJSONObject(i);
                                JSONArray images = child.getJSONArray("image");

                                for (int j = 0; j < images.length(); j++) {
                                    myDBHelper.insertIntoImages(child.getInt("id"),images.getString(j));
                                }
                                // myDBHelper.insertIntoPlace(child.getInt("id"), child.getString("name"), child.getString("description"), child.getString("district"), child.getString("bestSeason"), child.getString("additionalInformation"), child.getString("nearByPlaces"), child.getDouble("latitude"), child.getDouble("longitude"), child.getString("category"));
                                myDBHelper.insertIntoRecipe(child.getInt("id"), child.getString("name"), child.getString("time"), child.getString("ingredients"), child.getString("directions"), child.getString("category"),child.getString("inenglish"));
                            }

                            SharedPreferences preferences = getSharedPreferences("base_version", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("version", serverVersion);
                            editor.commit();





                        }else {
                            SharedPreferences preferences = getSharedPreferences("base_version", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("version", localVersion);
                            editor.commit();
                            Toast.makeText(getApplicationContext(),"Reatining The Same List",Toast.LENGTH_SHORT).show();
                        }

                        if(pd.isShowing())
                            pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Update Successful",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
        }

    }

    private void saveJsonFile(String data) {
        FileOutputStream stream = null;
        try {
            //File path = new File("/data/data/smartAmigos.com.nammakarnataka/general.json");
            File path = new File("/data/data"+getApplicationContext().getPackageName()+"/"+"general.json");
            stream = new FileOutputStream(path);
            stream.write(data.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
                        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
                        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                        searchView.setOnQueryTextListener(
                                new SearchView.OnQueryTextListener(){

                                    @Override
                                    public boolean onQueryTextSubmit(String query) {
                                        myDBHelper = new DatabaseHelper(getApplicationContext());
                                        Cursor cursor = myDBHelper.getRecipeByEnglishName(query);
                                        Fragment fragment = new SearchResults(cursor);
                                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                        //ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                                        ft.replace(R.id.app_bar, fragment);
                                        ft.commit();

                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String newText) {
                                        myDBHelper = new DatabaseHelper(getApplicationContext());
                                        Cursor cursor = myDBHelper.getRecipeByEnglishName(newText);
                                        Fragment fragment = new SearchResults(cursor);
                                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                        // ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                                        ft.replace(R.id.app_bar, fragment);
                                        ft.commit();

                                        return false;
                                    }

                                }
                        );
                        return true;
                    }
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        //((Activity) getApplicationContext()).overridePendingTransition(0,0);
                    //Press Search and then If u press back, then the below mentioned fargment is loaded
                      //  Fragment fragment = new breakfastFragment();
                       // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        //ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                       // ft.replace(R.id.app_bar, fragment);
                       // ft.hide(fragment);
                       // ft.detach(fragment);
                       // ft.commit();

                        // Do whatever you need
                        return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
                        // When the action view is collapsed, reset the query
                       // townList.setVisibility(View.INVISIBLE);
                        // Return true to allow the action view to collapse

                    }
                });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.






        int id = item.getItemId();

        switch (item.getItemId()) {


            case android.R.id.home:
                Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;


            case R.id.action_share:
                String str = "https://play.google.com/store/apps/details?id=" + getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "One Stop for your Indian Cusine Dishes\nDownload Now:\n" + str);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;


            case R.id.action_refresh:
                if (isNetworkConnected()) {
                    Toast.makeText(getApplicationContext(), "Please wait..", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("base_version", Context.MODE_PRIVATE);
                    localVersion = preferences.getInt("version", 0);
                    //new baseNewsVersion().execute("http://nammakarnataka.net23.net/general/base_version.json");
                    new baseFile().execute("https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/kannada_recipes.json");
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
            Fragment fragment;
            FragmentTransaction ft;
            Intent intent;
            int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_breakfast:
                fragment = new breakfastFragment();
                ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.app_bar, fragment);
                //ft.disallowAddToBackStack()
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_appetizers:
                fragment = new appetizersFragment();
                //fragment = new riceitemsFragment();
                ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_main_course:
                fragment = new maincourseFragment();
                // fragment = new lunchFragment();
                ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;



            case R.id.nav_dessert:
                fragment = new dessertFragment();
                //fragment = new dessertsFragment();
                ft = getSupportFragmentManager().beginTransaction();
//                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_snacks:
                fragment = new snacksFragment();
                //fragment = new snacksFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_festive:
                fragment = new festiveFragment() ;
               // fragment = new curryFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case R.id.nav_favourites:
                fragment = new favoritesFragment();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.app_bar, fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;



            case R.id.feedback:
                Intent intent3 = new Intent(getApplicationContext(), feedback.class);
                startActivity(intent3);
                //Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "ashwinchandlapur@gmail.com"));
              //  intent1.putExtra(Intent.EXTRA_SUBJECT,"Kitchen Feedback");
              //  startActivity(intent1);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
