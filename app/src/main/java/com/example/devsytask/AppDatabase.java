package com.example.devsytask;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RoomClassPrice.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract Dao_ Dao();

}
