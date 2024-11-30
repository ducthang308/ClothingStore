package Interface;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_CONVERSATION = "id";
    private static final String KEY_CART = "cartId";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }
    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }
    public void clearToken() {
        editor.remove(KEY_TOKEN);
        editor.apply();
    }
    public void saveUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }
    public void clearUserId() {
        editor.remove(KEY_USER_ID);
        editor.apply();
    }
    public void clearAll() {
        editor.clear();
        editor.apply();
    }
    public void saveName(String name) {
        editor.putString(KEY_USER_NAME, name);
        editor.apply();
    }
    public String getName() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }
    public void clearName() {
        editor.remove(KEY_USER_NAME);
        editor.apply();
    }

    public void saveId(int id) {
        editor.putInt(KEY_CONVERSATION, id);
        editor.apply();
    }
    public int getId() {
        return sharedPreferences.getInt(KEY_CONVERSATION, -1);
    }
    public void clearId() {
        editor.remove(KEY_CONVERSATION);
        editor.apply();
    }

    public void saveCartId(int cartId) {
        editor.putInt(KEY_CART, cartId);
        editor.apply();
    }
    public int getCartId() {
        return sharedPreferences.getInt(KEY_CART, -1);
    }
    public void clearCartId() {
        editor.remove(KEY_CART);
        editor.apply();
    }
}

