package Interface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import Model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIservice {

    Gson gson = new GsonBuilder()
            .setDateFormat("yy-mm-dd HH:mm:ss ")
            .create();

    APIservice apiservice = new Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIservice.class);


    @GET("User")
    Call<List<User>> getListuser(@Query("") String key);
}
