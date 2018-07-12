package com.example.matuto.misclaves;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.OutputStreamWriter;

public class Main2Activity extends AppCompatActivity {

    private TextView tvRut;
    private ImageButton ibtAgregar;
    private ListView LstCuentas;
    private String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvRut = (TextView) findViewById(R.id.tvRut);
        ibtAgregar = (ImageButton) findViewById(R.id.ibtAgregar);
        LstCuentas = (ListView) findViewById(R.id.Lstcuentas);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {


            usuario = bundle.get("usuario").toString();
            tvRut.setText(bundle.get("usuario").toString());
        }


        try {
            OutputStreamWriter osw = new OutputStreamWriter(
                    openFileOutput(usuario + ".txt", Activity.MODE_PRIVATE));

        } catch (Exception ex) {

        }


        ibtAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
    }
}
