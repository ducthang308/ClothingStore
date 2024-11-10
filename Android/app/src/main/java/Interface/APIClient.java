package Interface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.215:8088/")
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static ApiUsers getLoginService() {
        return getClient().create(ApiUsers.class);
    }
    public static ApiUsers getRegisterService() {
        return getClient().create(ApiUsers.class);
    }

    public static APICaterogy createcategory() {
        return getClient().create(APICaterogy.class);
    }
    public static APICaterogy getcategory() {
        return getClient().create(APICaterogy.class);
    }
    public static APICaterogy updatecategory(){
        return getClient().create(APICaterogy.class);
    }
    public static APICaterogy deletecategory(){
        return getClient().create(APICaterogy.class);
    }
    public static ApiDiscounts createDiscount(){
        return getClient().create(ApiDiscounts.class);
    }
    public static ApiDiscounts getDiscounts(){
        return getClient().create(ApiDiscounts.class);
    }
    public static ApiDiscounts updateDiscounts(){
        return getClient().create(ApiDiscounts.class);
    }
    public static ApiDiscounts deleteDiscounts(){
        return getClient().create(ApiDiscounts.class);
    }
};


    public static ApiDiscounts createDiscount(){ return getClient().create(ApiDiscounts.class);}
    public static ApiDiscounts getDiscounts(){ return getClient().create(ApiDiscounts.class);}
    public static ApiDiscounts updateDiscounts(){ return getClient().create(ApiDiscounts.class);}
    public static ApiDiscounts deleteDiscounts(){ return getClient().create(ApiDiscounts.class);}

    public static ApiCategories createCategory(){ return getClient().create(ApiCategories.class);}
    public static ApiCategories getCategories(){ return getClient().create(ApiCategories.class);}
    public static ApiCategories updateCategory(){ return getClient().create(ApiCategories.class);}
    public static ApiCategories deleteCategory(){ return getClient().create(ApiCategories.class);}
};

