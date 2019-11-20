package com.resuadam.whoami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.resuadam.whoami.R;
import com.resuadam.whoami.DatosPersonales;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        final EditText edNombre = (EditText) this.findViewById( R.id.edNombre );
        final EditText edEmail = (EditText) this.findViewById( R.id.edEmail );
        final EditText edDireccion = (EditText) this.findViewById( R.id.edDireccion );

        edNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setNombre( edNombre.getText().toString() );
            }
        });

        edDireccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setDireccion( edDireccion.getText().toString() );
            }
        });

        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setEmail( edEmail.getText().toString() );
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );
        String info = prefs.getString( "info", "" );
        this.datosPersonales = new DatosPersonales( info );

        final EditText edNombre = (EditText) this.findViewById( R.id.edNombre );
        final EditText edEmail = (EditText) this.findViewById( R.id.edEmail );
        final EditText edDireccion = (EditText) this.findViewById( R.id.edDireccion );

        edNombre.setText( this.datosPersonales.getNombre() );
        edDireccion.setText( this.datosPersonales.getDireccion() );
        edEmail.setText( this.datosPersonales.getEmail() );
    }


    @Override
    public void onPause()
    {
        super.onPause();

        SharedPreferences.Editor edit = this.getPreferences( Context.MODE_PRIVATE ).edit();

        edit.putString( "info", this.datosPersonales.toString() );
        edit.apply();
    }

    private DatosPersonales datosPersonales;
}