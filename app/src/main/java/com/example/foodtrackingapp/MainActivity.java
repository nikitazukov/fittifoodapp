package com.example.foodtrackingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.foodtrackingapp.db.FoodTrackingAppDbHelper;
import com.example.foodtrackingapp.helperclasses.Date;
import com.example.foodtrackingapp.produktliste.ProduktlisteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button input, output, gotTo;
    private TextView outview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input = findViewById(R.id.button8);
        output = findViewById(R.id.button9);
        outview = (TextView)findViewById(R.id.textView);
        gotTo = (Button)findViewById(R.id.button10);





        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(MainActivity.this,"klick wurde ausgeführt" + Date.getCurrentYear(),Toast.LENGTH_LONG).show();

            }
        });

        //produktliste um produkt erweitern - bearbeitung folgt !!
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"Produkt wird hinzugefügt.",Toast.LENGTH_LONG).show();

               //addPruduktToPruduktliste("Nüsse", 60, 1, 1, 350, getApplicationContext());
                startActivity(new Intent(getApplicationContext(), ProduktlisteActivity.class));

            }
        });

        //anzeige aller produktids in produktliste - bearbeitung folgt!!
        output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Produkt wird abgefragt!",Toast.LENGTH_LONG).show();
                FoodTrackingAppDbHelper dbHelper = new FoodTrackingAppDbHelper(MainActivity.this);
                String query = "SELECT *  FROM "+ FoodTrackingAppDbHelper.FoodTrackingAppEntry.TABLE_PRODUKTE_PRO_TAG+" WHERE "+ FoodTrackingAppDbHelper.FoodTrackingAppEntry.COL_YEAR+" = "+Date.getCurrentYear();
                Cursor data = dbHelper.getProdukte(query);


               ///MUSS NOCH ANGEPASST WERDEM!!!!!!!!!
                while (data.moveToNext()){
                    outview.append(data.getString(1) + " - " +
                            data.getString(2) + " - " +
                            data.getString(3) + " - " +
                            data.getString(4) + " - " +
                            data.getString(5) + " - " +
                            data.getString(6) + " - " +
                            data.getString(7) + " - " +
                            data.getString(8) + " - " +
                            data.getString(9) + " - " +
                            data.getString(10) );
                }

            }
        });

        //switch zur anderen activity
        gotTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProduktlisteActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
