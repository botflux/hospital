package fr.uha40.mendele.hospital.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.uha40.mendele.hospital.models.HospitalService;

@Dao
public interface HospitalServiceDao {

    @Query("SELECT * FROM hospitalservice")
    LiveData<List<HospitalService>> getAll();

    @Query("SELECT * FROM hospitalservice WHERE id = :id")
    LiveData<HospitalService> findById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (HospitalService hospitalService);

    @Delete
    void delete(HospitalService hospitalService);
}
