package com.salesland.personas.datasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.salesland.personas.entities.Persona;


@Database(entities = { Persona.class},
        version =1 , exportSchema = false)


public abstract class AppDataBase extends RoomDatabase {


    public abstract PersonaDAO personasDAO();


}
