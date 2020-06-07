package com.example.verdadoreto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;


public class nivel extends AppCompatActivity {
    TableRow atrevido,sinLimites;
    int cantidad;
    int intensidad;
    boolean band=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel);
        atrevido =(TableRow) findViewById(R.id.Atrevido);
        sinLimites =(TableRow) findViewById(R.id.Sinlimites);
        if(band==false){
            atrevido.setClickable(true);
            sinLimites.setClickable(false);
        }
        cantidad = getIntent().getIntExtra("personas",0);
    }
    public void sutil(View view){
        intensidad=0;
        sigIntent();
    }
    public void sensual(View view){
        intensidad=1;
        sigIntent();
    }
    public void atrevido(View view){
        intensidad=2;
        sigIntent();
    }
    public void sinLimites(View view){
        intensidad=3;
        sigIntent();
    }
    public void sigIntent(){
        Intent intent = new Intent(this, entradadatos.class);
        intent.putExtra("cantidadJugadores",cantidad);
        intent.putExtra("intensidad",intensidad);
        startActivity(intent);
    }
}
