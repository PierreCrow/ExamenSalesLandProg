package com.salesland.personas.datasource;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.salesland.personas.entities.Persona;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PersonaDAO {

    @Insert
    void InsertOnlyOne(Persona persona);
    @Insert
    void InsertMultiple(ArrayList<Persona> personas);

    @Update
    void Update(Persona persona);

    @Delete
    void Delete(Persona persona);

    @Query("DELETE  FROM Persona")
    void DeleteAll();

    @Query ("SELECT * FROM Persona WHERE id_persona = :personaID")
    Persona Select_by_ID(int personaID);

    @Query("SELECT * FROM Persona")
    List<Persona> GetAll();

    @Query( "DELETE from Persona where id_persona=:idPersona")
    void DeleteById(int idPersona);

    @Query("UPDATE Persona set Nombre=:nombre, Apellidos=:apellidos, Direccion=:direccion, RutaFoto=:rutafoto WHERE id_persona=:idPersona")
    void UpdateTarea(int idPersona, String nombre, String apellidos, String direccion, String rutafoto);



}
