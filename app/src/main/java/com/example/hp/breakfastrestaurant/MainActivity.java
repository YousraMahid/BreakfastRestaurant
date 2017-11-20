package com.example.hp.breakfastrestaurant;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.breakfastrestaurant.Activities.Add;
import com.example.hp.breakfastrestaurant.Activities.DetailLayout;
import com.example.hp.breakfastrestaurant.Adapters.BreakfastAdapter;

import static com.example.hp.breakfastrestaurant.Data.BreakfastContract.BreakfastEntry._URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ListView listView;
    BreakfastAdapter adapter;
    TextView detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_item);
        adapter = new BreakfastAdapter(this);
        detail = (TextView) findViewById(R.id.text_detail);
        listView.setAdapter(adapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this, DetailLayout.class);
                intent.putExtra("ID", id);

                startActivity(intent);

            }
        });
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView.setEmptyView(detail);


    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(this, _URI, null, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
