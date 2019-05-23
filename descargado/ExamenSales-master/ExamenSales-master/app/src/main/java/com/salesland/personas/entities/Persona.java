package com.salesland.personas.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName="Persona")
public class Persona implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id_persona")
    @SerializedName("id_persona")
    private Integer id_persona;


    @SerializedName("Nombre")
    private String Nombre;

    @SerializedName("Apellidos")
    private String Apellidos;


    @SerializedName("Direccion")
    private String Direccion;

    @SerializedName("RutaFoto")
    private String RutaFoto;


    public String getRutaFoto() {
        return RutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        RutaFoto = rutaFoto;
    }

    public Integer getId_persona() {
        return id_persona;
    }

    public void setId_persona(Integer id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }


    public Persona(String Nombre, String Apellidos, String Direccion, String RutaFoto)
    {
       // this.id_almacen=id_almacen;
      //  this.id_persona=id_persona;
        this.Nombre=Nombre;
        this.Apellidos = Apellidos;
        this.Direccion = Direccion;
        this.RutaFoto = RutaFoto;

    }



    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id_persona);
        dest.writeString(this.Nombre);
        dest.writeString(this.Apellidos);
        dest.writeString(this.Direccion);
        dest.writeString(this.RutaFoto);


    }


    public Persona(Parcel in) {
        id_persona = in.readInt();
        Nombre = in.readString();
        Apellidos = in.readString();
        Direccion = in.readString();
        RutaFoto = in.readString();

    }


    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };


}
