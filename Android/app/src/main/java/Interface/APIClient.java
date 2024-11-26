package Interface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.111:8088/")
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
    public static ApiUsers updatePass() {
        return getClient().create(ApiUsers.class);
    }


    //Category
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

    //Discount
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

    //Product
    public static ApiProduct createProduct(){
        return getClient().create(ApiProduct.class);
    }
    public static ApiProduct getProduct(){
        return getClient().create(ApiProduct.class);
    }
    public static ApiProduct updateProduct(){
        return getClient().create(ApiProduct.class);
    }
    public static ApiProduct deleteProduct(){
        return getClient().create(ApiProduct.class);
    }
    public static ApiProduct uploadImages(){
        return getClient().create(ApiProduct.class);
    }

    // Orders
    public static ApiOrders createOrder() {
        return getClient().create(ApiOrders.class);
    }

    public static ApiOrders getOrderById() {
        return getClient().create(ApiOrders.class);
    }

    public static ApiOrders getAllOrdersByUser() {
        return getClient().create(ApiOrders.class);
    }

    public static ApiOrders updateOrder() {
        return getClient().create(ApiOrders.class);
    }

    public static ApiOrders deleteOrder() {
        return getClient().create(ApiOrders.class);
    }

}