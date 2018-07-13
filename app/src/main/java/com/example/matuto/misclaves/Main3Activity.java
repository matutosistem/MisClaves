package com.example.matuto.misclaves;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.PerformanceTestCase;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private EditText nombrec;
    private EditText usuarioc;
    private EditText urlc;
    private EditText clavec;
    private EditText obserc;
    private BottomNavigationView btmMenu;
    int posicion;
    Bundle bundle;
    private ArrayList<Cuentas> cuentas = new ArrayList<Cuentas>();

    private String archivo;
    private String rut;

    Cuentas cuent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        nombrec = (EditText) findViewById(R.id.etNombre);
        usuarioc = (EditText) findViewById(R.id.etUsuarioC);
        urlc = (EditText) findViewById(R.id.etUrl);
        clavec = (EditText) findViewById(R.id.etClaveC);
        obserc = (EditText) findViewById(R.id.etObserv);
        btmMenu = (BottomNavigationView) findViewById(R.id.menu);

        bundle = getIntent().getExtras();
        rut = bundle.get("usuario").toString();
        archivo = "cuentas" + rut + ".txt";

        leerArchivo();

        if (bundle.containsKey("posicion")) {

            cuent = cuentas.get(bundle.getInt("posicion"));

            nombrec.setText(cuent.getNombre());
            usuarioc.setText(cuent.getUsuario());
            urlc.setText(cuent.getUrl());
            clavec.setText(cuent.getLeerClave());
            obserc.setText(cuent.getObserv());


        }


        btmMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.grabar:

                        if (!nombrec.getText().toString().isEmpty() && !usuarioc.getText().toString().isEmpty() && !clavec.getText().toString().isEmpty()) {


                            if (bundle.containsKey("posicion")) {
                                cuent.setUsuario(usuarioc.getText().toString());
                                cuent.setNombre(nombrec.getText().toString());
                                cuent.setLeerClave(clavec.getText().toString());
                                cuent.setObserv(obserc.getText().toString());
                                cuent.setUrl(urlc.getText().toString());
                            } else {
                                cuentas.add(new Cuentas(nombrec.getText().toString(),
                                        usuarioc.getText().toString(), urlc.getText().toString(),
                                        clavec.getText().toString(), obserc.getText().toString()));


                            }

                            grabarArchivo();
                            Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
                            intent.putExtra("usuario", rut);
                            startActivity(intent);
                            finish();

                        } else {


                            if (nombrec.getText().toString().isEmpty()) {
                                nombrec.setError("Debe Ingresar Nombre de la cuenta");
                            }

                            if (usuarioc.getText().toString().isEmpty()) {
                                usuarioc.setError("Debe ingresar Usuario cuenta");
                            }

                            if (clavec.getText().toString().isEmpty()) {
                                clavec.setError("Debe Ingresar clave cuenta");
                            }

                        }

                        break;

                    case R.id.eliminar:

                        /// meter dialogo de alerta a eliminar

                        if (bundle.containsKey("posicion")) {
                            cuentas.remove(bundle.getInt("posicion"));
                            grabarArchivo();
                            Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
                            intent.putExtra("usuario", rut);
                            startActivity(intent);
                            finish();
                        }

                        break;
                }
                return false;
            }
        });
    }

    private void borrarCuenta(int posicion) {
        cuentas.remove(posicion);
        grabarArchivo();
        leerArchivo();
    }

    private void grabarArchivo() {
        try (OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(archivo, Activity.MODE_PRIVATE))) {
            for (Cuentas cuenta : cuentas) {
                osw.write(cuenta.getNombre() + "|" + cuenta.getUsuario() + "|" + cuenta.getUrl() + "|" + cuenta.getLeerClave() + "|" + cuenta.getObserv() + "\n");
            }
        } catch (Exception ex) {
            Log.d("TAG_", "No se pudo crear el archivo " + archivo);
        }
    }

    public void leerArchivo() {
            cuentas.clear();
        try (InputStreamReader isr = new InputStreamReader(openFileInput(archivo));
             BufferedReader br = new BufferedReader(isr)) {
            String line = br.readLine();
            while (line != null) {
                String[] datos = line.split("\\|", -2);
                cuentas.add(new Cuentas(datos[0], datos[1], datos[2], datos[3], datos[4]));
                line = br.readLine();
            }

        } catch (FileNotFoundException fex) {
            Log.d("TAG_", "Archivo " + archivo + " no existe");
        } catch (IOException e) {
            Log.d("TAG_", "No se pudo leer archivo " + archivo);
        }
    }

}