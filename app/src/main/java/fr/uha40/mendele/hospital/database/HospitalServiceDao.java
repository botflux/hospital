package fr.uha40.mendele.hospital.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.uha40.mendele.hospital.models.HospitalService;

@Dao
public interface HospitalServiceDao {

    @Query("SELECT * FROM hospitalservice")
    List<HospitalService> getAll();

    @Query("SELECT * FROM hospitalservice WHERE id = :id")
    HospitalService findById(long id);

    @Insert
    void insertAll (HospitalService... hospitalServices);
}
