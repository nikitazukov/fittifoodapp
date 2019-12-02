package com.example.foodtrackingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.foodtrackingapp.db.FoodTrackingAppDbHelper;
import com.example.foodtrackingapp.helperclasses.Date;
import com.example.foodtrackingapp.produktliste.ProduktlisteActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button input, output, gotTo;
    private TextView outview, outview2;
    private int  protein, kohlenhydrate, fett, kcal, menge, jahr, monat, stunde, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        input = findViewById(R.id.button8);
        output = findViewById(R.id.button9);
        outview = (TextView)findViewById(R.id.textView);
        outview2 = findViewById(R.id.textView2);
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

            }
        });

        //switch zur anderen activity
        gotTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProduktlisteActivity.class));
            }
        });

        List produktname = new ArrayList<String>();
        List  menge = new ArrayList<Integer>();
        List  protein = new ArrayList<Integer>();
        List kohlenhydrate = new ArrayList<Integer>();
        List fett = new ArrayList<Integer>();
        List kcal = new ArrayList<Integer>();
        List year = new ArrayList<Integer>();
        List month = new ArrayList<Integer>();
        List day = new ArrayList<Integer>();
        List hour = new ArrayList<Integer>();
        List minute = new ArrayList<Integer>();

        FoodTrackingAppDbHelper dbHelper = new FoodTrackingAppDbHelper(MainActivity.this);
        String query = "SELECT *  FROM "+ FoodTrackingAppDbHelper.FoodTrackingAppEntry.TABLE_PRODUKTE_PRO_TAG+" WHERE "+ FoodTrackingAppDbHelper.FoodTrackingAppEntry.COL_YEAR+" = "+Date.getCurrentYear()+" AND "+FoodTrackingAppDbHelper.FoodTrackingAppEntry.COL_MONTH+" = "+Date.getCurrentMonth()+" AND "+FoodTrackingAppDbHelper.FoodTrackingAppEntry.COL_DAY+" = "+Date.getCurrentDay();
        Cursor data = dbHelper.getProdukte(query);

        ///MUSS NOCH ANGEPASST WERDEM!!!!!!!!!
        while (data.moveToNext()){
            produktname.add(data.getString(1));
            menge.add(data.getInt(2));
            protein.add(data.getInt(3));
            kohlenhydrate.add(data.getInt(4));
            fett.add(data.getInt(5));
            kcal.add(data.getInt(6));
            year.add(data.getInt(7));
            month.add(data.getInt(8));
            day.add(data.getInt(9));
            hour.add(data.getInt(10));
            minute.add(data.getInt(11));
            Log.i("PRODUKTNAME : ", produktname+"");
            this.menge+=data.getInt(2);
            this.protein+=data.getInt(3);
            this.kohlenhydrate+=data.getInt(4);
            this.fett+=data.getInt(5);
            this.kcal+=data.getInt(6);
        }

        outview.append("Tageskonsum : " +
                        this.menge+ " - "+
                        this.protein+" - "+
                        this.kohlenhydrate+" - "+
                        this.fett+" - "+
                        this.kcal);

        for (int i =0; i< produktname.size(); i++){
            outview2.append("Datum : "+
                            day.get(i)+"."+
                            month.get(i)+"."+
                            year.get(i)+" "+
                            "Produkt : "+
                            produktname.get(i)+" - "+
                            menge.get(i)+" - "+
                            protein.get(i)+" - "+
                            kohlenhydrate.get(i)+" - "+
                            fett.get(i)+" - "+
                            kcal.get(i)+"\n");
        }

        //CHART

        LineChart chart = findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(10,300));
        entries.add(new Entry(16,100));
        entries.add(new Entry(22,600));

        List<Entry> entriesKohlenhydrate = new ArrayList<Entry>();
        entriesKohlenhydrate.add(new Entry(10,600));
        entriesKohlenhydrate.add(new Entry(16,800));
        entriesKohlenhydrate.add(new Entry(22,100));

        //Protein
        LineDataSet dataSet = new LineDataSet(entries, "Protein"); // add entries to dataset
        LineDataSet dataSetKohlenhydrate = new LineDataSet(entriesKohlenhydrate, "Kohlenhydrate"); // add entries to dataset


        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataSet);
        dataSets.add(dataSetKohlenhydrate);

        LineData lineData = new LineData(dataSets);


        chart.setData(lineData);

        chart.invalidate(); // refresh


    }

    public int getSumofList(List liste){
        int summe=0;

       for (int i=0; i < liste.size(); i++){

           //summe+=liste.get(i);
       }

        return summe;
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
/*
    @Override
    public void onResume(){
        super.onResume();
        finish();
        startActivity(getIntent());
    }
*/
@Override
public void onRestart()
{
    super.onRestart();
    finish();
    startActivity(getIntent());
}

}
