package net.jakare.devlivery.utils.constants;

import android.provider.BaseColumns;

/**
 * Created by andresvasquez on 6/5/16.
 */

/**
 * Database tables and columns. Implement Base columns to add _id
 */
public class DatabaseConstants {

    /*
        Table User Definitions
     */
    public static final class UserColumns implements BaseColumns
    {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_UID= "UID";
        public static final String COLUMN_DISPLAY_NAME = "display_name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NOTIFICATIONS = "notifications";
        //Aud collumns
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_TIME = "time";
    }
}
