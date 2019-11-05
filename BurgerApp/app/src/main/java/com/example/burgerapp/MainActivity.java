package com.example.burgerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private BurgerConfig cfgBurguer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the burguer configuration
        this.cfgBurguer = new BurgerConfig();

        // Report relevant info
        Log.i( "MainActivity.OnCreate", "Number of ingredients: "
                + BurgerConfig.INGREDIENTS.length );

        for(int i = 0; i < BurgerConfig.INGREDIENTS.length; ++i) {
            Log.i( "MainActivity.OnCreate", "Available ingredients: "
                    + BurgerConfig.INGREDIENTS[ i ] );
        }

        // Create callback for the button allowing to check prices
        final Button btPrices = (Button) this.findViewById( R.id.btPrices );
        btPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.showPricesDialog();
            }
        });

        // Create callback for the button allowing to select ingredients
        Button btIngredients = (Button) this.findViewById( R.id.btIngredients );
        btIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.showIngredientsDialog();
            }
        });
    }
    // Más cosas...

    private void showIngredientsDialog()
    {
        AlertDialog.Builder dlg = new AlertDialog.Builder( this );

        dlg.setTitle( this.getResources().getString( R.string.lblIngredientSelection) );

        dlg.setMultiChoiceItems(
                BurgerConfig.INGREDIENTS,
                this.cfgBurguer.getSelected(),
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int pos, boolean value) {
                        MainActivity.this.cfgBurguer.setSelected(pos, value);
                    }
                });

        dlg.setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface v, int i) {
                MainActivity.this.updateTotals();
            }
        });
        dlg.create().show();
    }

    private void updateTotals()
    {
        final TextView lblTotal = (TextView) this.findViewById( R.id.lblTotal );
        final TextView lblSelection = (TextView) this.findViewById( R.id.lblIngredientsSelected );

        // Report
        Log.i( "MainActivity.updTotals", "Starting updating." );
        for(int i = 0; i < BurgerConfig.INGREDIENTS.length; ++i) {
            Log.i( "MainActivity.updTotals",
                    BurgerConfig.INGREDIENTS[ i ]
                            + " (" + BurgerConfig.COSTS[ i ] + ")"
                            + ": " + ( this.cfgBurguer.getSelected()[ i ] ? "Yes" : "No" )
            );
        }

        Log.i( "MainActivity.updTotals", "Total cost: "  + this.cfgBurguer.calculateCost() );

        // Update
        lblTotal.setText(
                String.format( Locale.getDefault(),
                        "%5.2f",
                        MainActivity.this.cfgBurguer.calculateCost() ) );
        lblSelection.setText( "─" + this.cfgBurguer.toListWith( "\n─" ) );
        Log.i( "MainActivity.updTotals", "End updating." );
    }
    private void showPricesDialog()
    {
        final StringBuilder desc = new StringBuilder();
        final TextView lblData = new TextView( this );
        AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        dlg.setTitle( this.getResources().getString( R.string.lblPrices) );

        // Build list with prices
        for(int i = 0; i < BurgerConfig.getNumIngredients(); ++i) {
            desc.append( String.format( Locale.getDefault(), "%4.2f€ %s",
                    BurgerConfig.COSTS[ i ],
                    BurgerConfig.INGREDIENTS[ i ] ) );
            desc.append( '\n' );
        }

        lblData.setText( desc.toString() );
        lblData.setPadding( 10, 10, 10, 10 );
        dlg.setView( lblData );
        dlg.setPositiveButton( "Ok", null );
        dlg.create().show();
    }
}
