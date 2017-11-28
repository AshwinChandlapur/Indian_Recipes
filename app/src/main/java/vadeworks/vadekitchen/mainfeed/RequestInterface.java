package vadeworks.vadekitchen.mainfeed;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ashwinchandlapur on 04/10/17.
 */

public interface RequestInterface {

    @GET("/AshwinChandlapur/ImgLoader/gh-pages/weekly_specials.json")
    Call<JSONResponse> getJSON();



    @GET("/AshwinChandlapur/ImgLoader/gh-pages/diet.json")
    Call<JSONResponse> getDiet();
}
