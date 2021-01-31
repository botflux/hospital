package fr.uha40.mendele.hospital.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import fr.uha40.mendele.hospital.models.Bed;

@Dao
public interface BedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (Bed bed);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update (Bed bed);

    @Delete
    void delete(Bed bed);

    @Query("SELECT * FROM bed WHERE bedId = :id")
    LiveData<Bed> findBedById(long id);
}
