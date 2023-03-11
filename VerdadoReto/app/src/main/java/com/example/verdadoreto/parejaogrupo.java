package com.example.verdadoreto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class parejaogrupo extends AppCompatActivity {
    int cantidad;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parejaogrupo);
    }
    public void pareja(View view){
        cantidad=2;
        sigIntent();
    }
    public void grupo(View view){
        cantidad=3;
        sigIntent();
    }
    public void sigIntent(){
        Intent intent = new Intent(this, nivel.class);
        intent.putExtra("personas",cantidad);
        startActivity(intent);
    }
}
