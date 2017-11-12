package database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jamga on 11/12/2017.
 */

public class SavedValues {
    private SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";

    public SavedValues() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getIP(Context context) {
        return getPrefs(context).getString("IPAddress", "default_value");
    }

    public static void setIP(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("IPAddress", input);
        editor.commit();
    }
}
