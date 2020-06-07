package com.example.verdadoreto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class entradadatos extends AppCompatActivity {
    boolean h=false;
    boolean m=false;
    boolean gay=false;
    Button b1;
    Button b2;
    TableLayout tl;
    private List<Persona> Nombres;
    int i=0,x=0;
    String texto;
    boolean genero;
    Persona p;
    int cantidad,intensidad;
    BufferedReader lector;
    OutputStreamWriter escritor;
    String nombreDelArchivo;
    boolean encontroArchivo=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entradadatos);
        Nombres = new ArrayList<Persona>();
        b1 = (Button) findViewById(R.id.Eliminarjugador);
        b2 = (Button) findViewById(R.id.agregarjugador);
        tl = (TableLayout) findViewById(R.id.tabla);
        cantidad = getIntent().getIntExtra("cantidadJugadores",0);
        intensidad = getIntent().getIntExtra("intensidad",0);
        try {
            generarjugadoresautomaticamente();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void abrirlector(){
        if(cantidad==2)
            nombreDelArchivo="pareja.txt";
        else
            nombreDelArchivo="grupo.txt";
        try
        {
            lector =new BufferedReader(new InputStreamReader(openFileInput(nombreDelArchivo)));
        }
        catch (Exception ex)
        {
            System.out.println("Error al abrir el fichero");
            encontroArchivo=false;
        }
    }
    public void generarjugadoresautomaticamente() throws IOException {
        abrirlector();
        if(cantidad==2){
            for (x=0;x<2;x++){
                if(encontroArchivo==true)
                    generar(lector.readLine(),lector.readLine());
                else
                    if(x==0) {
                        generar(null, "H");
                    }else{
                        generar(null,"M");
                    }
            }
            b1.setVisibility(View.INVISIBLE);
            b1.setEnabled(false);
            b2.setVisibility(View.INVISIBLE);
            b2.setEnabled(false);
        }else{
            if(encontroArchivo==true){
                int p=Integer.parseInt(lector.readLine());
               for(x=0;x<p;x++) {
                   generar(lector.readLine(), lector.readLine());
               }
            }else
                for(x=0;x<3;x++)
                    generar(null,"H");
        }
        if(lector!=null)
            lector.close();
    }
    public void generar(String nombre,String sexo){
        TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.fila, null);
        ((TextView)row.findViewById(R.id.Nombre)).setText(nombre);
        if(sexo.equals("M"))
            ((ToggleButton) row.findViewById(R.id.Genero)).setChecked(true);
        else
            ((ToggleButton) row.findViewById(R.id.Genero)).setChecked(false);
        if((i%2==0)){
            row.setBackgroundColor(Color.rgb(128,113,110));
        }
        tl.addView(row);
        tl.requestLayout();
        i++;
    }
    public void eliminar(View view){
        if(i>0){
            tl.removeViews(i-1,1);
            i--;
        }
        tl.requestLayout();
    }
    public void crear(View viw){
        generar(null,"H");
    }
    public void iniciarEscritor(){
        try
        {
            escritor=new OutputStreamWriter(openFileOutput(nombreDelArchivo, Context.MODE_PRIVATE));
        }
        catch (Exception ex)
        {
        }
    }

    public void comenzar(View view) throws IOException {
        boolean bandera=true;
        for (int i=0;i<tl.getChildCount();i++) {
            View v2 = (View) tl.getChildAt(i);
            texto = ((EditText) v2.findViewById(R.id.Nombre)).getText().toString();
            genero = ((ToggleButton) v2.findViewById(R.id.Genero)).isChecked();
            if (texto.equals("")) {
                bandera = false;
                break;
            }
            if(genero==true){
                m=true;
            }
            if(genero==false){
                h=true;
            }
        }
        if(bandera==true) {
            iniciarEscritor();
            Nombres.clear();
            if (cantidad == 3)
                escritor.write(tl.getChildCount() + "\n");
            for (int i = 0; i < tl.getChildCount(); i++) {
                View v2 = (View) tl.getChildAt(i);
                texto = ((EditText) v2.findViewById(R.id.Nombre)).getText().toString();
                if (texto.equals("")) {
                    bandera = false;
                    break;
                }
                escritor.write(texto + "\n");
                genero = ((ToggleButton) v2.findViewById(R.id.Genero)).isChecked();
                if (genero == true) {
                    p = new Persona(texto, "M");
                    escritor.write("M\n");
                } else {
                    p = new Persona(texto, "H");
                    escritor.write("H\n");
                }
                Nombres.add(p);
            }
            if (escritor != null)
                escritor.close();
            if(!m||!h){
                gay=true;
            }
            Intent intent = new Intent(this, sorteo.class);
            intent.putParcelableArrayListExtra("arreglo", (ArrayList) Nombres);
            intent.putExtra("intensidad",intensidad);
            intent.putExtra("homosexual",gay);
            startActivity(intent);
        }
        else {
            Toast toast1 =Toast.makeText(getApplicationContext(),"No dejes campos vacios", Toast.LENGTH_SHORT);
            toast1.show();
        }
    }
}
