package com.example.hp.breakfastrestaurant.Activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.breakfastrestaurant.R;
import com.squareup.picasso.Picasso;

import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.CONTAIN;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.ID;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.IMAGE;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.PRICE;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.PRODUCT_NAME;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.QUANTITY;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry._URI;

public class DetailLayout extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText productName;
    EditText quantity;
    EditText price;
    EditText contain;
    TextView id;
    long breakfastID;
    ImageButton inc;
    ImageButton dec;
    ImageButton delete;
    ImageButton send;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layout);
        inc = findViewById(R.id.add);
        dec = findViewById(R.id.sub);
        delete = findViewById(R.id.delete);
        send = findViewById(R.id.msg_order);
        image = findViewById(R.id.image);

        productName = (EditText) findViewById(R.id.edit_name);
        quantity = findViewById(R.id.edit_quantity);
        price = findViewById(R.id.edit_price);
        contain = findViewById(R.id.edit_contain);
        id = findViewById(R.id._id);
        breakfastID = getIntent().getLongExtra("ID", -1);

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity_add;
                if (!(quantity.getText().toString().trim().isEmpty())) {
                    quantity_add = Integer.parseInt(quantity.getText().toString());
                } else
                    quantity_add = 0;
                ContentValues values = new ContentValues();
                values.put(QUANTITY, ++quantity_add);
                String[] args = {String.valueOf(breakfastID)};
                getContentResolver().update(_URI, values, ID + "=?", args);
                quantity.setText(String.valueOf(quantity_add));
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity_sub;
                if (!(quantity.getText().toString().trim().isEmpty())) {
                    quantity_sub = Integer.parseInt(quantity.getText().toString());
                } else
                    quantity_sub = 0;
                ContentValues values = new ContentValues();
                if (quantity_sub > 0) {
                    values.put(QUANTITY, --quantity_sub);
                } else {
                    Toast.makeText(DetailLayout.this, "can't be data less than zero ", Toast.LENGTH_SHORT).show();
                    values.put(QUANTITY, 0);
                }
                String[] args = {String.valueOf(breakfastID)};
                getContentResolver().update(_URI, values, ID + "=?", args);
                quantity.setText(String.valueOf(quantity_sub));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
      @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DetailLayout.this)
                        .setTitle("Deleting Record")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String args[] = {String.valueOf(breakfastID)};
                                getContentResolver().delete(_URI, ID + "=?", args);
                                Toast.makeText(DetailLayout.this, "Delete it ", Toast.LENGTH_SHORT).show();
                                id.setText("");
                                productName.setText("");
                                quantity.setText("");
                                price.setText("");
                                contain.setText("");
                                finish();
                            }
                        })
                        .setNegativeButton("No", null).show();


            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "we need this " + productName.getText().toString() + "and I need the contain this breakfast  " + contain.getText().toString());
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.withAppendedPath(_URI, String.valueOf(breakfastID));

        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToNext()) {
            id.setText(String.valueOf(breakfastID));
            productName.setText(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
            quantity.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(QUANTITY))));
            price.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(PRICE))));
            contain.setText(cursor.getString(cursor.getColumnIndex(CONTAIN)));
            String im = cursor.getString(cursor.getColumnIndex(IMAGE));
            Picasso.with(DetailLayout.this).load(im).into(image);

        }
        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}