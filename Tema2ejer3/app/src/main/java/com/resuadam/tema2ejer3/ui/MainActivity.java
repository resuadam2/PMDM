package com.resuadam.tema2ejer3.ui;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.resuadam.tema2ejer3.R;
import com.resuadam.tema2ejer3.core.Calculadora;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_main );

        EditText edOp1 = (EditText) this.findViewById( R.id.edOp1 );
        EditText edOp2 = (EditText) this.findViewById( R.id.edOp2 );

        this.registerForContextMenu( edOp1 );
        this.registerForContextMenu( edOp2 );
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo cmi)
    {
        if ( view.getId() == R.id.edOp1
                || view.getId() == R.id.edOp2 )
        {
            this.getMenuInflater().inflate( R.menu.context_menu, contextMenu );
            contextMenu.setHeaderTitle( R.string.app_name );
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem)
    {
        boolean toret = false;

        switch( menuItem.getItemId() ) {
            case R.id.context_op_cuadrado:
                this.operaUnario( Calculadora.OperadorUnario.Sqr );
                toret = true;
                break;
            case R.id.context_op_raiz:
                this.operaUnario( Calculadora.OperadorUnario.Sqrt );
                toret = true;
                break;
            case R.id.context_op_negativo:
                this.operaUnario( Calculadora.OperadorUnario.Neg );
                toret = true;
                break;
            case R.id.context_op_factorial:
                this.operaUnario( Calculadora.OperadorUnario.Fac );
                toret = true;
                break;
        }

        return toret;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );

        this.getMenuInflater().inflate( R.menu.actions_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        boolean toret = false;

        switch( menuItem.getItemId() ) {
            case R.id.opSuma:
                this.operaBinario( Calculadora.OperadoBinario.Add );
                toret = true;
                break;
            case R.id.opResta:
                this.operaBinario( Calculadora.OperadoBinario.Sub );
                toret = true;
                break;
            case R.id.opMultiplica:
                this.operaBinario( Calculadora.OperadoBinario.Mul );
                toret = true;
                break;
            case R.id.opDivide:
                this.operaBinario( Calculadora.OperadoBinario.Div );
                toret = true;
                break;
            case R.id.opMedia:
                this.operaBinario( Calculadora.OperadoBinario.Ave );
                toret = true;
                break;
        }

        return toret;
    }

    private void operaUnario(Calculadora.OperadorUnario op)
    {
        // Buscar el elemento con el foco
        View componente = this.getCurrentFocus();

        if ( componente instanceof EditText ) {
            EditText edOp = (EditText) componente;

            try {
                Calculadora calc = new Calculadora( edOp.getText().toString(), "-1" );
                edOp.setText( Double.toString( calc.calcula( op ) ) );
            }
            catch(NumberFormatException | ArithmeticException exc)
            {
                edOp.setText( "0" );
                Toast.makeText( this, "Arithmetic error", Toast.LENGTH_LONG ).show();
            }

        }

        return;
    }

    private void operaBinario(Calculadora.OperadoBinario op)
    {
        EditText edOp1 = (EditText) this.findViewById( R.id.edOp1 );
        EditText edOp2 = (EditText) this.findViewById( R.id.edOp2 );
        TextView edResultado = (TextView) this.findViewById( R.id.edResultado );

        try {
            Calculadora calc = new Calculadora( edOp1.getText().toString(), edOp2.getText().toString() );
            edResultado.setText( Double.toString( calc.calcula( op ) ) );
        }
        catch(NumberFormatException | ArithmeticException exc)
        {
            edResultado.setText( "0" );
            Toast.makeText( this, "Arithmetic error", Toast.LENGTH_LONG ).show();
        }
    }
}