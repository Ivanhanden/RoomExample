package com.example.user.roomexample.data.database;

import android.content.Context;

import com.example.user.roomexample.domain.model.Note;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    private static volatile AppDatabase singleton;
    private static final String DATABASE_NAME = "NoteDb.db";

    public abstract NoteDao noteDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (singleton == null) {
            synchronized (AppDatabase.class) {
                if (singleton == null) {
                    singleton = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return singleton;
    }

    public static void destroyInstance() {
        singleton = null;
    }
}
