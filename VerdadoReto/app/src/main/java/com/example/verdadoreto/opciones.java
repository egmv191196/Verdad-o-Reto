package com.example.verdadoreto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class opciones extends AppCompatActivity {
    Button valorar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        valorar =(Button) findViewById(R.id.BValoracion);
        if(verificarValoracion()!=false){
            valorar.setVisibility(View.GONE);
        }
    }
    public boolean verificarValoracion(){
        boolean valorado;
        try{
            SharedPreferences prefs =
                    getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
             valorado = prefs.getBoolean("bandera", false);
        }catch(Exception e){
            valorado =false;
        }
        return valorado;
    }

    public void jugar(View view) {
        Intent intent = new Intent(this, parejaogrupo.class);
        startActivity(intent);
    }

    public void informacion(View view) {
        Intent intent = new Intent(this, informacion.class);
        startActivity(intent);
    }
    public void link(View view){
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("bandera", true);
        editor.commit();
        Uri webpage = Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.katana");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }
}


