package com.example.hp.breakfastrestaurant.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.CONTAIN;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.ID;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.IMAGE;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.PRICE;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.PRODUCT_NAME;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.QUANTITY;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.TABLE;

/**
 * Created by hp on 11/12/2017.
 */

public class BreakfastHelper extends SQLiteOpenHelper {

    public BreakfastHelper(Context context) {
        super(context, "breakfast.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE + "(" + ID + " INTEGER PRIMARY KEY, " + PRODUCT_NAME + " TEXT, " + QUANTITY + "  INTEGER," + PRICE + "  INTEGER," + IMAGE + " TEXT ," + CONTAIN + "  TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}



