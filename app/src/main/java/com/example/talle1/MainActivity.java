package com.example.talle1;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean checked1, checked2, checked3, confirmar = false, otro=false;
    Button btn, btnVolver;
    RadioGroup G1, G2, G3;
    RadioButton R1, R2, R3, R4, R5, R6, R7, R8, R9;
    EditText usuario;
    String cuentaAtras="";
    TextView datos, reloj, perdio;
    String usuario1 = " ";
    int c1 = 0, c2 = 0, c3 = 0, suma = 0, contar=0;
    Tiempo objColorear;
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        usuario = findViewById(R.id.usuario);
        datos = findViewById(R.id.datos);
        reloj = findViewById(R.id.reloj);
        scroll = findViewById(R.id.scroll);
        perdio = findViewById(R.id.perdio);
        btnVolver = findViewById(R.id.btnVolver);
        btn.setOnClickListener(this);
        Bundle traer = getIntent().getExtras();
        assert traer != null;
        contar = traer.getInt("temporizador");
        hacer(contar);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("cont",contar);
        outState.putString("usuario", usuario1);
        outState.putInt("puntaje1", c1);
        outState.putInt("puntaje2", c2);
        outState.putInt("puntaje3", c3);
        outState.putBoolean("confirmar", confirmar);
        outState.putString("cuentaAtras",cuentaAtras);
        Bundle prueba = new Bundle();
        prueba.putInt("temporizador",contar);
        getIntent().putExtras(prueba);
        objColorear.cancel(true);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        usuario1 = savedInstanceState.getString("usuario");
        c1 = savedInstanceState.getInt("puntaje1");
        c2 = savedInstanceState.getInt("puntaje2");
        c3 = savedInstanceState.getInt("puntaje3");
        confirmar = savedInstanceState.getBoolean("confirmar");
        contar= savedInstanceState.getInt("cont");
        cuentaAtras = savedInstanceState.getString("cuentaAtras");
        reloj.setText(cuentaAtras);
        otro= savedInstanceState.getBoolean("otro",true);
        if(confirmar == true){
            terminar();
        }
    }

    public void pregunta1(View view) {
        checked1 = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.R1:
                if (checked1) c1 = 1;
                break;
            case R.id.R2:
                if (checked1) c1 = 0;
                break;
            case R.id.R3:
                if (checked1) c1 = 0;
                break;
        }

    }

    public void pregunta2(View view) {
        checked2 = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.R4:
                if (checked2) c2 = 1;
                break;
            case R.id.R5:
                if (checked2) c2 = 0;
                break;
            case R.id.R6:
                if (checked2) c2 = 0;
                break;
        }
    }

    public void pregunta3(View view) {
        checked3 = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.R7:
                if (checked3) c3 = 1;
                break;
            case R.id.R8:
                if (checked3) c3 = 0;
                break;
            case R.id.R9:
                if (checked3) c3 = 0;
                break;
        }


    }


    public void terminar() {
        usuario1 = usuario.getText().toString().trim();
        if (usuario1.isEmpty()) {
            datos.setText("Inserte un nombre de usuario ");
        } else {
            btn.setVisibility(View.GONE);
            btnVolver.setVisibility(View.VISIBLE);
            objColorear.cancel(true);
            suma = c1 + c2 + c3;
            datos.setText("Bienvenido " + usuario1 + "\n tu resutado fue: " + suma + " de 3");
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
            confirmar = true;
                terminar();
                break;
        }
    }

    public void hacer(int contador){
        objColorear = new Tiempo();
        objColorear.execute(contador);
    }

    public class Tiempo extends AsyncTask<Integer,Integer,Void> {


        @Override
        protected Void doInBackground(Integer... voids) {

            for(int x = voids[0]; x<=15; x++){
                if (objColorear.isCancelled()==true) break;
                try {
                    Thread.sleep(1000);
                    contar=x;
                    publishProgress(15-x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... enteros) {// POR MEDIO DE ESTE MÉTODO SE ACTUALIA LA UI
            super.onProgressUpdate(enteros);
            cuentaAtras= "00:"+(enteros[0]<=9?"0":"")+enteros[0];
            reloj.setText(cuentaAtras);
            if (enteros[0]==0){
                datos.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);
                scroll.setVisibility(View.GONE);
                perdio.setVisibility(View.VISIBLE);
                btnVolver.setVisibility(View.VISIBLE);
            }
        }
    }

    public void volver(View h){
        Intent ir = new Intent(this,Main2Activity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TASK | ir.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ir);
    }

}
