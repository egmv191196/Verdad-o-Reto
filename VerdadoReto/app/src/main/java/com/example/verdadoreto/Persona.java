package com.example.verdadoreto;

import android.os.Parcel;
import android.os.Parcelable;

public class Persona implements Parcelable {
    String nombre;
    String sexo;
    public Persona(String nom,String sex){
        nombre=nom;
        sexo=sex;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    protected Persona(Parcel in) {
        nombre = in.readString();
        sexo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(sexo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Persona> CREATOR = new Parcelable.Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };
}

/*public class Persona  {
    String nombre;
    String sexo;
    public Persona(String nom,String sex){
        nombre=nom;
        sexo=sex;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}*/
