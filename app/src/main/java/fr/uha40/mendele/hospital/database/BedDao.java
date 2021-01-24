package fr.uha40.mendele.hospital.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import fr.uha40.mendele.hospital.models.Bed;

@Dao
public interface BedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (Bed bed);

    @Delete
    void delete(Bed bed);
}
