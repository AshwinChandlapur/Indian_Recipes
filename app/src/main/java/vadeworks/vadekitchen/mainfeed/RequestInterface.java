package vadeworks.vadekitchen.mainfeed;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ashwinchandlapur on 04/10/17.
 */

public interface RequestInterface {

    @GET("/AshwinChandlapur/ImgLoader/gh-pages/WeeklySpecials/weekly_specials.json")
    Call<JSONResponse> getWeeklySpecials();



    @GET("/AshwinChandlapur/ImgLoader/gh-pages/WeeklySpecials/diet_weekly_specials.json")
    Call<JSONResponse> getDietSpecials();


    @GET("/AshwinChandlapur/ImgLoader/gh-pages/WeeklySpecials/spicy_weekly_specials.json")
    Call<JSONResponse> getSpicySpecials();


    @GET("/AshwinChandlapur/ImgLoader/gh-pages/WeeklySpecials/quick_weekly_specials.json")
    Call<JSONResponse> getQuickSpecials();


    @GET("/AshwinChandlapur/ImgLoader/gh-pages/WeeklySpecials/nonveg_weekly_specials.json")
    Call<JSONResponse> getNonVegSpecials();
}
