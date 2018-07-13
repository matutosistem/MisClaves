package com.example.matuto.misclaves;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etClave;
    private Button btIngresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etClave = (EditText) findViewById(R.id.etClave);
        btIngresar = (Button) findViewById(R.id.btIngresar);


        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarUsuario();
            }
        });
    }


    private void guardarUsuario() {



        SharedPreferences pref = getSharedPreferences("usuarios", Context.MODE_PRIVATE);


        if (pref.getString(etUsuario.getText().toString(), "No existe").equals("No existe")) {

            SharedPreferences.Editor edit = pref.edit();
            edit.putString(etUsuario.getText().toString(), etClave.getText().toString());
            edit.commit();

        } else {
            if (pref.getString(etUsuario.getText().toString(), "Clave incorrecta").equals(etClave.getText().toString())) {

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("usuario", etUsuario.getText().toString());
                startActivity(intent);
            } else {
                etClave.setError("Clave Incorrecta");
            }

        }

    }

}

