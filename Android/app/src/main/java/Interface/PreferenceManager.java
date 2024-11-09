package Interface;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_TOKEN = "token";
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
}
