package com.detroitteatime.myflickr;

import android.provider.BaseColumns;
/**
 * Created by mark on 5/7/15.
 */
//String id, owner, secret, server, farm, title;
//        Boolean isPublic, isFriend, isFamily;

public class Contract{
    public static final String DATABASE_NAME = "myflick.db";

    public static final class PhotoEntry implements BaseColumns{

        public static final String TABLE_NAME = "photo_entry";
        public static final String DAY = "day";
        public static final String MIN = "min";
        public static final String MAX = "max";
        public static final String MORN = "morn";
        public static final String NIGHT = "night";
        public static final String EVE = "eve";
        public static final String WTF = "wtf";
        public static final String DESCRIPTION = "description";

    }
}
