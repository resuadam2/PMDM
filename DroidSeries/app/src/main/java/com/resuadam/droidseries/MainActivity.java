package com.resuadam.droidseries;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EtqApp = "droidseries";
    public static final String CfgFileName = "droidseries.cfg";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // Button "add"
        Button btAdd = (Button) this.findViewById( R.id.btAdd );
        btAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onAdd();
            }
        });

        // Create list view
        this.listSeries = new ArrayList<>();
        ListView lvSeries = (ListView) this.findViewById( R.id.lvSeries );
        lvSeries.setLongClickable( true );
        this.laSeries = new ArrayAdapter<Serie>(
                this,
                android.R.layout.simple_selectable_list_item,
                listSeries );
        lvSeries.setAdapter( this.laSeries );

        // Contextual menu
        this.registerForContextMenu( lvSeries );
    }

    private void saveSate() {
        try (FileOutputStream f = this.openFileOutput( CfgFileName, Context.MODE_PRIVATE ) )
        {
            PrintStream cfg = new PrintStream( f );

            for(Serie serie: this.listSeries) {
                cfg.println( serie.getName() );
                cfg.println( serie.getLastSeason() );
                cfg.println( serie.getLastEpisode() );
            }

            cfg.close();
        }
        catch(IOException exc) {
            Log.e( EtqApp, "Error saving state" );
        }


        System.out.println( "Saved state..." );
    }

    private void loadState() {
        try (FileInputStream f = this.openFileInput( CfgFileName  ) )
        {
            BufferedReader cfg = new BufferedReader( new InputStreamReader( f ) );

            this.listSeries.clear();
            String line = cfg.readLine();
            while( line != null ) {
                Serie serie = new Serie( line );
                serie.setLastSeason( Integer.parseInt( cfg.readLine() ) );
                serie.setLastEpisode( Integer.parseInt( cfg.readLine() ) );
                this.listSeries.add( serie  );

                line = cfg.readLine();
            }

            cfg.close();
        }
        catch (IOException exc)
        {
            Log.e( EtqApp, "Error loading state" );
        }




        System.out.println( "Loaded state..." );
    }

    @Override
    public void onStop()
    {
        super.onStop();
        this.saveSate();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.loadState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu( menu );

        this.getMenuInflater().inflate( R.menu.main_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch( menuItem.getItemId() ) {
            case R.id.mainMenuItemAddSerie:
                this.onAdd();
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo)
    {
        super.onCreateContextMenu( contextMenu, view, contextMenuInfo );

        if ( view.getId() == R.id.lvSeries ) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;

            contextMenu.setHeaderTitle( MainActivity.this.listSeries.get( acmi.position ).getName() );
            this.getMenuInflater().inflate( R.menu.context_menu, contextMenu );
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch( item.getItemId() ) {
            case R.id.contextMenuIncEpisode:
                this.listSeries.get( ( (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).position ).incEpisode();
                Toast.makeText( this, "Episode number incremented", Toast.LENGTH_SHORT ).show();
                break;
            case R.id.contextMenuIncSeason:
                this.listSeries.get( ( (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).position ).incSeason();
                Toast.makeText( this, "Season number incremented", Toast.LENGTH_SHORT ).show();
                break;
        }

        this.laSeries.notifyDataSetChanged();
        return true;
    }

    private void onAdd() {
        final EditText edName = new EditText( this );
        AlertDialog.Builder dlg = new AlertDialog.Builder( this );
        dlg.setTitle( "New series name" );
        dlg.setView( edName );
        dlg.setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    laSeries.add( new Serie( edName.getText().toString().trim() ) );
                    Toast.makeText( MainActivity.this, "Serie created", Toast.LENGTH_SHORT ).show();
                }
                catch(IllegalArgumentException exc) {
                    Toast.makeText( MainActivity.this, "Serie not created: " + exc.getMessage(), Toast.LENGTH_LONG ).show();
                }
            }
        });
        dlg.setNegativeButton( "Cancel", null );
        dlg.create().show();
    }

    private ArrayList<Serie> listSeries;
    private ArrayAdapter<Serie> laSeries;
}
