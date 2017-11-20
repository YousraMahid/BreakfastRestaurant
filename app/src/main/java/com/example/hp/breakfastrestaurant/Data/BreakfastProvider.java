package com.example.hp.breakfastrestaurant.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.CONTAIN;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.ID;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.IMAGE;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.PRICE;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.PRODUCT_NAME;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.QUANTITY;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.TABLE;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.authority;

/**
 * Created by hp on 11/12/2017.
 */

public class BreakfastProvider extends ContentProvider {
    BreakfastHelper helper;
    Cursor cursor;

    final static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(authority, TABLE, 1);
        uriMatcher.addURI(authority, TABLE + "/#", 2);
    }


    @Override
    public boolean onCreate() {
        helper = new BreakfastHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        int code = uriMatcher.match(uri);
        String[] columns = {ID, PRODUCT_NAME, QUANTITY, PRICE, IMAGE, CONTAIN};
        if (code == 1) {
            cursor = helper.getReadableDatabase().query(TABLE, columns, null, null, null, null, null);
        } else if (code == 2) {
            long id = ContentUris.parseId(uri);
            String[] args = {String.valueOf(id)};
            cursor = helper.getReadableDatabase().query(TABLE, columns, ID + "=?", args, null, null, null);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        helper.getWritableDatabase().insert(TABLE, null, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        helper.getWritableDatabase().delete(TABLE, s, strings);
        getContext().getContentResolver().notifyChange(uri, null);

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        helper.getWritableDatabase().update(TABLE, contentValues, s, strings);
        getContext().getContentResolver().notifyChange(uri, null);

        return 0;
    }
}
