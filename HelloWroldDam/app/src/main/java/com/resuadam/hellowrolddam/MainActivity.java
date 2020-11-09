package com.resuadam.hellowrolddam;

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
        Button bt = (Button) this.findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printEditText();
            }
        });
    }

    public void printEditText(){
        EditText text = (EditText) this.findViewById(R.id.editText);
        TextView tv = (TextView) this.findViewById(R.id.text_view);
        String aux = text.getText().toString();
        tv.setText(aux);
    }
}