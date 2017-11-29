package vadeworks.vadekitchen.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    //New COdes
    private static final String DATABASE_NAME = "vk_2017";
    private static final String RECIPE_TABLE = "vk_recipes";
    private static final String RECIPE_IMAGES = "vk_images";
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String TIME = "time";
    private static final String INGREDIENTS = "ingredients";
    private static final String DIRECTIONS = "directions";
    private static final String RECIPE_CATEGORY = "category";
    private static final String RECIPE_VIDEOURL = "videoUrl";
    private static final String IMAGE_URL = "image_url";
    private static final String TABLE_FAVOURITE = "vk_fav";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        //ALL New Codes
        String create_recipe_tables = "create table "+RECIPE_TABLE+" ("+RECIPE_ID+" integer primary key , "+RECIPE_NAME+"  text, "+TIME+" text, "+INGREDIENTS+" text, "+DIRECTIONS+" text,"+RECIPE_CATEGORY+" text,"+RECIPE_VIDEOURL+" text );";
        db.execSQL(create_recipe_tables);

        String create_images_tables = "create table "+RECIPE_IMAGES+" ( "+RECIPE_ID+" integer, "+IMAGE_URL+" text );";
        db.execSQL(create_images_tables);

        String create_favourite_table = "create table "+TABLE_FAVOURITE+" ("+RECIPE_ID+" integer primary key , "+RECIPE_NAME+"  text, "+TIME+" text, "+INGREDIENTS+" text, "+DIRECTIONS+" text,"+RECIPE_CATEGORY+" text,"+RECIPE_VIDEOURL+" text );";
        db.execSQL(create_favourite_table);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("drop table if exists "+TABLE_PLACES);
       // db.execSQL("drop table if exists "+TABLE_IMAGES);
//ALL New Codes
        db.execSQL("drop table if exists "+RECIPE_TABLE);
        db.execSQL("drop table if exists "+RECIPE_IMAGES);
        db.execSQL("drop table if exists "+TABLE_FAVOURITE);
        onCreate(db);
    }

    public void deleteTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,0,1);
    }




    //ALL New Codes
    public boolean insertIntoRecipe(int id, String name, String time, String ingredients, String directions, String category,String videoUrl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECIPE_ID, id);
        contentValues.put(RECIPE_NAME, name);
        contentValues.put(TIME,time);
        contentValues.put(INGREDIENTS,ingredients);
        contentValues.put(DIRECTIONS,directions);
        contentValues.put(RECIPE_CATEGORY, category);
        contentValues.put(RECIPE_VIDEOURL, videoUrl);
        db.insert(RECIPE_TABLE, null, contentValues);
        return true;
    }

    //ALL New Code
    public boolean insertIntoImages(int id, String image_url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RECIPE_ID, id);
        contentValues.put(IMAGE_URL, image_url);
        db.insert(RECIPE_IMAGES, null, contentValues);

        return true;
    }

    public boolean insertIntoFavourites(int id,String name){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RECIPE_ID, id);
        contentValues.put(RECIPE_NAME, name);

        db.insert(TABLE_FAVOURITE, null, contentValues);

        return true;
    }



    // ALL New Codes
    public Cursor getAllImagesArrayByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+RECIPE_IMAGES +" where "+RECIPE_ID+" = "+id+" ;",null);
    }

    public Cursor getRecipeByString(String str){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+RECIPE_TABLE+" where "+RECIPE_NAME+" like '%"+str+"%' ;",null);
    }
    public Cursor getAllLunch()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+RECIPE_TABLE+" where "+RECIPE_CATEGORY+" = 'lunch' ;",null);
    }

    public Cursor getAllAppetizers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+RECIPE_TABLE+" where "+RECIPE_CATEGORY+" = 'appetizers' ;",null);
    }

    public Cursor getAllBreakfast()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+RECIPE_TABLE+" where "+RECIPE_CATEGORY+" = 'breakfast' ;",null);
    }

    public Cursor getAllMaincourse()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+RECIPE_TABLE+" where "+RECIPE_CATEGORY+" = 'maincourse' ;",null);
    }

    public Cursor getAllDessert()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+RECIPE_TABLE+" where "+RECIPE_CATEGORY+" = 'dessert' ;",null);
    }

    public Cursor getAllSnacks()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+RECIPE_TABLE+" where "+RECIPE_CATEGORY+" = 'snacks' ;",null);
    }

    public Cursor getAllFestive()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+RECIPE_TABLE+" where "+RECIPE_CATEGORY+" = 'festive' ;",null);
    }




    public Cursor getRecipeById(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+RECIPE_TABLE+" where "+RECIPE_ID+" = "+id+";",null);
    }


    public Cursor getAllFavourites(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_FAVOURITE+" ;",null);

    }

    public void deleteFromFavourites(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVOURITE, RECIPE_ID + "=" + id, null);

    }



}