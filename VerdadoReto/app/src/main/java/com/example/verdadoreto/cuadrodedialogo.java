package com.example.verdadoreto;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class cuadrodedialogo {
    public cuadrodedialogo(Context contexto,String tipo1,String mensaje1){
        final Dialog dialogo= new Dialog(contexto);
        dialogo.setCanceledOnTouchOutside(false);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
        dialogo.setContentView(R.layout.cuadrodedialogo);
        TextView tipo=(TextView) dialogo.findViewById(R.id.tipo);
        TextView mensaje=(TextView) dialogo.findViewById(R.id.mensaje);
        Button aceptar=(Button) dialogo.findViewById(R.id.aceptar);
        tipo.setText(tipo1);
        mensaje.setText(mensaje1);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });
        dialogo.show();

    }
}
