package net.jakare.devlivery.controller.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.jakare.devlivery.model.dbClasses.User;
import net.jakare.devlivery.utils.constants.DatabaseConstants;

/**
 * Created by andresvasquez on 6/5/16.
 */

/**
 * Shared preference data access
 */
public class SharedPreferencesData {
    private Context context;
    private SharedPreferences preferences;

    //Constructor
    public SharedPreferencesData(Context context) {
        this.context = context;
        this.preferences= PreferenceManager.getDefaultSharedPreferences(context);
    }


    /**
     * Create the user data
     * @param user User Obj
     */
    public void setUserData(User user)
    {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(DatabaseConstants.UserColumns._ID,user.getIdUser());
        editor.putString(DatabaseConstants.UserColumns.COLUMN_DISPLAY_NAME,user.getDisplayName());
        editor.putString(DatabaseConstants.UserColumns.COLUMN_EMAIL,user.getEmail());
        editor.putInt(DatabaseConstants.UserColumns.COLUMN_NOTIFICATIONS,user.getNotifications());
        editor.apply();
    }

    /**
     * Obtain user data from Shared Preferences
     * @return
     */
    public User getUserData()
    {
        if (preferences.contains(DatabaseConstants.UserColumns._ID)) {
            User user=new User();
            user.setIdUser(preferences.getString(DatabaseConstants.UserColumns._ID,""));
            user.setDisplayName(preferences.getString(DatabaseConstants.UserColumns.COLUMN_DISPLAY_NAME, ""));
            user.setEmail(preferences.getString(DatabaseConstants.UserColumns.COLUMN_EMAIL, ""));
            user.setNotifications(preferences.getInt(DatabaseConstants.UserColumns.COLUMN_NOTIFICATIONS,0));
            return user;
        }
        return null;
    }
}
