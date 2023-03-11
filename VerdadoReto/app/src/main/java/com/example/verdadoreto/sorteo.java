package com.example.verdadoreto;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class sorteo extends AppCompatActivity {
    int intensidad;
    boolean gay;
    TextView personaje;
    private List<Persona> arregloPersonas;
    int numero,limite;
    int giro1=0;
    CountDownTimer timer;
    AlertDialog.Builder alertDialog1;
    Button giro;
    Button reto;
    Button verdad;
    String persona1,persona2,persona1Genero,persona2Genero;
    private InterstitialAd mInterstitialAd;
    int numerodereto;
    int numerodeverdad;
    InputStream isv;
    BufferedReader readerv;
    InputStream isr;
    BufferedReader readerr;
    String linea,mensaje,generoP1,generoP2;
    int cantidadRetos,cantidadVerdad,i;
    Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sorteo);
        MobileAds.initialize(this, "ca-app-pub-8866805066482215~2432872554");
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        personaje = findViewById(R.id.personaje);
        arregloPersonas = new ArrayList<Persona>();
        arregloPersonas = (ArrayList) getIntent().getParcelableArrayListExtra("arreglo");
        intensidad= getIntent().getIntExtra("intensidad",-1);
        gay=getIntent().getBooleanExtra("homosexual",false);
        System.out.println(intensidad);
        limite = arregloPersonas.size();
        giro = (Button) findViewById(R.id.giro);
        reto = (Button) findViewById(R.id.reto);
        verdad = (Button) findViewById(R.id.verdad);
        verdad.setEnabled(false);
        reto.setEnabled(false);
        contexto=this;
        try {
            cantidadDeRyV();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void iniciarLector(){
        if(intensidad==0) {
            isv = this.getResources().openRawResource(R.raw.vsuave);
            readerv = new BufferedReader(new InputStreamReader(isv));
            isr = this.getResources().openRawResource(R.raw.rsuave);
            readerr = new BufferedReader(new InputStreamReader(isr));
        }else{
            if(intensidad==1) {
                isv = this.getResources().openRawResource(R.raw.vmoderado);
                readerv = new BufferedReader(new InputStreamReader(isv));
                isr = this.getResources().openRawResource(R.raw.rmoderado);
                readerr = new BufferedReader(new InputStreamReader(isr));
            }else {
                if(intensidad==2) {
                    isv = this.getResources().openRawResource(R.raw.vatrevido);
                    readerv = new BufferedReader(new InputStreamReader(isv));
                    isr = this.getResources().openRawResource(R.raw.ratrevido);
                    readerr = new BufferedReader(new InputStreamReader(isr));
                }else{
                    if(intensidad==3) {
                        isv = this.getResources().openRawResource(R.raw.vsinlimtes);
                        readerv = new BufferedReader(new InputStreamReader(isv));
                        isr = this.getResources().openRawResource(R.raw.rsinlimites);
                        readerr = new BufferedReader(new InputStreamReader(isr));
                    }
                }
            }
        }
    }
    public void cerrarLector() throws IOException {
        readerv.close();
        readerr.close();
        isv.close();
        isr.close();
    }
    public void elegirPersonaje(View view){
        timer = new CountDownTimer( 1253, 5) {
            // Al declarar un nuevo CountDownTimer nos obliga a
            // sobreescribir algunos de sus eventos.
            @Override
            public void onTick(long millisUntilFinished) {
                // Este método se lanza por cada lapso de tiempo
                // transcurrido,
                numero = (int) (Math.random() * limite) ;
                personaje.setText(arregloPersonas.get(numero).getNombre());
                persona1=arregloPersonas.get(numero).getNombre();
                persona1Genero=arregloPersonas.get(numero).getSexo();

            }

            @Override
            public void onFinish() {
                giro.setEnabled(false);
                verdad.setEnabled(true);
                reto.setEnabled(true);
            }
        };
        timer.start();
    }
    public void reto(View view) throws IOException {
        numerodereto = (int) (Math.random() * cantidadRetos)+1 ;
        iniciarLector();
        readerr.readLine();
        for (i=0;i<numerodereto;i++){
            generoP1 =readerr.readLine();
            generoP2 = readerr.readLine();
            mensaje=readerr.readLine();
        }
        cerrarLector();
        System.out.println("El genero de la persona 1 es "+generoP1+" El genero de la persona 2 es "+generoP2+" el reto es "+mensaje);
        System.out.println("El genero de p1 es "+persona1Genero+" y su nombre es "+persona1);
        numero = (int) (Math.random() * limite);//Obtener el nombre de la otra persona
        persona2 = arregloPersonas.get(numero).getNombre();
        persona2Genero = arregloPersonas.get(numero).getSexo();
        System.out.println("El genero de p2 es "+persona2Genero+" y su nombre es "+persona2);


        if(!generoP1.equals(persona1Genero)&&!generoP1.equals("I")){//Se verifica que el sexo de la persona castigada coincida con el sexo de la persona que debe realizar el reto.
            System.out.println("No coincide el sexo con el reto");
            while(!persona1Genero.equals(generoP1)&&!generoP1.equals("I")){
                numerodereto = (int) (Math.random() * cantidadRetos) ;
                iniciarLector();
                readerr.readLine();
                for (i=0;i<numerodereto;i++){
                    generoP1 =readerr.readLine();
                    generoP2 = readerr.readLine();
                    mensaje=readerr.readLine();
                }
                System.out.println("El genero de la persona 1 es "+generoP1+" El genero de la persona 2 es "+generoP2+" el nuevo reto es "+mensaje);
                cerrarLector();
            }
            System.out.println("El genero de la persona 1 es "+generoP1+" El genero de la persona 2 es "+generoP2+" el nuevo reto es final "+mensaje);
        }//Se verifica que el sexo de la persona castigada coincida con el sexo de la persona que debe realizar el reto fin

            if (generoP2.equals("N")) {//aqui se verifica si se necesita otra persona para llevar a cabo el reto
                persona2 = "";
            } else {
                if (generoP2.equals("I")&&!persona1.equals(persona2)) {// aqui se verifica que no se necesita que sean de sexo diferente para cumplir el reto
                    System.out.println("No importa el sexo para cumplir el reto");
                }else {
                    if ((generoP2.equals(persona2Genero)) && !persona2Genero.equals(persona1Genero) && generoP1.equals(persona1Genero)) {// se verifica que los generos de las personas
                        System.out.println(persona1 + " " + persona2 + "=" + generoP2.equals(generoP1));                                 // coincidan con los necesarios para el reto
                    } else {
                        if(persona1Genero.equals(persona2Genero)||persona1.equals(persona2)) {//Se pregunta si ambos sexos son los mismos o los nombres son iguales.
                            System.out.println("Los dos son del mismo sexo");
                            if(gay==false) {
                                while (persona1Genero.equals(persona2Genero)) {
                                    numero = (int) (Math.random() * limite);//Obtener el nombre de la otra persona
                                    persona2 = arregloPersonas.get(numero).getNombre();
                                    persona2Genero = arregloPersonas.get(numero).getSexo();
                                }
                            }else{
                                while (persona1.equals(persona2)) {
                                    numero = (int) (Math.random() * limite);//Obtener el nombre de la otra persona
                                    persona2 = arregloPersonas.get(numero).getNombre();
                                    persona2Genero = arregloPersonas.get(numero).getSexo();
                                }
                                if(!generoP2.equals(persona2Genero)){
                                    while (!generoP2.equals(persona2Genero)) {
                                        numerodereto = (int) (Math.random() * cantidadRetos) ;
                                        iniciarLector();
                                        readerr.readLine();
                                        for (i = 0; i < numerodereto; i++) {
                                            generoP1 = readerr.readLine();
                                            generoP2 = readerr.readLine();
                                            mensaje = readerr.readLine();
                                        }
                                        System.out.println("El genero de la persona 1 es " + generoP1 + " El genero de la persona 2 es " + generoP2 + " el nuevo reto es " + mensaje);
                                        cerrarLector();
                                    }
                                }

                            }
                            System.out.println("El genero de p2 es " + persona2Genero + " y su nombre es " + persona2);
                        }
                    }
                }
            }

        readerr.close();
        aviso(0);

    }
    public void verdad(View view) throws IOException {
        iniciarLector();
        readerv.readLine();
        numerodeverdad = (int) (Math.random() * cantidadVerdad)+1 ;
        for (i=0;i<numerodeverdad;i++){
            mensaje=readerv.readLine();
        }
        readerv.close();
        aviso(1);
        cerrarLector();
    }
    public void cantidadDeRyV() throws IOException {
        iniciarLector();
        if(isr!=null) {
            linea=readerr.readLine();
            cantidadRetos=Integer.parseInt(linea);
        }
        if(isv!=null) {
            linea=readerv.readLine();
            cantidadVerdad= Integer.parseInt(linea);
        }
        System.out.println("Cantidad de retos "+cantidadRetos+" cantida de verdad "+cantidadVerdad);
        cerrarLector();
    }
    public void aviso(int band){
        if(band==0){
            giro1++;
            verdad.setEnabled(false);
            reto.setEnabled(false);
            new cuadrodedialogo(contexto,"Reto",persona1+" "+mensaje+" "+ persona2);
            giro.setEnabled(true);
        }else{
            giro1++;
            verdad.setEnabled(false);
            reto.setEnabled(false);
            new cuadrodedialogo(contexto,"Verdad",persona1+" "+mensaje);
            giro.setEnabled(true);

        }
        //lanzarPublicidad();
    }
    public void lanzarPublicidad(){
        if(giro1%5==0){
            showInterstitial();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publicidad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //Toast.makeText(sorteo.this, "ya se cargo", Toast.LENGTH_SHORT).show();
                //mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //Toast.makeText(sorteo.this,
                  //      "No se ha cargado",
                    //    Toast.LENGTH_SHORT).show();
                //mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                goToNextLevel();
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Toast.makeText(this, "No se cargo la publicidadd", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        //mNextLevelButton.setEnabled(false);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.        //mLevelTextView.setText("Level " + (++mLevel));
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

}
