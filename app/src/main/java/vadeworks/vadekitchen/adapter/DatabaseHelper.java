package vadeworks.vadekitchen.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nk_2017";

    private static final String TABLE_PLACES = "nk_places";
    private static final String TABLE_IMAGES = "nk_images";

    private static final String PLACE_ID = "id";
    private static final String PLACE_NAME = "name";
    private static final String PLACE_DESCRIPTION = "description";
    private static final String PLACE_DISTRICT = "district";
    private static final String PLACE_BESTSEASON = "bestSeason";
    private static final String PLACE_ADDITIONALINFO = "additionalInformation";
    private static final String PLACE_NEARBYPLACES = "nearByPlaces";
    private static final String PLACE_LATITUDE = "latitude";
    private static final String PLACE_LONGITUDE = "longitude";
    private static final String PLACE_CATEGORY = "category";

    private static final String IMAGE_URL = "image_url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_place_table = "create table "+TABLE_PLACES+" ("+PLACE_ID+" integer primary key , "+PLACE_NAME+"  text, "+PLACE_DESCRIPTION+" text, "+PLACE_DISTRICT+" text, "+PLACE_BESTSEASON+" text, "+PLACE_ADDITIONALINFO+" text, " +
                PLACE_NEARBYPLACES+" text, "+PLACE_LATITUDE+" double, "+PLACE_LONGITUDE+" double, "+PLACE_CATEGORY+" text );";
        db.execSQL(create_place_table);

        String create_images_table = "create table "+TABLE_IMAGES+" ( "+PLACE_ID+" integer, "+IMAGE_URL+" text );";
        db.execSQL(create_images_table);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_PLACES);
        db.execSQL("drop table if exists "+TABLE_IMAGES);
        onCreate(db);
    }

    public void deleteTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,0,1);
    }
    public boolean insertIntoPlace(int id, String name, String description, String district, String bestseason, String additionalInfo, String nearbyPlaces, double latitude, double longitude, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PLACE_ID, id);
        contentValues.put(PLACE_NAME, name);
        contentValues.put(PLACE_DESCRIPTION, description);
        contentValues.put(PLACE_DISTRICT, district);
        contentValues.put(PLACE_BESTSEASON, bestseason);
        contentValues.put(PLACE_ADDITIONALINFO, additionalInfo);
        contentValues.put(PLACE_NEARBYPLACES, nearbyPlaces);
        contentValues.put(PLACE_LATITUDE, latitude);
        contentValues.put(PLACE_LONGITUDE, longitude);
        contentValues.put(PLACE_CATEGORY, category);

        db.insert(TABLE_PLACES, null, contentValues);
        return true;
    }

    public boolean insertIntoImages(int id, String image_url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PLACE_ID, id);
        contentValues.put(IMAGE_URL, image_url);

        db.insert(TABLE_IMAGES, null, contentValues);

        return true;
    }
    public Cursor getAllDams(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_PLACES+" where "+PLACE_CATEGORY+" = 'dam' ;",null);
    }

    public Cursor getAllLunch()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_PLACES+" where "+PLACE_CATEGORY+" = 'lunch' ;",null);

    }

    public Cursor getAllImagesArrayByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_IMAGES+" where "+PLACE_ID+" = "+id+" ;",null);
    }
    public Cursor getPlaceByString(String str){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_PLACES+" where "+PLACE_NAME+" like '%"+str+"%' ;",null);
    }

    public Cursor getPlaceById(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_PLACES+" where "+PLACE_ID+" = "+id+";",null);
    }

}