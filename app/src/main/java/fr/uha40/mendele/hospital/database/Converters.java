package fr.uha40.mendele.hospital.database;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import fr.uha40.mendele.hospital.models.BedState;

public class Converters {

    @TypeConverter
    public static BedState toBedState (String str) {
        return BedState.valueOf(str);
    }

    @TypeConverter
    public static String fromBedState (BedState bedState) {
        return bedState.toString();
    }
}
