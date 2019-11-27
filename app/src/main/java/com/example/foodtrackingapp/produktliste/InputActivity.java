package com.example.foodtrackingapp.produktliste;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodtrackingapp.MainActivity;
import com.example.foodtrackingapp.db.FoodTrackingAppDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodtrackingapp.R;

public class InputActivity extends AppCompatActivity {

    //vars declaration
    private TextView tvName;
    private EditText etProtein, etKohlenhydrate, etFett, etKcal;
    private Button btnInput;
    private int protein, kohlenhydrate, fett, kcal;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //vars init
        tvName = (TextView) findViewById(R.id.produktname);
        etProtein = (EditText) findViewById(R.id.protein);
        etKohlenhydrate = (EditText) findViewById(R.id.kohlenhydrate);
        etFett = (EditText) findViewById(R.id.fett);
        etKcal = (EditText) findViewById(R.id.kcal);
        btnInput = (Button) findViewById(R.id.input);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = tvName.getText().toString();
                protein = Integer.parseInt(etProtein.getText().toString());
                kohlenhydrate = Integer.parseInt(etKohlenhydrate.getText().toString());
                fett = Integer.parseInt(etFett.getText().toString());
                kcal = Integer.parseInt(etKcal.getText().toString());

                FoodTrackingAppDbHelper.addPruduktToPruduktliste(name, protein, kohlenhydrate, fett, kcal, getApplicationContext());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
}
