package com.example.hp.breakfastrestaurant.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hp on 11/12/2017.
 */

public class BreakfastContract {
    public static final String authority = "com.example.hp.breakfastrestaurant";

    private BreakfastContract() {
    }


    public static class BreakfastEntry implements BaseColumns {
        public static final String ID = BaseColumns._ID;
        public static final String TABLE = "breakfast";
        public static final String PRODUCT_NAME = "product";
        public static final String QUANTITY = "quantity";
        public static final String PRICE = "price";
        public static final String IMAGE = "image";
        public static final String CONTAIN = "contain";
        public static final Uri _URI = Uri.parse("content://" + authority + "/" + TABLE);

    }
}
