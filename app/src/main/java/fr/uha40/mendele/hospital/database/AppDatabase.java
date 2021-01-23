package fr.uha40.mendele.hospital.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import fr.uha40.mendele.hospital.models.Bed;
import fr.uha40.mendele.hospital.models.HospitalService;
import fr.uha40.mendele.hospital.models.HospitalServiceWithBeds;

@Database(entities = {HospitalService.class, Bed.class}, version = 2)
@TypeConverters({ Converters.class })
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;

    public static void createInstance(Context applicationContext) {
        instance = Room
                .databaseBuilder(applicationContext, AppDatabase.class, "hospital.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static AppDatabase getInstance() {
        return instance;
    }

    public abstract HospitalServiceDao hospitalServiceDao();
}
