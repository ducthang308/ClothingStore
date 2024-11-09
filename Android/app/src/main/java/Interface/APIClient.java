package Interface;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) { // Thêm Context vào phương thức
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            // Lấy token từ SharedPreferences
                            SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            String token = prefs.getString("token", null);

                            Request request = chain.request();
                            if (token != null) {
                                request = request.newBuilder()
                                        .addHeader("Authorization", "Bearer " + token)
                                        .build();
                            }
                            return chain.proceed(request);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.215:8088/")
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Sử dụng Context trong các phương thức service
    public static ApiUsers getLoginService(Context context) {
        return getClient(context).create(ApiUsers.class);
    }

    public static ApiUsers getRegisterService(Context context) {
        return getClient(context).create(ApiUsers.class);
    }

    public static APICaterogy getCaterogyService(Context context){
        return getClient(context).create(APICaterogy.class);
    }
}
