package com.resuadam.burguerbuilderlistview.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.resuadam.burguerbuilderlistview.R;
import com.resuadam.burguerbuilderlistview.core.BurguerConfigurator;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the burguer configuration
        this.cfgBurguer = new BurguerConfigurator();

        // Report relevant info
        Log.i( "MainActivity.OnCreate", "Number of ingredients: "
                + BurguerConfigurator.INGREDIENTS.length );

        for(int i = 0; i < BurguerConfigurator.INGREDIENTS.length; ++i) {
            Log.i( "MainActivity.OnCreate", "Availabel ingredient: "
                    + BurguerConfigurator.INGREDIENTS[ i ] );
        }

        // Create callback for the button allowing to check prices
        Button btPrices = (Button) this.findViewById( R.id.btPrices );
        btPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        // Connect context menu
        final ListView lvIngredients = (ListView) this.findViewById( R.id.lvIngredients );
        this.registerForContextMenu( lvIngredients );

        this.showFixedIngredients();
        this.updateTotals();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );

        this.getMenuInflater().inflate( R.menu.main_menu, menu );
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo cmi)
    {
        super.onCreateContextMenu( menu, v, cmi );

        if ( v.getId() == R.id.lvIngredients ) {
            this.getMenuInflater().inflate( R.menu.ingredients_menu, menu );
        }

        return;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        boolean toret = false;

        if ( item.getItemId() == R.id.ingredients_menu_remove ) {
            toret = true;
            final int pos = ( (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).position;
            this.removeSelected( pos );
        }

        return toret;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        boolean toret = false;

        if ( menuItem.getItemId() == R.id.mainMenu_add ) {
            toret = true;
            this.showIngredientsDialog();
        }
        else
        if ( menuItem.getItemId() == R.id.mainMenu_exit ) {
            toret = true;
            System.exit( 0 );
        }

        return toret;
    }

    private void showFixedIngredients()
    {
        ListView lvFixedIngredients = (ListView) this.findViewById( R.id.lvFixedIngredients );

        String[] fixedIngredients = new String[] {
                String.format( "%4.2f€ %s", BurguerConfigurator.FIXED_COSTS[ 0 ],
                        BurguerConfigurator.FIXED_INGREDIENTS[ 0 ] ),
                String.format( "%4.2f€ %s", BurguerConfigurator.FIXED_COSTS[ 1 ],
                        BurguerConfigurator.FIXED_INGREDIENTS[ 1 ] )
        };

        lvFixedIngredients.setAdapter(
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        fixedIngredients ) );

    }

    private void showIngredients()
    {
        final int NUM_ITEMS = this.cfgBurguer.getSelected().length;
        final boolean[] selections = this.cfgBurguer.getSelected();
        final ListView lvIngredients = (ListView) this.findViewById( R.id.lvIngredients );

        // Create list
        ArrayList<String> ingredients = new ArrayList<>();
        for(int i = 0; i < NUM_ITEMS; ++i) {
            if ( selections[ i ] ) {
                ingredients.add(
                        String.format( "%4.2f€ %s", BurguerConfigurator.COSTS[ i ],
                                BurguerConfigurator.INGREDIENTS[ i ] ) );
            }
        }

        lvIngredients.setAdapter(
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        ingredients ) );

        lvIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.this.removeSelected(i);
            }
        });
    }

    private void removeSelected(int i)
    {
        final int listPos = MainActivity.this.cfgBurguer.getGlobalPosOfSelected( i );

        MainActivity.this.cfgBurguer.getSelected()[ listPos ] = false;
        MainActivity.this.showIngredients();
        MainActivity.this.updateTotals();
    }

    private void updateTotals()
    {
        final TextView lblTotal = (TextView) this.findViewById( R.id.lblTotal );

        // Report
        Log.i( "MainActivity.updTotals", "Starting updating." );
        for(int i = 0; i < BurguerConfigurator.INGREDIENTS.length; ++i) {
            Log.i( "MainActivity.updTotals",
                    BurguerConfigurator.INGREDIENTS[ i ]
                            + " (" + BurguerConfigurator.COSTS[ i ] + ")"
                            + ": " + ( this.cfgBurguer.getSelected()[ i ] ? "Yes" : "No" )
            );
        }

        Log.i( "MainActivity.updTotals", "Total cost: "  + this.cfgBurguer.calculateCost() );

        // Update
        lblTotal.setText(
                String.format( "%4.2f", MainActivity.this.cfgBurguer.calculateCost() ) );
        Log.i( "MainActivity.updTotals", "End updating." );
    }

    private void showIngredientsDialog()
    {
        final boolean[] selections = this.cfgBurguer.getSelected();
        AlertDialog.Builder dlg = new AlertDialog.Builder( this );

        dlg.setTitle( this.getResources().getString( R.string.lblIngredientSelection) );

        dlg.setMultiChoiceItems(
                BurguerConfigurator.INGREDIENTS,
                selections,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        selections[ i ] = b;
                    }
                }
        );

        dlg.setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.showIngredients();
                MainActivity.this.updateTotals();
            }
        });
        dlg.create().show();
    }

    private void showPricesDialog()
    {
        final int NUM_INGREDIENTS = BurguerConfigurator.INGREDIENTS.length;
        final String[] ingredientsWithPrices = new String[ NUM_INGREDIENTS ];
        final TextView lblData = new TextView( this );
        AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        dlg.setTitle( this.getResources().getString( R.string.lblPrices) );
        String ingredientsWithPricesToString = "";
        // Build list with prices
        for(int i = 0; i < ingredientsWithPrices.length; ++i) {
            ingredientsWithPrices[ i ] = String.format( "%4.2f€ %s",
                    BurguerConfigurator.COSTS[ i ],
                    BurguerConfigurator.INGREDIENTS[ i ] );
        }
        for (int j = 0; j < ingredientsWithPrices.length; ++j){
            ingredientsWithPricesToString += ingredientsWithPrices[j]+"\n";
        }
        lblData.setText( ingredientsWithPricesToString );
        lblData.setPadding( 10, 10, 10, 10 );
        dlg.setView( lblData );
        dlg.setPositiveButton( "Ok", null );
        dlg.create().show();
    }

    private BurguerConfigurator cfgBurguer;
}