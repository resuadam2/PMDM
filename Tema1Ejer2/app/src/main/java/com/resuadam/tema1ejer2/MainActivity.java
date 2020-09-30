package com.resuadam.tema1ejer2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button plus = (Button) this.findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus();
            }
        });
        Button minus = (Button) this.findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus();
            }
        });
        Button product = (Button) this.findViewById(R.id.product);
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product();
            }
        });
    }

    public void plus(){
        EditText n1 = this.findViewById(R.id.number);
        EditText n2 = this.findViewById(R.id.number2);
        int number1 = Integer.parseInt(n1.getText().toString());
        int number2 = Integer.parseInt(n2.getText().toString());
        TextView tv = this.findViewById(R.id.result);
        tv.setText(Integer.toString(number1+number2));
    }

    public void minus(){
        EditText n1 = this.findViewById(R.id.number);
        EditText n2 = this.findViewById(R.id.number2);
        int number1 = Integer.parseInt(n1.getText().toString());
        int number2 = Integer.parseInt(n2.getText().toString());
        TextView tv = this.findViewById(R.id.result);
        tv.setText(Integer.toString(number1-number2));
    }

    public void product(){
        EditText n1 = this.findViewById(R.id.number);
        EditText n2 = this.findViewById(R.id.number2);
        int number1 = Integer.parseInt(n1.getText().toString());
        int number2 = Integer.parseInt(n2.getText().toString());
        TextView tv = this.findViewById(R.id.result);
        tv.setText(Integer.toString(number1*number2));
    }
}