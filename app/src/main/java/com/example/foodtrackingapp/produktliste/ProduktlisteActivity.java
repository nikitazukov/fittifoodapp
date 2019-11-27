package com.example.foodtrackingapp.produktliste;

import android.database.Cursor;
import android.os.Bundle;

import com.example.foodtrackingapp.db.FoodTrackingAppDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.foodtrackingapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProduktlisteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FoodTrackingAppDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produktliste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dbHelper = new FoodTrackingAppDbHelper(this);

        List id = new ArrayList<Integer>();
        List produktname = new ArrayList<String>();
        List  protein = new ArrayList<Integer>();
        List kohlenhydrate = new ArrayList<Integer>();
        List fett = new ArrayList<Integer>();
        List kcal = new ArrayList<Integer>();

        Cursor data = dbHelper.getAllProdukteFromProduktlise();


        while(data.moveToNext()) {
            id.add(data.getInt(0));
            produktname.add(data.getString(1));
            protein.add(data.getInt(2));
            kohlenhydrate.add(data.getInt(3));
            fett.add(data.getInt(4));
            kcal.add(data.getInt(5));
        }
        data.close();

   /*     outview.setText(id + "\n"+
                produktname + "\n"+
                protein + "\n"+
                kohlenhydrate + "\n"+
                fett + "\n"+
                kcal + "\n" );
*/

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(id, produktname, protein, kohlenhydrate, fett, kcal);
        recyclerView.setAdapter(mAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
