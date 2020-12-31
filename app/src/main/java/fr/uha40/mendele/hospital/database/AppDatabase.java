package fr.uha40.mendele.hospital.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import fr.uha40.mendele.hospital.models.HospitalService;

@Database(entities = {HospitalService.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;

    public static void createInstance(Context applicationContext) {
        instance = Room
                .databaseBuilder(applicationContext, AppDatabase.class, "hospital.db")
                .build();
    }

    public static AppDatabase getInstance() {
        return instance;
    }

    public abstract HospitalServiceDao hospitalServiceDao();
}
