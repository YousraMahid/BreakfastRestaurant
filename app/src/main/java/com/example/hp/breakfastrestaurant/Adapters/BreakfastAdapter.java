package com.example.hp.breakfastrestaurant.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hp.breakfastrestaurant.R;

import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.ID;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.PRICE;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.PRODUCT_NAME;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.QUANTITY;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry._URI;

/**
 * Created by hp on 11/12/2017.
 */

public class BreakfastAdapter extends CursorAdapter {
    public BreakfastAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item, null);
    }

    @Override
    public void bindView(View convertView, final Context context, final Cursor cursor) {
        final TextView id = convertView.findViewById(R.id.id);
        TextView product_name = convertView.findViewById(R.id.product_name);
        final TextView quantity = convertView.findViewById(R.id.quantity);
        TextView price = convertView.findViewById(R.id.price);
        ImageButton btn_sale = convertView.findViewById(R.id.sale_img_btn);
        btn_sale.setFocusable(false);

        int id_txt = cursor.getInt(cursor.getColumnIndex(ID));
        int price_txt = cursor.getInt(cursor.getColumnIndex(PRICE));
        String product_txt = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME));
        id.setText(String.valueOf(id_txt));
        price.setText(String.valueOf(price_txt));
        product_name.setText(product_txt);
        int quantity_txt = cursor.getInt(cursor.getColumnIndex(QUANTITY));
        quantity.setText(String.valueOf(quantity_txt));
        btn_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity_sub = Integer.parseInt(quantity.getText().toString());

                ContentValues values = new ContentValues();
                if (quantity_sub > 0) {
                    values.put(QUANTITY, --quantity_sub);
                } else {
                    values.put(QUANTITY, 0);
                }
                String[] args = {String.valueOf(id.getText().toString())};
                context.getContentResolver().update(_URI, values, ID + "=?", args);


            }
        });

    }
}
