package com.example.foodtrackingapp.helperclasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import com.example.foodtrackingapp.MainActivity;
import com.example.foodtrackingapp.db.FoodTrackingAppDbHelper;

public class AlertDialogNumber {

    private Context context;
    private String name;
    private int protein, kohlenhydrate, fett, kcal;
    private EditText inputMenge;
    private int int_inputMenge;

    public AlertDialogNumber(String name, int protein, int kohlenhydrate, int fett, int kcal,Context context){
        this.context = context;
        this.name = name;
        this.protein = protein;
        this.kohlenhydrate = kohlenhydrate;
        this.fett = fett;
        this.kcal =kcal;

    }

    public void getDialog(){
        inputMenge = new EditText(context);
        inputMenge.setInputType(InputType.TYPE_CLASS_NUMBER);


        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle("Produktmenge wählen.");

        // set dialog message
        alertDialogBuilder
                .setView(inputMenge)
                .setMessage("Produkt und Menge durch Weiter bestätigen.")
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        int_inputMenge = Integer.parseInt(inputMenge.getText().toString());

                        Log.i("Ü IST: ", "" + kohlenhydrate);
                        FoodTrackingAppDbHelper.addPruduktToPrudukteProTag(name, protein, kohlenhydrate, fett,kcal, int_inputMenge, context);



                    }
                })
                .setNegativeButton("Abbrechen",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
