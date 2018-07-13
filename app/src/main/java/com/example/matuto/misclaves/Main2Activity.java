package com.example.matuto.misclaves;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private TextView tvRut;
    private ImageButton ibtAgregar;
    private ListView lstCuentas;
    private String usuario;
    private ArrayAdapter adap;
    public ArrayList<Cuentas> cuentas = new ArrayList<Cuentas>();
    Bundle bundle;
    String rut;
    String archivo;

    private Cuentas selecList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        tvRut = (TextView) findViewById(R.id.tvRut);
        ibtAgregar = (ImageButton) findViewById(R.id.ibtAgregar);
        lstCuentas = (ListView) findViewById(R.id.Lstcuentas);
        bundle = getIntent().getExtras();
        rut = bundle.get("usuario").toString();
        archivo = "cuentas" + rut + ".txt";


        if (bundle != null) {


            usuario = bundle.get("usuario").toString();
            tvRut.setText(bundle.get("usuario").toString());
        }

        leerArchivo();

        adap = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cuentas);
        lstCuentas.setAdapter(adap);


        ibtAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });


        lstCuentas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("posicion", position);
                startActivity(intent);
                finish();
            }
        });


        lstCuentas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                selecList = (Cuentas) cuentas.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                builder.setTitle("Confirmar");
                builder.setMessage("Esta seguro de que desea borrar la cuenta");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        borrarCuenta(position);
                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;






                /*
                borrarCuenta(position);
                return true;

                */
            }
        });


    }

    private void borrarCuenta(int posicion) {
        cuentas.remove(posicion);
        grabarArchivo();
        leerArchivo();
        adap.notifyDataSetChanged();
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
        // cuentas.clear();
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
