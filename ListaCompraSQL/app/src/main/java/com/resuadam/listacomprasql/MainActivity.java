package com.resuadam.listacomprasql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    protected static final int CODIGO_EDICION_ITEM = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        this.setContentView( R.layout.activity_main );

        ListView lvLista = this.findViewById( R.id.lvLista );
        ImageButton btInserta = this.findViewById( R.id.btInserta );

        // Inserta
        btInserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzaEditor( "", 0 );
            }
        });

        this.registerForContextMenu( lvLista );
        this.gestorDB = new DBManager( this.getApplicationContext() );
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Configurar lista
        final ListView lvLista = this.findViewById( R.id.lvLista );

        this.adaptadorDB = new SimpleCursorAdapter(
                this,
                R.layout.lvlista_item,
                null,
                new String[] { DBManager.COMPRA_COL_NOMBRE, DBManager.COMPRA_COL_NUM },
                new int[] { R.id.lvLista_Item_Nombre, R.id.lvLista_Item_Num }
        );

        lvLista.setAdapter( this.adaptadorDB );
        this.actualizaCompra();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        this.gestorDB.close();
        this.adaptadorDB.getCursor().close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu( menu, v, menuInfo );

        if ( v.getId() == R.id.lvLista ) {
            this.getMenuInflater().inflate( R.menu.lista_menu_contextual, menu );
        }

        return;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        boolean toret = super.onContextItemSelected(item);
        int pos = ( (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).position;
        Cursor cursor = this.adaptadorDB.getCursor();

        switch ( item.getItemId() ) {
            case R.id.item_contextual_elimina:
                if ( cursor.moveToPosition( pos ) ) {
                    final String nombre = cursor.getString( 0 );
                    this.gestorDB.eliminaItem( nombre );
                    this.actualizaCompra();
                    toret = true;
                } else {
                    String msg = this.getString( R.string.msgNoPos ) + ": " + pos;
                    Log.e( "context_elimina", msg );
                    Toast.makeText( this, msg, Toast.LENGTH_LONG ).show();
                }

                break;
            case R.id.item_contextual_modifica:
                if ( cursor.moveToPosition( pos ) ) {
                    final String nombre = cursor.getString( 0 );
                    final int cantidad = cursor.getInt( 1 );

                    lanzaEditor( nombre, cantidad );
                    toret = true;
                } else {
                    String msg = this.getString( R.string.msgNoPos ) + ": " + pos;
                    Log.e( "context_modifica", msg );
                    Toast.makeText( this, msg, Toast.LENGTH_LONG ).show();
                }

                break;
        }

        return toret;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent retData)
    {
        if ( resultCode == Activity.RESULT_OK
                && requestCode == CODIGO_EDICION_ITEM )
        {
            String nombre = retData.getExtras().getString( "nombre", "ERROR" );
            int num = retData.getExtras().getInt( "num", -1 );

            this.gestorDB.insertaItem( nombre, num );
            this.actualizaCompra();
        }

        return;
    }

    /** Actualiza el num. de elementos existentes en la vista. */
    private void actualizaCompra()
    {
        final TextView lblNum = this.findViewById( R.id.lblNum );

        this.adaptadorDB.changeCursor( this.gestorDB.getCompras() );
        lblNum.setText( String.format( Locale.getDefault(),"%d", this.adaptadorDB.getCount() ) );
    }

    private void lanzaEditor(String nombre, int cantidad)
    {
        Intent subActividad = new Intent( MainActivity.this, ItemEdicionActivity.class );

        subActividad.putExtra( "nombre", nombre );
        subActividad.putExtra( "num", cantidad );
        MainActivity.this.startActivityForResult( subActividad, CODIGO_EDICION_ITEM );
    }

    private DBManager gestorDB;
    private SimpleCursorAdapter adaptadorDB;
}