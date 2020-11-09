package com.example.tema3ejer2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        final EditText edNombre = (EditText) this.findViewById( R.id.edNombre );
        final EditText edEmail = (EditText) this.findViewById( R.id.edEmail );
        final EditText edDireccion = (EditText) this.findViewById( R.id.edDireccion );
        final EditText edTefl = (EditText) this.findViewById( R.id.edTelf );
        final EditText edApel = (EditText) this.findViewById( R.id.edApellido );

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

        edTefl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setTelf( edTefl.getText().toString() );
            }
        });

        edApel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.this.datosPersonales.setApellido( edApel.getText().toString() );
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        SharedPreferences prefs = this.getPreferences( Context.MODE_PRIVATE );
        String nombre = prefs.getString( "name", "" );
        String mail = prefs.getString( "email", "" );
        String dir = prefs.getString( "address", "" );
        String telf = prefs.getString("phone", "");
        String apellido = prefs.getString("surname", "");
        this.datosPersonales = new DatosPersonales( nombre, dir, mail, telf, apellido );

        final EditText edNombre = (EditText) this.findViewById( R.id.edNombre );
        final EditText edEmail = (EditText) this.findViewById( R.id.edEmail );
        final EditText edDireccion = (EditText) this.findViewById( R.id.edDireccion );
        final EditText edTelf = (EditText) this.findViewById( R.id.edTelf );
        final EditText edApel = (EditText) this.findViewById( R.id.edApellido );

        edNombre.setText( this.datosPersonales.getNombre() );
        edDireccion.setText( this.datosPersonales.getDireccion() );
        edEmail.setText( this.datosPersonales.getEmail() );
        edTelf.setText(this.datosPersonales.getTelf());
        edApel.setText(this.datosPersonales.getApellido());
    }


    @Override
    public void onPause()
    {
        super.onPause();

        SharedPreferences.Editor edit = this.getPreferences( Context.MODE_PRIVATE ).edit();

        edit.putString( "name", this.datosPersonales.getNombre() );
        edit.putString( "email", this.datosPersonales.getEmail() );
        edit.putString( "address", this.datosPersonales.getDireccion() );
        edit.putString( "phone", this.datosPersonales.getTelf() );
        edit.putString( "surname", this.datosPersonales.getApellido() );
        edit.apply();
    }

    private DatosPersonales datosPersonales;
}