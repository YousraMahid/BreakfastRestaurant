package com.example.hp.breakfastrestaurant.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.breakfastrestaurant.R;
import com.squareup.picasso.Picasso;

import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.CONTAIN;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.IMAGE;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.PRICE;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.PRODUCT_NAME;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry.QUANTITY;
import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry._URI;

public class Add extends AppCompatActivity {

    EditText product_name;
    EditText quantity;
    EditText price;
    EditText contain;
    ImageView image_show;
    Button image;
    TextView detail;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        product_name = (EditText) findViewById(R.id.edit_name);
        quantity = (EditText) findViewById(R.id.edit_quantity);
        price = (EditText) findViewById(R.id.edit_price);
        contain = (EditText) findViewById(R.id.edit_contain);
        detail = (TextView) findViewById(R.id.text_detail);
        image_show = (ImageView) findViewById(R.id.image_show);
        image = (Button) findViewById(R.id.choose_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 || requestCode == RESULT_OK) {
            Toast.makeText(this, "image selected", Toast.LENGTH_SHORT).show();
            uri = data.getData();
            Picasso.with(this).load(uri).into(image_show);

        } else {
            Toast.makeText(this, "not selected", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_add) {
            if (!(product_name.getText().toString().trim().isEmpty() || quantity.getText().toString().trim().isEmpty() || price.getText().toString().trim().isEmpty() || contain.getText().toString().trim().isEmpty())) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PRODUCT_NAME, product_name.getText().toString());
                contentValues.put(QUANTITY, quantity.getText().toString());
                contentValues.put(PRICE, price.getText().toString());
                contentValues.put(CONTAIN, contain.getText().toString());
                contentValues.put(IMAGE, String.valueOf(uri));
                getContentResolver().insert(_URI, contentValues);
                finish();

            } else {
                Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

